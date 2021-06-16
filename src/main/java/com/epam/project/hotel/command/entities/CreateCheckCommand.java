package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * This command is designed to create a payment receipt if a room came up to the user and it passed all checks. At the end, it puts an attribute of the check object
 * into the session and deletes the attributes of check-in, check-out and whether the room is free
 */
public class CreateCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("CreateCheckCommand#execute");
        String adress = "check.jsp";
        User user = (User) req.getSession().getAttribute("user");
        Room room = (Room) req.getSession().getAttribute("room");
        Date arrival = (Date) req.getSession().getAttribute("arrival");
        Date departure = (Date) req.getSession().getAttribute("departure");
        if(req.getSession().getAttribute("request") != null){
            Factory factory = MySQLFactory.getInstance();
            RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
            Request request = (Request) req.getSession().getAttribute("request");
            String status = "USER_ACCEPTED";
            request = requestDAO.updateRequestStatus(request, status);
            req.getSession().setAttribute("request", request);
        }
        log.info("Check info:" +
                "user = " + user +
                "room = " + room +
                "arrival = " + arrival +
                "departure = " + departure);
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        Check check;
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = checkDAO.createCheck(con, user, room, arrival, departure);
            if(user.isDiscount()){
                user = userDAO.updateDiscount(con, user);
                req.getSession().setAttribute("user", user);
            }
            con.commit();
        } catch (SQLException e) {
            log.error("Problem at transaction create check");
            userDAO.rollback(con);
            throw new AppException("Cannot create check, try again");
        }
        finally {
            userDAO.close(con);
        }

        log.info("Check after creation" + check);
        req.getSession().setAttribute("check", check);
        req.getSession().removeAttribute("arrival");
        req.getSession().removeAttribute("departure");
        req.getSession().removeAttribute("dates");
        return adress;
    }
}

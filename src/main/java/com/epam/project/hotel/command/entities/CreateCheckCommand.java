package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class CreateCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
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
        Check check = checkDAO.createCheck(user, room, arrival, departure);
        req.getSession().setAttribute("check", check);
        req.getSession().removeAttribute("arrival");
        req.getSession().removeAttribute("departure");
        req.getSession().removeAttribute("dates");
        return adress;
    }
}

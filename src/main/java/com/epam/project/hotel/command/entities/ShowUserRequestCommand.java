package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class ShowUserRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowUserRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowUserRequestCommand#execute");
        String adress = "user-request.jsp";
        Factory factory = MySQLFactory.getInstance();
        RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        User user;
        Request request;
        Room room;
        Connection con = null;
        try {
            user = (User) req.getSession().getAttribute("user");
            con = DataSource.getConnection();
            request = requestDAO.findRequestByUser(con, user);
            room = roomDAO.findRoomID(con, request.getRoom_id());
            log.info("request = " + request + " room = " + room + " user = " + user);
            con.commit();
        } catch (SQLException e) {
            log.error("Problem at transaction show user request", e);
            roomDAO.rollback(con);
            throw new DBException("Cannot find request, try again");
        }
        finally {
            roomDAO.close(con);
        }
        req.getSession().setAttribute("request", request);
        req.getSession().setAttribute("room", room);
        req.getSession().setAttribute("arrival", request.getArrival());
        req.getSession().setAttribute("departure", request.getDepartment());
        return adress;
    }
}

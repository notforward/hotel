package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.RoomDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This command allows the manager to create a response
 * to the user's request and returns a request at the end.
 */
public class ConfirmRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger(ConfirmRequestCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ConfirmRequestCommand#execute");
        String adress = "request-admin.jsp";
        Request request = (Request) req.getSession().getAttribute("request");
        log.info("request = " + request);
        String accepted = "MANAGER_ACCEPTED";
        Factory factory = MySQLFactory.getInstance();
        RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        log.info("requestDAO = " + requestDAO + " roomDAO =" + roomDAO);
        Connection con = null;
        Room room;
        try {
            con = DataSource.getConnection();
            room = (Room) req.getSession().getAttribute("available_room");
            log.info("room = " + room);
            request = requestDAO.createResponse(con, request, room);
            request = requestDAO.updateRequestStatus(con, request, accepted);
            con.commit();
        } catch (SQLException e) {
            roomDAO.rollback(con);
            log.error("Problem at transaction confirm request command", e);
            throw new AppException("Cannot create response, try again");
        }
        finally {
            roomDAO.close(con);
        }
        req.getSession().setAttribute("request" , request);
        return adress;
    }
}
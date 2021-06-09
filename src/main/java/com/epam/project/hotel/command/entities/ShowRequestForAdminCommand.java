package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.RoomDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowRequestForAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRequestForAdminCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowRequestForAdminCommand#execute");
        String adress = "show-request.jsp";
        int id = Integer.parseInt(req.getParameter("id"));
        log.info("id = " + id);
        Factory factory;
        RoomDAO roomDAO = null;
        RequestDAO requestDAO;
        List<Room> rooms;
        Request request;
        Connection con = null;
        try {
            factory = MySQLFactory.getInstance();
            roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
            requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
            con = DataSource.getConnection();
            rooms = roomDAO.findAllRooms(con);
            request = requestDAO.findRequestByID(con, id);
            con.commit();
        } catch (SQLException e) {
            log.info("Problem at transaction showRequestForAdmin");
            roomDAO.rollback(con);
            throw new DBException("Cannot show current request, try again");
        } finally {
            if (roomDAO != null) {
                roomDAO.close(con);
            }
        }
        req.getSession().setAttribute("rooms", rooms);
        req.getSession().setAttribute("request", request);
        return adress;
    }
}

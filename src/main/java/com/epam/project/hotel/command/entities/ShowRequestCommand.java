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

public class ShowRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowRequestCommand#execute");
        String adress = "request-admin.jsp";
        int request_id = Integer.parseInt(req.getParameter("request_id"));
        Factory factory = MySQLFactory.getInstance();;
        RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");;
        Connection con = null;
        Request request;
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        List<Room> rooms;
        try {
            con = DataSource.getConnection();
            request = requestDAO.findRequestByID(con, request_id);
            if(request.getRoom_id() != 0){
                Room room = roomDAO.findRoomID(con, request.getRoom_id());
                req.getSession().setAttribute("available_room", room);
            }
            log.info("request after finding = " + request);
            rooms = roomDAO.findAllRooms(con);
            log.info("rooms after finding = " + rooms);
            con.commit();
        } catch (DBException | SQLException e) {
            requestDAO.rollback(con);
            log.error("Problem at transaction showRequestCommand", e);
            throw new DBException("Cannot show request, try again");
        }
        finally {
            requestDAO.close(con);
        }
        req.getSession().setAttribute("request", request);
        req.getSession().setAttribute("rooms", rooms);
        return adress;
    }
}

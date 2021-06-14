package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.dao.Factory;
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

public class CheckDateAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(CheckDateAdminCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("CheckDateAdminCommand#execute");
        String adress = "request-admin.jsp";
        Request request = (Request) req.getSession().getAttribute("request");
        log.info("request = " + request);
        int room_id = Integer.parseInt(req.getParameter("selected_room"));
        log.info("room id = " + room_id);
        Factory factory = MySQLFactory.getInstance();
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        Room room;
        Connection con = null;
        boolean available;
        try {
            con = DataSource.getConnection();
            room = roomDAO.findRoomID(con, room_id);
            log.info("room after finding = " + room);
            available = checkDAO.checkCreation(con, request.getArrival(), request.getDepartment(), room.getId());
            log.info("available = " + available);
            con.commit();
        } catch (AppException | SQLException e) {
            roomDAO.rollback(con);
            log.error("Problem at transaction command checking date", e);
            throw new AppException("Cannot check dates, try again");
        }
        finally {
            roomDAO.close(con);
        }
        if(available){
            req.getSession().setAttribute("available_room", room);
        }
        req.getSession().setAttribute("available", available);
        return adress;
    }
}

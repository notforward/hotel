package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRoomsCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRoomsCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowRoomsCommand#execute");
        String adress = "rooms.jsp";
        Factory factory = MySQLFactory.getInstance();
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        List<Room> rooms = roomDAO.findAllRooms();
        log.info("Rooms = " + rooms);
        req.getSession().setAttribute("rooms", rooms);
        return adress;
    }
}

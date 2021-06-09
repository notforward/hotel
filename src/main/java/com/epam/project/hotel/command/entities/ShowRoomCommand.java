package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRoomCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRoomCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowRoomCommand#execute");
        String adress = "room.jsp";
        int id = Integer.parseInt(req.getParameter("id"));
        log.info("id = " + id);
        Factory factory = MySQLFactory.getInstance();
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        Room room = roomDAO.findRoomID(id);
        log.info("Room = " + room);
        req.getSession().setAttribute("room", room);
        return adress;
    }
}

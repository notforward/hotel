package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRoomCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditRoomCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("EditRoomCommand#execute");
        String adress = "room.jsp";
        Room room = (Room) req.getSession().getAttribute("room");
        String status = req.getParameter("status");
        log.info("Room = " + room + " status = " + status);
        RoomDAO roomDAO = new RoomDAO();
        room = roomDAO.updateRoomStatus(room, status);
        req.getSession().setAttribute("room", room);
        req.getSession().setAttribute("editRoomResult", true);
        return adress;
    }
}

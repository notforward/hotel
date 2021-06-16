package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRoomCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRoomCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ShowRoomCommand#execute");
        String adress = "room.jsp";
        int id = Integer.parseInt(req.getParameter("id"));
        log.info("id = " + id);
        Factory factory = MySQLFactory.getInstance();
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        Room room = roomDAO.findRoomID(id);
        if(room == null){
            throw new AppException("Cannot find selected room, try again");
        }
        log.info("Room = " + room);
        User user = (User) req.getSession().getAttribute("user");
        if(user.isDiscount()){
            room.setPrice((int) (room.getPrice() * 0.8));
        }
        req.getSession().setAttribute("room", room);
        return adress;
    }
}

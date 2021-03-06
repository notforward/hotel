package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * This command is a check for the availability of a room for a regular user and returns
 * the dates on which the room is recorded and whether it is available
 */
public class CheckDateCommand implements Command {
    private static final Logger log = LogManager.getLogger(CheckDateCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("CheckDateCommand#execute");
        String adress = "book.jsp";
        boolean available;

        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");

        Date arrival = Date.valueOf(req.getParameter("arrival"));
        Date department = Date.valueOf(req.getParameter("department"));
        Room room = (Room) req.getSession().getAttribute("room");
        log.info("arrival = " + arrival + " departure = " + department + " room = " + room);
        if(arrival == null || department == null || room == null){
            throw new AppException("Cannot resolve params, try again");
        }
        available = checkDAO.checkCreation(arrival, department, room.getId());
        req.getSession().setAttribute("dates", available);
        req.getSession().setAttribute("arrival", arrival);
        req.getSession().setAttribute("departure", department);
        return adress;
    }
}

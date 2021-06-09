package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

public class CreateCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("CreateCheckCommand#execute");
        String adress = "check.jsp";
        User user = ((Optional<User>) req.getSession().getAttribute("user"))
                .get();
        Room room = (Room) req.getSession().getAttribute("room");
        Date arrival = (Date) req.getSession().getAttribute("arrival");
        Date departure = (Date) req.getSession().getAttribute("departure");
        log.info("Check info:" +
                "user = " + user +
                "room = " + room +
                "arrival = " + arrival +
                "departure = " + departure);

        CheckDAO checkDAO = new CheckDAO();
        Check check = checkDAO.createCheck(user, room, arrival, departure);
        req.getSession().setAttribute("check", check);
        req.getSession().removeAttribute("arrival");
        req.getSession().removeAttribute("departure");
        req.getSession().removeAttribute("dates");
        return adress;
    }
}

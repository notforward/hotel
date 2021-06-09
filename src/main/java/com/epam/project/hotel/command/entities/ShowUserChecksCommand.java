package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowUserChecksCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowUserChecksCommand#execute");
        String adress = "checks.jsp";
        User user = ((Optional<User>) (req.getSession().getAttribute("user")))
                .get();
        CheckDAO checkDAO = new CheckDAO();
        List<Check> checks = checkDAO.findAllUserChecks(user);
        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms = roomDAO.findAllRooms();
        req.getSession().setAttribute("checks", checks);
        req.getSession().setAttribute("rooms", rooms);
        return adress;
    }
}

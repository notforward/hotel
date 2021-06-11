package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class ShowUserCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowUserCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowUserCheckCommand#execute");
        String adress = "check.jsp";
        int id = Integer.parseInt(req.getParameter("id"));
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        Check check;
        Room room;
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = checkDAO.findCheckByID(con, id);
            room = roomDAO.findRoomID(con, check.getRoom_id());
        } catch (DBException e) {
            throw new DBException("Cannot show check, please try again");
        }
        finally {
            checkDAO.close(con);
        }
        log.info("check = " + check + "room = " + room);
        req.getSession().setAttribute("check", check);
        req.getSession().setAttribute("room", room);
        return adress;
    }
}

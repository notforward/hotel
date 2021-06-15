package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The command allows using the pagination principle to view all available rooms,
 * sort them or select an existing one
 */
public class ShowRoomsCommand implements Command {
    private static final int shift = 0;
    private static final Logger log = LogManager.getLogger(ShowRoomsCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ShowRoomsCommand#execute");
        String adress = "rooms.jsp";
        int page;
        if(req.getParameter("page") == null){
            page = 1;
        } else {
            page = Integer.parseInt(
                    req.getParameter("page"));
        }
        log.info("page = " + page);
        int pageSize = 3;

        Factory factory = MySQLFactory.getInstance();
        RoomDAO roomDAO = (RoomDAO) factory.getDAO("RoomDAO");
        List<Room> rooms;
        String sortBy = String.valueOf(
                req.getSession().getAttribute("sortBy"));
        if(req.getSession().getAttribute("sortBy") == null){
            log.info("sortby == null");
            sortBy = "price";
        }
        log.info("sort by=" + sortBy);
        rooms = roomDAO.findRooms((page - 1) * pageSize, pageSize, sortBy);
        int roomsSize = roomDAO.findRoomsSize();
        int pages = (int) Math.ceil(roomsSize * 1.0 / pageSize);
        int minPagePossible = Math.max(page - shift, 1);
        int maxPagePossible = Math.min(page + shift, pages);
        log.info("Rooms = " + rooms +
            " pages = " + pages + " room size = " + roomsSize
                + " minPossible" + minPagePossible + " max possible" + maxPagePossible);

        req.setAttribute("rooms", rooms);
        req.setAttribute("pages", pages);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("minPossiblePage", minPagePossible);
        req.setAttribute("maxPossiblePage", maxPagePossible);
        req.getSession().setAttribute("rooms", rooms);
        return adress;
    }
}

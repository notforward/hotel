package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * A team that implements the pagination principle and
 * allows the manager to view all new requests
 */
public class ShowRequestsCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRequestsCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ShowRequestsCommand#execute");
        String adress = "requests.jsp";
        int page = Integer.parseInt(
                req.getParameter("page"));
        int pageSize = 6;
        log.info("page = " + page);

        Factory factory = MySQLFactory.getInstance();
        RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
        List<Request> requests = requestDAO.findRequests((page - 1) * pageSize, pageSize);

        int roomsSize = requestDAO.findRequestsSize();
        int pages = (int) Math.ceil(roomsSize * 1.0 / pageSize);
        int minPagePossible = Math.max(page, 1);
        int maxPagePossible = Math.min(page, pages);
        log.info("Requests = " + requests +
                " pages = " + pages + " room size = " + roomsSize
                + " minPossible" + minPagePossible + " max possible" + maxPagePossible);

        req.setAttribute("requests", requests);
        req.setAttribute("pages", pages);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("minPossiblePage", minPagePossible);
        req.setAttribute("maxPossiblePage", maxPagePossible);
        req.getSession().setAttribute("requests", requests);
        req.getSession().removeAttribute("available");
        return adress;
    }
}

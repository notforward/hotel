package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserChecksCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ShowUserChecksCommand#execute");
        String adress = "checks.jsp";
        int page = Integer.parseInt(
                req.getParameter("page"));
        int pageSize = 6;
        log.info("page = " + page);

        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        User user = (User) req.getSession().getAttribute("user");
        List<Check> checks = checkDAO.findChecks((page - 1) * pageSize, pageSize, user.getId());
        if(checks.size() == 0){
            throw new AppException("You have not any checks, book room to get one!");
        }
        int roomsSize = checkDAO.findChecksSize();
        int pages = (int) Math.ceil(roomsSize * 1.0 / pageSize);
        int minPagePossible = Math.max(page, 1);
        int maxPagePossible = Math.min(page, pages);
        log.info("Checks = " + checks +
                " pages = " + pages + " room size = " + roomsSize
                + " minPossible" + minPagePossible + " max possible" + maxPagePossible);
        req.setAttribute("checks", checks);
        req.setAttribute("pages", pages);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("minPossiblePage", minPagePossible);
        req.setAttribute("maxPossiblePage", maxPagePossible);
        req.getSession().setAttribute("checks", checks);
        return adress;
    }
}

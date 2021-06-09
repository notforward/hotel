package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.RequestDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRequestsCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowRequestsCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowRequestsCommand#execute");
        String adress = "admin.jsp";
        RequestDAO requestDAO = new RequestDAO();
        List<Request> requests = requestDAO.findAllRequests();
        log.info("requests = " + requests);
        req.getSession().setAttribute("requests", requests);
        return adress;
    }
}

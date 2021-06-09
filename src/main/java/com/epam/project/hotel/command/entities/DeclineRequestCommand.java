package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.RequestDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeclineRequestCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("DeclineRequestCommand#execute");
        String adress = "show-request.jsp";
        Request request = (Request) req.getSession().getAttribute("request");
        log.info("request = " + request);
        Factory factory = MySQLFactory.getInstance();
        RequestDAO requestDAO = (RequestDAO) factory.getDAO("RequestDAO");
        String declined = "MANAGER_DECLINED";
        request = requestDAO.updateRequestStatus(request, declined);
        req.getSession().setAttribute("request" , request);
        return adress;
    }
}

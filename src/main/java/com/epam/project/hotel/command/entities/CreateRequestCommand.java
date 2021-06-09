package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.RequestDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Optional;

public class CreateRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("CreateRequestCommand#execute");
        String adress = "request.jsp";
        int size = Integer.parseInt(req.getParameter("size"));
        String room_class = req.getParameter("class");
        Date arrival = Date.valueOf(req.getParameter("arrival"));
        Date department = Date.valueOf(req.getParameter("department"));
        User user = ((Optional<User>) req.getSession().getAttribute("user")).get();
        RequestDAO requestDAO = new RequestDAO();
        Request request = requestDAO.createRequest(user, size, room_class, arrival, department);
        if(request != null){
            req.getSession().setAttribute("requestDone", true);
        }
        req.getSession().setAttribute("request", request);
        return adress;
    }
}

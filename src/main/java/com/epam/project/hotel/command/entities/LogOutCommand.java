package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogOutCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("LogOutCommand#execute");
        if(req.getSession().getAttribute("user") != null){
            req.getSession().removeAttribute("user");
            log.info("Session user after log out = " + req.getSession().getAttribute("user"));
        }
        return "index.jsp";
    }
}

package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple command that puts the "locale" attribute in the session, which is responsible for the interface language
 */
public class SelectLocaleCommand implements Command {
    private static final Logger log = LogManager.getLogger(SelectLocaleCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("SelectLocaleCommand#execute");
        req.getSession().setAttribute("locale", req.getParameter("locale"));
        return "index.jsp";
    }
}

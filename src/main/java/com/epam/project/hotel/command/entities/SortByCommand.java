package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SortByCommand implements Command {
    private static final Logger log = LogManager.getLogger(SortByCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("SortByPriceCommand#execute");
        String sortBy = req.getParameter("orderBy");
        req.getSession().setAttribute("sortBy", sortBy);
        return new ShowRoomsCommand().execute(req, resp);
    }
}

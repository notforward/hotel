package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowProfileCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("ShowProfileCommand#execute");
        User user = (User) req.getSession().getAttribute("user");
        if(user == null){
            return "index.jsp";
        }
        return "profile.jsp";
    }
}

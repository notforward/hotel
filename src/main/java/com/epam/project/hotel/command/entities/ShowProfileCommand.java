package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple command that allows you to open the available information about the user received from the session. If the user is not found, it returns an error.
 */
public class ShowProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowProfileCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("ShowProfileCommand#execute");
        String adress = "profile.jsp";
        User user = (User) req.getSession().getAttribute("user");
        log.info("User = " + user);
        if(user == null){
            throw new AppException("You are not logged, try to log in");
        }
        return adress;
    }
}

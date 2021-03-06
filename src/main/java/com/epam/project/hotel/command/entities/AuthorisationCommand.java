package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This command is designed to work out situations of user authorization
 * by putting the User attribute and today's date in the session.
 */
public class AuthorisationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AuthorisationCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        logger.info("AuthorisationCommand#execute");
        String adress = "profile.jsp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if("".equals(login) || "".equals(password)){
            throw new AppException("Fill all data spaces, please");
        }
        logger.info("login = " + login + " password = " + password);
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        User user = userDAO.findUserLOG(login);
        if(user == null){
            throw new AppException("User did not found, try again"   );
        }
        password = userDAO.hashPass(password);
        logger.info("password after hashing = " + password);
        if(!user.getPassword().equals(password)){
            throw new AppException("Wrong password, try again");
        }
        logger.info("User = " + user);
        req.getSession().setAttribute("user", user);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        logger.info("Today = " + date);
        req.getSession().setAttribute("today", date);
        return adress;
    }
}

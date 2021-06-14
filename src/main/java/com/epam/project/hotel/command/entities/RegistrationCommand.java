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

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegistrationCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("RegistrationCommand#execute");
        String adress = "profile.jsp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        log.info("Creation of user with params: login = " + login + " pass = " + password + " email = " + email);
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        User user = userDAO.createUser(login, password, email);
        log.info("user = " + user);
        req.getSession().setAttribute("user", user);
        return adress;
    }
}

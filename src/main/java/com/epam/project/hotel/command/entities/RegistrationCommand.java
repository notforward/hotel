package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegistrationCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("RegistrationCommand#execute");
        String adress = "profile.jsp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if("".equals(login) || "".equals(password) || "".equals(email)){
            throw new DBException("Fill all data spaces, please");
        }

        UserDAO userDAO = new UserDAO();
        Optional<User> user;
        try {
            user = userDAO.createUser(login, password, email);
        }
        catch (DBException e) {
            log.error("User already exist error");
            throw new DBException("User already exist, try to login", e);
        }
        log.info("user = " + user);
        req.getSession().setAttribute("user", user);
        return adress;
    }
}

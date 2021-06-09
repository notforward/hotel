package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.RoomDAO;
import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthorisationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AuthorisationCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        logger.info("AuthorisationCommand#execute");
        String adress = "profile.jsp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if("".equals(login) || "".equals(password)){
            throw new DBException("Fill all data spaces, please");
        }
        logger.info("login = " + login);
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        Optional<User> user = userDAO.findUserLOG(login);
        if(!user.isPresent()){
            throw new DBException("User did not found, try again"   );
        }
        if(!user.get().getPassword().equals(password)){
            throw new DBException("Wrong password, try again");
        }
        logger.info("User = " + user.get());
        req.getSession().setAttribute("user", user);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        logger.info("Today = " + date);
        req.getSession().setAttribute("today", date);
        return adress;
    }
}

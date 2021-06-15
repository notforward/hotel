package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.entities.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A command that allows a user to pay a check in a simple manner and returns a new check object for payment to the session
 */
public class PayCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(PayCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        log.info("PayCheckCommand#execute");
        String adress = "check.jsp";
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        Check check = (Check) req.getSession().getAttribute("check");
        log.info("Check = " + check);
        check = checkDAO.updateCheckStatus(check, "PAYED");
        log.info("Check after updating status = " + check);
        req.getSession().setAttribute("check", check);
        return adress;
    }
}

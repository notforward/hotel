package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.CheckDAO;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayCheckCommand implements Command {
    private static final Logger log = LogManager.getLogger(PayCheckCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("PayCheckCommand#execute");
        String adress = "check.jsp";
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        Check check = (Check) req.getSession().getAttribute("check");
        check = checkDAO.updateCheckStatus(check, "PAYED");
        log.info("Check = " + check);
        req.getSession().setAttribute("check", check);
        return adress;
    }
}

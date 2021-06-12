package com.epam.project.hotel.controller;



import com.epam.project.hotel.command.entities.AuthorisationCommand;
import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.command.CommandContainer;
import com.epam.project.hotel.sql.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="controller", urlPatterns = {"/controller"})
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthorisationCommand.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("#doGet");
        String adress = "error.jsp";
        String name = req.getParameter("command");
        logger.info("Command = " + name);
        Command command = CommandContainer.getCommand(name);
        try{
            adress = command.execute(req, resp);
        }
        catch (DBException e) {
            logger.error("Problem at doGet", e);
            req.getSession().setAttribute("error", e);
        }
        logger.info("forwarded to " + adress);
        if(adress.equals("index.jsp")){
            req.getRequestDispatcher(adress).forward(req, resp);
        }
        // "/WEB-INF/jsp/" + adress
        req.getRequestDispatcher(adress).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("#doPost");
        String adress = "error.jsp";
        String name = req.getParameter("command");
        logger.info("Command = " + name);
        Command command = CommandContainer.getCommand(name);
        try{
            adress = command.execute(req, resp);
        }
        catch (DBException e) {
            logger.error("Problem at doPost", e);
            req.getSession().setAttribute("error", e);
        }
        logger.info("redirected to " + adress);
        if(adress.equals("index.jsp")){
            resp.sendRedirect(adress);
        }
        // "/WEB-INF/jsp/" + adress
        resp.sendRedirect(adress);
    }
}

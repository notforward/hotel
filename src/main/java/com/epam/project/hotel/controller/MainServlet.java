package com.epam.project.hotel.controller;



import com.epam.project.hotel.command.entities.AuthorisationCommand;
import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.command.CommandContainer;
import com.epam.project.hotel.sql.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The main controller of the application, which helps to use commands to move to business logic. Implements the PRG pattern
 */
@WebServlet(name="controller", urlPatterns = {"/controller"})
public class MainServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AuthorisationCommand.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("#doGet");
        String adress = "error.jsp";
        String name = req.getParameter("command");
        log.info("Command = " + name);
        Command command = CommandContainer.getCommand(name);
        try{
            adress = command.execute(req, resp);
        }
        catch (AppException e) {
            log.error("Problem at doGet", e);
            req.getSession().setAttribute("error", e);
        }
        log.info("forwarded to " + adress);
        if(adress.equals("index.jsp")){
            req.getRequestDispatcher(adress).forward(req, resp);
        }
        req.getRequestDispatcher(adress).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("#doPost");
        String adress = "error.jsp";
        String name = req.getParameter("command");
        log.info("Command = " + name);
        Command command = CommandContainer.getCommand(name);
        try{
            adress = command.execute(req, resp);
        }
        catch (AppException e) {
            log.error("Problem at doPost", e);
            req.getSession().setAttribute("error", e);
        }
        log.info("redirected to " + adress);
        if(adress.equals("index.jsp")){
            resp.sendRedirect(adress);
        }
        resp.sendRedirect(adress);
    }
}

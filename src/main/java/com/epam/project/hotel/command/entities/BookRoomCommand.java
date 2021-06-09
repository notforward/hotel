package com.epam.project.hotel.command.entities;

import com.epam.project.hotel.command.Command;
import com.epam.project.hotel.sql.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookRoomCommand implements Command {
    private static final Logger log = LogManager.getLogger(BookRoomCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        log.info("BookRoomCommand#execute");
        String adress = "book.jsp";
        return adress;
    }
}

package com.epam.project.hotel.command;

import com.epam.project.hotel.sql.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException;
}

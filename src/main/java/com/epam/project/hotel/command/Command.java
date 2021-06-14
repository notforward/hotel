package com.epam.project.hotel.command;

import com.epam.project.hotel.sql.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException;
}

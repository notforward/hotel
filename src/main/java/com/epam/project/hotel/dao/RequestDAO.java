package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

public interface RequestDAO extends EntityDAO{
    String INSERT_REQUEST = "INSERT INTO " +
            "request(user_id, request_size, request_class, request_arrival, request_department, request_status)" +
            " VALUES (?, ?, ?, ?, ?, 'CREATED')";
    String SELECT_REQUEST = "SELECT * FROM request WHERE request_id = ?";
    String SELECT_ALL_REQUESTS = "SELECT * FROM request";

    List<Request> findAllRequests() throws DBException;

    Request createRequest(User user, int size, String room_class, Date arrival, Date department) throws DBException;

    Request findRequestByID(Connection con, int id) throws DBException;

    Request extractRequest(ResultSet rs) throws DBException;
}

package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
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
    String UPDATE_REQUEST_STATUS = "UPDATE request SET request_status = ? WHERE request_id = ?";
    String INSERT_RESPONSE = "INSERT INTO" +
            " response VALUES (?, ?)";
    String SELECT_RESPONSE = "SELECT * FROM response WHERE response_request_id = ?";
    String SELECT_REQUEST_BY_USER_ID = "SELECT * FROM request WHERE user_id = ? ORDER BY request_id DESC LIMIT 1";
    String SELECT_REQUESTS = "SELECT * FROM request LIMIT ? OFFSET ?";
    String FIND_SIZE = "SELECT COUNT(*) FROM request";

    List<Request> findAllRequests() throws DBException;

    List<Request> findRequests(int offset, int limit) throws DBException;

    int findRequestsSize() throws DBException;

    Request createRequest(User user, int size, String room_class, Date arrival, Date department) throws DBException;

    Request findRequestByID(Connection con, int id) throws DBException;

    Request findRequestByUser(Connection con, User user) throws DBException;

    Request extractRequest(ResultSet rs) throws DBException;

    Request createResponse(Connection con, Request request, Room room) throws DBException;

    Request extractResponse(Request request, ResultSet rs) throws DBException;

    Request updateRequestStatus(Request request, String status) throws DBException;
    Request updateRequestStatus(Connection con, Request request, String status) throws DBException;
}

package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Request;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO implements com.epam.project.hotel.dao.RequestDAO {
    private static final Logger log = LogManager.getLogger(RequestDAO.class);
    protected RequestDAO(){

    }
    @Override
    public List<Request> findAllRequests() throws DBException {
        log.info("#findAllRequests");
        Connection con = null;
        Statement st;
        ResultSet rs;
        List<Request> requests;
        try {
            con = DataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SELECT_ALL_REQUESTS);
            requests = new ArrayList<>();
            while(rs.next()){
                requests.add(extractRequest(rs));
            }
            log.info("Requests = " + requests);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.info("Problem at findAllRequests");
            throw new DBException("Cannot find all requests, please try again");
        }
        finally {
            close(con);
        }
        return requests;
    }

    @Override
    public Request createRequest(User user , int size, String room_class, Date arrival, Date department) throws DBException {
        log.info("#createRequest size = " + size + " class = " + room_class +
                " arrival = " + arrival + " department = " + department + " user = " + user);
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;
        Request request = null;
        try {
            con = DataSource.getConnection();
            ps = con.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getId());
            ps.setInt(2, size);
            ps.setString(3, room_class);
            ps.setDate(4, arrival);
            ps.setDate(5, department);
            log.info("ps = " + ps);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                int request_id = rs.getInt(1);
                log.info("request id = " + request_id);
                request = findRequestByID(con, request_id);
            }
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem at createRequest");
            throw new DBException("Cannot create request, please try again", e);
        }
        finally {
            close(con);
        }
        return request;
    }

    @Override
    public Request findRequestByID(Connection con, int id) throws DBException {
        log.info("#findRequestByID");
        PreparedStatement ps;
        ResultSet rs;
        Request request = null;
        try {
            ps = con.prepareStatement(SELECT_REQUEST);
            int k = 1;
            ps.setInt(k, id);
            log.info("ps select request = " + ps);
            rs = ps.executeQuery();
            if(rs.next()){
                request = extractRequest(rs);
            }
            ps = con.prepareStatement(SELECT_RESPONSE);
            ps.setInt(k, id);
            log.info("ps select response = " + ps);
            rs = ps.executeQuery();
            if(rs.next() && request != null){
                request = extractResponse(request, rs);
                log.info("request after extracting response = " + request);
            }
        } catch (SQLException e) {
            log.error("Problem at findRequestByID");
            throw new DBException("Cannot create request, please try again", e);
        }
        return request;
    }

    @Override
    public Request findRequestByUser(Connection con, User user) throws DBException {
        log.info("#findRequestByUser , user = " + user);
        PreparedStatement ps;
        ResultSet rs;
        Request request = null;
        try {
            ps = con.prepareStatement(SELECT_REQUEST_BY_USER_ID);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                request = extractRequest(rs);
                String status = request.getStatus();
                if(status.equals("MANAGER_ACCEPTED")){
                    ps = con.prepareStatement(SELECT_RESPONSE);
                    ps.setInt(1, request.getRequest_id());
                    log.info("ps = " + ps);
                    rs = ps.executeQuery();
                    if(rs.next()){
                        request = extractResponse(request, rs);
                    }
                }
                log.info("request = " + request);
            }
        } catch (SQLException e) {
            log.error("Problem at findRequestByUser");
            throw new DBException("Cannot find request, try again", e);
        }
        return request;
    }

    @Override
    public Request extractRequest(ResultSet rs) throws DBException {
        log.info("#extractRequest");
        Request request = new Request();
        try {
            request.setRequest_id(rs.getInt("request_id"));
            request.setUser_id(rs.getInt("user_id"));
            request.setSize(rs.getInt("request_size"));
            request.setRoom_class(rs.getString("request_class"));
            request.setArrival(rs.getDate("request_arrival"));
            request.setDepartment(rs.getDate("request_department"));
            request.setStatus(rs.getString("request_status"));
        } catch (SQLException e) {
            log.error("Problem at extractRequest");
            throw new DBException("Cannot create request, please try again", e);
        }
        return request;
    }

    @Override
    public Request createResponse(Connection con, Request request, Room room) throws DBException {
        log.info("#createResponse request = " + request + " room = " + room);
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(INSERT_RESPONSE);
            int k = 1;
            ps.setInt(k++, request.getRequest_id());
            ps.setInt(k, room.getId());
            log.info("ps = " + ps);
            ps.executeUpdate();
            request = findRequestByID(con, request.getRequest_id());
            log.info("request after creating response = " + request);
        } catch (SQLException e) {
            log.error("Problem at createResponse");
            throw new DBException("Cannot create response, try again", e);
        }
        return request;
    }

    @Override
    public Request extractResponse(Request request, ResultSet rs) throws DBException {
        log.info("#extractResponse req = " + request);
        try {
            request.setRoom_id(rs.getInt("room_id"));
        } catch (SQLException e) {
            log.error("Problem at extract Response", e);
            throw new DBException("Cannot extract response, try again");
        }
        return request;
    }

    @Override
    public Request updateRequestStatus(Request request, String status) throws DBException {
        Connection con = null;
        try {
            con = DataSource.getConnection();
            request = updateRequestStatus(con, request, status);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem at updatingRequestStatus");
            throw new DBException("Cannot update request status, try again");
        }
        finally {
            close(con);
        }
        return request;
    }

    @Override
    public Request updateRequestStatus(Connection con, Request request, String status) throws DBException{
        log.info("updateRequestStatus request = " + request + " status = " + status);
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(UPDATE_REQUEST_STATUS);
            int k = 1;
            ps.setString(k++, status);
            ps.setInt(k, request.getRequest_id());
            log.info("ps = " + ps);
            ps.executeUpdate();
            request = findRequestByID(con, request.getRequest_id());
            log.info("request after update = " + request);
        } catch (DBException | SQLException e) {
            log.error("Problem at updateRequestStatus");
            throw new DBException("Cannot update request status, try again");
        }
        return request;
    }

    @Override
    public void rollback(Connection con) {
        if(con != null){
            try{
                con.rollback();
            } catch (SQLException e) {
                log.info("Cannot close connection", e);
            }
        }
    }

    @Override
    public void close(AutoCloseable ac) {
        if(ac != null){
            try {
                ac.close();
            } catch (Exception e) {
                log.info("Cannot close auto closeable", e);
            }
        }
    }
}

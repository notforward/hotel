package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface    CheckDAO extends EntityDAO {

    String INSERT_CHECK = "INSERT INTO payment_check(user_id, room_id, room_in, room_out, check_price, check_status, check_creation, check_terminate)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String INSPECT_CHECK = "SELECT * FROM payment_check WHERE payment_check.room_id = ?" +
            " AND payment_check.check_status != 'TERMINATED'";
    String SELECT_CHECK_BY_ID = "SELECT * FROM payment_check WHERE payment_check.check_id = ?";
    String UPDATE_CHECK_STATUS = "UPDATE payment_check SET check_status=? WHERE check_id=?";
    String SELECT_ALL_CHECKS = "SELECT * FROM payment_check";
    String SELECT_ROOMS = "SELECT * FROM payment_check WHERE payment_check.user_id = ? LIMIT ? OFFSET ?";
    String FIND_SIZE = "SELECT COUNT(*) FROM payment_check";

    Check findCheckByID(int id) throws DBException;
    Check findCheckByID(Connection con, int id) throws DBException;

    Check updateCheckStatus(Check check, String status) throws DBException;
    Check updateCheckStatus(Connection con, Check check, String status) throws DBException;

    List<Check> findAllChecks(Connection con) throws DBException;
    List<Check> findChecks(int offset, int limit, int user_id) throws DBException;

    int findChecksSize() throws DBException;

    Boolean checkCreation(Date arrival, Date department, int id) throws DBException;
    Boolean checkCreation(Connection con, Date arrival, Date department, int id) throws DBException;

    Check createCheck(User user, Room room, Date arrival, Date departure) throws DBException;
    Check createCheck(Connection con, User user, Room room, Date arrival, Date departure) throws DBException;

}

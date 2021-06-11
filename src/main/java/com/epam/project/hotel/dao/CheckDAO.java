package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface CheckDAO extends EntityDAO {

    String INSERT_CHECK = "INSERT INTO payment_check(user_id, room_id, room_in, room_out, check_price, check_status) VALUES (?, ?, ?, ?, ?, ?)";
    String INSPECT_CHECK = "SELECT * FROM payment_check WHERE payment_check.room_id = ?" +
            " AND payment_check.check_status != 'SENT'";
    String SELECT_USER_BY_ID = "SELECT * FROM payment_check WHERE payment_check.user_id = ?";
    String SELECT_CHECK_BY_ID = "SELECT * FROM payment_check WHERE payment_check.check_id = ?";
    String UPDATE_CHECK_STATUS = "UPDATE payment_check SET check_status='PAYED' WHERE check_id=?";
    Check findCheckByID(int id) throws DBException;
    Check findCheckByID(Connection con, int id) throws DBException;

    Check updateCheckStatus(Check check) throws DBException;

    List<Check> findAllUserChecks(User user) throws DBException;

    Boolean checkCreation(Date arrival, Date department, int id) throws DBException;
    Boolean checkCreation(Connection con, Date arrival, Date department, int id) throws DBException;

    Check createCheck(User user, Room room, Date arrival, Date departure) throws DBException;
    Check createCheck(Connection con, User user, Room room, Date arrival, Date departure) throws DBException;

}

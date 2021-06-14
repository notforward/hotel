package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.entities.User;
import com.epam.project.hotel.sql.AppException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends EntityDAO {

    String FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE user_login = ?";
    String INSERT_USER = "INSERT INTO user(user_login, user_password, user_email, role_id) values (?, ?, ?, 1);";

    User createUser(String login, String password, String email) throws AppException;
    User createUser(Connection con, String login, String password, String email) throws AppException;

    User findUserID(int id) throws AppException;
    User findUserID(Connection con, int id) throws AppException;

    User findUserLOG(String login) throws AppException;
    User findUserLOG(Connection con, String login) throws AppException;
    User extractUser(ResultSet rs) throws SQLException;

    boolean checkPass(User user);
}

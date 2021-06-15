package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.entities.Entity;
import com.epam.project.hotel.sql.entities.User;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Class that helps to work with user table in database.
 */
public class UserDAO implements com.epam.project.hotel.dao.UserDAO, Entity {
    private static final Logger log = LogManager.getLogger(UserDAO.class);

    protected UserDAO() {

    }
    // Method that provide creation of user
    @Override
    public User createUser(String login, String password, String email) throws AppException {
        log.info("UserDAO#createUser(Login = " + login + "pass = " + password + " email = " + email);
        Connection con = DataSource.getConnection();
        User user;
        try {
            user = createUser(con, login, password, email);
        } catch (AppException e) {
            log.error("Error in create user", e);
            throw new AppException("Cannot create user, try again", e);
        } finally {
            close(con);
        }
        log.info("User = " + user);
        return user;
    }
    // Method with connection same as above
    @Override
    public User createUser(Connection con, String login, String password, String email) throws AppException {
        log.info("UserDAO#createUser(Con, login, pass, email)");
        User user;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(INSERT_USER);
            int k = 1;
            ps.setString(k++, login);
            ps.setString(k++, hashPass(password));
            ps.setString(k, email);
            log.info("ps = " + ps);
            ps.execute();

            user = findUserLOG(con, login);
        } catch (SQLException e) {
            log.error("Error in createUser", e);
            throw new AppException("Cannot create user, try again", e);
        }
        log.info("user = " + user);
        return user;
    }
    // Hashing password with MD5 algorithm
    @Override
    public String hashPass(String password) throws AppException {
        log.info("#hashPass, password = " + password);
        MessageDigest messageDigest;
        byte[] digest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error("Problem at hashing password", e);
            throw new AppException("Cannot hash pass, try again");
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(
                bigInt.toString()
        );

        while( md5Hex.length() < 32 ){
            md5Hex.append("0").append(md5Hex);
        }
        log.info("pass after hashing = " + md5Hex.toString());
        return md5Hex.toString();
    }
    // finding user on selected id
    @Override
    public User findUserID(int id) throws AppException {
        log.info("UserDAO#findUserID(id)");
        Connection con = DataSource.getConnection();
        User user;
        try {
            user = findUserID(con, id);
            log.info("User = " + user);
        } catch (AppException e) {
            log.error("Error in find user by ID", e);
            throw new AppException("Cannot find selected user, try again", e);
        } finally {
            close(con);
        }
        return user;
    }
    // with connection
    @Override
    public User findUserID(Connection con, int id) throws AppException {
        log.info("UserDAO#findUserID id = " + id);
        PreparedStatement ps;
        ResultSet rs;
        log.info("id = " + id);
        User user = null;
        try {
            ps = con.prepareStatement(FIND_USER_BY_ID);
            int k = 1;
            ps.setInt(k, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException e) {
            log.error("Error in findUserLOG", e);
            throw new AppException("Cannot find user by ID, try again", e);
        }
        return user;
    }
    // helps to find user with mentioned login
    @Override
    public User findUserLOG(String login) throws AppException {
        log.info("UserDAO#findUserLOG");
        Connection con = DataSource.getConnection();
        User user;
        try {
            user = findUserLOG(con, login);
            log.info("Login of user = " + login);
        } catch (AppException e) {
            log.error("Error in find user by LOGIN", e);
            throw new AppException("Cannot find selected user, try again", e);
        } finally {
            close(con);
        }
        return user;
    }
    // same as above with connection
    @Override
    public User findUserLOG(Connection con, String login) throws AppException {
        log.info("UserDAO#findUserLOG(Con, log)");
        PreparedStatement ps;
        ResultSet rs;
        log.info("Login = " + login);
        User user = null;
        try {
            ps = con.prepareStatement(FIND_USER_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException e) {
            log.error("Error in findUserLOG", e);
            throw new AppException("Cannot find user by ID, try again", e);
        } finally {
            close(con);
        }
        return user;
    }
    // Helps to get entity of user on selected result set
    @Override
    public User extractUser(ResultSet rs) throws SQLException {
        log.info("RoomDAO#extractUser");
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setLogin(rs.getString("user_login"));
        user.setPassword(rs.getString("user_password"));
        user.setEmail(rs.getString("user_email"));
        user.setRole(rs.getString("role_id"));
        log.info("User role = " + user.getRole());
        return user;
    }
    // Rollback for transaction
    @Override
    public void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                log.info("Cannot close connection", e);
            }
        }
    }
    // Close connection method
    @Override
    public void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.info("Cannot close auto closeable", e);
            }
        }
    }

}

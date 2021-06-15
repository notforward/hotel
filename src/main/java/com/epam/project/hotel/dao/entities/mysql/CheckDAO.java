package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import com.epam.project.hotel.sql.entities.Room;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class that represents the functionality for working with the database (payment_check table)
 */

public class CheckDAO implements com.epam.project.hotel.dao.CheckDAO {
    private static final Logger log = LogManager.getLogger(CheckDAO.class);

    protected CheckDAO() {

    }
    // This method helps to find check by ID. Return - check entity
    @Override
    public Check findCheckByID(int id) throws AppException {
        log.info("#findCheckByID id=" + id);
        Connection con = null;
        Check check;
        try {
            con = DataSource.getConnection();
            check = findCheckByID(con, id);
            log.info("check = " + check);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Error in findCheckByID");
            throw new AppException("Cannot find check, please try again");
        } finally {
            close(con);
        }
        return check;
    }
    // Same method with getting connection
    @Override
    public Check findCheckByID(Connection con, int id) throws AppException {
        log.info("findCheckByID id=" + id + " con = " + con);
        PreparedStatement ps;
        ResultSet rs;
        Check check = null;
        try {
            ps = con.prepareStatement(SELECT_CHECK_BY_ID);
            int k = 1;
            ps.setInt(k, id);
            log.info("ps = " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                check = extractCheck(rs);
            }
        } catch (SQLException e) {
            log.error("Error in findCheckByID");
            throw new AppException("Cannot find check, please try again");
        }
        return check;
    }
    // This method helps to update check status. Return - check entity
    @Override
    public Check updateCheckStatus(Check check, String status) throws AppException {
        log.info("#updateCheckStatus check = " + check);
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = updateCheckStatus(con, check, status);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem in update check status");
            throw new AppException("Cannot update check status, please try again");
        } finally {
            close(con);
        }
        return check;
    }
    // Same as above with getting connection
    @Override
    public Check updateCheckStatus(Connection con, Check check, String status) throws AppException {
        log.info("#updateCheckStatus check = " + check + " status = " + status);
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(UPDATE_CHECK_STATUS);
            int k = 1;
            ps.setString(k++, status);
            ps.setInt(k, check.getCheck_id());
            log.info("ps = " + ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Problem in update check status");
            throw new AppException("Cannot update check status, please try again");
        }
        check = findCheckByID(con, check.getCheck_id());
        return check;
    }

    // This method helps to find all checks. Return - list of check entity
    @Override
    public List<Check> findAllChecks(Connection con) throws AppException {
        log.info("#findAllChecks");
        Statement st;
        ResultSet rs;
        List<Check> checks;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SELECT_ALL_CHECKS);
            checks = new ArrayList<>();
            while (rs.next()){
                checks.add(extractCheck(rs));
            }
        } catch (SQLException e) {
            log.error("Problem at finding all checks", e);
            throw new AppException("Cannot find all checks, try again");
        }
        log.info("Checks = " + checks);
        return checks;
    }
    // This method helps to find checks with offset and limit params in sql query. Depends on user_id
    @Override
    public List<Check> findChecks(int offset, int limit, int user_id) throws AppException {
        log.info("#findRooms offset = " + offset + " limit = " + limit + " id = " + user_id);
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;
        List<Check> checks;
        try {
            con = DataSource.getConnection();
            ps = con.prepareStatement(SELECT_ROOMS);
            int k = 1;
            ps.setInt(k++, user_id);
            ps.setInt(k++, limit);
            ps.setInt(k, offset);
            log.info("ps = " + ps);
            rs = ps.executeQuery();
            checks = new ArrayList<>();
            while(rs.next()){
                checks.add(extractCheck(rs));
            }
            con.commit();
            log.info("checks = " + checks);
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem findChecks");
            throw new AppException("Cannot find checks, try again");
        }
        finally {
            close(con);
        }
        return checks;
    }
    // Returns amount of all checks in DB
    @Override
    public int findChecksSize() throws AppException {
        log.info("#findChecksSize");
        Connection con = null;
        Statement st;
        ResultSet rs;
        int size = 0;
        try {
            con = DataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_SIZE);
            if(rs.next()){
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem findCheckSize");
            throw new AppException("Cannot find rooms, try again");
        }
        finally {
            close(con);
        }
        log.info("size = " + size);
        return size;
    }
    // Helps to get check entity from rs
    private Check extractCheck(ResultSet rs) throws SQLException {
        log.info("#extractCheck");
        Check check = new Check();
        check.setCheck_id(rs.getInt("check_id"));
        check.setUser_id(rs.getInt("user_id"));
        check.setRoom_id(rs.getInt("room_id"));
        check.setRoom_in(rs.getDate("room_in"));
        check.setRoom_out(rs.getDate("room_out"));
        check.setPrice(rs.getInt("check_price"));
        check.setCheck_status(rs.getString("check_status"));
        check.setCheck_creation(rs.getDate("check_creation"));
        check.setCheck_terminate(rs.getDate("check_terminate"));

        log.info("check = " + check);
        return check;
    }
    // Checking is it available to create check for dates that mentioned
    @Override
    public Boolean checkCreation(Date arrival, Date department, int id) throws AppException {
        log.info("CheckDAO#checkCreation arrival = " + arrival + " department = " + department
        + " id = " + id);
        Connection con = null;
        boolean available;
        try {
            con = DataSource.getConnection();
            available = checkCreation(con, arrival, department, id);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Error in checkCreation", e);
            throw new AppException("Sorry, cannot check dates, try again", e);
        } finally {
            close(con);
        }
        log.info("is available = " + available);
        return available;
    }
    // Same as above with getting connection
    @Override
    public Boolean checkCreation(Connection con, Date arrival, Date department, int id) throws AppException {
        log.info("CheckDAO#checkCreation arrival = " + arrival + " department = " + department
                + " id = " + id);
        if (arrival.after(department)) {
            log.info("Arrival after department");
            return false;
        }
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(INSPECT_CHECK);
            log.info(ps);
            ps.setInt(1, id);
            log.info("ps = " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                log.info("arrival after room_in where arrival = " + arrival + " room_in = " + rs.getDate("room_in")
                       + arrival.after(rs.getDate("room_in")));
                log.info("arrival before room_out where arrival = " + arrival + "room_out = " + rs.getDate("room_out")
                        + " result = " + arrival.before(rs.getDate("room_out")));
                if (
                        (arrival.after(rs.getDate("room_in"))
                                && arrival.before(rs.getDate("room_out")))
                                || (department.before(rs.getDate("room_out"))
                                && department.after(rs.getDate("room_in")))

                                || (arrival.before(rs.getDate("room_in"))
                                && department.after(rs.getDate("room_out")))

                                || (department.equals(rs.getDate("room_out"))
                                && arrival.equals(rs.getDate("room_in")))
                ) {
                    log.info("Dates are busy, returning false");
                    return false;
                }
            }
        } catch (SQLException e) {
            log.error("Error in checkCreation", e);
            throw new AppException("Sorry, cannot check dates, try again", e);
        }
        log.info("Dates are not busy, returning true");
        return true;
    }
    // This method helps to createCheck
    @Override
    public Check createCheck(User user, Room room, Date arrival, Date departure) throws AppException {
        log.info("#createCheck user = " + user + " room = " + room + " arrival = " + arrival
        + " departure = " + departure);
        Check check;
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = createCheck(con, user, room, arrival, departure);
            con.commit();
        } catch (AppException | SQLException e) {
            rollback(con);
            log.error("Problem in creating check");
            throw new AppException("Can not create check, try again", e);
        } finally {
            close(con);
        }
        log.info("check = " + check);
        return check;
    }
    // Same as above with getting connection
    @Override
    public Check createCheck(Connection con, User user, Room room, Date arrival, Date departure) throws AppException {
        log.info("#createCheck user = " + user + " room = " + room + " arrival = " + arrival
                + " departure = " + departure);

        Check check = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(INSERT_CHECK, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            ps.setInt(k++, user.getId());
            ps.setInt(k++, room.getId());
            ps.setDate(k++, (java.sql.Date) arrival);
            ps.setDate(k++, (java.sql.Date) departure);
            ps.setInt(k++, room.getPrice());
            ps.setString(k++, "NOT PAYED");

            long millis = System.currentTimeMillis();
            java.sql.Date today = new java.sql.Date(millis);
            log.info("Today = " + today);
            int daysToTerminate = 2;
            LocalDate terminate = LocalDate.now().plusDays(daysToTerminate);
            ps.setDate(k++, today);
            ps.setDate(k, java.sql.Date.valueOf(terminate));
            log.info("ps = " + ps);
            ps.executeUpdate();
            con.commit();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                check = findCheckByID(rs.getInt(1));
                log.info("Check = " + check);
            }
        } catch (SQLException e) {
            log.error("Error in createCheck");
            throw new AppException("Cannot create check, please try again");
        }
        log.info("check = " + check);
        return check;
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

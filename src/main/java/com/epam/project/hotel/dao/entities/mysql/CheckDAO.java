package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.DBException;
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

public class CheckDAO implements com.epam.project.hotel.dao.CheckDAO {
    private static final Logger log = LogManager.getLogger(CheckDAO.class);

    protected CheckDAO() {

    }

    @Override
    public Check findCheckByID(int id) throws DBException {
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
            throw new DBException("Cannot find check, please try again");
        } finally {
            close(con);
        }
        return check;
    }

    @Override
    public Check findCheckByID(Connection con, int id) throws DBException {
        log.info("findCheckByID id=" + id + " con = " + con);
        PreparedStatement ps;
        ResultSet rs;
        Check check = null;
        try {
            ps = con.prepareStatement(SELECT_CHECK_BY_ID);
            ps.setInt(1, id);
            log.info("ps = " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                check = extractCheck(rs);
            }
        } catch (SQLException e) {
            log.error("Error in findCheckByID");
            throw new DBException("Cannot find check, please try again");
        }
        return check;
    }

    @Override
    public Check updateCheckStatus(Check check, String status) throws DBException {
        log.info("updateCheckStatus + check = " + check);
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = updateCheckStatus(con, check, status);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem in update check status");
            throw new DBException("Cannot update check status, please try again");
        } finally {
            close(con);
        }
        return check;
    }

    @Override
    public Check updateCheckStatus(Connection con, Check check, String status) throws DBException {
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
            throw new DBException("Cannot update check status, please try again");
        }
        check = findCheckByID(check.getCheck_id());
        return check;
    }

    @Override
    public List<Check> findAllChecks(Connection con) throws DBException {
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
            throw new DBException("Cannot find all checks, try again");
        }
        log.info("Checks = " + checks);
        return checks;
    }

    @Override
    public List<Check> findChecks(int offset, int limit, int user_id) throws DBException {
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
            throw new DBException("Cannot find checks, try again");
        }
        finally {
            close(con);
        }
        return checks;
    }

    @Override
    public int findChecksSize() throws DBException {
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
            throw new DBException("Cannot find rooms, try again");
        }
        finally {
            close(con);
        }
        log.info("size = " + size);
        return size;
    }

    private Check extractCheck(ResultSet rs) throws SQLException {
        log.info("CheckDAO#extractCheck");

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

    @Override
    public Boolean checkCreation(Date arrival, Date department, int id) throws DBException {
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
            throw new DBException("Sorry, cannot check dates, try again", e);
        } finally {
            close(con);
        }
        log.info("is available = " + available);
        return available;
    }

    @Override
    public Boolean checkCreation(Connection con, Date arrival, Date department, int id) throws DBException {
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
                ) {
                    log.info("Dates are busy, returning false");
                    return false;
                }
            }
        } catch (SQLException e) {
            log.error("Error in checkCreation", e);
            throw new DBException("Sorry, cannot check dates, try again", e);
        }
        log.info("Dates are not busy, returning true");
        return true;
    }

    @Override
    public Check createCheck(User user, Room room, Date arrival, Date departure) throws DBException {
        log.info("#createCheck user = " + user + " room = " + room + " arrival = " + arrival
        + " departure = " + departure);
        Check check;
        Connection con = null;
        try {
            con = DataSource.getConnection();
            check = createCheck(con, user, room, arrival, departure);
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            log.error("Problem in creating check");
            throw new DBException("Can not create check, try again", e);
        } finally {
            close(con);
        }
        log.info("check = " + check);
        return check;
    }

    @Override
    public Check createCheck(Connection con, User user, Room room, Date arrival, Date departure) throws DBException {
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
            throw new DBException("Cannot create check, please try again");
        }
        log.info("check = " + check);
        return check;
    }

    @Override
    public void rollback(Connection con) {
        if (con != null) {
            try {
                log.info("rollback");
                con.rollback();
            } catch (SQLException e) {
                log.info("Cannot close connection", e);
            }
        }
    }

    @Override
    public void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                log.info("closing");
                ac.close();
            } catch (Exception e) {
                log.info("Cannot close auto closeable", e);
            }
        }
    }
}

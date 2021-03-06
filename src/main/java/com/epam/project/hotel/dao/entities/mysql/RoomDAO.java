package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that helps to work with room table in database.
 */
public class RoomDAO implements com.epam.project.hotel.dao.RoomDAO {
    private static final Logger log = LogManager.getLogger(RoomDAO.class);

    protected RoomDAO() {

    }
    // Helps to find room by id
    @Override
    public Room findRoomID(int id) throws AppException {
        log.info("RoomDAO#findAllRooms(id)");
        Connection con = null;
        Room room;
        try {
            con = DataSource.getConnection();
            room = findRoomID(con, id);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem at findRoomID(id)", e);
            throw new AppException("Can not find specified room, try again");
        } finally {
            close(con);
        }
        return room;
    }
    // Method with connection
    @Override
    public Room findRoomID(Connection con, int id) throws AppException {
        log.info("RoomDAO#findRoomID(con, id)");
        PreparedStatement ps;
        Room room = null;
        try {
            ps = con.prepareStatement(FIND_ROOM_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                room = extractRoom(con, rs);
            }
        } catch (SQLException e) {
            log.error("Problem in findRoomID", e);
            throw new AppException("Cannot find specified room, try again");
        }
        return room;
    }
    // Helps to find all rooms
    @Override
    public List<Room> findAllRooms() throws AppException {
        log.info("RoomDAO#findAllRooms(-)");
        Connection con = null;
        List<Room> rooms;
        try {
            con = DataSource.getConnection();
            rooms = new ArrayList<>(findAllRooms(con));
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem at findAllRooms(no param)", e);
            throw new AppException("Can not find all rooms, try again");
        } finally {
            close(con);
        }
        return rooms;
    }
    // With connection
    @Override
    public List<Room> findAllRooms(Connection con) throws AppException {
        log.info("RoomDAO#findAllRooms(con)");
        List<Room> rooms = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_ROOMS);
            while (rs.next()) {
                rooms.add(extractRoom(con, rs));
            }
        } catch (SQLException e) {
            log.error("Problem in finding all rooms");
            throw new AppException("Can not find all rooms, try again", e);
        }
        return rooms;
    }
    // Helps to find selected rooms on offset with limit and order
    @Override
    public List<Room> findRooms(int offset, int limit, String orderBy) throws AppException {
            log.info("#findRooms offset = " + offset + " limit = " + limit + " orderBy = " + orderBy);
            Connection con = null;
            PreparedStatement ps;
        ResultSet rs;
        List<Room> rooms;
        try {
            con = DataSource.getConnection();
            ps = con.prepareStatement(SELECT_ROOMS);
            log.info("orderBy = " + orderBy);
            switch (orderBy) {
                case("price"):
                    ps = con.prepareStatement(SELECT_ROOMS_PRICE);
                    break;
                case("size"):
                    ps = con.prepareStatement(SELECT_ROOMS_SIZE);
                    break;
                case("class"):
                    ps = con.prepareStatement(SELECT_ROOMS_CLASS);
                    break;
                case("status"):
                    ps = con.prepareStatement(SELECT_ROOMS_STATUS);
            }
            int k = 1;
            ps.setInt(k++, limit);
            ps.setInt(k, offset);
            log.info("ps = " + ps);
            rs = ps.executeQuery();
            rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(extractRoom(con, rs));
            }
            con.commit();
            log.info("rooms = " + rooms);
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem findRooms");
            throw new AppException("Cannot find rooms, try again");
        } finally {
            close(con);
        }
        return rooms;
    }
    // Counts all rooms in table and returns integer
    @Override
    public int findRoomsSize() throws AppException {
        log.info("#findRoomsSize");
        Connection con = null;
        Statement st;
        ResultSet rs;
        int size = 0;
        try {
            con = DataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_SIZE);
            if (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            rollback(con);
            log.error("Problem findRooms");
            throw new AppException("Cannot find rooms, try again");
        } finally {
            close(con);
        }
        log.info("size = " + size);
        return size;
    }
    // Helps to update room status on selected room
    @Override
    public Room updateRoomStatus(Room room, String status) throws AppException {
        log.info("RoomDAO#updateRoomStatus(-)");
        Connection con = null;
        PreparedStatement ps;
        try {
            con = DataSource.getConnection();
            ps = con.prepareStatement(UPDATE_ROOM_STATUS);
            int k = 1;
            ps.setString(k++, status);
            ps.setInt(k, room.getId());
            log.info("ps = " + ps);
            ps.executeUpdate();
            con.commit();

            room = findRoomID(room.getId());
        } catch (SQLException e) {
            rollback(con);
            log.info("Problem at updateRoomStatus", e);
            throw new AppException("Cannot update room status, please try again", e);
        } finally {
            close(con);
        }
        return room;
    }
    // Helps to extract room information and create entity of room on selected connection and result set
    @Override
    public Room extractRoom(Connection con, ResultSet rs) throws AppException {
        log.info("RoomDAO#extractRoom");
        Room room;
        try {
            room = new Room();
            room.setId(rs.getInt("room_id"));
            room.setStatus(rs.getString("room_status"));
            log.info("Room id = " + room.getId());
            room.setPrice(rs.getInt("room_price"));
            room.setSize(rs.getInt("room_size"));
            room.setRoom_class(rs.getString("room_class"));
            room.setName(rs.getString("room_name"));
            room.setDescription(rs.getString("room_description"));
            room.setPhoto(rs.getString("room_photo"));
            log.info("room = " + room);
        } catch (SQLException e) {
            log.error("Problem in extracting room");
            throw new AppException("Can not find specified room, try again", e);
        }
        return room;
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
    // Close connection
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

package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RoomDAO implements com.epam.project.hotel.dao.RoomDAO {
    private static final Logger log = LogManager.getLogger(RoomDAO.class);
    protected RoomDAO(){

    }
    @Override
    public Room findRoomID(int id) throws DBException {
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
            throw new DBException("Can not find specified room, try again");
        }
        finally {
            close(con);
        }
        return room;
    }

    @Override
    public Room findRoomID(Connection con, int id) throws DBException {
        log.info("RoomDAO#findRoomID(con, id)");
        PreparedStatement ps;
        Room room = null;
        try {
            ps = con.prepareStatement(FIND_ROOM_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                room = extractRoom(con, rs);
                extractRoomInfo(room, con);
            }
        } catch (SQLException e) {
            log.error("Problem in findRoomID", e);
            throw new DBException("Cannot find specified room, try again");
        }
        return room;
    }

    @Override
    public List<Room> findAllRooms() throws DBException {
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
            throw new DBException("Can not find all rooms, try again");
        }
        finally {
            close(con);
        }
        return rooms;
    }

    @Override
    public List<Room> findAllRooms(Connection con) throws DBException {
        log.info("RoomDAO#findAllRooms(con)");
        List<Room> rooms = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_ROOMS);
            while(rs.next()){
                rooms.add(extractRoom(con, rs));
            }
        } catch (SQLException e) {
            log.error("Problem in finding all rooms");
            throw new DBException("Can not find all rooms, try again", e);
        }
        return rooms;
    }

    @Override
    public Room updateRoomStatus(Room room, String status) throws DBException {
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
            throw new DBException("Cannot update room status, please try again", e);
        }
        finally {
            close(con);
        }
        return room;
    }

    @Override
    public Room extractRoom(Connection con, ResultSet rs) throws DBException {
        log.info("RoomDAO#extractRoom");
        Room room;
        try {
            room = new Room();
            room.setId(rs.getInt("room_id"));
            room.setStatus(rs.getString("room_status"));
            log.info("Room id = " + room.getId());
            room.setIn(rs.getDate("room_in"));
            log.info("room in = " + room.getIn());
            room.setOut(rs.getDate("room_out"));
            extractRoomInfo(room, con);
        } catch (SQLException e) {
            log.error("Problem in extracting room");
            throw new DBException("Can not find specified room, try again", e);
        }
        return room;
    }

    @Override
    public void extractRoomInfo(Room room, Connection con) throws DBException {
        log.info("RoomDAO#extractRoomInfo");
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(FIND_ROOM_INFO);
            ps.setInt(1, room.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                room.setPrice(rs.getInt("room_price"));
                room.setSize(rs.getInt("room_size"));
                room.setRoom_class(rs.getString("room_class"));
                room.setName(rs.getString("room_name"));
                room.setDescription(rs.getString("room_description"));
                room.setPhoto(rs.getString("room_photo"));
            }
        } catch (SQLException e) {
            log.error("Problem in extracting room info");
            throw new DBException("Can not find specified room, try again", e);
        }
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

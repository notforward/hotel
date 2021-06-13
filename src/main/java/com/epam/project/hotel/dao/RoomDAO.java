package com.epam.project.hotel.dao;

import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.entities.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface RoomDAO extends EntityDAO {
    String FIND_ALL_ROOMS = "SELECT * FROM room";
    String FIND_ROOM_BY_ID = "SELECT * FROM room WHERE room_id = ?";
    String UPDATE_ROOM_STATUS = "UPDATE room SET room_status = ? WHERE room_id = ?";
    String SELECT_ROOMS = "SELECT * FROM room ORDER BY ? DESC LIMIT ? OFFSET ?";
    String SELECT_ROOMS_PRICE = "SELECT * FROM room ORDER BY room.room_price DESC LIMIT ? OFFSET ?";
    String SELECT_ROOMS_SIZE = "SELECT * FROM room ORDER BY room.room_size DESC LIMIT ? OFFSET ?";
    String SELECT_ROOMS_CLASS = " SELECT * FROM room ORDER BY room.room_class DESC LIMIT ? OFFSET ?";
    String SELECT_ROOMS_STATUS = "SELECT * FROM room ORDER BY room.room_status DESC LIMIT ? OFFSET ?";
    String FIND_SIZE = "SELECT COUNT(*) FROM room";

    Room findRoomID(int id) throws DBException;
    Room findRoomID(Connection con, int id) throws DBException;

    List<Room> findAllRooms() throws DBException;
    List<Room> findAllRooms(Connection con) throws DBException;
    List<Room> findRooms(int offset, int limit, String orderBy) throws DBException;

    int findRoomsSize() throws DBException;

    Room updateRoomStatus(Room room, String status) throws DBException;
    Room extractRoom(Connection con, ResultSet rs) throws DBException;
}

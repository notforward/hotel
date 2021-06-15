package com.epam.project.hotel.dao;

import java.sql.Connection;

/**
 * Father of all entities of DAO factory, interface-mark
 */
public interface EntityDAO {
    void rollback(Connection con);
    void close(AutoCloseable ac);
}

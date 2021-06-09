package com.epam.project.hotel.dao;

import java.sql.Connection;

public interface EntityDAO {
    void rollback(Connection con);
    void close(AutoCloseable ac);
}

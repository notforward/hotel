package com.epam.project.hotel.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;


public class DataSource {

    public static Connection getConnection() throws AppException {
        Connection con;
        try {
            Context initContext;
            initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            javax.sql.DataSource ds = (javax.sql.DataSource) envContext.lookup("jdbc/hotel_db");
            con = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new AppException("Failed in getConnection method", e);
        }
        return con;
    }
}

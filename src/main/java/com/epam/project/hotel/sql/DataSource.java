package com.epam.project.hotel.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;


public class DataSource {
    /*
    @return returns connection for
     */
    public static Connection getConnection() throws DBException {
        Connection con;
        try {
            Context initContext;
            initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            javax.sql.DataSource ds = (javax.sql.DataSource) envContext.lookup("jdbc/hotel_db");
            con = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new DBException("Failed in getConnection method", e);
        }
        return con;
    }
}

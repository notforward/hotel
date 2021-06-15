package com.epam.project.hotel.dao;

/**
 * For factories (OracleFactory, MySQLFactory)
 */
public interface Factory {
    EntityDAO getDAO(String name);
}

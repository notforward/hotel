package com.epam.project.hotel.dao;

public interface Factory {
    EntityDAO getDAO(String name);
}

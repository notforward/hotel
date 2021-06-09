package com.epam.project.hotel.dao.entities.mysql;

import com.epam.project.hotel.dao.EntityDAO;
import com.epam.project.hotel.dao.Factory;

public class MySQLFactory implements Factory {
    private static MySQLFactory instance = null;

    private MySQLFactory(){

    }

    public static MySQLFactory getInstance() {
        if(instance == null){
            instance = new MySQLFactory();
        }
        return instance;
    }
    public EntityDAO getDAO(String name){
        EntityDAO dao = null;
        switch (name){
            case("CheckDAO"):
                dao = new CheckDAO();
                break;
            case("RequestDAO"):
                dao = new RequestDAO();
                break;
            case("RoomDAO"):
                dao = new RoomDAO();
                break;
            case("UserDAO"):
                dao = new UserDAO();
        }
        return dao;
    }
}

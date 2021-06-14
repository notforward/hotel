package com.epam.project.hotel.sql;

public class AppException extends Exception{
    public AppException(String message, Throwable cause){
        super(message, cause);
    }
    public AppException(String message){
        super(message);
    }
}

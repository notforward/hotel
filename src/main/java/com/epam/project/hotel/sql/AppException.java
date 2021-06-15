package com.epam.project.hotel.sql;

/**
 * Custom app exception that helps to localize all non standard situations
 */
public class AppException extends Exception{
    public AppException(String message, Throwable cause){
        super(message, cause);
    }
    public AppException(String message){
        super(message);
    }
}

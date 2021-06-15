package com.epam.project.hotel.sql.entities;

import java.sql.Date;
/**
 * Entity for request table
 */
public class Request implements Entity {

    private int request_id;
    private int user_id;
    private int size;
    private String room_class;
    private Date arrival;
    private Date department;
    private String status;
    private int room_id;

    @Override
    public String toString() {
        return "Request{" +
                "request_id=" + request_id +
                ", user_id=" + user_id +
                ", size=" + size +
                ", room_class='" + room_class + '\'' +
                ", arrival=" + arrival +
                ", department=" + department +
                ", status='" + status + '\'' +
                ", room_id=" + room_id +
                '}';
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRoom_class() {
        return room_class;
    }

    public void setRoom_class(String room_class) {
        this.room_class = room_class;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDepartment() {
        return department;
    }

    public void setDepartment(Date department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}

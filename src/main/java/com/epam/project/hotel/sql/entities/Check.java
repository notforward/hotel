package com.epam.project.hotel.sql.entities;

import java.util.Date;

public class Check implements Entity{
    private int check_id;
    private int user_id;
    private int room_id;
    private int price;
    private Date room_in;
    private Date room_out;
    private String check_status;
    private String check_description;

    @Override
    public String toString() {
        return "Check{" +
                "check_id=" + check_id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", price=" + price +
                ", room_in=" + room_in +
                ", room_out=" + room_out +
                ", check_status='" + check_status + '\'' +
                ", check_description='" + check_description + '\'' +
                '}';
    }

    public int getCheck_id() {
        return check_id;
    }

    public void setCheck_id(int check_id) {
        this.check_id = check_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int check_price) {
        this.price = check_price;
    }

    public Date getRoom_in() {
        return room_in;
    }

    public void setRoom_in(Date room_in) {
        this.room_in = room_in;
    }

    public Date getRoom_out() {
        return room_out;
    }

    public void setRoom_out(Date room_out) {
        this.room_out = room_out;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getCheck_description() {
        return check_description;
    }

    public void setCheck_description(String check_description) {
        this.check_description = check_description;
    }
}

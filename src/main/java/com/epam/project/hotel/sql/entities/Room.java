package com.epam.project.hotel.sql.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Room implements Entity{
    private int id;
    private String status;
    private Date in;
    private Date out;
    private String name;
    private String description;
    private int price;
    private int size;
    private String room_class;
    private String photo;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", in='" + in + '\'' +
                ", out='" + out + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", room_class='" + room_class + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIn() {
        return in;
    }

    public void setIn(Date in) {
        this.in = in;
    }

    public Date getOut() {
        return out;
    }

    public void setOut(Date out) {
        this.out = out;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

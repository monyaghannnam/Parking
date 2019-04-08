package com.example.parking;

public class Slot {
    int id;
    int status;
    int floor_id;

    public Slot(){

    }
    public Slot(int id, int status, int floor_id) {
        this.id = id;
        this.status = status;
        this.floor_id = floor_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getFloor_id() {
        return floor_id;
    }
}

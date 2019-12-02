package com.jamie.devonrocksandstones.models;

public class Stone {

    private int id;
    private String status;

    public Stone(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}

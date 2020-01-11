package com.jamie.devonrocksandstones.models;

public class Stone {

    private int id;
    private int location;
    private String status;
    private String area;

    public Stone(int id, int location, String status, String area) {
        this.id = id;
        this.status = status;
        this.location = location;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public int getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getArea() {
        return area;
    }
}

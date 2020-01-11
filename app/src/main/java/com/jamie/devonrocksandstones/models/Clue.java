package com.jamie.devonrocksandstones.models;

public class Clue {

    private int id;
    private String content;
    private String addedBy;
    private String image;

    public Clue(int id, String content, String addedBy, String image) {
        this.id = id;
        this.content = content;
        this.addedBy = addedBy;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getImage() {
        return image;
    }

}
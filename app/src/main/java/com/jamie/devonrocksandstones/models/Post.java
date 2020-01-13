package com.jamie.devonrocksandstones.models;

public class Post {

    private int id;
    private String content;
    private String postedBy;
    private String image;
    private boolean deletable;

    public Post(int id, String content, String postedBy, String image) {
        this.id = id;
        this.content = content;
        this.postedBy = postedBy;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public String getImage() {
        return image;
    }

    public boolean isDeletable() {
        return deletable;
    }

}

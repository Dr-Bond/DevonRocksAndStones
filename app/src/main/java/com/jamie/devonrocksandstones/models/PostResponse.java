package com.jamie.devonrocksandstones.models;

import java.util.List;

public class PostResponse {

    private boolean error;
    private List<Post> posts;

    public PostResponse(boolean error, List<Post> posts) {
        this.error = error;
        this.posts = posts;
    }

    public boolean isError() {
        return error;
    }

    public List<Post> getPosts() {
        return posts;
    }
}

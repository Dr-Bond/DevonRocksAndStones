package com.jamie.devonrocksandstones.models;

import java.util.List;

public class StoneResponse {

    private boolean error;
    private List<Stone> stones;

    public StoneResponse(boolean error, List<Stone> stones) {
        this.error = error;
        this.stones = stones;
    }

    public boolean isError() {
        return error;
    }

    public List<Stone> getStones() {
        return stones;
    }
}

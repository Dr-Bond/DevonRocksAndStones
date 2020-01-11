package com.jamie.devonrocksandstones.models;

import java.util.List;

public class ClueResponse {

    private boolean error;
    private List<Clue> clues;

    public ClueResponse(boolean error, List<Clue> clues) {
        this.error = error;
        this.clues = clues;
    }

    public boolean isError() {
        return error;
    }

    public List<Clue> getClues() {
        return clues;
    }
}

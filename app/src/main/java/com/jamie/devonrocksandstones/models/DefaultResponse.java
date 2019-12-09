package com.jamie.devonrocksandstones.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String msg;

    public DefaultResponse(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return msg;
    }
}

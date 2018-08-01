package com.example.jwtdemo.domain.model;

import java.io.Serializable;

/**
 * @author shuai
 * @version 1.0
 */
public class APIResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static int SUCCESS = 0;
    private final static int FAILURE = 1;

    private int status;
    private String message;
    private Object data;
    private boolean valid;

    public static APIResult success() {
        return new APIResult().setStatus(SUCCESS);
    }

    public static APIResult failure() {
        return new APIResult().setStatus(FAILURE);
    }

    public int getStatus() {
        return status;
    }

    private APIResult setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public APIResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public APIResult setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getValid() {
        return valid;
    }

    public APIResult setValid(boolean valid) {
        this.valid = valid;
        return this;
    }
}
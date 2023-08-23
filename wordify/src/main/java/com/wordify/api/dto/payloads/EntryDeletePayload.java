package com.wordify.api.dto.payloads;

public class EntryDeletePayload {
    private int id;
    private int userId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
       
}

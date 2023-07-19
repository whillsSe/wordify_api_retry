package com.wordify.auth.dto;


public class RefreshTokenInfo {
    private String refreshTokeString;
    private int userId;
    private long expirationMilliSec;
    
    public RefreshTokenInfo(String refreshTokeString, int userId, long expirationMilliSec) {
        this.refreshTokeString = refreshTokeString;
        this.userId = userId;
        this.expirationMilliSec = expirationMilliSec;
    }
    public String getRefreshTokeString() {
        return refreshTokeString;
    }
    public void setRefreshTokeString(String refreshTokeString) {
        this.refreshTokeString = refreshTokeString;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public long getExpiration() {
        return expirationMilliSec;
    }
    public void setExpiration(long expirationMilliSec) {
        this.expirationMilliSec = expirationMilliSec;
    }
    
}

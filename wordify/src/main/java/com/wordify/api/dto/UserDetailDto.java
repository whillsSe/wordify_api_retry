package com.wordify.api.dto;

public class UserDetailDto extends UserBaseDto{
    private String nickName;
    private String iconSrcUrl;
    private String profile;
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getIconSrcUrl() {
        return iconSrcUrl;
    }
    public void setIconSrcUrl(String iconSrcUrl) {
        this.iconSrcUrl = iconSrcUrl;
    }
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
}

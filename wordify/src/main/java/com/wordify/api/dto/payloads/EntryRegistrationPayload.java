package com.wordify.api.dto.payloads;

public class EntryRegistrationPayload {
    private Integer userId;
    private String wordString;
    private String phoneticString;
    private String[] tagStrings;
    private String MeaningString;
    private String[] exampleString;
    
    public String getWordString() {
        return wordString;
    }
    public void setWordString(String wordString) {
        this.wordString = wordString;
    }
    public String getPhoneticString() {
        return phoneticString;
    }
    public void setPhoneticString(String phoneticString) {
        this.phoneticString = phoneticString;
    }
    public String[] getTagStrings() {
        return tagStrings;
    }
    public void setTagStrings(String[] tagStrings) {
        this.tagStrings = tagStrings;
    }
    public String getMeaningString() {
        return MeaningString;
    }
    public void setMeaningString(String meaningString) {
        MeaningString = meaningString;
    }
    public String[] getExampleStrings() {
        return exampleString;
    }
    public void setExampleStrings(String[] exampleString) {
        this.exampleString = exampleString;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}

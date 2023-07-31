package com.wordify.api.dto.payloads;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EntryRegistrationPayload {
    private Integer userId;
    @Size(min = 1 ,max = 20)
    private String wordString;
    @Size(min = 1 ,max = 20)
    @Pattern(regexp = "[\\u3041-\\u3096ー]",message = "[${validatedValue}]は[{regexp}]に一致しません。"+"ひらがな、もしくは伸ばし棒(’ー’)のみが使用できます。")
    private String phoneticString;
    @Size(min = 1 ,max = 20)
    @Valid
    private String[] tagStrings;
    @Size(min=0,max=400)
    private String meaningString;
    @Size(min=0,max=100)
    @Valid
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
        return meaningString;
    }
    public void setMeaningString(String meaningString) {
        this.meaningString = meaningString;
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

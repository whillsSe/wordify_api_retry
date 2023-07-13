package com.wordify.api.dto.payloads.params;

public class StringParam implements ICustomParam {
    private String value;

    public StringParam(String value) {
        this.value = value;
    }
    public Object getValue(){
        return value;
    }
}

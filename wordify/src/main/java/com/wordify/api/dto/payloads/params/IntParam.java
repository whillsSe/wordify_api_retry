package com.wordify.api.dto.payloads.params;

public class IntParam implements ICustomParam {
    private int value;
    public IntParam(int value) {
        this.value = value;
    }
    public Object getValue(){
        return value;
    }
}

package com.wordify.api.dto.params;

public class IntParam implements ICustomParam {
    private int value;
    public IntParam(int value) {
        this.value = value;
    }
    public Object getValue(){
        return value;
    }
}

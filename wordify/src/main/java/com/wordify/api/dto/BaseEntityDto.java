package com.wordify.api.dto;

public class BaseEntityDto {
    //Word,Phonetic,Tagといった、Create,Readのみ行うマスタ系DBにしてる要素。
    //情報だけ取得できればよく、更新日等の情報は不要であるため。
    String id;
    String Value;
    
    public BaseEntityDto(String id, String value) {
        this.id = id;
        Value = value;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }
    
}

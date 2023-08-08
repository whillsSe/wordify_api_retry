package com.wordify.api.dto.payloads;

public class EntryUpdatePayload extends EntryRegistrationPayload {
    private int entryId;
   
    public int getEntryId() {
        return entryId;
    }
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
    
}
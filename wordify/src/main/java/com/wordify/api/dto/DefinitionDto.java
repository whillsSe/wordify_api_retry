package com.wordify.api.dto;

import java.util.List;

public class DefinitionDto {
    private int id;
    private int authorId;
    private MeaningDto meaning;
    private List<ExampleDto> examples;
    private List<TagDto> tags;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public MeaningDto getMeaning() {
        return meaning;
    }
    public void setMeaning(MeaningDto meaning) {
        this.meaning = meaning;
    }
    public List<ExampleDto> getExamples() {
        return examples;
    }
    public void setExamples(List<ExampleDto> examples) {
        this.examples = examples;
    }
    public List<TagDto> getTags() {
        return tags;
    }
    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}

package com.algolia.search.models.indexing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

public class Alternative {
  public String getType() {
    return type;
  }

  public Alternative setType(String type) {
    this.type = type;
    return this;
  }

  public List<String> getWords() {
    return words;
  }

  public Alternative setWords(List<String> words) {
    this.words = words;
    return this;
  }

  public Long getTypos() {
    return typos;
  }

  public Alternative setTypos(Long typos) {
    this.typos = typos;
    return this;
  }

  public Long getOffset() {
    return offset;
  }

  public Alternative setOffset(Long offset) {
    this.offset = offset;
    return this;
  }

  public Long getLength() {
    return length;
  }

  public Alternative setLength(Long length) {
    this.length = length;
    return this;
  }

  @JsonDeserialize(using = FlatListDeserializer.class)
  private String type;

  private List<String> words;
  private Long typos;
  private Long offset;
  private Long length;
}

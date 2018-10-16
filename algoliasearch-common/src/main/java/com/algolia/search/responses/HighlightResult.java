package com.algolia.search.responses;

import java.io.Serializable;
import java.util.List;

public class HighlightResult implements Serializable {
  private String value;
  private String matchLevel;
  private List<String> matchedWords;
  private boolean fullyHighlighted;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getMatchLevel() {
    return matchLevel;
  }

  public void setMatchLevel(String matchLevel) {
    this.matchLevel = matchLevel;
  }

  public List<String> getMatchedWords() {
    return matchedWords;
  }

  public void setMatchedWords(List<String> matchedWords) {
    this.matchedWords = matchedWords;
  }

  public boolean isFullyHighlighted() {
    return fullyHighlighted;
  }

  public void setFullyHighlighted(boolean fullyHighlighted) {
    this.fullyHighlighted = fullyHighlighted;
  }
}

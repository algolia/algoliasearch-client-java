package com.algolia.search.inputs.synonym;

import java.util.List;

public class AltCorrection2 implements AbstractSynonym {

  private String objectID;
  private String word;
  private List<String> corrections;

  @Override
  public String getObjectID() {
    return null;
  }

  public AltCorrection2 setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return "altCorrection2";
  }

  public String getWord() {
    return word;
  }

  public AltCorrection2 setWord(String word) {
    this.word = word;
    return this;
  }

  public List<String> getCorrections() {
    return corrections;
  }

  public AltCorrection2 setCorrections(List<String> corrections) {
    this.corrections = corrections;
    return this;
  }
}

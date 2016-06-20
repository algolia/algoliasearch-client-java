package com.algolia.search.inputs.synonym;

import java.util.List;

public class AltCorrection1 implements AbstractSynonym {

  private String objectID;
  private String word;
  private List<String> corrections;

  @Override
  public String getObjectID() {
    return null;
  }

  public AltCorrection1 setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return "altCorrection1";
  }

  public String getWord() {
    return word;
  }

  public AltCorrection1 setWord(String word) {
    this.word = word;
    return this;
  }

  public List<String> getCorrections() {
    return corrections;
  }

  public AltCorrection1 setCorrections(List<String> corrections) {
    this.corrections = corrections;
    return this;
  }
}

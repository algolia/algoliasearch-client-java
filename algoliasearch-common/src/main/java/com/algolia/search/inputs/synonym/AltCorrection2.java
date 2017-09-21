package com.algolia.search.inputs.synonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AltCorrection2 implements AbstractSynonym {

  private String objectID;
  private String word;
  private List<String> corrections;

  @Override
  public String getObjectID() {
    return objectID;
  }

  public AltCorrection2 setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return SynonymType.ALT_CORRECTION_2;
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

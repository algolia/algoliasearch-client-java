package com.algolia.search.inputs.synonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AltCorrection1 implements AbstractSynonym {

  private String objectID;
  private String word;
  private List<String> corrections;

  @Override
  public String getObjectID() {
    return objectID;
  }

  public AltCorrection1 setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return SynonymType.ALT_CORRECTION_1;
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

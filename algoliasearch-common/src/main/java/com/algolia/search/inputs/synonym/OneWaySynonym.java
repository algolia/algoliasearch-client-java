package com.algolia.search.inputs.synonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneWaySynonym implements AbstractSynonym {

  private String objectID;
  private String input;
  private List<String> synonyms;

  @Override
  public String getObjectID() {
    return objectID;
  }

  public OneWaySynonym setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return SynonymType.ONE_WAY_SYNONYM;
  }

  public String getInput() {
    return input;
  }

  public OneWaySynonym setInput(String input) {
    this.input = input;
    return this;
  }

  public List<String> getSynonyms() {
    return synonyms;
  }

  public OneWaySynonym setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }
}

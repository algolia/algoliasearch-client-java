package com.algolia.search.inputs.synonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Synonym implements AbstractSynonym {

  private String objectID;
  private List<String> synonyms;

  public Synonym() {}

  public Synonym(List<String> synonyms) {
    this.synonyms = synonyms;
  }

  @Override
  public String getObjectID() {
    return objectID;
  }

  public Synonym setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getType() {
    return SynonymType.SYNONYM;
  }

  public List<String> getSynonyms() {
    return synonyms;
  }

  public Synonym setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }
}

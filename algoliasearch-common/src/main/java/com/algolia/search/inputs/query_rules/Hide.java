package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hide {

  private String objectID;

  public Hide(String objectID) {
    this.objectID = objectID;
  }

  public Hide() {}

  public String getObjectID() {
    return objectID;
  }

  public Hide setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }
}

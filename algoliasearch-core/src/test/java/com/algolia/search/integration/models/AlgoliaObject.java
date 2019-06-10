package com.algolia.search.integration.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlgoliaObject implements Serializable {

  public AlgoliaObject() {}

  public AlgoliaObject(String objectID, String attribute) {
    this.objectID = objectID;
    this.attribute = attribute;
  }

  public String getObjectID() {
    return objectID;
  }

  public AlgoliaObject setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getAttribute() {
    return attribute;
  }

  public AlgoliaObject setAttribute(String attribute) {
    this.attribute = attribute;
    return this;
  }

  private String objectID;
  private String attribute;
}

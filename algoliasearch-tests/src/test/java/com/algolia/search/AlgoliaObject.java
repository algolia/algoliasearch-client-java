package com.algolia.search;

public class AlgoliaObject {

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

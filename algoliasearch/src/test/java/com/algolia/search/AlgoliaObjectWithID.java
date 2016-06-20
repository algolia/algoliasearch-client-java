package com.algolia.search;

public class AlgoliaObjectWithID extends AlgoliaObject {

  private String objectID;

  public AlgoliaObjectWithID() {
    super();
  }

  public AlgoliaObjectWithID(String name, int age) {
    super(name, age);
  }

  public AlgoliaObjectWithID(String objectID, String name, int age) {
    super(name, age);
    this.objectID = objectID;
  }

  public String getObjectID() {
    return objectID;
  }

  public AlgoliaObjectWithID setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }
}

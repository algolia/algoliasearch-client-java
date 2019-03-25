package com.algolia.search.integration.models;

import java.io.Serializable;

public class ObjectToBatch implements Serializable {

  // dummy constructor for deserialization
  public ObjectToBatch() {}

  public ObjectToBatch(String objectID) {
    this.objectID = objectID;
  }

  public ObjectToBatch(String objectID, String key) {
    this.objectID = objectID;
    this.key = key;
  }

  public String getObjectID() {
    return objectID;
  }

  public ObjectToBatch setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getKey() {
    return key;
  }

  public ObjectToBatch setKey(String key) {
    this.key = key;
    return this;
  }

  private String objectID;
  private String key;
}

package com.algolia.search.models;

public class UpdateObjectResponse extends IndexingResponse {

  public String getObjectID() {
    return objectID;
  }

  public UpdateObjectResponse setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public UpdateObjectResponse setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private String objectID;
  private String updatedAt;
}

package com.algolia.search.models;

import java.time.ZonedDateTime;

public class UpdateObjectResponse extends IndexingResponse {

  public String getObjectID() {
    return objectID;
  }

  public UpdateObjectResponse setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public UpdateObjectResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private String objectID;
  private ZonedDateTime updatedAt;
}

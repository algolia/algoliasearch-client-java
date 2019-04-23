package com.algolia.search.models.indexing;

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

  @Override
  public String toString() {
    return "UpdateObjectResponse{"
        + "objectID='"
        + objectID
        + '\''
        + ", updatedAt="
        + updatedAt
        + '}';
  }

  private String objectID;
  private ZonedDateTime updatedAt;
}

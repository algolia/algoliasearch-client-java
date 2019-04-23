package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class RemoveUserIdResponse implements Serializable {

  public String getUserId() {
    return userId;
  }

  public RemoveUserIdResponse setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public ZonedDateTime getDeletedAt() {
    return deletedAt;
  }

  public RemoveUserIdResponse setDeletedAt(ZonedDateTime deletedAt) {
    this.deletedAt = deletedAt;
    return this;
  }

  @Override
  public String toString() {
    return "RemoveUserIdResponse{" + "userId='" + userId + '\'' + ", deletedAt=" + deletedAt + '}';
  }

  private String userId;
  private ZonedDateTime deletedAt;
}

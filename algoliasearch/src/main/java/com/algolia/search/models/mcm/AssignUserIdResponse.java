package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AssignUserIdResponse implements Serializable {

  private String userId;
  private ZonedDateTime createdAt;

  public String getUserId() {
    return userId;
  }

  public AssignUserIdResponse setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public AssignUserIdResponse setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}

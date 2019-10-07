package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AssignUserIdsResponse implements Serializable {

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public AssignUserIdsResponse setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  private ZonedDateTime createdAt;
}

package com.algolia.search.models.personalization;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Deprecated
public class SetStrategyResponse implements Serializable {
  private ZonedDateTime updatedAt;

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SetStrategyResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }
}

package com.algolia.search.models;

import java.time.OffsetDateTime;

public class SaveRuleResponse extends IndexingResponse {
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SaveRuleResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private OffsetDateTime updatedAt;
}

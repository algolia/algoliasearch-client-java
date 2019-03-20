package com.algolia.search.models.rules;

import com.algolia.search.models.indexing.IndexingResponse;
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

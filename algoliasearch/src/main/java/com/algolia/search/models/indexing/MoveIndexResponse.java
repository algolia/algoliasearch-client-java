package com.algolia.search.models.indexing;

import java.time.OffsetDateTime;

public class MoveIndexResponse extends IndexingResponse {
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public MoveIndexResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private OffsetDateTime updatedAt;
}

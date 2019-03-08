package com.algolia.search.models;

import java.time.OffsetDateTime;

public class ClearSynonymsResponse extends IndexingResponse {

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public ClearSynonymsResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private OffsetDateTime updatedAt;
}

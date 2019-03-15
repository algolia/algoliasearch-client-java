package com.algolia.search.models;

import java.time.OffsetDateTime;

public class CopyToResponse extends IndexingResponse {

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public CopyToResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private OffsetDateTime updatedAt;
}

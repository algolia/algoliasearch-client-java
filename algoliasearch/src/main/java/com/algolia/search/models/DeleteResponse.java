package com.algolia.search.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class DeleteResponse extends IndexingResponse implements Serializable {

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public DeleteResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private ZonedDateTime updatedAt;
}

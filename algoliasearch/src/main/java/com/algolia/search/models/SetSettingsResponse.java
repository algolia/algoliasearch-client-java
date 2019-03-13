package com.algolia.search.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class SetSettingsResponse extends IndexingResponse
    implements Serializable, IAlgoliaWaitableResponse {

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SetSettingsResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private ZonedDateTime updatedAt;
}

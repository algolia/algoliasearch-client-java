package com.algolia.search.models.settings;

import com.algolia.search.models.WaitableResponse;
import com.algolia.search.models.indexing.IndexingResponse;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class SetSettingsResponse extends IndexingResponse
    implements Serializable, WaitableResponse {

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SetSettingsResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  @Override
  public String toString() {
    return "SetSettingsResponse{" + "updatedAt=" + updatedAt + '}';
  }

  private ZonedDateTime updatedAt;
}

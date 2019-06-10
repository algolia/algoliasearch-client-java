package com.algolia.search.models.synonyms;

import com.algolia.search.models.indexing.IndexingResponse;
import java.time.OffsetDateTime;

public class SaveSynonymResponse extends IndexingResponse {

  public String getId() {
    return id;
  }

  public SaveSynonymResponse setId(String id) {
    this.id = id;
    return this;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SaveSynonymResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  private String id;
  private OffsetDateTime updatedAt;
}

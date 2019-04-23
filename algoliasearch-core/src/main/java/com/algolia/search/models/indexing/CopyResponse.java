package com.algolia.search.models.indexing;

import com.algolia.search.models.WaitableResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.function.BiConsumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyResponse implements Serializable, WaitableResponse {

  public Long getTaskID() {
    return taskID;
  }

  public CopyResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  public void setWaitConsumer(BiConsumer<String, Long> waitConsumer) {
    this.waitConsumer = waitConsumer;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public CopyResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public String getIndexName() {
    return indexName;
  }

  public CopyResponse setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  private OffsetDateTime updatedAt;
  private BiConsumer<String, Long> waitConsumer;
  private Long taskID;
  private String indexName;

  @Override
  public String toString() {
    return "CopyResponse{"
        + "updatedAt="
        + updatedAt
        + ", taskID="
        + taskID
        + ", indexName='"
        + indexName
        + '\''
        + '}';
  }

  @Override
  public void waitTask() {
    waitConsumer.accept(indexName, taskID);
  }
}

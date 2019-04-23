package com.algolia.search.models.indexing;

import com.algolia.search.models.WaitableResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoveIndexResponse implements Serializable, WaitableResponse {
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public MoveIndexResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public String getIndexName() {
    return indexName;
  }

  public MoveIndexResponse setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  public MoveIndexResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  public void setWaitBiConsumer(BiConsumer<String, Long> waitBiConsumer) {
    this.waitBiConsumer = waitBiConsumer;
  }

  public void setWaitConsumer(Consumer<Long> waitConsumer) {
    this.waitConsumer = waitConsumer;
  }

  private OffsetDateTime updatedAt;
  private String indexName;
  private BiConsumer<String, Long> waitBiConsumer;
  private Consumer<Long> waitConsumer;
  private Long taskID;

  @Override
  public String toString() {
    return "MoveIndexResponse{"
        + "updatedAt="
        + updatedAt
        + ", indexName='"
        + indexName
        + '\''
        + ", taskID="
        + taskID
        + '}';
  }

  @Override
  public void waitTask() {
    if (waitBiConsumer != null) {
      waitBiConsumer.accept(indexName, taskID);
    } else {
      waitConsumer.accept(taskID);
    }
  }
}

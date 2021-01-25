package com.algolia.search.models.dictionary;

import com.algolia.search.models.WaitableResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.function.Consumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryResponse implements WaitableResponse, Serializable {

  private Long taskID;
  private ZonedDateTime updatedAt;
  private Consumer<Long> waitConsumer;

  public Long getTaskID() {
    return taskID;
  }

  public DictionaryResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public DictionaryResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public void setWaitConsumer(Consumer<Long> waitConsumer) {
    this.waitConsumer = waitConsumer;
  }

  @Override
  public void waitTask() {
    waitConsumer.accept(getTaskID());
  }
}

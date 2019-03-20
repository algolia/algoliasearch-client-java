package com.algolia.search.models.indexing;

import com.algolia.search.models.IAlgoliaWaitableResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.function.Consumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("WeakerAccess")
public class IndexingResponse implements IAlgoliaWaitableResponse, Serializable {

  private Long taskID;
  private Consumer<Long> waitConsumer;

  public Long getTaskID() {
    return taskID;
  }

  public IndexingResponse setTaskID(Long taskID) {
    this.taskID = taskID;
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

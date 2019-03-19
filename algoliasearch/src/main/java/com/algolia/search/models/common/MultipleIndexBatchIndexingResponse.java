package com.algolia.search.models.common;

import com.algolia.search.models.IAlgoliaWaitableResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleIndexBatchIndexingResponse implements Serializable, IAlgoliaWaitableResponse {

  public List<String> getObjectIDs() {
    return objectIDs;
  }

  public MultipleIndexBatchIndexingResponse setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  public Map<String, Long> getTaskID() {
    return taskID;
  }

  public MultipleIndexBatchIndexingResponse setTaskID(Map<String, Long> taskID) {
    this.taskID = taskID;
    return this;
  }

  public void setWaitConsumer(BiConsumer<String, Long> waitConsumer) {
    this.waitConsumer = waitConsumer;
  }

  private BiConsumer<String, Long> waitConsumer;
  private List<String> objectIDs;
  private Map<String, Long> taskID;

  @Override
  public void waitTask() {
    for (Map.Entry<String, Long> entry : taskID.entrySet()) {
      waitConsumer.accept(entry.getKey(), entry.getValue());
    }
  }
}

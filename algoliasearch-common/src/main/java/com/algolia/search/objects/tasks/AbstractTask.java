package com.algolia.search.objects.tasks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractTask<T> {

  @JsonIgnore
  protected String indexName;

  protected T taskID;

  public T getTaskID() {
    return taskID;
  }

  public AbstractTask<T> setTaskID(T taskID) {
    this.taskID = taskID;
    return this;
  }

  abstract public Long getTaskIDToWaitFor();

  public AbstractTask<T> setIndex(String indexName) {
    this.indexName = indexName;
    return this;
  }

  public String getIndexName() {
    return indexName;
  }
}

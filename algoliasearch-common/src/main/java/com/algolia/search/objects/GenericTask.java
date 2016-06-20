package com.algolia.search.objects;

import com.algolia.search.APIClient;
import com.algolia.search.exceptions.AlgoliaException;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class GenericTask<T> {

  private T taskID;

  @JsonIgnore
  protected APIClient apiClient;

  @JsonIgnore
  protected String indexName;

  public GenericTask<T> setAttributes(String indexName, APIClient apiClient) {
    this.apiClient = apiClient;
    this.indexName = indexName;

    return this;
  }

  /**
   * Wait for the completion of this task
   *
   * @throws AlgoliaException
   */
  public void waitForCompletion() throws AlgoliaException {
    apiClient.waitTask(indexName, this, 100);
  }

  /**
   * Wait for the completion of this task
   *
   * @param timeToWait the time to wait in milliseconds
   * @throws AlgoliaException
   */
  public void waitForCompletion(long timeToWait) throws AlgoliaException {
    apiClient.waitTask(indexName, this, timeToWait);
  }

  public T getTaskID() {
    return taskID;
  }

  public GenericTask setTaskID(T taskID) {
    this.taskID = taskID;
    return this;
  }

  abstract public Long getTaskIDToWaitFor();
}

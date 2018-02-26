package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.tasks.AbstractTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GenericTask<T> extends AbstractTask<T> {

  @JsonIgnore APIClient apiClient;

  @JsonIgnore
  public GenericTask<T> setAPIClient(APIClient apiClient) {
    this.apiClient = apiClient;
    return this;
  }

  @Override
  public GenericTask<T> setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }

  /** Wait for the completion of this task */
  public void waitForCompletion() throws AlgoliaException {
    apiClient.waitTask(this, 100);
  }

  /**
   * Wait for the completion of this task
   *
   * @param timeToWait the time to wait in milliseconds
   */
  public void waitForCompletion(long timeToWait) throws AlgoliaException {
    apiClient.waitTask(this, timeToWait);
  }

  @Override
  public GenericTask<T> setTaskID(T taskID) {
    super.setTaskID(taskID);
    return this;
  }
}

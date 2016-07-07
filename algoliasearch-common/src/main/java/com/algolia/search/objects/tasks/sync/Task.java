package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;
import com.algolia.search.exceptions.AlgoliaException;

public class Task extends GenericTask<Long> {

  public Task setAPIClient(APIClient apiClient) {
    this.apiClient = apiClient;
    return this;
  }

  /**
   * Wait for the completion of this task
   *
   * @throws AlgoliaException
   */
  public void waitForCompletion() throws AlgoliaException {
    apiClient.waitTask(this, 100);
  }

  /**
   * Wait for the completion of this task
   *
   * @param timeToWait the time to wait in milliseconds
   * @throws AlgoliaException
   */
  public void waitForCompletion(long timeToWait) throws AlgoliaException {
    apiClient.waitTask(this, timeToWait);
  }

  @Override
  public Long getTaskIDToWaitFor() {
    return getTaskID();
  }

  @Override
  public Task setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}

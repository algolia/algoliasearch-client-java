package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task extends GenericTask<Long> {

  @Override
  @JsonIgnore
  public Task setAPIClient(APIClient apiClient) {
    super.setAPIClient(apiClient);
    return this;
  }

  /** Wait for the completion of this task */
  public void waitForCompletion() throws AlgoliaException {
    waitForCompletion(RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task
   *
   * @param timeToWait the time to wait in milliseconds
   */
  public void waitForCompletion(long timeToWait) throws AlgoliaException {
    waitForCompletion(timeToWait, RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task
   *
   * @param requestOptions Options to pass to this request
   */
  public void waitForCompletion(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    waitForCompletion(100, requestOptions);
  }

  /**
   * Wait for the completion of this task
   *
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  public void waitForCompletion(long timeToWait, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    apiClient.waitTask(this, timeToWait, requestOptions);
  }

  @Override
  @JsonIgnore
  public Long getTaskIDToWaitFor() {
    return getTaskID();
  }

  @Override
  @JsonIgnore
  public Task setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }

  @Override
  public Task setTaskID(Long taskID) {
    super.setTaskID(taskID);
    return this;
  }

  @Override
  public String toString() {
    return "Task{"
        + "apiClient="
        + apiClient
        + ", indexName='"
        + indexName
        + '\''
        + ", taskID="
        + taskID
        + '}';
  }
}

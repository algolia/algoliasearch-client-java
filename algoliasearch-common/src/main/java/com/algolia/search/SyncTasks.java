package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

public interface SyncTasks<T> extends SyncBaseIndex<T> {

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   */
  default void waitTask(@Nonnull Task task, long timeToWait) throws AlgoliaException {
    waitTask(task, timeToWait, RequestOptions.empty);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull Task task, long timeToWait, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);

    getApiClient().waitTask(task, timeToWait, requestOptions);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   */
  default void waitTask(@Nonnull Task task) throws AlgoliaException {
    getApiClient().waitTask(task, 100);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param taskID ID of the task to wait for
   */
  default void waitTask(@Nonnull Long taskID) throws AlgoliaException {
    Task task = new Task().setAPIClient(getApiClient()).setIndex(getName()).setTaskID(taskID);
    waitTask(task);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param taskID ID of the task to wait for
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull Long taskID, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Task task = new Task().setAPIClient(getApiClient()).setIndex(getName()).setTaskID(taskID);
    waitTask(task, requestOptions);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull Task task, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().waitTask(task, 100, requestOptions);
  }
}

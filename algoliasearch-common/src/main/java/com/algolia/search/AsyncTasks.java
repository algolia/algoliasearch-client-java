package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

public interface AsyncTasks<T> extends AsyncBaseIndex<T> {

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   */
  default void waitTask(@Nonnull AsyncTask task, long timeToWait) {
    waitTask(task, timeToWait);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(
      @Nonnull AsyncTask task, long timeToWait, @Nonnull RequestOptions requestOptions) {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    getApiClient().waitTask(task, timeToWait, requestOptions);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   */
  default void waitTask(@Nonnull AsyncTask task) {
    waitTask(task, RequestOptions.empty);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param taskID ID of the task to wait for
   */
  default void waitTask(@Nonnull Long taskID) throws AlgoliaException {
    AsyncTask task = new AsyncTask().setIndex(getName()).setTaskID(taskID);
    waitTask(task);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull AsyncTask task, @Nonnull RequestOptions requestOptions) {
    getApiClient().waitTask(task, 100, requestOptions);
  }
}

package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.TaskStatusResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

class TaskUtils {

  private TaskUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Wait for a task to complete before executing the next line of code
   *
   * @param taskId The Algolia taskID
   * @param timeToWait The time to wait between each call
   * @param requestOptions Options to pass to this request
   * @param getTaskAsync The function to retrieve the task status
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  static void waitTask(
      long taskId,
      long timeToWait,
      RequestOptions requestOptions,
      BiFunction<Long, RequestOptions, CompletableFuture<TaskStatusResponse>> getTaskAsync) {
    while (true) {

      TaskStatusResponse response;

      try {
        response = getTaskAsync.apply(taskId, requestOptions).get();
      } catch (InterruptedException | ExecutionException e) {
        // If the future was cancelled or the thread was interrupted or future completed
        // exceptionally
        // We stop
        break;
      }

      if (Objects.equals("published", response.getStatus())) return;

      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException ignored) {
        // Restore interrupted state...
        Thread.currentThread().interrupt();
      }

      timeToWait *= 2;
      timeToWait = Math.min(timeToWait, Defaults.MAX_TIME_MS_TO_WAIT);
    }
  }
}

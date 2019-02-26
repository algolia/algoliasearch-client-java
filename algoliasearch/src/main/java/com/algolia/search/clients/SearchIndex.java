package com.algolia.search.clients;

import com.algolia.search.Defaults;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.TaskStatusResponse;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.transport.IHttpTransport;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class SearchIndex<T> {

  private final IHttpTransport transport;
  private final AlgoliaConfig config;
  private final String urlEncodedIndexName;
  private final String indexName;
  private final Class<T> klass;

  SearchIndex(IHttpTransport transport, AlgoliaConfig config, String indexName, Class<T> klass) {
    this.transport = transport;
    this.config = config;
    this.indexName = indexName;
    this.urlEncodedIndexName = QueryStringHelper.urlEncodeUTF8(indexName);
    this.klass = klass;
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   */
  public IndexSettings setSettings(@Nonnull IndexSettings settings) {
    return LaunderThrowable.unwrap(setSettingsAsync(settings));
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   */
  public CompletableFuture<IndexSettings> setSettingsAsync(@Nonnull IndexSettings settings) {
    return setSettingsAsync(settings, new RequestOptions());
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @param forwardToReplicas if true forward the settings to the replicas
   */
  public CompletableFuture<IndexSettings> setSettingsAsync(
      @Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return setSettingsAsync(settings, requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @param forwardToReplicas if true forward the settings to the replicas
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<IndexSettings> setSettingsAsync(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return setSettingsAsync(settings, requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<IndexSettings> setSettingsAsync(
      @Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions) {
    return transport
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + urlEncodedIndexName + "/settings",
            CallType.WRITE,
            settings,
            IndexSettings.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   */
  public CompletableFuture<TaskStatusResponse> getTaskAsync(@Nonnull Long taskID) {
    return getTaskAsync(taskID, new RequestOptions());
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<TaskStatusResponse> getTaskAsync(
      @Nonnull Long taskID, @Nonnull RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/task/" + taskID,
        CallType.READ,
        null,
        TaskStatusResponse.class,
        requestOptions);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param taskId The Algolia taskID
   */
  public void waitTask(long taskId) {
    waitTask(taskId, 100, new RequestOptions());
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param taskId The Algolia taskID
   * @param timeToWait The time to wait between each call
   * @param requestOptions Options to pass to this request
   */
  public void waitTask(long taskId, long timeToWait, RequestOptions requestOptions) {
    while (true) {

      TaskStatusResponse response;

      try {
        response = getTaskAsync(taskId, requestOptions).get();
      } catch (InterruptedException | ExecutionException e) {
        // If the future was cancelled or the thread was interrupted or future completed
        // exceptionally
        // We stop
        break;
      }

      if (java.util.Objects.equals("published", response.getStatus())) {
        return;
      }

      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException ignored) {
      }

      timeToWait *= 2;
      timeToWait =
          timeToWait > Defaults.MAX_TIME_MS_TO_WAIT ? Defaults.MAX_TIME_MS_TO_WAIT : timeToWait;
    }
  }
}

package com.algolia.search.clients;

import static java.util.stream.Collectors.toList;

import com.algolia.search.Defaults;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.models.*;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.transport.HttpTransport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class SearchIndex<T> {

  private final HttpTransport transport;
  private final AlgoliaConfig config;
  private final String urlEncodedIndexName;
  private final String indexName;
  private final Class<T> klass;

  SearchIndex(HttpTransport transport, AlgoliaConfig config, String indexName, Class<T> klass) {
    this.transport = transport;
    this.config = config;
    this.indexName = indexName;
    this.urlEncodedIndexName = QueryStringHelper.urlEncodeUTF8(indexName);
    this.klass = klass;
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   */
  public SearchResult<T> search(@Nonnull Query query) {
    return LaunderThrowable.unwrap(searchAsync(query));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @param requestOptions Options to pass to this request
   */
  public SearchResult<T> search(@Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchAsync(query, requestOptions));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   */
  public CompletableFuture<SearchResult<T>> searchAsync(@Nonnull Query query) {
    return searchAsync(query, null);
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<SearchResult<T>> searchAsync(
      @Nonnull Query query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A query key is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/query",
            CallType.READ,
            query,
            SearchResult.class,
            klass,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            config.getExecutor());
  }

  /**
   * Split records into smaller chunks before sending them to the API asynchronously
   *
   * @param data The data to send and chunk
   * @param actionType The action type of the batch
   * @param requestOptions Options to pass to this request
   */
  CompletableFuture<BatchIndexingResponse> splitIntoBatchesAsync(
      @Nonnull Iterable<T> data, @Nonnull String actionType, RequestOptions requestOptions) {

    Objects.requireNonNull(data, "Data are required.");
    Objects.requireNonNull(actionType, "An action type is required.");

    List<CompletableFuture<BatchResponse>> futures = new ArrayList<>();
    List<T> records = new ArrayList<>();

    for (T item : data) {

      if (records.size() == config.getBatchSize()) {
        BatchRequest<T> request = new BatchRequest<>(actionType, records);
        CompletableFuture<BatchResponse> batch = batchAsync(request, requestOptions);
        futures.add(batch);
        records.clear();
      }

      records.add(item);
    }

    if (records.size() > 0) {
      BatchRequest<T> request = new BatchRequest<>(actionType, records);
      CompletableFuture<BatchResponse> batch = batchAsync(request, requestOptions);
      futures.add(batch);
    }

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenComposeAsync(
            v -> {
              List<BatchResponse> resp =
                  futures.stream().map(CompletableFuture::join).collect(toList());

              return CompletableFuture.completedFuture(new BatchIndexingResponse(resp));
            },
            config.getExecutor());
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request -
   */
  public CompletableFuture<BatchResponse> batchAsync(@Nonnull BatchRequest<T> request) {
    return batchAsync(request, null);
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<BatchResponse> batchAsync(
      @Nonnull BatchRequest<T> request, RequestOptions requestOptions) {

    Objects.requireNonNull(request, "A BatchRequest key is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/batch",
            CallType.WRITE,
            request,
            BatchResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   */
  public DeleteResponse deleteObject(@Nonnull String objectID) {
    return LaunderThrowable.unwrap(deleteObjectAsync(objectID, null));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @param requestOptions Options to pass to this request
   */
  public DeleteResponse deleteObject(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(deleteObjectAsync(objectID, requestOptions));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   */
  public CompletableFuture<DeleteResponse> deleteObjectAsync(@Nonnull String objectID) {
    return deleteObjectAsync(objectID, null);
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> deleteObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + urlEncodedIndexName + "/" + objectID,
            CallType.WRITE,
            null,
            DeleteResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   */
  public DeleteResponse clearObjects() {
    return LaunderThrowable.unwrap(clearObjectsAsync(null));
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @param requestOptions Options to pass to this request
   */
  public DeleteResponse clearObjects(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(clearObjectsAsync(requestOptions));
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   */
  public CompletableFuture<DeleteResponse> clearObjectsAsync() {
    return clearObjectsAsync(null);
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> clearObjectsAsync(RequestOptions requestOptions) {

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/clear",
            CallType.WRITE,
            null,
            DeleteResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   */
  public SetSettingsResponse setSettings(@Nonnull IndexSettings settings) {
    return LaunderThrowable.unwrap(setSettingsAsync(settings));
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   */
  public CompletableFuture<SetSettingsResponse> setSettingsAsync(@Nonnull IndexSettings settings) {
    return setSettingsAsync(settings, new RequestOptions());
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @param forwardToReplicas if true forward the settings to the replicas
   */
  public CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {

    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

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
  public CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return setSettingsAsync(settings, requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(settings, "Index settings are required.");

    return transport
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + urlEncodedIndexName + "/settings",
            CallType.WRITE,
            settings,
            SetSettingsResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /** Get the settings of an index. */
  public IndexSettings getSettings() {
    return LaunderThrowable.unwrap(getSettingsAsync(null));
  }

  /**
   * Get the settings of an index.
   *
   * @param requestOptions Options to pass to this request
   */
  public IndexSettings getSettings(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getSettingsAsync(requestOptions));
  }

  /** Get the settings of an index. */
  public CompletableFuture<IndexSettings> getSettingsAsync() {
    return getSettingsAsync(null);
  }

  /**
   * Get the settings of an index.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<IndexSettings> getSettingsAsync(RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/settings",
        CallType.READ,
        null,
        IndexSettings.class,
        requestOptions);
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   */
  public Rule getRule(@Nonnull String objectID) {
    return LaunderThrowable.unwrap(getRuleAsync(objectID));
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   */
  public Rule getRule(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getRuleAsync(objectID, requestOptions));
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   */
  public CompletableFuture<Rule> getRuleAsync(@Nonnull String objectID) {
    return getRuleAsync(objectID, null);
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<Rule> getRuleAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The rule ID is required.");

    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/rules/" + objectID,
        CallType.READ,
        null,
        Rule.class,
        requestOptions);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   */
  public SearchResult<Rule> searchRule(@Nonnull RuleQuery query) {
    return LaunderThrowable.unwrap(searchRuleAsync(query, null));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   */
  public SearchResult<Rule> searchRule(@Nonnull RuleQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchRuleAsync(query, requestOptions));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   */
  public CompletableFuture<SearchResult<Rule>> searchRuleAsync(@Nonnull RuleQuery query) {
    return searchRuleAsync(query, null);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<SearchResult<Rule>> searchRuleAsync(
      @Nonnull RuleQuery query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A search query is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/rules/search",
            CallType.READ,
            null,
            SearchResult.class,
            Rule.class,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<Rule>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            config.getExecutor());
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   */
  public CompletableFuture<TaskStatusResponse> getTaskAsync(long taskID) {
    return getTaskAsync(taskID, null);
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<TaskStatusResponse> getTaskAsync(
      long taskID, RequestOptions requestOptions) {
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
    waitTask(taskId, 100, null);
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

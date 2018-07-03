package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.http.AlgoliaRequestKind;
import com.algolia.search.http.AsyncAlgoliaHttpClient;
import com.algolia.search.http.HttpMethod;
import com.algolia.search.inputs.*;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.batch.*;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.async.*;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class AsyncAPIClient {

  /** Constructor & protected stuff */
  protected final AsyncAlgoliaHttpClient httpClient;

  protected final AsyncAPIClientConfiguration configuration;
  protected final Executor executor;

  AsyncAPIClient(AsyncAlgoliaHttpClient httpClient, AsyncAPIClientConfiguration configuration) {
    this.httpClient = httpClient;
    this.configuration = configuration;
    this.executor = configuration.getExecutorService();
  }

  /** Close the internal HTTP client */
  public void close() throws AlgoliaException {
    this.httpClient.close();
  }

  /*
   All public method
  */

  /**
   * List all existing indexes
   *
   * @return A List of the indices and their metadata
   */
  public CompletableFuture<List<Index.Attributes>> listIndices() {
    return listIndices(RequestOptions.empty);
  }

  /**
   * List all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   */
  public CompletableFuture<List<Index.Attributes>> listIndices(
      @Nonnull RequestOptions requestOptions) {
    CompletableFuture<Indices> result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes"),
                requestOptions,
                Indices.class));

    return result.thenApply(Indices::getItems);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name name of the index
   * @param klass class of the object in this index
   * @param <T> the type of the objects in this index
   * @return The initialized index
   */
  public <T> AsyncIndex<T> initIndex(@Nonnull String name, @Nonnull Class<T> klass) {
    return new AsyncIndex<>(name, klass, this);
  }

  public AsyncAnalytics initAnalytics() {
    return new AsyncAnalytics(this);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name name of the index
   * @return The initialized index
   */
  public AsyncIndex<?> initIndex(@Nonnull String name) {
    return new AsyncIndex<>(name, Object.class, this);
  }

  /**
   * Moves an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> moveIndex(
      @Nonnull String srcIndexName, @Nonnull String dstIndexName) {
    return moveIndex(srcIndexName, dstIndexName, RequestOptions.empty);
  }

  /**
   * Moves an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> moveIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      @Nonnull RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", srcIndexName, "operation"),
                    requestOptions,
                    AsyncTask.class)
                .setData(new OperationOnIndex("move", dstIndexName)))
        .thenApply(s -> s.setIndex(srcIndexName));
  }

  /**
   * Copy an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> copyIndex(
      @Nonnull String srcIndexName, @Nonnull String dstIndexName) {
    return copyIndex(srcIndexName, dstIndexName, null, RequestOptions.empty);
  }

  /**
   * Copy an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> copyIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      @Nonnull RequestOptions requestOptions) {
    return copyIndex(srcIndexName, dstIndexName, null, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scope the list of scope to copy
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> copyIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      List<String> scope,
      @Nonnull RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", srcIndexName, "operation"),
                    requestOptions,
                    AsyncTask.class)
                .setData(new OperationOnIndex("copy", dstIndexName, scope)))
        .thenApply(s -> s.setIndex(srcIndexName));
  }

  /**
   * Copy an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scope the list of scope to copy
   * @return The task associated
   */
  public CompletableFuture<AsyncTask> copyIndex(
      @Nonnull String srcIndexName, @Nonnull String dstIndexName, @Nonnull List<String> scope) {
    return copyIndex(srcIndexName, dstIndexName, scope, RequestOptions.empty);
  }

  /**
   * Return 10 last log entries.
   *
   * @return A List<Log>
   */
  public CompletableFuture<List<Log>> getLogs() {
    return getLogs(RequestOptions.empty);
  }

  /**
   * Return 10 last log entries.
   *
   * @param requestOptions Options to pass to this request
   * @return A List<Log>
   */
  public CompletableFuture<List<Log>> getLogs(@Nonnull RequestOptions requestOptions) {
    CompletableFuture<Logs> result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "logs"),
                requestOptions,
                Logs.class));

    return result.thenApply(Logs::getLogs);
  }

  /**
   * Return last logs entries
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry)
   * @param length Specify the maximum number of entries to retrieve starting at offset. Maximum
   *     allowed value: 1000
   * @param logType Specify the type of log to retrieve
   * @return The List of Logs
   */
  public CompletableFuture<List<Log>> getLogs(
      @Nonnull Integer offset, @Nonnull Integer length, @Nonnull LogType logType) {
    return getLogs(offset, length, logType, RequestOptions.empty);
  }

  /**
   * Return last logs entries
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry)
   * @param length Specify the maximum number of entries to retrieve starting at offset. Maximum
   *     allowed value: 1000
   * @param logType Specify the type of log to retrieve
   * @param requestOptions Options to pass to this request
   * @return The List of Logs
   */
  public CompletableFuture<List<Log>> getLogs(
      @Nonnull Integer offset,
      @Nonnull Integer length,
      @Nonnull LogType logType,
      @Nonnull RequestOptions requestOptions) {
    Preconditions.checkArgument(offset >= 0, "offset must be >= 0, was %s", offset);
    Preconditions.checkArgument(length >= 0, "length must be >= 0, was %s", length);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("offset", offset.toString());
    parameters.put("length", length.toString());
    parameters.put("type", logType.getName());

    CompletableFuture<Logs> result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.GET,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "logs"),
                    requestOptions,
                    Logs.class)
                .setParameters(parameters));

    return result.thenApply(Logs::getLogs);
  }

  /** Deprecated: use {@link #listApiKeys()} */
  @Deprecated
  public CompletableFuture<List<ApiKey>> listKeys() {
    return listApiKeys();
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @return A List of Keys
   */
  public CompletableFuture<List<ApiKey>> listApiKeys() {
    return listApiKeys(RequestOptions.empty);
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @return A List of Keys
   */
  public CompletableFuture<List<ApiKey>> listApiKeys(@Nonnull RequestOptions requestOptions) {
    CompletableFuture<ApiKeys> result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys"),
                requestOptions,
                ApiKeys.class));

    return result.thenApply(ApiKeys::getKeys);
  }

  /** Deprecated: use {@link #getApiKey(String)} */
  @Deprecated
  public CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) {
    return getApiKey(key);
  }

  /**
   * Get an Key from it's name
   *
   * @param key name of the key
   * @return the key
   */
  public CompletableFuture<Optional<ApiKey>> getApiKey(@Nonnull String key) {
    return getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get an Key from it's name
   *
   * @param key name of the key
   * @param requestOptions Options to pass to this request
   * @return the key
   */
  public CompletableFuture<Optional<ApiKey>> getApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys", key),
                requestOptions,
                ApiKey.class))
        .thenApply(Optional::ofNullable);
  }

  /** Deprecated: use {@link #deleteApiKey(String)} */
  @Deprecated
  public CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) {
    return deleteApiKey(key);
  }

  /**
   * Delete an existing key
   *
   * @param key name of the key
   */
  public CompletableFuture<DeleteKey> deleteApiKey(@Nonnull String key) {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete an existing key
   *
   * @param key name of the key
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteKey> deleteApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
            HttpMethod.DELETE,
            AlgoliaRequestKind.SEARCH_API_WRITE,
            Arrays.asList("1", "keys", key),
            requestOptions,
            DeleteKey.class));
  }

  /** Deprecated: use {@link #addApiKey(ApiKey)} */
  @Deprecated
  public CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) {
    return addApiKey(key);
  }

  /**
   * Create a new key
   *
   * @param key the key with the ACLs
   * @return the metadata of the key (such as it's name)
   */
  public CompletableFuture<CreateUpdateKey> addApiKey(@Nonnull ApiKey key) {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Create a new key
   *
   * @param key the key with the ACLs
   * @param requestOptions Options to pass to this request
   * @return the metadata of the key (such as it's name)
   */
  public CompletableFuture<CreateUpdateKey> addApiKey(
      @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys"),
                requestOptions,
                CreateUpdateKey.class)
            .setData(key));
  }

  /** Deprecated: use {@link #updateApiKey(String, ApiKey)} */
  @Deprecated
  public CompletableFuture<CreateUpdateKey> updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key);
  }

  /**
   * Update a key
   *
   * @param keyName name of the key to update
   * @param key the key with the ACLs
   * @return the metadata of the key (such as it's name)
   */
  public CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /**
   * Update a key
   *
   * @param keyName name of the key to update
   * @param key the key with the ACLs
   * @param requestOptions Options to pass to this request
   * @return the metadata of the key (such as it's name)
   */
  public CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.PUT,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys", keyName),
                requestOptions,
                CreateUpdateKey.class)
            .setData(key));
  }

  /**
   * Generate a secured and public API Key from a query and an optional user token identifying the
   * current user
   *
   * @param privateApiKey your private API Key
   * @param query contains the parameter applied to the query (used as security)
   */
  @SuppressWarnings("unused")
  public String generateSecuredApiKey(@Nonnull String privateApiKey, @Nonnull Query query)
      throws AlgoliaException {
    return generateSecuredApiKey(privateApiKey, query, null);
  }

  /**
   * Generate a secured and public API Key from a query and an optional user token identifying the
   * current user
   *
   * @param privateApiKey your private API Key
   * @param query contains the parameter applied to the query (used as security)
   * @param userToken an optional token identifying the current user
   */
  @SuppressWarnings("WeakerAccess")
  public String generateSecuredApiKey(
      @Nonnull String privateApiKey, @Nonnull Query query, String userToken)
      throws AlgoliaException {
    return Utils.generateSecuredApiKey(privateApiKey, query, userToken);
  }

  /**
   * Wait for the completion of this task /!\ WARNING /!\ This method is blocking
   *
   * @param task the task to wait
   */
  public <T> void waitTask(@Nonnull AsyncGenericTask<T> task) {
    waitTask(task, 100L, RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task /!\ WARNING /!\ This method is blocking
   *
   * @param task the task to wait
   * @param timeToWait the time to wait in milliseconds
   */
  public <T> void waitTask(@Nonnull AsyncGenericTask<T> task, long timeToWait) {
    waitTask(task, timeToWait, RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task /!\ WARNING /!\ This method is blocking
   *
   * @param task the task to wait
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  public <T> void waitTask(
      @Nonnull AsyncGenericTask<T> task, long timeToWait, @Nonnull RequestOptions requestOptions) {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    while (true) {
      CompletableFuture<TaskStatus> status =
          httpClient.requestWithRetry(
              new AlgoliaRequest<>(
                  HttpMethod.GET,
                  AlgoliaRequestKind.SEARCH_API_WRITE,
                  Arrays.asList(
                      "1",
                      "indexes",
                      task.getIndexName(),
                      "task",
                      task.getTaskIDToWaitFor().toString()),
                  requestOptions,
                  TaskStatus.class));

      TaskStatus result;
      try {
        result = status.get();
      } catch (CancellationException | InterruptedException | ExecutionException e) {
        // If the future was cancelled or the thread was interrupted or future completed
        // exceptionally
        // We stop
        break;
      }

      if (java.util.Objects.equals("published", result.getStatus())) {
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

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have a valid index name (not null)
   *
   * @param operations the list of operations to perform
   * @return the associated task
   */
  public CompletableFuture<AsyncTasksMultipleIndex> batch(
      @Nonnull List<BatchOperation> operations) {
    return batch(operations, RequestOptions.empty);
  }

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have a valid index name (not null)
   *
   * @param operations the list of operations to perform
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  public CompletableFuture<AsyncTasksMultipleIndex> batch(
      @Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions) {
    boolean atLeastOneHaveIndexNameNull =
        operations.stream().anyMatch(o -> o.getIndexName() == null);
    if (atLeastOneHaveIndexNameNull) {
      return Utils.completeExceptionally(
          new AlgoliaException("All batch operations must have an index name set"));
    }

    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", "*", "batch"),
                    requestOptions,
                    AsyncTasksMultipleIndex.class)
                .setData(new BatchOperations(operations)))
        .thenApply(AsyncTasksMultipleIndex::computeIndex);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>
   * MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @return the result of the queries
   */
  @SuppressWarnings("unused")
  public CompletableFuture<MultiQueriesResult> multipleQueries(@Nonnull List<IndexQuery> queries) {
    return multipleQueries(queries, MultiQueriesStrategy.NONE);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>
   * MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @param requestOptions Options to pass to this request
   * @return the result of the queries
   */
  @SuppressWarnings("unused")
  public CompletableFuture<MultiQueriesResult> multipleQueries(
      @Nonnull List<IndexQuery> queries, @Nonnull RequestOptions requestOptions) {
    return multipleQueries(queries, MultiQueriesStrategy.NONE, requestOptions);
  }

  /**
   * Performs multiple searches on multiple indices
   *
   * @param queries the queries
   * @param strategy the strategy to apply to this multiple queries
   * @return the result of the queries
   */
  @SuppressWarnings("WeakerAccess")
  public CompletableFuture<MultiQueriesResult> multipleQueries(
      @Nonnull List<IndexQuery> queries, @Nonnull MultiQueriesStrategy strategy) {
    return multipleQueries(queries, strategy, RequestOptions.empty);
  }

  /**
   * Performs multiple searches on multiple indices
   *
   * @param queries the queries
   * @param strategy the strategy to apply to this multiple queries
   * @param requestOptions Options to pass to this request
   * @return the result of the queries
   */
  @SuppressWarnings("WeakerAccess")
  public CompletableFuture<MultiQueriesResult> multipleQueries(
      @Nonnull List<IndexQuery> queries,
      @Nonnull MultiQueriesStrategy strategy,
      @Nonnull RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", "*", "queries"),
                requestOptions,
                MultiQueriesResult.class)
            .setData(new MultipleQueriesRequests(queries))
            .setParameters(ImmutableMap.of("strategy", strategy.getName())));
  }

  /**
   * Delete an existing index
   *
   * @param indexName The index name that will be deleted
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> deleteIndex(@Nonnull String indexName) {
    return deleteIndex(indexName, RequestOptions.empty);
  }

  /**
   * Delete an existing index
   *
   * @param indexName The index name that will be deleted
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  public CompletableFuture<AsyncTask> deleteIndex(
      @Nonnull String indexName, @Nonnull RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.DELETE,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName),
                requestOptions,
                AsyncTask.class))
        .thenApply(s -> s.setIndex(indexName));
  }

  /** Package protected method for the Index class */
  <T> CompletableFuture<AsyncTaskIndexing> addObject(
      String indexName, T object, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName),
                    requestOptions,
                    AsyncTaskIndexing.class)
                .setData(object))
        .thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTaskIndexing> addObject(
      String indexName, String objectID, T object, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID),
                    requestOptions,
                    AsyncTaskIndexing.class)
                .setData(object))
        .thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<Optional<T>> getObject(
      String indexName, String objectID, Class<T> klass, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, objectID),
                requestOptions,
                klass))
        .thenApply(Optional::ofNullable);
  }

  <T> CompletableFuture<AsyncTaskSingleIndex> addObjects(
      String indexName, List<T> objects, RequestOptions requestOptions) {
    return batchSingleIndex(
            indexName,
            objects.stream().map(BatchAddObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .thenApply(s -> s.setIndex(indexName));
  }

  private CompletableFuture<AsyncTaskSingleIndex> batchSingleIndex(
      String indexName, List<BatchOperation> operations, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "batch"),
                    requestOptions,
                    AsyncTaskSingleIndex.class)
                .setData(new Batch(operations)))
        .thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTask> saveObject(
      String indexName, String objectID, T object, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID),
                    requestOptions,
                    AsyncTask.class)
                .setData(object))
        .thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTaskSingleIndex> saveObjects(
      String indexName, List<T> objects, RequestOptions requestOptions) {
    return batchSingleIndex(
            indexName,
            objects.stream().map(BatchUpdateObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> deleteObject(
      String indexName, String objectID, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.DELETE,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, objectID),
                requestOptions,
                AsyncTask.class))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> deleteObjects(
      String indexName, List<String> objectIDs, RequestOptions requestOptions) {
    return batchSingleIndex(
            indexName,
            objectIDs.stream().map(BatchDeleteObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> clearIndex(String indexName, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "clear"),
                requestOptions,
                AsyncTask.class))
        .thenApply(s -> s.setIndex(indexName));
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<List<T>> getObjects(
      String indexName, List<String> objectIDs, Class<T> klass, RequestOptions requestOptions) {
    Requests requests =
        new Requests(
            objectIDs
                .stream()
                .map(o -> new Requests.Request().setIndexName(indexName).setObjectID(o))
                .collect(Collectors.toList()));
    AlgoliaRequest<Results> algoliaRequest =
        new AlgoliaRequest<>(
            HttpMethod.POST,
            AlgoliaRequestKind.SEARCH_API_READ,
            Arrays.asList("1", "indexes", "*", "objects"),
            requestOptions,
            Results.class,
            klass);

    return httpClient
        .requestWithRetry(algoliaRequest.setData(requests))
        .thenApply(Results::getResults);
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<List<T>> getObjects(
      String indexName,
      List<String> objectIDs,
      List<String> attributesToRetrieve,
      Class<T> klass,
      RequestOptions requestOptions) {
    String encodedAttributesToRetrieve = String.join(",", attributesToRetrieve);
    Requests requests =
        new Requests(
            objectIDs
                .stream()
                .map(
                    o ->
                        new Requests.Request()
                            .setIndexName(indexName)
                            .setObjectID(o)
                            .setAttributesToRetrieve(encodedAttributesToRetrieve))
                .collect(Collectors.toList()));
    AlgoliaRequest<Results> algoliaRequest =
        new AlgoliaRequest<>(
            HttpMethod.POST,
            AlgoliaRequestKind.SEARCH_API_READ,
            Arrays.asList("1", "indexes", "*", "objects"),
            requestOptions,
            Results.class,
            klass);

    return httpClient
        .requestWithRetry(algoliaRequest.setData(requests))
        .thenApply(Results::getResults);
  }

  CompletableFuture<IndexSettings> getSettings(String indexName, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, "settings"),
                requestOptions,
                IndexSettings.class)
            .setParameters(ImmutableMap.of("getVersion", "2")));
  }

  CompletableFuture<AsyncTask> setSettings(
      String indexName,
      IndexSettings settings,
      Boolean forwardToReplicas,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "settings"),
                    requestOptions,
                    AsyncTask.class)
                .setData(settings)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<List<ApiKey>> listKeys(String indexName, RequestOptions requestOptions) {
    CompletableFuture<ApiKeys> result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys"),
                requestOptions,
                ApiKeys.class));

    return result.thenApply(ApiKeys::getKeys);
  }

  CompletableFuture<Optional<ApiKey>> getKey(
      String indexName, String key, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys", key),
                requestOptions,
                ApiKey.class))
        .thenApply(Optional::ofNullable);
  }

  CompletableFuture<DeleteKey> deleteKey(
      String indexName, String key, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
            HttpMethod.DELETE,
            AlgoliaRequestKind.SEARCH_API_WRITE,
            Arrays.asList("1", "indexes", indexName, "keys", key),
            requestOptions,
            DeleteKey.class));
  }

  CompletableFuture<CreateUpdateKey> addKey(
      String indexName, ApiKey key, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys"),
                requestOptions,
                CreateUpdateKey.class)
            .setData(key));
  }

  CompletableFuture<CreateUpdateKey> updateApiKey(
      String indexName, String keyName, ApiKey key, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.PUT,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys", keyName),
                requestOptions,
                CreateUpdateKey.class)
            .setData(key));
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<SearchResult<T>> search(
      String indexName, Query query, Class<T> klass, RequestOptions requestOptions) {
    AlgoliaRequest<SearchResult<T>> algoliaRequest =
        new AlgoliaRequest(
            HttpMethod.POST,
            AlgoliaRequestKind.SEARCH_API_READ,
            Arrays.asList("1", "indexes", indexName, "query"),
            requestOptions,
            SearchResult.class,
            klass);

    return httpClient
        .requestWithRetry(algoliaRequest.setData(query))
        .thenCompose(
            result -> {
              CompletableFuture<SearchResult<T>> r = new CompletableFuture<>();
              if (result == null) { // Special case when the index does not exists
                r.completeExceptionally(
                    new AlgoliaIndexNotFoundException(indexName + " does not exist"));
              } else {
                r.complete(result);
              }
              return r;
            });
  }

  CompletableFuture<AsyncTaskSingleIndex> batch(
      String indexName, List<BatchOperation> operations, RequestOptions requestOptions) {
    // Special case for single index batches, indexName of operations should be null
    boolean onSameIndex =
        operations.stream().allMatch(o -> java.util.Objects.equals(null, o.getIndexName()));
    if (!onSameIndex) {
      Utils.completeExceptionally(new AlgoliaException("All operations are not on the same index"));
    }

    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "batch"),
                    requestOptions,
                    AsyncTaskSingleIndex.class)
                .setData(new BatchOperations(operations)))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(
      String indexName,
      PartialUpdateOperation operation,
      Boolean createIfNotExists,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, operation.getObjectID(), "partial"),
                    requestOptions,
                    AsyncTaskSingleIndex.class)
                .setParameters(ImmutableMap.of("createIfNotExists", createIfNotExists.toString()))
                .setData(operation.toSerialize()))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(
      String indexName, String objectID, Object object, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID, "partial"),
                    requestOptions,
                    AsyncTaskSingleIndex.class)
                .setData(object))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> saveSynonym(
      String indexName,
      String synonymID,
      AbstractSynonym content,
      Boolean forwardToReplicas,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
                .setData(content))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<Optional<AbstractSynonym>> getSynonym(
      String indexName, String synonymID, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                requestOptions,
                AbstractSynonym.class))
        .thenApply(Optional::ofNullable);
  }

  CompletableFuture<AsyncTask> deleteSynonym(
      String indexName,
      String synonymID,
      Boolean forwardToReplicas,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.DELETE,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> clearSynonyms(
      String indexName, Boolean forwardToReplicas, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", "clear"),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<SearchSynonymResult> searchSynonyms(
      String indexName, SynonymQuery query, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "synonyms", "search"),
                requestOptions,
                SearchSynonymResult.class)
            .setData(query));
  }

  CompletableFuture<AsyncTask> batchSynonyms(
      String indexName,
      List<AbstractSynonym> synonyms,
      Boolean forwardToReplicas,
      Boolean replaceExistingSynonyms,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", "batch"),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(
                    ImmutableMap.of(
                        "forwardToReplicas",
                        forwardToReplicas.toString(),
                        "replaceExistingSynonyms",
                        replaceExistingSynonyms.toString()))
                .setData(synonyms))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(
      String indexName,
      List<Object> objects,
      boolean createIfNotExists,
      RequestOptions requestOptions) {
    return batch(
            indexName,
            objects
                .stream()
                .map(
                    createIfNotExists
                        ? BatchPartialUpdateObjectOperation::new
                        : BatchPartialUpdateObjectNoCreateOperation::new)
                .collect(Collectors.toList()),
            requestOptions)
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<SearchFacetResult> searchForFacetValues(
      String indexName,
      String facetName,
      String facetQuery,
      Query query,
      RequestOptions requestOptions) {
    query = query == null ? new Query() : query;
    query = query.addCustomParameter("facetQuery", facetQuery);

    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, "facets", facetName, "query"),
                requestOptions,
                SearchFacetResult.class)
            .setData(query));
  }

  CompletableFuture<AsyncTask> saveRule(
      String indexName,
      String queryRuleID,
      Rule queryRule,
      Boolean forwardToReplicas,
      RequestOptions requestOptions) {
    if (queryRuleID.isEmpty()) {
      CompletableFuture f = new CompletableFuture<>();
      f.completeExceptionally(new AlgoliaException("Cannot save rule with empty queryRuleID"));
      return f;
    }
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", queryRuleID),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
                .setData(queryRule))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<Optional<Rule>> getRule(
      String indexName, String queryRuleID, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "rules", queryRuleID),
                requestOptions,
                Rule.class))
        .thenApply(Optional::ofNullable);
  }

  CompletableFuture<AsyncTask> deleteRule(
      String indexName,
      String queryRuleID,
      Boolean forwardToReplicas,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.DELETE,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", queryRuleID),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> clearRules(
      String indexName, Boolean forwardToReplicas, RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", "clear"),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<SearchRuleResult> searchRules(
      String indexName, RuleQuery query, RequestOptions requestOptions) {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "rules", "search"),
                requestOptions,
                SearchRuleResult.class)
            .setData(query));
  }

  CompletableFuture<AsyncTask> batchRules(
      String indexName,
      List<Rule> rules,
      Boolean forwardToReplicas,
      Boolean clearExistingRules,
      RequestOptions requestOptions) {
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", "batch"),
                    requestOptions,
                    AsyncTask.class)
                .setParameters(
                    ImmutableMap.of(
                        "forwardToReplicas",
                        forwardToReplicas.toString(),
                        "clearExistingRules",
                        clearExistingRules.toString()))
                .setData(rules))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> deleteBy(
      String indexName, Query query, RequestOptions requestOptions) {
    query = query == null ? new Query() : query;
    return httpClient
        .requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "deleteByQuery"),
                    requestOptions,
                    AsyncTask.class)
                .setData(query))
        .thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskABTest> addABTest(ABTest abtest) {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.ANALYTICS_API,
                Arrays.asList("2", "abtests"),
                RequestOptions.empty,
                AsyncTaskABTest.class)
            .setData(abtest));
  }

  CompletableFuture<AsyncTaskABTest> stopABTest(long id) {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.POST,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id), "stop"),
            RequestOptions.empty,
            AsyncTaskABTest.class));
  }

  CompletableFuture<AsyncTaskABTest> deleteABTest(long id) {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.DELETE,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id)),
            RequestOptions.empty,
            AsyncTaskABTest.class));
  }

  CompletableFuture<ABTest> getABTest(long id) {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.GET,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id)),
            RequestOptions.empty,
            ABTest.class));
  }

  CompletableFuture<ABTests> getABTests(int offset, int limit) {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.ANALYTICS_API,
                Arrays.asList("2", "abtests"),
                RequestOptions.empty,
                ABTests.class)
            .setParameters(
                ImmutableMap.of(
                    "offset", Integer.toString(offset),
                    "limit", Integer.toString(limit))));
  }
}

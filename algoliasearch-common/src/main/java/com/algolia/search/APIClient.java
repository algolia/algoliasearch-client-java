package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.http.AlgoliaRequestKind;
import com.algolia.search.http.HttpMethod;
import com.algolia.search.inputs.*;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.batch.*;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.sync.*;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public class APIClient {

  /** Constructor & protected stuff */
  protected final AlgoliaHttpClient httpClient;

  protected final APIClientConfiguration configuration;

  APIClient(AlgoliaHttpClient httpClient, APIClientConfiguration configuration) {
    this.httpClient = httpClient;
    this.configuration = configuration;
  }

  /** Close the internal HTTP client */
  public void close() throws AlgoliaException {
    this.httpClient.close();
  }

  /*
   * All public method
   */

  /**
   * List all existing indexes
   *
   * @return A List of the indices and their metadata
   */
  public List<Index.Attributes> listIndexes() throws AlgoliaException {
    return listIndexes(RequestOptions.empty);
  }

  /**
   * List all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   */
  public List<Index.Attributes> listIndexes(@Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Indices result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes"),
                requestOptions,
                Indices.class));

    return result.getItems();
  }

  /** Deprecated: use {@link #listIndexes()} */
  @Deprecated
  public List<Index.Attributes> listIndices() throws AlgoliaException {
    return listIndexes(RequestOptions.empty);
  }

  /** Deprecated: use {@link #listIndexes(RequestOptions)} */
  @Deprecated
  public List<Index.Attributes> listIndices(@Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return listIndexes(requestOptions);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name name of the index
   * @param klass class of the object in this index
   * @param <T> the type of the objects in this index
   * @return The initialized index
   */
  public <T> Index<T> initIndex(@Nonnull String name, @Nonnull Class<T> klass) {
    return new Index<>(name, klass, this);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name name of the index
   * @return The initialized index
   */
  public Index<?> initIndex(@Nonnull String name) {
    return new Index<>(name, Object.class, this);
  }

  public Analytics initAnalytics() {
    return new Analytics(this);
  }

  /**
   * Moves an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  public Task moveIndex(@Nonnull String srcIndexName, @Nonnull String dstIndexName)
      throws AlgoliaException {
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
  public Task moveIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", srcIndexName, "operation"),
                    requestOptions,
                    Task.class)
                .setData(new OperationOnIndex("move", dstIndexName)));

    return result.setAPIClient(this).setIndex(srcIndexName);
  }

  /**
   * Copy an existing index
   *
   * @param srcIndexName the index name that will be the source of the copy
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  public Task copyIndex(@Nonnull String srcIndexName, @Nonnull String dstIndexName)
      throws AlgoliaException {
    return copyIndex(srcIndexName, dstIndexName, RequestOptions.empty);
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
  public Task copyIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
  public Task copyIndex(
      @Nonnull String srcIndexName,
      @Nonnull String dstIndexName,
      List<String> scope,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", srcIndexName, "operation"),
                    requestOptions,
                    Task.class)
                .setData(new OperationOnIndex("copy", dstIndexName, scope)));

    return result.setAPIClient(this).setIndex(srcIndexName);
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
  public Task copyIndex(
      @Nonnull String srcIndexName, @Nonnull String dstIndexName, @Nonnull List<String> scope)
      throws AlgoliaException {
    return copyIndex(srcIndexName, dstIndexName, scope, RequestOptions.empty);
  }

  /**
   * Delete an existing index
   *
   * @param indexName The index name that will be deleted
   * @return The associated task
   */
  public Task deleteIndex(@Nonnull String indexName) throws AlgoliaException {
    return deleteIndex(indexName, RequestOptions.empty);
  }

  /**
   * Delete an existing index
   *
   * @param indexName The index name that will be deleted
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  public Task deleteIndex(@Nonnull String indexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.DELETE,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName),
                requestOptions,
                Task.class));

    return result.setAPIClient(this).setIndex(indexName);
  }

  /**
   * Return 10 last log entries.
   *
   * @return A List<Log>
   */
  public List<Log> getLogs() throws AlgoliaException {
    return getLogs(RequestOptions.empty);
  }

  /**
   * Return 10 last log entries.
   *
   * @param requestOptions Options to pass to this request
   * @return A List<Log>
   */
  public List<Log> getLogs(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    Logs result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "logs"),
                requestOptions,
                Logs.class));

    return result.getLogs();
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
  public List<Log> getLogs(
      @Nonnull Integer offset, @Nonnull Integer length, @Nonnull LogType logType)
      throws AlgoliaException {
    return this.getLogs(offset, length, logType, RequestOptions.empty);
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
  public List<Log> getLogs(
      @Nonnull Integer offset,
      @Nonnull Integer length,
      @Nonnull LogType logType,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Preconditions.checkArgument(offset >= 0, "offset must be >= 0, was %s", offset);
    Preconditions.checkArgument(length >= 0, "length must be >= 0, was %s", length);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("offset", offset.toString());
    parameters.put("length", length.toString());
    parameters.put("type", logType.getName());

    Logs result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.GET,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "logs"),
                    requestOptions,
                    Logs.class)
                .setParameters(parameters));

    return result.getLogs();
  }

  /** Deprecated: use {@link #listApiKeys()} */
  @Deprecated
  public List<ApiKey> listKeys() throws AlgoliaException {
    return listApiKeys();
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @return A List of Keys
   */
  public List<ApiKey> listApiKeys() throws AlgoliaException {
    return this.listApiKeys(RequestOptions.empty);
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @return A List of Keys
   */
  public List<ApiKey> listApiKeys(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    ApiKeys result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys"),
                requestOptions,
                ApiKeys.class));

    return result.getKeys();
  }

  /** Deprecated: use {@link #getApiKey(String)} */
  @Deprecated
  public Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key);
  }

  /**
   * Get a Key from its name
   *
   * @param key name of the key
   * @return the key
   */
  public Optional<ApiKey> getApiKey(@Nonnull String key) throws AlgoliaException {
    return this.getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get a Key from its name
   *
   * @param key name of the key
   * @param requestOptions Options to pass to this request
   * @return the key
   */
  public Optional<ApiKey> getApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return Optional.ofNullable(
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "keys", key),
                requestOptions,
                ApiKey.class)));
  }

  /** Deprecated: use {@link #deleteApiKey(String)} */
  @Deprecated
  public DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key);
  }

  /**
   * Delete an existing key
   *
   * @param key name of the key
   */
  public DeleteKey deleteApiKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete an existing key
   *
   * @param key name of the key
   * @param requestOptions Options to pass to this request
   */
  public DeleteKey deleteApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
  public CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key);
  }

  /**
   * Create a new key
   *
   * @param key the key with the ACLs
   * @return the metadata of the key (such as it's name)
   */
  public CreateUpdateKey addApiKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Create a new key
   *
   * @param key the key with the ACLs
   * @param requestOptions Options to pass to this request
   * @return the metadata of the key (such as it's name)
   */
  public CreateUpdateKey addApiKey(@Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
  public CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateApiKey(keyName, key);
  }

  /**
   * Update a key
   *
   * @param keyName name of the key to update
   * @param key the key with the ACLs
   * @return the metadata of the key (such as it's name)
   */
  public CreateUpdateKey updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
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
  public CreateUpdateKey updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
   * Wait for the completion of this task
   *
   * @param task the task to wait
   */
  public <T> void waitTask(@Nonnull GenericTask<T> task) throws AlgoliaException {
    waitTask(task, 100L, RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task
   *
   * @param task the task to wait
   * @param timeToWait the time to wait in milliseconds
   */
  public <T> void waitTask(@Nonnull GenericTask<T> task, long timeToWait) throws AlgoliaException {
    waitTask(task, timeToWait, RequestOptions.empty);
  }

  /**
   * Wait for the completion of this task
   *
   * @param task the task to wait
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  public <T> void waitTask(
      @Nonnull GenericTask<T> task, long timeToWait, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    while (true) {
      TaskStatus status =
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

      if (java.util.Objects.equals("published", status.getStatus())) {
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
  public TasksMultipleIndex batch(@Nonnull List<BatchOperation> operations)
      throws AlgoliaException {
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
  public TasksMultipleIndex batch(
      @Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    boolean atLeastOneHaveIndexNameNull =
        operations.stream().anyMatch(o -> o.getIndexName() == null);
    if (atLeastOneHaveIndexNameNull) {
      throw new AlgoliaException("All batch operations must have an index name set");
    }

    TasksMultipleIndex request =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", "*", "batch"),
                    requestOptions,
                    TasksMultipleIndex.class)
                .setData(new BatchOperations(operations)));

    return request.setAPIClient(this);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>
   * MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @return the result of the queries
   */
  public MultiQueriesResult multipleQueries(@Nonnull List<IndexQuery> queries)
      throws AlgoliaException {
    return multipleQueries(queries, RequestOptions.empty);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>
   * MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @param requestOptions Options to pass to this request
   * @return the result of the queries
   */
  public MultiQueriesResult multipleQueries(
      @Nonnull List<IndexQuery> queries, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return multipleQueries(queries, MultiQueriesStrategy.NONE, requestOptions);
  }

  /**
   * Performs multiple searches on multiple indices
   *
   * @param queries the queries
   * @param strategy the strategy to apply to this multiple queries
   * @return the result of the queries
   */
  public MultiQueriesResult multipleQueries(
      @Nonnull List<IndexQuery> queries, @Nonnull MultiQueriesStrategy strategy)
      throws AlgoliaException {
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
  public MultiQueriesResult multipleQueries(
      @Nonnull List<IndexQuery> queries,
      @Nonnull MultiQueriesStrategy strategy,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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

  <T> TaskIndexing addObject(String indexName, T object, RequestOptions requestOptions)
      throws AlgoliaException {
    TaskIndexing result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName),
                    requestOptions,
                    TaskIndexing.class)
                .setData(object));

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> TaskIndexing addObject(
      String indexName, String objectID, T object, RequestOptions requestOptions)
      throws AlgoliaException {
    TaskIndexing result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID),
                    requestOptions,
                    TaskIndexing.class)
                .setData(object));

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> Optional<T> getObject(
      String indexName, String objectID, Class<T> klass, RequestOptions requestOptions)
      throws AlgoliaException {
    return Optional.ofNullable(
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, objectID),
                requestOptions,
                klass)));
  }

  <T> TaskSingleIndex addObjects(String indexName, List<T> objects, RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSingleIndex(
            indexName,
            objects.stream().map(BatchAddObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .setAPIClient(this)
        .setIndex(indexName);
  }

  private TaskSingleIndex batchSingleIndex(
      String indexName, List<BatchOperation> operations, RequestOptions requestOptions)
      throws AlgoliaException {
    TaskSingleIndex result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "batch"),
                    requestOptions,
                    TaskSingleIndex.class)
                .setData(new Batch(operations)));

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> Task saveObject(String indexName, String objectID, T object, RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID),
                    requestOptions,
                    Task.class)
                .setData(object));

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> TaskSingleIndex saveObjects(String indexName, List<T> objects, RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSingleIndex(
            indexName,
            objects.stream().map(BatchUpdateObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .setAPIClient(this)
        .setIndex(indexName);
  }

  Task deleteObject(String indexName, String objectID, RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.DELETE,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, objectID),
                requestOptions,
                Task.class));

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex deleteObjects(
      String indexName, List<String> objectIDs, RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSingleIndex(
            indexName,
            objectIDs.stream().map(BatchDeleteObjectOperation::new).collect(Collectors.toList()),
            requestOptions)
        .setAPIClient(this)
        .setIndex(indexName);
  }

  Task clearIndex(String indexName, RequestOptions requestOptions) throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "clear"),
                requestOptions,
                Task.class));

    return result.setAPIClient(this).setIndex(indexName);
  }

  @SuppressWarnings("unchecked")
  <T> List<T> getObjects(
      String indexName, List<String> objectIDs, Class<T> klass, RequestOptions requestOptions)
      throws AlgoliaException {
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

    return httpClient.requestWithRetry(algoliaRequest.setData(requests)).getResults();
  }

  @SuppressWarnings("unchecked")
  <T> List<T> getObjects(
      String indexName,
      List<String> objectIDs,
      List<String> attributesToRetrieve,
      Class<T> klass,
      RequestOptions requestOptions)
      throws AlgoliaException {
    final String encodedAttributesToRetrieve = String.join(",", attributesToRetrieve);
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

    return httpClient.requestWithRetry(algoliaRequest.setData(requests)).getResults();
  }

  IndexSettings getSettings(String indexName, RequestOptions requestOptions)
      throws AlgoliaException {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, "settings"),
                requestOptions,
                IndexSettings.class)
            .setParameters(ImmutableMap.of("getVersion", "2")));
  }

  Task setSettings(
      String indexName,
      IndexSettings settings,
      Boolean forwardToReplicas,
      RequestOptions requestOptions)
      throws AlgoliaException {
    Task result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "settings"),
                    requestOptions,
                    Task.class)
                .setData(settings)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())));

    return result.setAPIClient(this).setIndex(indexName);
  }

  List<ApiKey> listKeys(String indexName, RequestOptions requestOptions) throws AlgoliaException {
    ApiKeys result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys"),
                requestOptions,
                ApiKeys.class));

    return result.getKeys();
  }

  Optional<ApiKey> getKey(String indexName, String key, RequestOptions requestOptions)
      throws AlgoliaException {
    return Optional.ofNullable(
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys", key),
                requestOptions,
                ApiKey.class)));
  }

  DeleteKey deleteKey(String indexName, String key, RequestOptions requestOptions)
      throws AlgoliaException {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
            HttpMethod.DELETE,
            AlgoliaRequestKind.SEARCH_API_WRITE,
            Arrays.asList("1", "indexes", indexName, "keys", key),
            requestOptions,
            DeleteKey.class));
  }

  CreateUpdateKey addKey(String indexName, ApiKey key, RequestOptions requestOptions)
      throws AlgoliaException {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "keys"),
                requestOptions,
                CreateUpdateKey.class)
            .setData(key));
  }

  CreateUpdateKey updateApiKey(
      String indexName, String keyName, ApiKey key, RequestOptions requestOptions)
      throws AlgoliaException {
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
  <T> SearchResult<T> search(
      String indexName, Query query, Class<T> klass, RequestOptions requestOptions)
      throws AlgoliaException {
    AlgoliaRequest<SearchResult> algoliaRequest =
        new AlgoliaRequest<>(
            HttpMethod.POST,
            AlgoliaRequestKind.SEARCH_API_READ,
            Arrays.asList("1", "indexes", indexName, "query"),
            requestOptions,
            SearchResult.class,
            klass);

    SearchResult<T> result = httpClient.requestWithRetry(algoliaRequest.setData(query));
    if (result == null) { // Special case when the index does not exists
      throw new AlgoliaIndexNotFoundException(indexName + " does not exist");
    }
    return result;
  }

  TaskSingleIndex batch(
      String indexName, List<BatchOperation> operations, RequestOptions requestOptions)
      throws AlgoliaException {
    // Special case for single index batches, indexName of operations should be null
    boolean onSameIndex =
        operations.stream().allMatch(o -> java.util.Objects.equals(null, o.getIndexName()));
    if (!onSameIndex) {
      throw new AlgoliaException("All operations are not on the same index");
    }

    TaskSingleIndex result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "batch"),
                    requestOptions,
                    TaskSingleIndex.class)
                .setData(new BatchOperations(operations)));

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex partialUpdateObject(
      String indexName,
      PartialUpdateOperation operation,
      Boolean createIfNotExists,
      RequestOptions requestOptions)
      throws AlgoliaException {
    TaskSingleIndex result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, operation.getObjectID(), "partial"),
                    requestOptions,
                    TaskSingleIndex.class)
                .setParameters(ImmutableMap.of("createIfNotExists", createIfNotExists.toString()))
                .setData(operation.toSerialize()));

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex partialUpdateObject(
      String indexName, String objectID, Object object, RequestOptions requestOptions)
      throws AlgoliaException {
    TaskSingleIndex result =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, objectID, "partial"),
                    requestOptions,
                    TaskSingleIndex.class)
                .setData(object));

    return result.setAPIClient(this).setIndex(indexName);
  }

  Task saveSynonym(
      String indexName,
      String synonymID,
      AbstractSynonym content,
      Boolean forwardToReplicas,
      RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
                .setData(content));

    return task.setAPIClient(this).setIndex(indexName);
  }

  Optional<AbstractSynonym> getSynonym(
      String indexName, String synonymID, RequestOptions requestOptions) throws AlgoliaException {
    return Optional.ofNullable(
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                requestOptions,
                AbstractSynonym.class)));
  }

  Task deleteSynonym(
      String indexName, String synonymID, Boolean forwardToReplicas, RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.DELETE,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())));

    return task.setAPIClient(this).setIndex(indexName);
  }

  Task clearSynonyms(String indexName, Boolean forwardToReplicas, RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", "clear"),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())));

    return task.setAPIClient(this).setIndex(indexName);
  }

  SearchSynonymResult searchSynonyms(
      String indexName, SynonymQuery query, RequestOptions requestOptions) throws AlgoliaException {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "synonyms", "search"),
                requestOptions,
                SearchSynonymResult.class)
            .setData(query));
  }

  Task batchSynonyms(
      String indexName,
      List<AbstractSynonym> synonyms,
      Boolean forwardToReplicas,
      Boolean replaceExistingSynonyms,
      RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "synonyms", "batch"),
                    requestOptions,
                    Task.class)
                .setParameters(
                    ImmutableMap.of(
                        "forwardToReplicas",
                        forwardToReplicas.toString(),
                        "replaceExistingSynonyms",
                        replaceExistingSynonyms.toString()))
                .setData(synonyms));

    return task.setAPIClient(this).setIndex(indexName);
  }

  void deleteByQuery(String indexName, Query query, int batchSize, RequestOptions requestOptions)
      throws AlgoliaException {
    query =
        query
            .setAttributesToRetrieve(Collections.singletonList("objectID"))
            .setAttributesToHighlight(Collections.emptyList())
            .setAttributesToSnippet(Collections.emptyList())
            .setHitsPerPage(1000) // Magic number
            .setDistinct(Distinct.of(false));

    List<String> objectToDelete = new ArrayList<>(batchSize);
    for (ObjectID o : new IndexIterable<>(this, indexName, query, requestOptions, ObjectID.class)) {
      objectToDelete.add(o.getObjectID());

      while (objectToDelete.size() >= batchSize) {
        List<String> subList = objectToDelete.subList(0, batchSize);
        deleteObjects(indexName, subList, requestOptions).waitForCompletion();
        subList.clear();
      }
    }

    if (!objectToDelete.isEmpty()) {
      deleteObjects(indexName, objectToDelete, requestOptions).waitForCompletion();
    }
  }

  Task deleteBy(String indexName, Query query, RequestOptions requestOptions)
      throws AlgoliaException {
    query = query == null ? new Query() : query;
    AlgoliaRequest<Task> algoliaRequest =
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "deleteByQuery"),
                requestOptions,
                Task.class)
            .setData(query);

    return httpClient.requestWithRetry(algoliaRequest).setIndex(indexName).setAPIClient(this);
  }

  TaskSingleIndex partialUpdateObjects(
      String indexName,
      List<Object> objects,
      boolean createIfNotExists,
      RequestOptions requestOptions)
      throws AlgoliaException {
    TaskSingleIndex task =
        batch(
            indexName,
            objects
                .stream()
                .map(
                    createIfNotExists
                        ? BatchPartialUpdateObjectOperation::new
                        : BatchPartialUpdateObjectNoCreateOperation::new)
                .collect(Collectors.toList()),
            requestOptions);

    return task.setAPIClient(this).setIndex(indexName);
  }

  @SuppressWarnings("unchecked")
  public <T> BrowseResult<T> browse(
      String indexName, Query query, String cursor, Class<T> klass, RequestOptions requestOptions)
      throws AlgoliaException {
    AlgoliaRequest<BrowseResult> algoliaRequest =
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, "browse"),
                requestOptions,
                BrowseResult.class,
                klass)
            .setData(query.setCursor(cursor));

    return httpClient.requestWithRetry(algoliaRequest);
  }

  SearchFacetResult searchForFacetValues(
      String indexName,
      String facetName,
      String facetQuery,
      Query query,
      RequestOptions requestOptions)
      throws AlgoliaException {
    query = query == null ? new Query() : query;
    query = query.addCustomParameter("facetQuery", facetQuery);
    AlgoliaRequest<SearchFacetResult> algoliaRequest =
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_READ,
                Arrays.asList("1", "indexes", indexName, "facets", facetName, "query"),
                requestOptions,
                SearchFacetResult.class)
            .setData(query);

    return httpClient.requestWithRetry(algoliaRequest);
  }

  Task saveRule(
      String indexName,
      String ruleId,
      Rule queryRule,
      Boolean forwardToReplicas,
      RequestOptions requestOptions)
      throws AlgoliaException {
    if (ruleId.isEmpty()) {
      throw new AlgoliaException("Cannot save rule with empty queryRuleID");
    }

    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.PUT,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", ruleId),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
                .setData(queryRule));

    return task.setAPIClient(this).setIndex(indexName);
  }

  Optional<Rule> getRule(String indexName, String queryRulesID, RequestOptions requestOptions)
      throws AlgoliaException {
    return Optional.ofNullable(
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                HttpMethod.GET,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "rules", queryRulesID),
                requestOptions,
                Rule.class)));
  }

  Task deleteRule(
      String indexName,
      String queryRulesID,
      Boolean forwardToReplicas,
      RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.DELETE,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", queryRulesID),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())));

    return task.setAPIClient(this).setIndex(indexName);
  }

  Task clearRules(String indexName, Boolean forwardToReplicas, RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", "clear"),
                    requestOptions,
                    Task.class)
                .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString())));

    return task.setAPIClient(this).setIndex(indexName);
  }

  SearchRuleResult searchRules(String indexName, RuleQuery query, RequestOptions requestOptions)
      throws AlgoliaException {
    return httpClient.requestWithRetry(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.SEARCH_API_WRITE,
                Arrays.asList("1", "indexes", indexName, "rules", "search"),
                requestOptions,
                SearchRuleResult.class)
            .setData(query));
  }

  Task batchRules(
      String indexName,
      List<Rule> queryRules,
      Boolean forwardToReplicas,
      Boolean clearExistingRules,
      RequestOptions requestOptions)
      throws AlgoliaException {
    Task task =
        httpClient.requestWithRetry(
            new AlgoliaRequest<>(
                    HttpMethod.POST,
                    AlgoliaRequestKind.SEARCH_API_WRITE,
                    Arrays.asList("1", "indexes", indexName, "rules", "batch"),
                    requestOptions,
                    Task.class)
                .setParameters(
                    ImmutableMap.of(
                        "forwardToReplicas",
                        forwardToReplicas.toString(),
                        "clearExistingRules",
                        clearExistingRules.toString()))
                .setData(queryRules));

    return task.setAPIClient(this).setIndex(indexName);
  }

  public TaskABTest addABTest(ABTest abtest) throws AlgoliaException {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.ANALYTICS_API,
                Arrays.asList("2", "abtests"),
                RequestOptions.empty,
                TaskABTest.class)
            .setData(abtest));
  }

  public TaskABTest stopABTest(long id) throws AlgoliaException {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.POST,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id), "stop"),
            RequestOptions.empty,
            TaskABTest.class));
  }

  public TaskABTest deleteABTest(long id) throws AlgoliaException {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.DELETE,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id)),
            RequestOptions.empty,
            TaskABTest.class));
  }

  public ABTest getABTest(long id) throws AlgoliaException {
    return httpClient.requestAnalytics(
        new AlgoliaRequest<>(
            HttpMethod.GET,
            AlgoliaRequestKind.ANALYTICS_API,
            Arrays.asList("2", "abtests", Long.toString(id)),
            RequestOptions.empty,
            ABTest.class));
  }

  public ABTests getABTests(int offset, int limit) throws AlgoliaException {
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

  /** Used internally for deleteByQuery */
  private static class ObjectID {

    private String objectID;

    public String getObjectID() {
      return objectID;
    }

    public ObjectID setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }
  }
}

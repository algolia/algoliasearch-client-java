package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.http.AsyncAlgoliaHttpClient;
import com.algolia.search.http.HttpMethod;
import com.algolia.search.inputs.*;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchDeleteObjectOperation;
import com.algolia.search.inputs.batch.BatchPartialUpdateObjectOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.async.*;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class AsyncAPIClient {

  /**
   * Constructor & protected stuff
   */
  protected final AsyncAlgoliaHttpClient httpClient;
  protected final AsyncAPIClientConfiguration configuration;
  protected final Executor executor;

  AsyncAPIClient(AsyncAlgoliaHttpClient httpClient, AsyncAPIClientConfiguration configuration) {
    this.httpClient = httpClient;
    this.configuration = configuration;
    this.executor = configuration.getExecutorService();
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
    CompletableFuture<Indices> result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(HttpMethod.GET, true, Arrays.asList("1", "indexes"), Indices.class)
    );

    return result.thenApply(Indices::getItems);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name  name of the index
   * @param klass class of the object in this index
   * @param <T>   the type of the objects in this index
   * @return The initialized index
   */
  public <T> AsyncIndex<T> initIndex(@Nonnull String name, @Nonnull Class<T> klass) {
    return new AsyncIndex<>(name, klass, this);
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
   * Return 10 last log entries.
   *
   * @return A List<Log>
   */
  public CompletableFuture<List<Log>> getLogs() {
    CompletableFuture<Logs> result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "logs"),
        Logs.class
      )
    );

    return result.thenApply(Logs::getLogs);
  }

  /**
   * Return last logs entries
   *
   * @param offset  Specify the first entry to retrieve (0-based, 0 is the most recent log entry)
   * @param length  Specify the maximum number of entries to retrieve starting at offset. Maximum allowed value: 1000
   * @param logType Specify the type of log to retrieve
   * @return The List of Logs
   */
  public CompletableFuture<List<Log>> getLogs(@Nonnull Integer offset, @Nonnull Integer length, @Nonnull LogType logType) {
    Preconditions.checkArgument(offset >= 0, "offset must be >= 0, was %s", offset);
    Preconditions.checkArgument(length >= 0, "length must be >= 0, was %s", length);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("offset", offset.toString());
    parameters.put("length", length.toString());
    parameters.put("type", logType.getName());

    CompletableFuture<Logs> result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "logs"),
        Logs.class
      ).setParameters(parameters)
    );

    return result.thenApply(Logs::getLogs);
  }

  /**
   * Deprecated: use listApiKeys
   */
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
    CompletableFuture<ApiKeys> result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "keys"),
        ApiKeys.class
      )
    );

    return result.thenApply(ApiKeys::getKeys);
  }

  /**
   * Deprecated: use getApiKey
   */
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
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "keys", key),
        ApiKey.class
      )
    ).thenApply(Optional::ofNullable);
  }

  /**
   * Deprecated: use deleteApiKey
   */
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
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "keys", key),
        DeleteKey.class
      )
    );
  }

  /**
   * Deprecated: Use addApiKey
   */
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
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "keys"),
        CreateUpdateKey.class
      ).setData(key)
    );
  }

  /**
   * Deprecated: use updateApiKey
   */
  @Deprecated
  public CompletableFuture<CreateUpdateKey> updateKey(@Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key);
  }

  /**
   * Update a key
   *
   * @param keyName name of the key to update
   * @param key     the key with the ACLs
   * @return the metadata of the key (such as it's name)
   */
  public CompletableFuture<CreateUpdateKey> updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "keys", keyName),
        CreateUpdateKey.class
      ).setData(key)
    );
  }

  /**
   * Generate a secured and public API Key from a query and an
   * optional user token identifying the current user
   *
   * @param privateApiKey your private API Key
   * @param query         contains the parameter applied to the query (used as security)
   */
  @SuppressWarnings("unused")
  public String generateSecuredApiKey(@Nonnull String privateApiKey, @Nonnull Query query) throws AlgoliaException {
    return generateSecuredApiKey(privateApiKey, query, null);
  }

  /**
   * Generate a secured and public API Key from a query and an
   * optional user token identifying the current user
   *
   * @param privateApiKey your private API Key
   * @param query         contains the parameter applied to the query (used as security)
   * @param userToken     an optional token identifying the current user
   */
  @SuppressWarnings("WeakerAccess")
  public String generateSecuredApiKey(@Nonnull String privateApiKey, @Nonnull Query query, String userToken) throws AlgoliaException {
    return Utils.generateSecuredApiKey(privateApiKey, query, userToken);
  }

  /**
   * Wait for the completion of this task
   * /!\ WARNING /!\ This method is blocking
   *
   * @param task       the task to wait
   * @param timeToWait the time to wait in milliseconds
   */
  public <T> void waitTask(@Nonnull AsyncGenericTask<T> task, long timeToWait) {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    while (true) {
      CompletableFuture<TaskStatus> status = httpClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes", task.getIndexName(), "task", task.getTaskIDToWaitFor().toString()),
          TaskStatus.class
        )
      );

      TaskStatus result;
      try {
        result = status.get();
      } catch (CancellationException | InterruptedException | ExecutionException e) {
        //If the future was cancelled or the thread was interrupted or future completed exceptionally
        //We stop
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
      timeToWait = timeToWait > Defaults.MAX_TIME_MS_TO_WAIT ? Defaults.MAX_TIME_MS_TO_WAIT : timeToWait;
    }
  }

  /**
   * Custom batch
   * <p>
   * All operations must have a valid index name (not null)
   *
   * @param operations the list of operations to perform
   * @return the associated task
   */
  public CompletableFuture<AsyncTasksMultipleIndex> batch(@Nonnull List<BatchOperation> operations) {
    boolean atLeastOneHaveIndexNameNull = operations.stream().anyMatch(o -> o.getIndexName() == null);
    if (atLeastOneHaveIndexNameNull) {
      return Utils.completeExceptionally(new AlgoliaException("All batch operations must have an index name set"));
    }

    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", "*", "batch"),
        AsyncTasksMultipleIndex.class
      ).setData(new BatchOperations(operations))
    ).thenApply(AsyncTasksMultipleIndex::computeIndex);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @return the result of the queries
   */
  @SuppressWarnings("unused")
  public CompletableFuture<MultiQueriesResult> multipleQueries(@Nonnull List<IndexQuery> queries) {
    return multipleQueries(queries, MultiQueriesStrategy.NONE);
  }

  /**
   * Performs multiple searches on multiple indices
   *
   * @param queries  the queries
   * @param strategy the strategy to apply to this multiple queries
   * @return the result of the queries
   */
  @SuppressWarnings("WeakerAccess")
  public CompletableFuture<MultiQueriesResult> multipleQueries(@Nonnull List<IndexQuery> queries, @Nonnull MultiQueriesStrategy strategy) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        true,
        Arrays.asList("1", "indexes", "*", "queries"),
        MultiQueriesResult.class
      )
        .setData(new MultipleQueriesRequests(queries))
        .setParameters(ImmutableMap.of("strategy", strategy.getName()))
    );
  }

  /**
   * Package protected method for the Index class
   **/

  CompletableFuture<AsyncTask> moveIndex(String srcIndexName, String dstIndexName) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", srcIndexName, "operation"),
        AsyncTask.class
      ).setData(new OperationOnIndex("move", dstIndexName))
    ).thenApply(s -> s.setIndex(srcIndexName));
  }

  CompletableFuture<AsyncTask> copyIndex(String srcIndexName, String dstIndexName) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", srcIndexName, "operation"),
        AsyncTask.class
      ).setData(new OperationOnIndex("copy", dstIndexName))
    ).thenApply(s -> s.setIndex(srcIndexName));
  }

  CompletableFuture<AsyncTask> deleteIndex(String indexName) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName),
        AsyncTask.class
      )
    ).thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTaskIndexing> addObject(String indexName, T object) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName),
        AsyncTaskIndexing.class
      ).setData(object)
    ).thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTaskIndexing> addObject(String indexName, String objectID, T object) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        AsyncTaskIndexing.class
      ).setData(object)
    ).thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<Optional<T>> getObject(String indexName, String objectID, Class<T> klass) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        true,
        Arrays.asList("1", "indexes", indexName, objectID),
        klass
      )
    ).thenApply(Optional::ofNullable);
  }

  <T> CompletableFuture<AsyncTaskSingleIndex> addObjects(String indexName, List<T> objects) {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchAddObjectOperation::new).collect(Collectors.toList())
    ).thenApply(s -> s.setIndex(indexName));
  }

  private CompletableFuture<AsyncTaskSingleIndex> batchSingleIndex(String indexName, List<BatchOperation> operations) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "batch"),
        AsyncTaskSingleIndex.class
      ).setData(new Batch(operations))
    ).thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTask> saveObject(String indexName, String objectID, T object) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        AsyncTask.class
      ).setData(object)
    ).thenApply(s -> s.setIndex(indexName));
  }

  <T> CompletableFuture<AsyncTaskSingleIndex> saveObjects(String indexName, List<T> objects) {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchUpdateObjectOperation::new).collect(Collectors.toList())
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> deleteObject(String indexName, String objectID) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        AsyncTask.class
      )
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> deleteObjects(String indexName, List<String> objectIDs) {
    return batchSingleIndex(
      indexName,
      objectIDs.stream().map(BatchDeleteObjectOperation::new).collect(Collectors.toList())
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> clearIndex(String indexName) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "clear"),
        AsyncTask.class
      )
    ).thenApply(s -> s.setIndex(indexName));
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<List<T>> getObjects(String indexName, List<String> objectIDs, Class<T> klass) {
    Requests requests = new Requests(objectIDs.stream().map(o -> new Requests.Request().setIndexName(indexName).setObjectID(o)).collect(Collectors.toList()));
    AlgoliaRequest<Results> algoliaRequest = new AlgoliaRequest<>(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "objects"),
      Results.class,
      klass
    );

    return httpClient
      .requestWithRetry(algoliaRequest.setData(requests))
      .thenApply(Results::getResults);
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<List<T>> getObjects(String indexName, List<String> objectIDs, List<String> attributesToRetrieve, Class<T> klass) {
    String encodedAttributesToRetrieve = String.join(",", attributesToRetrieve);
    Requests requests = new Requests(objectIDs.stream().map(o -> new Requests.Request().setIndexName(indexName).setObjectID(o).setAttributesToRetrieve(encodedAttributesToRetrieve)).collect(Collectors.toList()));
    AlgoliaRequest<Results> algoliaRequest = new AlgoliaRequest<>(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "objects"),
      Results.class,
      klass
    );

    return httpClient
      .requestWithRetry(algoliaRequest.setData(requests))
      .thenApply(Results::getResults);
  }

  CompletableFuture<IndexSettings> getSettings(String indexName) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        true,
        Arrays.asList("1", "indexes", indexName, "settings"),
        IndexSettings.class
      ).setParameters(ImmutableMap.of("getVersion", "2"))
    );
  }

  CompletableFuture<AsyncTask> setSettings(String indexName, IndexSettings settings, Boolean forwardToReplicas) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, "settings"),
        AsyncTask.class
      )
        .setData(settings)
        .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<List<ApiKey>> listKeys(String indexName) {
    CompletableFuture<ApiKeys> result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "keys"),
        ApiKeys.class
      )
    );

    return result.thenApply(ApiKeys::getKeys);
  }

  CompletableFuture<Optional<ApiKey>> getKey(String indexName, String key) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "keys", key),
        ApiKey.class
      )
    ).thenApply(Optional::ofNullable);
  }

  CompletableFuture<DeleteKey> deleteKey(String indexName, String key) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, "keys", key),
        DeleteKey.class
      )
    );
  }

  CompletableFuture<CreateUpdateKey> addKey(String indexName, ApiKey key) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "keys"),
        CreateUpdateKey.class
      ).setData(key)
    );
  }

  CompletableFuture<CreateUpdateKey> updateKey(String indexName, String keyName, ApiKey key) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, "keys", keyName),
        CreateUpdateKey.class
      ).setData(key)
    );
  }

  @SuppressWarnings("unchecked")
  <T> CompletableFuture<SearchResult<T>> search(String indexName, Query query, Class<T> klass) {
    AlgoliaRequest<SearchResult<T>> algoliaRequest = new AlgoliaRequest(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", indexName, "query"),
      SearchResult.class,
      klass
    );

    return httpClient
      .requestWithRetry(algoliaRequest.setData(new Search(query)))
      .thenCompose(result -> {
        CompletableFuture<SearchResult<T>> r = new CompletableFuture<>();
        if (result == null) { //Special case when the index does not exists
          r.completeExceptionally(new AlgoliaIndexNotFoundException(indexName + " does not exist"));
        } else {
          r.complete(result);
        }
        return r;
      });
  }

  CompletableFuture<AsyncTaskSingleIndex> batch(String indexName, List<BatchOperation> operations) {
    //Special case for single index batches, indexName of operations should be null
    boolean onSameIndex = operations.stream().allMatch(o -> java.util.Objects.equals(null, o.getIndexName()));
    if (!onSameIndex) {
      Utils.completeExceptionally(new AlgoliaException("All operations are not on the same index"));
    }

    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "batch"),
        AsyncTaskSingleIndex.class
      ).setData(new BatchOperations(operations))
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(String indexName, PartialUpdateOperation operation, Boolean createIfNotExists) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, operation.getObjectID(), "partial"),
        AsyncTaskSingleIndex.class
      ).setParameters(ImmutableMap.of("createIfNotExists", createIfNotExists.toString())).setData(operation.toSerialize())
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(String indexName, String objectID, Object object) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, objectID, "partial"),
        AsyncTaskSingleIndex.class
      ).setData(object)
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> saveSynonym(String indexName, String synonymID, AbstractSynonym content, Boolean forwardToReplicas, Boolean replaceExistingSynonyms) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
        AsyncTask.class
      ).setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString())).setData(content)
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<Optional<AbstractSynonym>> getSynonym(String indexName, String synonymID) {
    return httpClient
      .requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
          AbstractSynonym.class
        )
      )
      .thenApply(Optional::ofNullable);
  }

  CompletableFuture<AsyncTask> deleteSynonym(String indexName, String synonymID, Boolean forwardToReplicas) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
        AsyncTask.class
      ).setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTask> clearSynonyms(String indexName, Boolean forwardToReplicas) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "clear"),
        AsyncTask.class
      ).setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString()))
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<SearchSynonymResult> searchSynonyms(String indexName, SynonymQuery query) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "search"),
        SearchSynonymResult.class
      ).setData(query)
    );
  }

  CompletableFuture<AsyncTask> batchSynonyms(String indexName, List<AbstractSynonym> synonyms, Boolean forwardToReplicas, Boolean replaceExistingSynonyms) {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "batch"),
        AsyncTask.class
      )
        .setParameters(ImmutableMap.of("forwardToReplicas", forwardToReplicas.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString()))
        .setData(synonyms)
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(String indexName, List<Object> objects) {
    return batch(
      indexName,
      objects.stream().map(BatchPartialUpdateObjectOperation::new).collect(Collectors.toList())
    ).thenApply(s -> s.setIndex(indexName));
  }

  CompletableFuture<SearchFacetResult> searchForFacetValues(String indexName, String facetName, String facetQuery, Query query) {
    query = query == null ? new Query() : query;
    query = query.addCustomParameter("facetQuery", facetQuery);

    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        true,
        Arrays.asList("1", "indexes", indexName, "facets", facetName, "query"),
        SearchFacetResult.class
      ).setData(new Search(query))
    );
  }
}

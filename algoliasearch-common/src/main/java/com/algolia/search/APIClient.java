package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.http.HttpMethod;
import com.algolia.search.inputs.*;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchDeleteObjectOperation;
import com.algolia.search.inputs.batch.BatchPartialUpdateObjectOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.sync.*;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class APIClient {

  /**
   * Constructor & protected stuff
   */
  protected final AlgoliaHttpClient httpClient;
  protected final APIClientConfiguration configuration;

  APIClient(AlgoliaHttpClient httpClient, APIClientConfiguration configuration) {
    this.httpClient = httpClient;
    this.configuration = configuration;
  }

  /**
   * All public method
   */

  /**
   * List all existing indexes
   *
   * @return A List of the indices and their metadata
   * @throws AlgoliaException
   */
  public List<Index.Attributes> listIndices() throws AlgoliaException {
    Indices result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(HttpMethod.GET, true, Arrays.asList("1", "indexes"), Indices.class)
    );

    return result.getItems();
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param name  name of the index
   * @param klass class of the object in this index
   * @param <T>   the type of the objects in this index
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

  /**
   * Return 10 last log entries.
   *
   * @return A List<Log>
   * @throws AlgoliaException
   */
  public List<Log> getLogs() throws AlgoliaException {
    Logs result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "logs"),
        Logs.class
      )
    );

    return result.getLogs();
  }

  /**
   * Return last logs entries
   *
   * @param offset  Specify the first entry to retrieve (0-based, 0 is the most recent log entry)
   * @param length  Specify the maximum number of entries to retrieve starting at offset. Maximum allowed value: 1000
   * @param logType Specify the type of log to retrieve
   * @return The List of Logs
   * @throws AlgoliaException
   */
  public List<Log> getLogs(@Nonnull Integer offset, @Nonnull Integer length, @Nonnull LogType logType) throws AlgoliaException {
    Preconditions.checkArgument(offset >= 0, "offset must be >= 0, was %s", offset);
    Preconditions.checkArgument(length >= 0, "length must be >= 0, was %s", length);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("offset", offset.toString());
    parameters.put("length", length.toString());
    parameters.put("type", logType.getName());

    Logs result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "logs"),
        Logs.class
      ).setParameters(parameters)
    );

    return result.getLogs();
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @return A List of Keys
   * @throws AlgoliaException
   */
  public List<ApiKey> listKeys() throws AlgoliaException {
    ApiKeys result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "keys"),
        ApiKeys.class
      )
    );

    return result.getKeys();
  }

  /**
   * Get an Key from it's name
   *
   * @param key name of the key
   * @return the key
   * @throws AlgoliaException
   */
  public Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return Optional.ofNullable(httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "keys", key),
        ApiKey.class
      )
    ));
  }

  /**
   * Delete an existing key
   *
   * @param key name of the key
   * @throws AlgoliaException
   */
  public DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
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
   * Create a new key
   *
   * @param key the key with the ACLs
   * @return the metadata of the key (such as it's name)
   * @throws AlgoliaException
   */
  public CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "keys"),
        CreateUpdateKey.class).setData(key)
    );
  }

  /**
   * Update a key
   *
   * @param keyName name of the key to update
   * @param key     the key with the ACLs
   * @return the metadata of the key (such as it's name)
   * @throws AlgoliaException
   */
  public CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "keys", keyName),
        CreateUpdateKey.class).setData(key)
    );
  }

  /**
   * Generate a secured and public API Key from a query and an
   * optional user token identifying the current user
   *
   * @param privateApiKey your private API Key
   * @param query         contains the parameter applied to the query (used as security)
   * @throws AlgoliaException
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
   * @throws AlgoliaException
   */
  @SuppressWarnings("WeakerAccess")
  public String generateSecuredApiKey(@Nonnull String privateApiKey, @Nonnull Query query, String userToken) throws AlgoliaException {
    return Utils.generateSecuredApiKey(privateApiKey, query, userToken);
  }

  public <T> void waitTask(@Nonnull GenericTask<T> task, long timeToWait) throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    while (true) {
      TaskStatus status = httpClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes", task.getIndexName(), "task", task.getTaskIDToWaitFor().toString()),
          TaskStatus.class)
      );

      if (Objects.equals("published", status.getStatus())) {
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
   * @throws AlgoliaException
   */
  public TasksMultipleIndex batch(@Nonnull List<BatchOperation> operations) throws AlgoliaException {
    boolean atLeastOneHaveIndexNameNull = operations.stream().anyMatch(o -> o.getIndexName() == null);
    if (atLeastOneHaveIndexNameNull) {
      throw new AlgoliaException("All batch operations must have an index name set");
    }

    TasksMultipleIndex request = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        true,
        Arrays.asList("1", "indexes", "*", "batch"),
        TasksMultipleIndex.class
      ).setData(new BatchOperations(operations))
    );

    return request.setAPIClient(this);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>MultiQueriesStrategy.NONE</code>
   *
   * @param queries the queries
   * @return the result of the queries
   * @throws AlgoliaException
   */
  public MultiQueriesResult multipleQueries(@Nonnull List<IndexQuery> queries) throws AlgoliaException {
    return multipleQueries(queries, MultiQueriesStrategy.NONE);
  }

  /**
   * Performs multiple searches on multiple indices
   *
   * @param queries  the queries
   * @param strategy the strategy to apply to this multiple queries
   * @return the result of the queries
   * @throws AlgoliaException
   */
  @SuppressWarnings("WeakerAccess")
  public MultiQueriesResult multipleQueries(@Nonnull List<IndexQuery> queries, @Nonnull MultiQueriesStrategy strategy) throws AlgoliaException {
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

  Task moveIndex(String srcIndexName, String dstIndexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", srcIndexName, "operation"),
        Task.class
      ).setData(new OperationOnIndex("move", dstIndexName))
    );

    return result.setAPIClient(this).setIndex(srcIndexName);
  }

  Task copyIndex(String srcIndexName, String dstIndexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", srcIndexName, "operation"),
        Task.class
      ).setData(new OperationOnIndex("copy", dstIndexName))
    );

    return result.setAPIClient(this).setIndex(srcIndexName);
  }

  Task deleteIndex(String indexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName),
        Task.class
      )
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> TaskIndexing addObject(String indexName, T object) throws AlgoliaException {
    TaskIndexing result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName),
        TaskIndexing.class
      ).setData(object)
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> TaskIndexing addObject(String indexName, String objectID, T object) throws AlgoliaException {
    TaskIndexing result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        TaskIndexing.class
      ).setData(object)
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> Optional<T> getObject(String indexName, String objectID, Class<T> klass) throws AlgoliaException {
    return Optional.ofNullable(httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        true,
        Arrays.asList("1", "indexes", indexName, objectID),
        klass
      )
    ));
  }

  <T> TaskSingleIndex addObjects(String indexName, List<T> objects) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchAddObjectOperation::new).collect(Collectors.toList())
    )
      .setAPIClient(this)
      .setIndex(indexName);
  }

  private TaskSingleIndex batchSingleIndex(String indexName, List<BatchOperation> operations) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "batch"),
        TaskSingleIndex.class
      ).setData(new Batch(operations))
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> Task saveObject(String indexName, String objectID, T object) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        Task.class
      ).setData(object)
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  <T> TaskSingleIndex saveObjects(String indexName, List<T> objects) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchUpdateObjectOperation::new).collect(Collectors.toList())
    )
      .setAPIClient(this)
      .setIndex(indexName);
  }

  Task deleteObject(String indexName, String objectID) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, objectID),
        Task.class
      )
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex deleteObjects(String indexName, List<String> objectIDs) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objectIDs.stream().map(BatchDeleteObjectOperation::new).collect(Collectors.toList())
    )
      .setAPIClient(this)
      .setIndex(indexName);
  }

  Task clearIndex(String indexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "clear"),
        Task.class
      )
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  @SuppressWarnings("unchecked")
  <T> List<T> getObjects(String indexName, List<String> objectIDs, Class<T> klass) throws AlgoliaException {
    Requests requests = new Requests(objectIDs.stream().map(o -> new Requests.Request().setIndexName(indexName).setObjectID(o)).collect(Collectors.toList()));
    AlgoliaRequest<Results> algoliaRequest = new AlgoliaRequest<>(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "objects"),
      Results.class,
      klass
    );

    return httpClient.requestWithRetry(algoliaRequest.setData(requests)).getResults();
  }

  IndexSettings getSettings(String indexName) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        true,
        Arrays.asList("1", "indexes", indexName, "settings"),
        IndexSettings.class
      ).setParameters(ImmutableMap.of("getVersion", "2"))
    );
  }

  Task setSettings(String indexName, IndexSettings settings, Boolean forwardToSlaves) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, "settings"),
        Task.class
      )
        .setData(settings)
        .setParameters(ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString()))
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  List<ApiKey> listKeys(String indexName) throws AlgoliaException {
    ApiKeys result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "keys"),
        ApiKeys.class
      )
    );

    return result.getKeys();
  }

  Optional<ApiKey> getKey(String indexName, String key) throws AlgoliaException {
    return Optional.ofNullable(httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "keys", key),
        ApiKey.class
      )
    ));
  }

  DeleteKey deleteKey(String indexName, String key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, "keys", key),
        DeleteKey.class
      )
    );
  }

  CreateUpdateKey addKey(String indexName, ApiKey key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "keys"),
        CreateUpdateKey.class
      ).setData(key)
    );
  }

  CreateUpdateKey updateKey(String indexName, String keyName, ApiKey key) throws AlgoliaException {
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
  <T> SearchResult<T> search(String indexName, Query query, Class<T> klass) throws AlgoliaException {
    AlgoliaRequest<SearchResult> algoliaRequest = new AlgoliaRequest<>(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", indexName, "query"),
      SearchResult.class,
      klass
    );

    return httpClient.requestWithRetry(algoliaRequest.setData(new Search(query)));
  }

  TaskSingleIndex batch(String indexName, List<BatchOperation> operations) throws AlgoliaException {
    //Special case for single index batches, indexName of operations should be null
    boolean onSameIndex = operations.stream().allMatch(o -> Objects.equals(null, o.getIndexName()));
    if (!onSameIndex) {
      throw new AlgoliaException("All operations are not on the same index");
    }

    TaskSingleIndex result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "batch"),
        TaskSingleIndex.class
      ).setData(new BatchOperations(operations))
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex partialUpdateObject(String indexName, PartialUpdateOperation operation, Boolean createIfNotExists) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, operation.getObjectID(), "partial"),
        TaskSingleIndex.class
      ).setParameters(ImmutableMap.of("createIfNotExists", createIfNotExists.toString())).setData(operation.toSerialize())
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  TaskSingleIndex partialUpdateObject(String indexName, String objectID, Object object) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, objectID, "partial"),
        TaskSingleIndex.class
      ).setData(object)
    );

    return result.setAPIClient(this).setIndex(indexName);
  }

  Task saveSynonym(String indexName, String synonymID, AbstractSynonym content, Boolean forwardToSlaves, Boolean replaceExistingSynonyms) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.PUT,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
        Task.class
      ).setParameters(ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString())).setData(content)
    );


    return task.setAPIClient(this).setIndex(indexName);
  }

  Optional<AbstractSynonym> getSynonym(String indexName, String synonymID) throws AlgoliaException {
    return Optional.ofNullable(
      httpClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
          AbstractSynonym.class
        )
      )
    );
  }

  Task deleteSynonym(String indexName, String synonymID, Boolean forwardToSlaves) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.DELETE,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
        Task.class
      ).setParameters(ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString()))
    );

    return task.setAPIClient(this).setIndex(indexName);
  }

  Task clearSynonyms(String indexName, Boolean forwardToSlaves) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "clear"),
        Task.class
      ).setParameters(ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString()))
    );

    return task.setAPIClient(this).setIndex(indexName);
  }

  SearchSynonymResult searchSynonyms(String indexName, SynonymQuery query) throws AlgoliaException {
    return httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "search"),
        SearchSynonymResult.class).setData(query)
    );
  }

  Task batchSynonyms(String indexName, List<AbstractSynonym> synonyms, Boolean forwardToSlaves, Boolean replaceExistingSynonyms) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.POST,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", "batch"),
        Task.class
      )
        .setParameters(ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString()))
        .setData(synonyms)
    );

    return task.setAPIClient(this).setIndex(indexName);
  }

  void deleteByQuery(String indexName, Query query, int batchSize) throws AlgoliaException {
    query = query
      .setAttributesToRetrieve(Collections.singletonList("objectID"))
      .setAttributesToHighlight(Collections.emptyList())
      .setAttributesToSnippet(Collections.emptyList())
      .setHitsPerPage(1000) //Magic number
      .setDistinct(false);

    List<String> objectToDelete = new ArrayList<>(batchSize);
    for (ObjectID o : new IndexIterable<>(this, indexName, query, ObjectID.class)) {
      objectToDelete.add(o.getObjectID());

      while (objectToDelete.size() >= batchSize) {
        List<String> subList = objectToDelete.subList(0, batchSize);
        deleteObjects(indexName, subList).waitForCompletion();
        subList.clear();
      }
    }

    if (!objectToDelete.isEmpty()) {
      deleteObjects(indexName, objectToDelete).waitForCompletion();
    }
  }

  TaskSingleIndex partialUpdateObjects(String indexName, List<Object> objects) throws AlgoliaException {
    TaskSingleIndex task = batch(
      indexName,
      objects.stream().map(BatchPartialUpdateObjectOperation::new).collect(Collectors.toList())
    );

    return task.setAPIClient(this).setIndex(indexName);
  }

  @SuppressWarnings("unchecked")
  <T> BrowseResult<T> browse(String indexName, Query query, String cursor, Class<T> klass) throws AlgoliaException {
    AlgoliaRequest<BrowseResult> algoliaRequest = new AlgoliaRequest<>(
      HttpMethod.GET,
      true,
      Arrays.asList("1", "indexes", indexName, "browse"),
      BrowseResult.class,
      klass
    );

    return httpClient.requestWithRetry(algoliaRequest.setParameters(query.setCursor(cursor).toQueryParam()));
  }

  /**
   * Used internally for deleteByQuery
   */
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

package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.*;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchDeleteObjectOperation;
import com.algolia.search.inputs.batch.BatchPartialUpdateObjectOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.responses.*;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.apache.commons.codec.binary.Hex;

import javax.annotation.Nonnull;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class APIClient {

  private static final long MAX_TIME_MS_TO_WAIT = 10000L;

  /**
   * Constructor & protected stuff
   */
  protected final AlgoliaHttpClient httpClient;

  APIClient(AlgoliaHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  private static String hmac(String key, String msg) throws AlgoliaException {
    Mac hmac;
    try {
      hmac = Mac.getInstance("HmacSHA256");
      hmac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
    } catch (NoSuchAlgorithmException e) {
      throw new AlgoliaException("Can not find HmacSHA256 algorithm", e);
    } catch (InvalidKeyException e) {
      throw new AlgoliaException("Can not init HmacSHA256 algorithm", e);
    }
    byte[] rawHmac = hmac.doFinal(msg.getBytes());
    byte[] hexBytes = new Hex().encode(rawHmac);
    return new String(hexBytes);
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
      HttpMethod.GET,
      true,
      Arrays.asList("1", "indexes"),
      Indices.class
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
      HttpMethod.GET,
      false,
      Arrays.asList("1", "logs"),
      Logs.class
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
      HttpMethod.GET,
      false,
      Arrays.asList("1", "logs"),
      parameters,
      Logs.class
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
      HttpMethod.GET,
      false,
      Arrays.asList("1", "keys"),
      ApiKeys.class
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
      HttpMethod.GET,
      false,
      Arrays.asList("1", "keys", key),
      ApiKey.class
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
      HttpMethod.DELETE,
      false,
      Arrays.asList("1", "keys", key),
      DeleteKey.class
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
      HttpMethod.POST,
      false,
      Arrays.asList("1", "keys"),
      key,
      CreateUpdateKey.class
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
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "keys", keyName),
      key,
      CreateUpdateKey.class
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
  public String generateSecuredApiKey(@Nonnull String privateApiKey, @Nonnull Query query, String userToken) throws AlgoliaException {
    if (userToken != null && userToken.length() > 0) {
      query.setUserToken(userToken);
    }
    String queryStr = query.toParam();
    String key = hmac(privateApiKey, queryStr);

    return new String(Base64.getEncoder().encode(String.format("%s%s", key, queryStr).getBytes(Charset.forName("UTF8"))));
  }

  public <T> void waitTask(@Nonnull String indexName, @Nonnull GenericTask<T> task, long timeToWait) throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    while (true) {
      TaskStatus status = httpClient.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "task", task.getTaskIDToWaitFor().toString()),
        TaskStatus.class
      );

      if (Objects.equals("published", status.getStatus())) {
        return;
      }
      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException ignored) {
      }
      timeToWait *= 2;
      timeToWait = timeToWait > MAX_TIME_MS_TO_WAIT ? MAX_TIME_MS_TO_WAIT : timeToWait;
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
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "batch"),
      new BatchOperations(operations),
      TasksMultipleIndex.class
    );

    return request.setAttributes(null, this);
  }

  /**
   * Performs multiple searches on multiple indices with the strategy <code>{@link MultiQueriesStrategy.NONE}</code>
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
  public MultiQueriesResult multipleQueries(@Nonnull List<IndexQuery> queries, @Nonnull MultiQueriesStrategy strategy) throws AlgoliaException {
    return httpClient.requestWithRetry(
     HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "queries"),
      ImmutableMap.of("strategy", strategy.getName()),
      new MultipleQueriesRequests(queries),
      MultiQueriesResult.class
    );
  }

  /**
   * Package protected method for the Index class
   **/

  Task moveIndex(String srcIndexName, String dstIndexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", srcIndexName, "operation"),
      new OperationOnIndex("move", dstIndexName),
      Task.class
    );

    return result.setAttributes(srcIndexName, this);
  }

  Task copyIndex(String srcIndexName, String dstIndexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", srcIndexName, "operation"),
      new OperationOnIndex("copy", dstIndexName),
      Task.class
    );

    return result.setAttributes(srcIndexName, this);
  }

  Task deleteIndex(String name) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.DELETE,
      false,
      Arrays.asList("1", "indexes", name),
      Task.class
    );

    return result.setAttributes(name, this);
  }

  <T> TaskIndexing addObject(String indexName, T object) throws AlgoliaException {
    TaskIndexing result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName),
      object,
      TaskIndexing.class
    );

    return result.setAttributes(indexName, this);
  }

  <T> TaskIndexing addObject(String indexName, String objectID, T object) throws AlgoliaException {
    TaskIndexing result = httpClient.requestWithRetry(
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "indexes", indexName, objectID),
      object,
      TaskIndexing.class
    );

    return result.setAttributes(indexName, this);
  }

  <T> Optional<T> getObject(String indexName, String objectID, Class<T> klass) throws AlgoliaException {
    return Optional.ofNullable(httpClient.requestWithRetry(
      HttpMethod.GET,
      true,
      Arrays.asList("1", "indexes", indexName, objectID),
      klass
    ));
  }

  <T> TaskSingleIndex addObjects(String indexName, List<T> objects) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchAddObjectOperation::new).collect(Collectors.toList())
    ).setAttributes(indexName, this);
  }

  TaskSingleIndex batchSingleIndex(String indexName, List<BatchOperation> operations) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "batch"),
      new Batch(operations),
      TaskSingleIndex.class
    );

    return result.setAttributes(indexName, this);
  }

  <T> Task saveObject(String indexName, String objectID, T object) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "indexes", indexName, objectID),
      object,
      Task.class
    );

    return result.setAttributes(indexName, this);
  }

  <T> TaskSingleIndex saveObjects(String indexName, List<T> objects) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objects.stream().map(BatchUpdateObjectOperation::new).collect(Collectors.toList())
    ).setAttributes(indexName, this);
  }

  Task deleteObject(String indexName, String objectID) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.DELETE,
      false,
      Arrays.asList("1", "indexes", indexName, objectID),
      Task.class
    );

    return result.setAttributes(indexName, this);
  }

  TaskSingleIndex deleteObjects(String indexName, List<String> objectIDs) throws AlgoliaException {
    return batchSingleIndex(
      indexName,
      objectIDs.stream().map(BatchDeleteObjectOperation::new).collect(Collectors.toList())
    ).setAttributes(indexName, this);
  }

  Task clearIndex(String indexName) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "clear"),
      Task.class
    );

    return result.setAttributes(indexName, this);
  }

  <T> List<T> getObjects(String indexName, List<String> objectIDs, Class<T> klass) throws AlgoliaException {
    TypeToken<Results<T>> typeToken =
      new TypeToken<Results<T>>() {
      }.where(new TypeParameter<T>() {
      }, klass);

    Results<T> result = httpClient.requestWithRetry(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", "*", "objects"),
      new Requests(objectIDs.stream().map(o -> new Requests.Request().setIndexName(indexName).setObjectID(o)).collect(Collectors.toList())),
      typeToken.getType()
    );

    return result.getResults();
  }

  IndexSettings getSettings(String indexName) throws AlgoliaException {
    Map<String, String> params = new HashMap<>();
    params.put("getVersion", "2");

    return httpClient.requestWithRetry(
      HttpMethod.GET,
      true,
      Arrays.asList("1", "indexes", indexName, "settings"),
      params,
      IndexSettings.class
    );
  }

  Task setSettings(String indexName, IndexSettings settings) throws AlgoliaException {
    Task result = httpClient.requestWithRetry(
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "indexes", indexName, "settings"),
      settings,
      Task.class
    );

    return result.setAttributes(indexName, this);
  }

  List<ApiKey> listKeys(String indexName) throws AlgoliaException {
    ApiKeys result = httpClient.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes", indexName, "keys"),
      ApiKeys.class
    );

    return result.getKeys();
  }

  Optional<ApiKey> getKey(String indexName, String key) throws AlgoliaException {
    return Optional.ofNullable(httpClient.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes", indexName, "keys", key),
      ApiKey.class
    ));
  }

  DeleteKey deleteKey(String indexName, String key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      HttpMethod.DELETE,
      false,
      Arrays.asList("1", "indexes", indexName, "keys", key),
      DeleteKey.class
    );
  }

  CreateUpdateKey addKey(String indexName, ApiKey key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "keys"),
      key,
      CreateUpdateKey.class
    );
  }

  CreateUpdateKey updateKey(String indexName, String keyName, ApiKey key) throws AlgoliaException {
    return httpClient.requestWithRetry(
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "indexes", indexName, "keys", keyName),
      key,
      CreateUpdateKey.class
    );
  }

  <T> SearchResult<T> search(String indexName, Query query, Class<T> klass) throws AlgoliaException {
    TypeToken<SearchResult<T>> typeToken =
      new TypeToken<SearchResult<T>>() {
      }.where(new TypeParameter<T>() {
      }, klass);

    return httpClient.requestWithRetry(
      HttpMethod.POST,
      true,
      Arrays.asList("1", "indexes", indexName, "query"),
      Maps.newHashMap(),
      new Search(query),
      typeToken.getType()
    );
  }

  TaskSingleIndex batch(String indexName, List<BatchOperation> operations) throws AlgoliaException {
    //Special case for single index batches, indexName of operations should be null
    boolean onSameIndex = operations.stream().allMatch(o -> Objects.equals(null, o.getIndexName()));
    if (!onSameIndex) {
      throw new AlgoliaException("All operations are not on the same index");
    }

    TaskSingleIndex result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "batch"),
      new BatchOperations(operations),
      TaskSingleIndex.class
    );

    return result.setAttributes(indexName, this);
  }

  TaskSingleIndex partialUpdateObject(String indexName, PartialUpdateOperation operation, Boolean createIfNotExists) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, operation.getObjectID(), "partial"),
      ImmutableMap.of("createIfNotExists", createIfNotExists.toString()),
      operation.toSerialize(),
      TaskSingleIndex.class
    );

    return result.setAttributes(indexName, this);
  }

  TaskSingleIndex partialUpdateObject(String indexName, String objectID, Object object) throws AlgoliaException {
    TaskSingleIndex result = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, objectID, "partial"),
      object,
      TaskSingleIndex.class
    );

    return result.setAttributes(indexName, this);
  }

  Task saveSynonym(String indexName, String synonymID, AbstractSynonym content, Boolean forwardToSlaves, Boolean replaceExistingSynonyms) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      HttpMethod.PUT,
      false,
      Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
      ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString()),
      content,
      Task.class
    );

    return task.setAttributes(indexName, this);
  }

  Optional<AbstractSynonym> getSynonym(String indexName, String synonymID) throws AlgoliaException {
    return Optional.ofNullable(
      httpClient.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
        AbstractSynonym.class
      )
    );
  }

  Task deleteSynonym(String indexName, String synonymID, Boolean forwardToSlaves) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      HttpMethod.DELETE,
      false,
      Arrays.asList("1", "indexes", indexName, "synonyms", synonymID),
      ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString()),
      Task.class
    );

    return task.setAttributes(indexName, this);
  }

  Task clearSynonyms(String indexName, Boolean forwardToSlaves) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "synonyms", "clear"),
      ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString()),
      Task.class
    );

    return task.setAttributes(indexName, this);
  }

  SearchSynonymResult searchSynonyms(String indexName, SynonymQuery query) throws AlgoliaException {
    return httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "synonyms", "search"),
      query,
      SearchSynonymResult.class
    );
  }

  Task batchSynonyms(String indexName, List<AbstractSynonym> synonyms, Boolean forwardToSlaves, Boolean replaceExistingSynonyms) throws AlgoliaException {
    Task task = httpClient.requestWithRetry(
      HttpMethod.POST,
      false,
      Arrays.asList("1", "indexes", indexName, "synonyms", "batch"),
      ImmutableMap.of("forwardToSlaves", forwardToSlaves.toString(), "replaceExistingSynonyms", replaceExistingSynonyms.toString()),
      synonyms,
      Task.class
    );

    return task.setAttributes(indexName, this);
  }

  //TODO
  TaskSingleIndex deleteByQuery(String indexName, Query query) {
    return null;
  }

  TaskSingleIndex partialUpdateObjects(String indexName, List<Object> objects) throws AlgoliaException {
    TaskSingleIndex task = batch(
      indexName,
      objects.stream().map(BatchPartialUpdateObjectOperation::new).collect(Collectors.toList())
    );

    return task.setAttributes(indexName, this);
  }

  <T> BrowseResult<T> browse(String indexName, Query query, String cursor, Class<T> klass) throws AlgoliaException {
    TypeToken<BrowseResult<T>> typeToken =
      new TypeToken<BrowseResult<T>>() {
      }.where(new TypeParameter<T>() {
      }, klass);

    return httpClient.requestWithRetry(
      HttpMethod.GET,
      true,
      Arrays.asList("1", "indexes", indexName, "browse"),
      query.setCursor(cursor).toQueryParam(),
      typeToken.getType()
    );
  }
}

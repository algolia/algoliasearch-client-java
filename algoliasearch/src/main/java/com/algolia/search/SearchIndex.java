package com.algolia.search;

import static java.util.stream.Collectors.toList;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.helpers.AlgoliaHelper;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.IAlgoliaWaitableResponse;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.TaskStatusResponse;
import com.algolia.search.models.indexing.*;
import com.algolia.search.models.indexing.BatchRequest;
import com.algolia.search.models.indexing.BatchResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public final class SearchIndex<T>
    implements SearchIndexRules<T>,
        SearchIndexSynonyms<T>,
        SearchIndexSettings<T>,
        SearchIndexSearching<T> {

  private final HttpTransport transport;
  private final SearchConfig config;
  private final String urlEncodedIndexName;
  private final String indexName;
  private final Class<T> klass;

  SearchIndex(HttpTransport transport, AlgoliaConfigBase config, String indexName, Class<T> klass) {
    this.transport = transport;
    this.config = (SearchConfig) config;
    this.indexName = indexName;
    this.urlEncodedIndexName = QueryStringHelper.urlEncodeUTF8(indexName);
    this.klass = klass;
  }

  public SearchConfig getConfig() {
    return config;
  }

  @Override
  public Class<T> getKlass() {
    return klass;
  }

  @Override
  public HttpTransport getTransport() {
    return transport;
  }

  @Override
  public String getUrlEncodedIndexName() {
    return urlEncodedIndexName;
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<T> getObjectAsync(@Nonnull String objectID) {
    return getObjectAsync(objectID, null);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
   * @param attributesToRetrieve List of attributes to retrieve. By default, all retrievable
   *     attributes are returned.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID,
      @Nonnull List<String> attributesToRetrieve,
      RequestOptions requestOptions) {

    Objects.requireNonNull(attributesToRetrieve, "AttributesToRetrieve are required.");
    Objects.requireNonNull(objectID, "objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters(
        "attributesToRetrieve",
        QueryStringHelper.urlEncodeUTF8(String.join(",", attributesToRetrieve)));

    return getObjectAsync(objectID, requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {

    Objects.requireNonNull(objectID, "objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/" + objectID,
        CallType.READ,
        klass,
        requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<List<T>> getObjectsAsync(@Nonnull List<String> objectIDs) {
    return getObjectsAsync(objectIDs, null, null);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @param attributesToRetrieve List of attributes to retrieve. By default, all retrievable
   *     attributes are returned.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<List<T>> getObjectsAsync(
      @Nonnull List<String> objectIDs, List<String> attributesToRetrieve) {
    return getObjectsAsync(objectIDs, attributesToRetrieve, null);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<List<T>> getObjectsAsync(
      @Nonnull List<String> objectIDs, RequestOptions requestOptions) {
    return getObjectsAsync(objectIDs, null, requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @param attributesToRetrieve List of attributes to retrieve. By default, all retrievable
   *     attributes are returned.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<List<T>> getObjectsAsync(
      @Nonnull List<String> objectIDs,
      List<String> attributesToRetrieve,
      RequestOptions requestOptions) {

    Objects.requireNonNull(objectIDs, "Object IDs are required.");

    if (objectIDs.isEmpty()) {
      throw new IllegalArgumentException("objectIDs can't be empty.");
    }

    List<MultipleGetObject> queries = new ArrayList<>();

    for (String objectId : objectIDs) {
      queries.add(new MultipleGetObject(this.indexName, objectId, attributesToRetrieve));
    }

    MultipleGetObjectsRequest request = new MultipleGetObjectsRequest(queries);

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/objects",
            CallType.READ,
            request,
            MultipleGetObjectsResponse.class,
            klass,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<List<T>> r = new CompletableFuture<>();
              r.complete(resp.getResults());
              return r;
            },
            config.getExecutor());
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data Data to update
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<UpdateObjectResponse> partialUpdateObjectAsync(@Nonnull T data) {
    return partialUpdateObjectAsync(data, false, null);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data Data to update
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public CompletableFuture<UpdateObjectResponse> partialUpdateObjectAsync(
      @Nonnull T data, RequestOptions requestOptions) {
    return partialUpdateObjectAsync(data, false, requestOptions);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data Data to update
   * @param createIfNotExists When true, a partial update on a nonexistent object will create the
   *     object (generating the objectID and using the attributes as defined in the object). WHen
   *     false, a partial update on a nonexistent object will be ignored (but no error will be sent
   *     back).
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public CompletableFuture<UpdateObjectResponse> partialUpdateObjectAsync(
      @Nonnull T data, @Nonnull Boolean createIfNotExists) {
    return partialUpdateObjectAsync(data, createIfNotExists, null);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data Data to update
   * @param createIfNotExists When true, a partial update on a nonexistent object will create the
   *     object (generating the objectID and using the attributes as defined in the object). WHen
   *     false, a partial update on a nonexistent object will be ignored (but no error will be sent
   *     back).
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public CompletableFuture<UpdateObjectResponse> partialUpdateObjectAsync(
      @Nonnull T data, @Nonnull Boolean createIfNotExists, RequestOptions requestOptions) {

    Objects.requireNonNull(data, "Data is required.");
    Objects.requireNonNull(createIfNotExists, "createIfNotExists is required.");

    String objectID = AlgoliaHelper.getObjectID(data, klass);

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters("createIfNotExists", createIfNotExists.toString());

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/" + objectID + "/" + "partial",
            CallType.WRITE,
            data,
            UpdateObjectResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data The data to send to the API
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> partialUpdateObjectsAsync(
      @Nonnull Iterable<T> data) {
    return partialUpdateObjectsAsync(data, false, null);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data The data to send to the API
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> partialUpdateObjectsAsync(
      @Nonnull Iterable<T> data, RequestOptions requestOptions) {
    return partialUpdateObjectsAsync(data, false, requestOptions);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data The data to send to the API
   * @param createIfNotExists When true, a partial update on a nonexistent object will create the
   *     object (generating the objectID and using the attributes as defined in the object). WHen
   *     false, a partial update on a nonexistent object will be ignored (but no error will be sent
   *     back).
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> partialUpdateObjectsAsync(
      @Nonnull Iterable<T> data, boolean createIfNotExists) {
    return partialUpdateObjectsAsync(data, createIfNotExists, null);
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data The data to send to the API
   * @param createIfNotExists When true, a partial update on a nonexistent object will create the
   *     object (generating the objectID and using the attributes as defined in the object). WHen
   *     false, a partial update on a nonexistent object will be ignored (but no error will be sent
   *     back).
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> partialUpdateObjectsAsync(
      @Nonnull Iterable<T> data, boolean createIfNotExists, RequestOptions requestOptions) {
    Objects.requireNonNull(data, "Data are required.");

    String action =
        createIfNotExists
            ? ActionEnum.PARTIAL_UPDATE_OBJECT
            : ActionEnum.PARTIAL_UPDATE_OBJECT_NO_CREATE;

    return splitIntoBatchesAsync(data, action, requestOptions);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectAsync(@Nonnull T data) {
    return saveObjectAsync(data, false, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "ADD_OBJECT", otherwise
   *     will perform an "UpdateObject"
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectAsync(
      @Nonnull T data, boolean autoGenerateObjectID) {
    return saveObjectAsync(data, autoGenerateObjectID, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectAsync(
      @Nonnull T data, RequestOptions requestOptions) {
    return saveObjectAsync(data, false, requestOptions);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "ADD_OBJECT", otherwise
   *     will perform an "UpdateObject"
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectAsync(
      @Nonnull T data, boolean autoGenerateObjectID, RequestOptions requestOptions) {

    Objects.requireNonNull(data, "Data are required.");

    return saveObjectsAsync(Collections.singletonList(data), autoGenerateObjectID, requestOptions);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(@Nonnull Iterable<T> data) {
    return saveObjectsAsync(data, false, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "ADD_OBJECT", otherwise
   *     will perform an "UpdateObject"
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID) {
    return saveObjectsAsync(data, autoGenerateObjectID, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(
      @Nonnull Iterable<T> data, RequestOptions requestOptions) {
    return saveObjectsAsync(data, false, requestOptions);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "ADD_OBJECT", otherwise
   *     will perform an "UpdateObject"
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID, RequestOptions requestOptions) {
    Objects.requireNonNull(data, "Data are required.");

    if (autoGenerateObjectID) {
      return splitIntoBatchesAsync(data, ActionEnum.ADD_OBJECT, requestOptions);
    }

    return splitIntoBatchesAsync(data, ActionEnum.UPDATE_OBJECT, requestOptions);
  }

  /**
   * Split records into smaller chunks before sending them to the API asynchronously
   *
   * @param data The data to send and chunk
   * @param actionType The action type of the batch
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  <E> CompletableFuture<BatchIndexingResponse> splitIntoBatchesAsync(
      @Nonnull Iterable<E> data, @Nonnull String actionType) {
    return splitIntoBatchesAsync(data, actionType, null);
  }

  /**
   * Split records into smaller chunks before sending them to the API asynchronously
   *
   * @param data The data to send and chunk
   * @param actionType The action type of the batch
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  <E> CompletableFuture<BatchIndexingResponse> splitIntoBatchesAsync(
      @Nonnull Iterable<E> data, @Nonnull String actionType, RequestOptions requestOptions) {

    Objects.requireNonNull(data, "Data are required.");
    Objects.requireNonNull(actionType, "An action type is required.");

    List<CompletableFuture<BatchResponse>> futures = new ArrayList<>();
    List<E> records = new ArrayList<>();

    for (E item : data) {

      if (records.size() == config.getBatchSize()) {
        BatchRequest<E> request = new BatchRequest<>(actionType, records);
        CompletableFuture<BatchResponse> batch = batchAsync(request, requestOptions);
        futures.add(batch);
        records.clear();
      }

      records.add(item);
    }

    if (records.size() > 0) {
      BatchRequest<E> request = new BatchRequest<>(actionType, records);
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request) {
    return LaunderThrowable.unwrap(batchAsync(request, null));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(batchAsync(request, requestOptions));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request -
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public <E> CompletableFuture<BatchResponse> batchAsync(@Nonnull BatchRequest<E> request) {
    return batchAsync(request, null);
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public <E> CompletableFuture<BatchResponse> batchAsync(
      @Nonnull BatchRequest<E> request, RequestOptions requestOptions) {

    Objects.requireNonNull(request, "A BatchRequest is required.");

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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public DeleteResponse deleteObject(@Nonnull String objectID) {
    return LaunderThrowable.unwrap(deleteObjectAsync(objectID, null));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public DeleteResponse deleteObject(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(deleteObjectAsync(objectID, requestOptions));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteResponse> deleteObjectAsync(@Nonnull String objectID) {
    return deleteObjectAsync(objectID, null);
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteResponse> deleteObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + urlEncodedIndexName + "/" + objectID,
            CallType.WRITE,
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
   * Remove objects from an index using their object ids.
   *
   * @param objectIDs List of objectIDs to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BatchIndexingResponse> deleteObjectsAsync(
      @Nonnull List<String> objectIDs) {
    return deleteObjectsAsync(objectIDs, null);
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectIDs List of objectIDs to delete
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<BatchIndexingResponse> deleteObjectsAsync(
      @Nonnull List<String> objectIDs, RequestOptions requestOptions) {
    Objects.requireNonNull(objectIDs, "The objectID is required.");

    if (objectIDs.isEmpty()) {
      throw new IllegalArgumentException("objectIDs can't be empty.");
    }

    List<Map<String, String>> request = new ArrayList<>();

    for (String id : objectIDs) {
      Map<String, String> tmp = new HashMap<>();
      tmp.put("objectID", id);
      request.add(tmp);
    }

    return splitIntoBatchesAsync(request, ActionEnum.DELETE_OBJECT, requestOptions);
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteResponse> clearObjectsAsync() {
    return clearObjectsAsync(null);
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteResponse> clearObjectsAsync(RequestOptions requestOptions) {

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/clear",
            CallType.WRITE,
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
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<MultiResponse> replaceAllObjectsAsync(Iterable<T> data) {
    return replaceAllObjectsAsync(data, null, false);
  }

  /**
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
   * @param safe Run all API calls synchronously
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<MultiResponse> replaceAllObjectsAsync(Iterable<T> data, boolean safe) {
    return replaceAllObjectsAsync(data, null, safe);
  }

  /**
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
   * @param requestOptions Options to pass to this request
   * @param safe Run all API calls synchronously
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<MultiResponse> replaceAllObjectsAsync(
      Iterable<T> data, RequestOptions requestOptions, boolean safe) {
    Objects.requireNonNull(data, "Data can't be null");

    Random rnd = new Random();
    String tmpIndexName = indexName + "_tmp_" + rnd.nextInt(100);
    SearchIndex<T> tmpIndex = new SearchIndex<>(transport, config, tmpIndexName, klass);

    List<String> scopes = Arrays.asList(CopyScope.RULES, CopyScope.SETTINGS, CopyScope.SYNONYMS);

    List<CompletableFuture<? extends IAlgoliaWaitableResponse>> futures = new ArrayList<>();

    // Copy index resources
    CompletableFuture<CopyToResponse> copyResponseFuture =
        copyToAsync(tmpIndexName, scopes, requestOptions);
    futures.add(copyResponseFuture);

    if (safe) {
      copyResponseFuture.join().waitTask();
    }

    // Save new objects
    CompletableFuture<BatchIndexingResponse> saveObjectsFuture =
        tmpIndex.saveObjectsAsync(data, requestOptions);
    futures.add(saveObjectsFuture);

    if (safe) {
      saveObjectsFuture.join().waitTask();
    }

    // Move temporary index to source index
    CompletableFuture<MoveIndexResponse> moveIndexFuture =
        moveFromAsync(QueryStringHelper.urlEncodeUTF8(tmpIndexName), requestOptions);
    futures.add(moveIndexFuture);

    if (safe) {
      moveIndexFuture.join().waitTask();
    }

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenComposeAsync(
            v -> {
              List<IAlgoliaWaitableResponse> resp =
                  futures.stream().map(CompletableFuture::join).collect(toList());

              return CompletableFuture.completedFuture(new MultiResponse().setResponses(resp));
            },
            config.getExecutor());
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source to move
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<MoveIndexResponse> moveFromAsync(@Nonnull String sourceIndex) {
    return moveFromAsync(sourceIndex, null);
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source to move
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<MoveIndexResponse> moveFromAsync(
      @Nonnull String sourceIndex, RequestOptions requestOptions) {

    Objects.requireNonNull(sourceIndex, "sourceIndex can't be null.");

    if (sourceIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("sourceIndex is required.");
    }

    MoveIndexRequest request =
        new MoveIndexRequest().setOperation(MoveType.MOVE).setDestination(indexName);

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + sourceIndex + "/operation",
            CallType.WRITE,
            request,
            MoveIndexResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Make a copy of an index, including its objects, settings, synonyms, and query rules.
   *
   * @param destinationIndex The destination index
   * @param scope The scope of the copy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  CompletableFuture<CopyToResponse> copyToAsync(
      @Nonnull String destinationIndex, List<String> scope, RequestOptions requestOptions) {

    if (destinationIndex == null || destinationIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("destinationIndex is required.");
    }

    CopyToRequest request =
        new CopyToRequest()
            .setOperation(MoveType.COPY)
            .setDestination(destinationIndex)
            .setScope(scope);

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/operation",
            CallType.WRITE,
            request,
            CopyToResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browse query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public IndexIterable<T> browse(@Nonnull BrowseIndexQuery query) {
    return new IndexIterable<>(this, query);
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browse query
   */
  public BrowseIndexResponse<T> browseFrom(@Nonnull BrowseIndexQuery query) {
    return LaunderThrowable.unwrap(browseFromAsync(query, null));
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browse query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public BrowseIndexResponse<T> browseFrom(
      @Nonnull BrowseIndexQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(browseFromAsync(query, requestOptions));
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browse query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<BrowseIndexResponse<T>> browseFromAsync(
      @Nonnull BrowseIndexQuery query) {
    return browseFromAsync(query, null);
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browse query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<BrowseIndexResponse<T>> browseFromAsync(
      @Nonnull BrowseIndexQuery query, RequestOptions requestOptions) {
    Objects.requireNonNull(query, "A query is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/browse",
            CallType.READ,
            query,
            BrowseIndexResponse.class,
            klass,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<BrowseIndexResponse<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            config.getExecutor());
  }

  /** Delete the index and all its settings, including links to its replicas. */
  public CompletableFuture<DeleteResponse> deleteAsync() {
    return deleteAsync(null);
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteResponse> deleteAsync(RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.DELETE,
        "/1/indexes/" + urlEncodedIndexName,
        CallType.WRITE,
        DeleteResponse.class,
        requestOptions);
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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
        TaskStatusResponse.class,
        requestOptions);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param taskId The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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

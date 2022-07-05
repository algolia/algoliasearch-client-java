package com.algolia.search;

import static java.util.stream.Collectors.toList;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.WaitableResponse;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.TaskStatusResponse;
import com.algolia.search.models.indexing.*;
import com.algolia.search.util.AlgoliaUtils;
import com.algolia.search.util.QueryStringUtils;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * This class holds all endpoints for an Index.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/search/">Algolia.com</a>
 */
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
  private final Class<T> clazz;

  /**
   * Create an instance of {@link SearchIndex}. It doesn't perform an API call.
   *
   * @param transport The transport layer.
   * @param config The related client's configuration.
   * @param indexName The non-encoded index name.
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  SearchIndex(HttpTransport transport, ConfigBase config, String indexName, Class<T> clazz) {
    this.transport = transport;
    this.config = (SearchConfig) config;
    this.indexName = indexName;
    this.urlEncodedIndexName = QueryStringUtils.urlEncodeUTF8(indexName);
    this.clazz = clazz;
  }

  public SearchConfig getConfig() {
    return config;
  }

  @Override
  public Class<T> getClazz() {
    return clazz;
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public T getObject(@Nonnull String objectID) {
    return LaunderThrowable.await(getObjectAsync(objectID));
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public T getObject(
      @Nonnull String objectID,
      @Nonnull List<String> attributesToRetrieve,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(getObjectAsync(objectID, attributesToRetrieve, requestOptions));
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public T getObject(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(getObjectAsync(objectID, requestOptions));
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID,
      @Nonnull List<String> attributesToRetrieve,
      RequestOptions requestOptions) {

    Objects.requireNonNull(attributesToRetrieve, "AttributesToRetrieve are required.");
    Objects.requireNonNull(objectID, "objectID is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(objectID)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters(
        "attributesToRetrieve",
        QueryStringUtils.urlEncodeUTF8(String.join(",", attributesToRetrieve)));

    return getObjectAsync(objectID, requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization)
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {

    Objects.requireNonNull(objectID, "objectID is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(objectID)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/" + QueryStringUtils.urlEncodeUTF8(objectID),
        CallType.READ,
        clazz,
        requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<T> getObjects(@Nonnull List<String> objectIDs) {
    return LaunderThrowable.await(getObjectsAsync(objectIDs));
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @param attributesToRetrieve List of attributes to retrieve. By default, all retrievable
   *     attributes are returned.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<T> getObjects(@Nonnull List<String> objectIDs, List<String> attributesToRetrieve) {
    return LaunderThrowable.await(getObjectsAsync(objectIDs, attributesToRetrieve));
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<T> getObjects(@Nonnull List<String> objectIDs, RequestOptions requestOptions) {
    return LaunderThrowable.await(getObjectsAsync(objectIDs, requestOptions));
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<T> getObjects(
      @Nonnull List<String> objectIDs,
      List<String> attributesToRetrieve,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(getObjectsAsync(objectIDs, attributesToRetrieve, requestOptions));
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
            clazz,
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public UpdateObjectResponse partialUpdateObject(@Nonnull T data) {
    return LaunderThrowable.await(partialUpdateObjectAsync(data));
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
  public UpdateObjectResponse partialUpdateObject(@Nonnull T data, RequestOptions requestOptions) {
    return LaunderThrowable.await(partialUpdateObjectAsync(data, requestOptions));
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
  public UpdateObjectResponse partialUpdateObject(
      @Nonnull T data, @Nonnull Boolean createIfNotExists) {
    return LaunderThrowable.await(partialUpdateObjectAsync(data, createIfNotExists));
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
  public UpdateObjectResponse partialUpdateObject(
      @Nonnull T data, @Nonnull Boolean createIfNotExists, RequestOptions requestOptions) {
    return LaunderThrowable.await(
        partialUpdateObjectAsync(data, createIfNotExists, requestOptions));
  }

  /**
   * Update one or more attributes of an existing object. This method enables you to update only a
   * part of an object by singling out one or more attributes of an existing object and performing
   * the following actions:
   *
   * @param data Data to update
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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

    String objectID = AlgoliaUtils.getObjectID(data, clazz);

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
  public BatchIndexingResponse partialUpdateObjects(@Nonnull Iterable<T> data) {
    return LaunderThrowable.await(partialUpdateObjectsAsync(data));
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
  public BatchIndexingResponse partialUpdateObjects(
      @Nonnull Iterable<T> data, RequestOptions requestOptions) {
    return LaunderThrowable.await(partialUpdateObjectsAsync(data, requestOptions));
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
  public BatchIndexingResponse partialUpdateObjects(
      @Nonnull Iterable<T> data, boolean createIfNotExists) {
    return LaunderThrowable.await(partialUpdateObjectsAsync(data, createIfNotExists));
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
  public BatchIndexingResponse partialUpdateObjects(
      @Nonnull Iterable<T> data, boolean createIfNotExists, RequestOptions requestOptions) {
    return LaunderThrowable.await(
        partialUpdateObjectsAsync(data, createIfNotExists, requestOptions));
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
  public BatchIndexingResponse saveObject(@Nonnull T data) {
    return LaunderThrowable.await(saveObjectAsync(data));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
   */
  public BatchIndexingResponse saveObject(@Nonnull T data, boolean autoGenerateObjectID) {
    return LaunderThrowable.await(saveObjectAsync(data, autoGenerateObjectID));
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
  public BatchIndexingResponse saveObject(@Nonnull T data, RequestOptions requestOptions) {
    return LaunderThrowable.await(saveObjectAsync(data, requestOptions));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
   */
  public BatchIndexingResponse saveObject(
      @Nonnull T data, boolean autoGenerateObjectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(saveObjectAsync(data, autoGenerateObjectID, requestOptions));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
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
  public BatchIndexingResponse saveObjects(@Nonnull Iterable<T> data) {
    return LaunderThrowable.await(saveObjectsAsync(data));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
   */
  public BatchIndexingResponse saveObjects(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID) {
    return LaunderThrowable.await(saveObjectsAsync(data, autoGenerateObjectID));
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
  public BatchIndexingResponse saveObjects(
      @Nonnull Iterable<T> data, RequestOptions requestOptions) {
    return LaunderThrowable.await(saveObjectsAsync(data, requestOptions));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
   */
  public BatchIndexingResponse saveObjects(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(saveObjectsAsync(data, autoGenerateObjectID, requestOptions));
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
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
   *     Jacksonannotation @JsonProperty(\"objectID\"") and autoGenerateObjectID = false
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID, RequestOptions requestOptions) {

    Objects.requireNonNull(data, "Data are required.");

    if (autoGenerateObjectID) {
      return splitIntoBatchesAsync(data, ActionEnum.ADD_OBJECT, requestOptions);
    }

    // If we are not auto generating objectIDS we must make sure clazz has an objectID or Jackson
    // annotation with objectID
    AlgoliaUtils.ensureObjectID(clazz);

    return splitIntoBatchesAsync(data, ActionEnum.UPDATE_OBJECT, requestOptions);
  }

  /**
   * Split records into smaller chunks before sending them to the API asynchronously
   *
   * @param data The data to send and chunk
   * @param actionType The action type of the batch
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request) {
    return LaunderThrowable.await(batchAsync(request, null));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request, RequestOptions requestOptions) {
    return LaunderThrowable.await(batchAsync(request, requestOptions));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request -
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse deleteObject(@Nonnull String objectID) {
    return LaunderThrowable.await(deleteObjectAsync(objectID, null));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse deleteObject(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteObjectAsync(objectID, requestOptions));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectID The Algolia objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeleteResponse> deleteObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(objectID)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + urlEncodedIndexName + "/" + QueryStringUtils.urlEncodeUTF8(objectID),
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public BatchIndexingResponse deleteObjects(@Nonnull List<String> objectIDs) {
    return LaunderThrowable.await(deleteObjectsAsync(objectIDs));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectIDs List of objectIDs to delete
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public BatchIndexingResponse deleteObjects(
      @Nonnull List<String> objectIDs, RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteObjectsAsync(objectIDs, requestOptions));
  }

  /**
   * Remove objects from an index using their object ids.
   *
   * @param objectIDs List of objectIDs to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse clearObjects() {
    return LaunderThrowable.await(clearObjectsAsync(null));
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @param requestOptions Options to pass to this request
   */
  public DeleteResponse clearObjects(RequestOptions requestOptions) {
    return LaunderThrowable.await(clearObjectsAsync(requestOptions));
  }

  /**
   * Clear the records of an index without affecting its settings. This method enables you to delete
   * an index’s contents (records) without removing any settings, rules and synonyms.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public MultiResponse replaceAllObjects(Iterable<T> data) {
    return LaunderThrowable.await(replaceAllObjectsAsync(data));
  }

  /**
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
   * @param safe Run all API calls synchronously
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public MultiResponse replaceAllObjects(Iterable<T> data, boolean safe) {
    return LaunderThrowable.await(replaceAllObjectsAsync(data, safe));
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public MultiResponse replaceAllObjects(
      Iterable<T> data, RequestOptions requestOptions, boolean safe) {
    return LaunderThrowable.await(replaceAllObjectsAsync(data, requestOptions, safe));
  }

  /**
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<MultiResponse> replaceAllObjectsAsync(
      Iterable<T> data, RequestOptions requestOptions, boolean safe) {
    Objects.requireNonNull(data, "Data can't be null");

    Random rnd = new Random();
    String tmpIndexName = indexName + "_tmp_" + rnd.nextInt(100);
    SearchIndex<T> tmpIndex = new SearchIndex<>(transport, config, tmpIndexName, clazz);

    List<String> scopes = Arrays.asList(CopyScope.RULES, CopyScope.SETTINGS, CopyScope.SYNONYMS);

    List<CompletableFuture<? extends WaitableResponse>> futures = new ArrayList<>();

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
        moveFromAsync(QueryStringUtils.urlEncodeUTF8(tmpIndexName), requestOptions);
    futures.add(moveIndexFuture);

    if (safe) {
      moveIndexFuture.join().waitTask();
    }

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenComposeAsync(
            v -> {
              List<WaitableResponse> resp =
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public MoveIndexResponse moveFrom(@Nonnull String sourceIndex) {
    return LaunderThrowable.await(moveFromAsync(sourceIndex));
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source to move
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public MoveIndexResponse moveFrom(@Nonnull String sourceIndex, RequestOptions requestOptions) {
    return LaunderThrowable.await(moveFromAsync(sourceIndex, requestOptions));
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source to move
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<MoveIndexResponse> moveFromAsync(
      @Nonnull String sourceIndex, RequestOptions requestOptions) {

    Objects.requireNonNull(sourceIndex, "sourceIndex can't be null.");

    if (AlgoliaUtils.isEmptyWhiteSpace(sourceIndex)) {
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  CompletableFuture<CopyToResponse> copyToAsync(
      @Nonnull String destinationIndex, List<String> scope, RequestOptions requestOptions) {

    if (AlgoliaUtils.isEmptyWhiteSpace(destinationIndex)) {
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
   * @param query The browseObjects query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public IndexIterable<T> browseObjects(@Nonnull BrowseIndexQuery query) {
    return new IndexIterable<>(this, query);
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browseObjects query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public BrowseIndexResponse<T> browseFrom(@Nonnull BrowseIndexQuery query) {
    return LaunderThrowable.await(browseFromAsync(query, null));
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browseObjects query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public BrowseIndexResponse<T> browseFrom(
      @Nonnull BrowseIndexQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.await(browseFromAsync(query, requestOptions));
  }

  /**
   * This method allows you to retrieve all index content It can retrieve up to 1,000 records per
   * call and supports full text search and filters. You can use the same query parameters as for a
   * search query
   *
   * @param query The browseObjects query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @param query The browseObjects query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
            clazz,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<BrowseIndexResponse<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            config.getExecutor());
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse delete() {
    return LaunderThrowable.await(deleteAsync());
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse delete(RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteAsync(requestOptions));
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeleteResponse> deleteAsync() {
    return deleteAsync(null);
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It does not accept
   * empty filters or a query.
   *
   * @param query The query used to select objects to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse deleteBy(@Nonnull Query query) {
    return LaunderThrowable.await(deleteByAsync(query));
  }

  /**
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It does not accept
   * empty filters or a query.
   *
   * @param query The query used to select objects to delete
   * @param requestOptions requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeleteResponse deleteBy(@Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteByAsync(query, requestOptions));
  }

  /**
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It does not accept
   * empty filters or a query.
   *
   * @param query The query used to select objects to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeleteResponse> deleteByAsync(@Nonnull Query query) {
    return deleteByAsync(query, null);
  }

  /**
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It does not accept
   * empty filters or a query.
   *
   * @param query The query used to select objects to delete
   * @param requestOptions requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeleteResponse> deleteByAsync(
      @Nonnull Query query, RequestOptions requestOptions) {
    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/deleteByQuery",
            CallType.WRITE,
            query,
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
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public TaskStatusResponse getTask(long taskID) {
    return LaunderThrowable.await(getTaskAsync(taskID));
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public TaskStatusResponse getTask(long taskID, RequestOptions requestOptions) {
    return LaunderThrowable.await(getTaskAsync(taskID, requestOptions));
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<TaskStatusResponse> getTaskAsync(long taskID) {
    return getTaskAsync(taskID, null);
  }

  /**
   * Get the status of the given task
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * Return whether an index exists or not
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public boolean exists() {
    return LaunderThrowable.await(existsAsync());
  }

  /**
   * Return whether an index exists or not
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<Boolean> existsAsync() {
    try {
      this.getSettings();
    } catch (AlgoliaApiException ex) {
      if (ex.getHttpErrorCode() == 404) {
        return CompletableFuture.completedFuture(false);
      }
      throw ex;
    }
    return CompletableFuture.completedFuture(true);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param taskId The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public void waitTask(long taskId, long timeToWait, RequestOptions requestOptions) {
    TaskUtils.waitTask(taskId, timeToWait, requestOptions, this::getTaskAsync);
  }
}

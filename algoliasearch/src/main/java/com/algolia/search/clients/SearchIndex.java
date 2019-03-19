package com.algolia.search.clients;

import static java.util.stream.Collectors.toList;

import com.algolia.search.Defaults;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.helpers.AlgoliaHelper;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.models.*;
import com.algolia.search.transport.HttpTransport;
import java.util.*;
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
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   */
  public CompletableFuture<SearchForFacetResponse> searchForFacetValuesAsync(
      @Nonnull SearchForFacetRequest query) {
    return searchForFacetValuesAsync(query, null);
  }

  /**
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SearchForFacetResponse> searchForFacetValuesAsync(
      @Nonnull SearchForFacetRequest query, RequestOptions requestOptions) {
    Objects.requireNonNull(query, "query is required.");

    if (query.getFacetName() == null || query.getFacetName().trim().length() == 0) {
      throw new AlgoliaRuntimeException("facetName is required.");
    }

    if (query.getFacetQuery() == null || query.getFacetQuery().trim().length() == 0) {
      throw new AlgoliaRuntimeException("facetQuery is required.");
    }

    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/1/indexes/" + urlEncodedIndexName + "/facets/" + query.getFacetName() + "/query",
        CallType.READ,
        query,
        SearchForFacetResponse.class,
        requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectID ID of the object within that index
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
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID,
      @Nonnull List<String> attributesToRetrieve,
      RequestOptions requestOptions) {

    Objects.requireNonNull(attributesToRetrieve, "AttributesToRetrieve are required.");

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
   */
  public CompletableFuture<T> getObjectAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {

    Objects.requireNonNull(objectID, "objectID are required.");

    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/" + objectID,
        CallType.READ,
        null,
        klass,
        requestOptions);
  }

  /**
   * Retrieve one or more objects, potentially from the index, in a single API call.
   *
   * @param objectIDs ID of the object within that index
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
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   * @param data Data to update
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
   */
  public CompletableFuture<BatchIndexingResponse> partialUpdateObjectsAsync(
      @Nonnull Iterable<T> data, boolean createIfNotExists, RequestOptions requestOptions) {
    Objects.requireNonNull(data, "Data are required.");

    String action =
        createIfNotExists ? ActionEnum.PartialUpdateObject : ActionEnum.PartialUpdateObjectNoCreate;

    return splitIntoBatchesAsync(data, action, requestOptions);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectAsync(@Nonnull T data) {
    return saveObjectAsync(data, false, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "AddObject", otherwise will
   *     perform an "UpdateObject"
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
   * @param autoGenerateObjectID If set to true, the method will perform "AddObject", otherwise will
   *     perform an "UpdateObject"
   * @param requestOptions Options to pass to this request
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
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(@Nonnull Iterable<T> data) {
    return saveObjectsAsync(data, false, null);
  }

  /**
   * This method allows you to create records on your index by sending one or more objects Each
   * object contains a set of attributes and values, which represents a full record on an index.
   *
   * @param data The data to send
   * @param autoGenerateObjectID If set to true, the method will perform "AddObject", otherwise will
   *     perform an "UpdateObject"
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
   * @param autoGenerateObjectID If set to true, the method will perform "AddObject", otherwise will
   *     perform an "UpdateObject"
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<BatchIndexingResponse> saveObjectsAsync(
      @Nonnull Iterable<T> data, boolean autoGenerateObjectID, RequestOptions requestOptions) {
    Objects.requireNonNull(data, "Data are required.");

    if (autoGenerateObjectID) {
      return splitIntoBatchesAsync(data, ActionEnum.AddObject, requestOptions);
    }

    return splitIntoBatchesAsync(data, ActionEnum.UpdateObject, requestOptions);
  }

  /**
   * Split records into smaller chunks before sending them to the API asynchronously
   *
   * @param data The data to send and chunk
   * @param actionType The action type of the batch
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
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request) {
    return LaunderThrowable.unwrap(batchAsync(request, null));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
   */
  public <E> BatchResponse batch(@Nonnull BatchRequest<E> request, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(batchAsync(request, requestOptions));
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request -
   */
  public <E> CompletableFuture<BatchResponse> batchAsync(@Nonnull BatchRequest<E> request) {
    return batchAsync(request, null);
  }

  /**
   * Perform several indexing operations in one API call.
   *
   * @param request The batch request
   * @param requestOptions Options to pass to this request
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

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

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
   * Remove objects from an index using their object ids.
   *
   * @param objectIDs List of objectIDs to delete
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

    return splitIntoBatchesAsync(request, ActionEnum.DeleteObject, requestOptions);
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
   * Push a new set of objects and remove all previous ones. Settings, synonyms and query rules are
   * untouched. Replace all records in an index without any downtime.
   *
   * @param data The data to send
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
   */
  public CompletableFuture<MultiResponse> replaceAllObjectsAsync(
      Iterable<T> data, RequestOptions requestOptions, boolean safe) {
    Objects.requireNonNull(data, "Data can't be null");

    Random rnd = new Random();
    String tmpIndexName = indexName + "_tmp_" + rnd.nextInt(100);
    SearchIndex<T> tmpIndex = new SearchIndex<>(transport, config, tmpIndexName, klass);

    List<String> scopes = Arrays.asList(CopyScope.Rules, CopyScope.Settings, CopyScope.Synonyms);

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
        moveFromAsync(tmpIndexName, requestOptions);
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
   */
  public CompletableFuture<MoveIndexResponse> moveFromAsync(@Nonnull String sourceIndex) {
    return moveFromAsync(sourceIndex, null);
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source to move
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<MoveIndexResponse> moveFromAsync(
      @Nonnull String sourceIndex, RequestOptions requestOptions) {

    Objects.requireNonNull(sourceIndex, "sourceIndex can't be null.");

    if (sourceIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("sourceIndex is required.");
    }

    MoveIndexRequest request =
        new MoveIndexRequest().setOperation(MoveType.Move).setDestination(indexName);

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
   */
  CompletableFuture<CopyToResponse> copyToAsync(
      @Nonnull String destinationIndex, List<String> scope, RequestOptions requestOptions) {

    if (destinationIndex == null || destinationIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("destinationIndex is required.");
    }

    CopyToRequest request =
        new CopyToRequest()
            .setOperation(MoveType.Copy)
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

    return setSettingsAsync(settings, forwardToReplicas, new RequestOptions());
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

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
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
      @Nonnull IndexSettings settings, RequestOptions requestOptions) {

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

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

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
  public SearchResult<Rule> searchRules(@Nonnull RuleQuery query) {
    return LaunderThrowable.unwrap(searchRulesAsync(query, null));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   */
  public SearchResult<Rule> searchRules(@Nonnull RuleQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchRulesAsync(query, requestOptions));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   */
  public CompletableFuture<SearchResult<Rule>> searchRulesAsync(@Nonnull RuleQuery query) {
    return searchRulesAsync(query, null);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<SearchResult<Rule>> searchRulesAsync(
      @Nonnull RuleQuery query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A search query is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/rules/search",
            CallType.READ,
            query,
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
   * Create or update a single rule.
   *
   * @param rule A query rule
   */
  public CompletableFuture<SaveRuleResponse> saveRuleAsync(@Nonnull Rule rule) {
    return saveRuleAsync(rule, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   */
  public CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule, @Nonnull Boolean forwardToReplicas) {
    return saveRuleAsync(rule, forwardToReplicas, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return saveRuleAsync(rule, requestOptions);
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   */
  public CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule, @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(rule, "A rule is required.");
    Objects.requireNonNull(requestOptions, "RequestOptions are required.");

    if (rule.getObjectID().trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + urlEncodedIndexName + "/rules/" + rule.getObjectID(),
            CallType.WRITE,
            rule,
            SaveRuleResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   */
  public CompletableFuture<SaveRuleResponse> saveRulesAsync(@Nonnull List<Rule> rules) {
    return saveRulesAsync(rules, new RequestOptions());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   */
  public CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules) {

    return saveRulesAsync(rules, forwardToReplicas, clearExistingRules, new RequestOptions());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    Objects.requireNonNull(clearExistingRules, "clearExistingRules is required.");

    requestOptions
        .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString())
        .addExtraQueryParameters("clearExistingRules", clearExistingRules.toString());

    return saveRulesAsync(rules, requestOptions);
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules, RequestOptions requestOptions) {

    Objects.requireNonNull(rules, "Rules are required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/rules/batch",
            CallType.WRITE,
            rules,
            SaveRuleResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   */
  public CompletableFuture<DeleteResponse> deleteRuleAsync(@Nonnull String objectID) {
    return deleteRuleAsync(objectID, null);
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> deleteRuleAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + urlEncodedIndexName + "/rules/" + objectID,
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
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   */
  public CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(@Nonnull List<Rule> rules) {
    return saveRulesAsync(rules, false, true, new RequestOptions());
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   */
  public CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(
      @Nonnull List<Rule> rules, @Nonnull Boolean forwardToReplicas) {
    return saveRulesAsync(rules, forwardToReplicas, true, new RequestOptions());
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(
      @Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions) {
    return saveRulesAsync(rules, false, true, requestOptions);
  }

  /** Delete all rules in an index. */
  public CompletableFuture<DeleteResponse> clearRulesAsync() {
    return clearRulesAsync(false);
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   */
  public CompletableFuture<DeleteResponse> clearRulesAsync(@Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return clearRulesAsync(requestOptions);
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> clearRulesAsync(
      @Nonnull Boolean forwardToReplicas, RequestOptions requestOptions) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return clearRulesAsync(requestOptions);
  }

  /**
   * Delete all rules in an index.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> clearRulesAsync(RequestOptions requestOptions) {

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/rules/clear",
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
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   */
  public SearchResult<Synonym> searchSynonyms(SynonymQuery query) {
    return LaunderThrowable.unwrap(searchSynonymsAsync(query, null));
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   * @param requestOptions Options to pass to this request
   */
  public SearchResult<Synonym> searchSynonyms(SynonymQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchSynonymsAsync(query, requestOptions));
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   */
  public CompletableFuture<SearchResult<Synonym>> searchSynonymsAsync(SynonymQuery query) {
    return searchSynonymsAsync(query, null);
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  public CompletableFuture<SearchResult<Synonym>> searchSynonymsAsync(
      SynonymQuery query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A query is required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/synonyms/search",
            CallType.READ,
            query,
            SearchResult.class,
            Synonym.class,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<Synonym>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            config.getExecutor());
  }

  /**
   * Get a single synonym using its object id.
   *
   * @param objectID Algolia's objectID
   */
  public CompletableFuture<Synonym> getSynonymAsync(@Nonnull String objectID) {
    return getSynonymAsync(objectID, null);
  }

  /**
   * Get a single synonym using its object id.
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<Synonym> getSynonymAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The synonym ID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/indexes/" + urlEncodedIndexName + "/synonyms/" + objectID,
        CallType.READ,
        null,
        Synonym.class,
        requestOptions);
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(@Nonnull Synonym synonym) {
    return saveSynonymAsync(synonym, false, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   * @param forwardToReplicas Forward the request to the replicas
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym, @Nonnull Boolean forwardToReplicas) {
    return saveSynonymAsync(synonym, forwardToReplicas, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   * @param forwardToReplicas Forward the request to the replicas
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return saveSynonymAsync(synonym, requestOptions);
  }

  /**
   * Create or update a single synonym on an index.
   *
   * @param synonym Algolia's synonym
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym, @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(synonym, "A synonym is required.");
    Objects.requireNonNull(requestOptions, "RequestOptions are required.");

    if (synonym.getObjectID().trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + urlEncodedIndexName + "/synonyms/" + synonym.getObjectID(),
            CallType.WRITE,
            synonym,
            SaveSynonymResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(@Nonnull List<Synonym> synonyms) {
    return saveSynonymsAsync(synonyms, false, false, new RequestOptions());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   * @param replaceExistingSynonyms Replace all existing synonyms
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean replaceExistingSynonyms) {
    return saveSynonymsAsync(
        synonyms, forwardToReplicas, replaceExistingSynonyms, new RequestOptions());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   * @param replaceExistingSynonyms Replace all existing synonyms
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean replaceExistingSynonyms,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    Objects.requireNonNull(replaceExistingSynonyms, "replaceExistingSynonyms is required.");

    requestOptions
        .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString())
        .addExtraQueryParameters("replaceExistingSynonyms", replaceExistingSynonyms.toString());

    return saveSynonymsAsync(synonyms, requestOptions);
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms, RequestOptions requestOptions) {

    Objects.requireNonNull(synonyms, "synonyms are required.");

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/synonyms/batch",
            CallType.WRITE,
            synonyms,
            SaveSynonymResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   */
  public CompletableFuture<DeleteResponse> deleteSynonymAsync(@Nonnull String objectID) {
    return deleteSynonymAsync(objectID, false);
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   * @param forwardToReplicas Forward the request to the replicas
   */
  public CompletableFuture<DeleteResponse> deleteSynonymAsync(
      @Nonnull String objectID, @Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return deleteSynonymAsync(objectID, requestOptions);
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> deleteSynonymAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return transport
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + urlEncodedIndexName + "/synonyms/" + objectID,
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

  /** Remove all synonyms from an index. */
  public CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync() {
    return clearSynonymsAsync(new RequestOptions());
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param forwardToReplicas Forward the request to the replicas
   */
  public CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync(
      @Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return clearSynonymsAsync(requestOptions);
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync(
      RequestOptions requestOptions) {

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + urlEncodedIndexName + "/synonyms/clear",
            CallType.WRITE,
            null,
            ClearSynonymsResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            config.getExecutor());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   */
  public CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms) {
    return saveSynonymsAsync(synonyms, false, true, new RequestOptions());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   */
  public CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms, @Nonnull Boolean forwardToReplicas) {
    return saveSynonymsAsync(synonyms, forwardToReplicas, true, new RequestOptions());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms, @Nonnull RequestOptions requestOptions) {
    return saveSynonymsAsync(synonyms, false, true, requestOptions);
  }

  /** Delete the index and all its settings, including links to its replicas. */
  public CompletableFuture<DeleteResponse> deleteAsync() {
    return deleteAsync(null);
  }

  /**
   * Delete the index and all its settings, including links to its replicas.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteResponse> deleteAsync(RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.DELETE,
        "/1/indexes/" + urlEncodedIndexName,
        CallType.WRITE,
        null,
        DeleteResponse.class,
        requestOptions);
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

  public AlgoliaConfig getConfig() {
    return config;
  }
}

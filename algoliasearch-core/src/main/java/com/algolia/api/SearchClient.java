// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.api;

import com.algolia.ApiClient;
import com.algolia.exceptions.*;
import com.algolia.model.search.*;
import com.algolia.utils.*;
import com.algolia.utils.retry.CallType;
import com.algolia.utils.retry.StatefulHost;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import okhttp3.Call;

public class SearchClient extends ApiClient {

  public SearchClient(String appId, String apiKey) {
    this(appId, apiKey, null);
  }

  public SearchClient(String appId, String apiKey, ClientOptions options) {
    super(appId, apiKey, "Search", "4.0.0-SNAPSHOT", options);
    if (options != null && options.getHosts() != null) {
      this.setHosts(options.getHosts());
    } else {
      this.setHosts(getDefaultHosts(appId));
    }
    this.setConnectTimeout(2000);
    this.setReadTimeout(5000);
    this.setWriteTimeout(30000);
  }

  private static List<StatefulHost> getDefaultHosts(String appId) {
    List<StatefulHost> hosts = new ArrayList<StatefulHost>();
    hosts.add(new StatefulHost(appId + "-dsn.algolia.net", "https", EnumSet.of(CallType.READ)));
    hosts.add(new StatefulHost(appId + ".algolia.net", "https", EnumSet.of(CallType.WRITE)));

    List<StatefulHost> commonHosts = new ArrayList<StatefulHost>();
    hosts.add(new StatefulHost(appId + "-1.algolianet.net", "https", EnumSet.of(CallType.READ, CallType.WRITE)));
    hosts.add(new StatefulHost(appId + "-2.algolianet.net", "https", EnumSet.of(CallType.READ, CallType.WRITE)));
    hosts.add(new StatefulHost(appId + "-3.algolianet.net", "https", EnumSet.of(CallType.READ, CallType.WRITE)));

    Collections.shuffle(commonHosts, new Random());

    return Stream.concat(hosts.stream(), commonHosts.stream()).collect(Collectors.toList());
  }

  /**
   * Add a new API Key with specific permissions/restrictions.
   *
   * @param apiKey (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return AddApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public AddApiKeyResponse addApiKey(ApiKey apiKey, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(addApiKeyAsync(apiKey, requestOptions));
  }

  /**
   * Add a new API Key with specific permissions/restrictions.
   *
   * @param apiKey (required)
   * @return AddApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public AddApiKeyResponse addApiKey(ApiKey apiKey) throws AlgoliaRuntimeException {
    return this.addApiKey(apiKey, null);
  }

  /**
   * (asynchronously) Add a new API Key with specific permissions/restrictions.
   *
   * @param apiKey (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<AddApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<AddApiKeyResponse> addApiKeyAsync(ApiKey apiKey, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    if (apiKey == null) {
      throw new AlgoliaRuntimeException("Parameter `apiKey` is required when calling `addApiKey`.");
    }

    Object bodyObj = apiKey;

    // create path and map variables
    String requestPath = "/1/keys";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<AddApiKeyResponse>() {});
  }

  /**
   * (asynchronously) Add a new API Key with specific permissions/restrictions.
   *
   * @param apiKey (required)
   * @return CompletableFuture<AddApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<AddApiKeyResponse> addApiKeyAsync(ApiKey apiKey) throws AlgoliaRuntimeException {
    return this.addApiKeyAsync(apiKey, null);
  }

  /**
   * Add or replace an object with a given object ID. If the object does not exist, it will be
   * created. If it already exists, it will be replaced.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param body The Algolia object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse addOrUpdateObject(String indexName, String objectID, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(addOrUpdateObjectAsync(indexName, objectID, body, requestOptions));
  }

  /**
   * Add or replace an object with a given object ID. If the object does not exist, it will be
   * created. If it already exists, it will be replaced.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param body The Algolia object. (required)
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse addOrUpdateObject(String indexName, String objectID, Object body) throws AlgoliaRuntimeException {
    return this.addOrUpdateObject(indexName, objectID, body, null);
  }

  /**
   * (asynchronously) Add or replace an object with a given object ID. If the object does not exist,
   * it will be created. If it already exists, it will be replaced.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param body The Algolia object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> addOrUpdateObjectAsync(
    String indexName,
    String objectID,
    Object body,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `addOrUpdateObject`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `addOrUpdateObject`.");
    }

    if (body == null) {
      throw new AlgoliaRuntimeException("Parameter `body` is required when calling `addOrUpdateObject`.");
    }

    Object bodyObj = body;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtWithObjectIdResponse>() {});
  }

  /**
   * (asynchronously) Add or replace an object with a given object ID. If the object does not exist,
   * it will be created. If it already exists, it will be replaced.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param body The Algolia object. (required)
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> addOrUpdateObjectAsync(String indexName, String objectID, Object body)
    throws AlgoliaRuntimeException {
    return this.addOrUpdateObjectAsync(indexName, objectID, body, null);
  }

  /**
   * Add a single source to the list of allowed sources.
   *
   * @param source The source to add. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse appendSource(Source source, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(appendSourceAsync(source, requestOptions));
  }

  /**
   * Add a single source to the list of allowed sources.
   *
   * @param source The source to add. (required)
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse appendSource(Source source) throws AlgoliaRuntimeException {
    return this.appendSource(source, null);
  }

  /**
   * (asynchronously) Add a single source to the list of allowed sources.
   *
   * @param source The source to add. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> appendSourceAsync(Source source, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (source == null) {
      throw new AlgoliaRuntimeException("Parameter `source` is required when calling `appendSource`.");
    }

    Object bodyObj = source;

    // create path and map variables
    String requestPath = "/1/security/sources/append";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<CreatedAtResponse>() {});
  }

  /**
   * (asynchronously) Add a single source to the list of allowed sources.
   *
   * @param source The source to add. (required)
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> appendSourceAsync(Source source) throws AlgoliaRuntimeException {
    return this.appendSourceAsync(source, null);
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userID
   * is directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param assignUserIdParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse assignUserId(String xAlgoliaUserID, AssignUserIdParams assignUserIdParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(assignUserIdAsync(xAlgoliaUserID, assignUserIdParams, requestOptions));
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userID
   * is directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param assignUserIdParams (required)
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse assignUserId(String xAlgoliaUserID, AssignUserIdParams assignUserIdParams) throws AlgoliaRuntimeException {
    return this.assignUserId(xAlgoliaUserID, assignUserIdParams, null);
  }

  /**
   * (asynchronously) Assign or Move a userID to a cluster. The time it takes to migrate (move) a
   * user is proportional to the amount of data linked to the userID. Upon success, the response is
   * 200 OK. A successful response indicates that the operation has been taken into account, and the
   * userID is directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param assignUserIdParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> assignUserIdAsync(
    String xAlgoliaUserID,
    AssignUserIdParams assignUserIdParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (xAlgoliaUserID == null) {
      throw new AlgoliaRuntimeException("Parameter `xAlgoliaUserID` is required when calling `assignUserId`.");
    }

    if (assignUserIdParams == null) {
      throw new AlgoliaRuntimeException("Parameter `assignUserIdParams` is required when calling `assignUserId`.");
    }

    Object bodyObj = assignUserIdParams;

    // create path and map variables
    String requestPath = "/1/clusters/mapping";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (xAlgoliaUserID != null) {
      headers.put("X-Algolia-User-ID", this.parameterToString(xAlgoliaUserID));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<CreatedAtResponse>() {});
  }

  /**
   * (asynchronously) Assign or Move a userID to a cluster. The time it takes to migrate (move) a
   * user is proportional to the amount of data linked to the userID. Upon success, the response is
   * 200 OK. A successful response indicates that the operation has been taken into account, and the
   * userID is directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param assignUserIdParams (required)
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> assignUserIdAsync(String xAlgoliaUserID, AssignUserIdParams assignUserIdParams)
    throws AlgoliaRuntimeException {
    return this.assignUserIdAsync(xAlgoliaUserID, assignUserIdParams, null);
  }

  /**
   * Perform multiple write operations targeting one index, in a single API call.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param batchWriteParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return BatchResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public BatchResponse batch(String indexName, BatchWriteParams batchWriteParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(batchAsync(indexName, batchWriteParams, requestOptions));
  }

  /**
   * Perform multiple write operations targeting one index, in a single API call.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param batchWriteParams (required)
   * @return BatchResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public BatchResponse batch(String indexName, BatchWriteParams batchWriteParams) throws AlgoliaRuntimeException {
    return this.batch(indexName, batchWriteParams, null);
  }

  /**
   * (asynchronously) Perform multiple write operations targeting one index, in a single API call.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param batchWriteParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<BatchResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<BatchResponse> batchAsync(String indexName, BatchWriteParams batchWriteParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `batch`.");
    }

    if (batchWriteParams == null) {
      throw new AlgoliaRuntimeException("Parameter `batchWriteParams` is required when calling `batch`.");
    }

    Object bodyObj = batchWriteParams;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/batch".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<BatchResponse>() {});
  }

  /**
   * (asynchronously) Perform multiple write operations targeting one index, in a single API call.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param batchWriteParams (required)
   * @return CompletableFuture<BatchResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<BatchResponse> batchAsync(String indexName, BatchWriteParams batchWriteParams) throws AlgoliaRuntimeException {
    return this.batchAsync(indexName, batchWriteParams, null);
  }

  /**
   * Assign multiple userIDs to a cluster. Upon success, the response is 200 OK. A successful
   * response indicates that the operation has been taken into account, and the userIDs are directly
   * usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param batchAssignUserIdsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse batchAssignUserIds(
    String xAlgoliaUserID,
    BatchAssignUserIdsParams batchAssignUserIdsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(batchAssignUserIdsAsync(xAlgoliaUserID, batchAssignUserIdsParams, requestOptions));
  }

  /**
   * Assign multiple userIDs to a cluster. Upon success, the response is 200 OK. A successful
   * response indicates that the operation has been taken into account, and the userIDs are directly
   * usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param batchAssignUserIdsParams (required)
   * @return CreatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CreatedAtResponse batchAssignUserIds(String xAlgoliaUserID, BatchAssignUserIdsParams batchAssignUserIdsParams)
    throws AlgoliaRuntimeException {
    return this.batchAssignUserIds(xAlgoliaUserID, batchAssignUserIdsParams, null);
  }

  /**
   * (asynchronously) Assign multiple userIDs to a cluster. Upon success, the response is 200 OK. A
   * successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param batchAssignUserIdsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> batchAssignUserIdsAsync(
    String xAlgoliaUserID,
    BatchAssignUserIdsParams batchAssignUserIdsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (xAlgoliaUserID == null) {
      throw new AlgoliaRuntimeException("Parameter `xAlgoliaUserID` is required when calling `batchAssignUserIds`.");
    }

    if (batchAssignUserIdsParams == null) {
      throw new AlgoliaRuntimeException("Parameter `batchAssignUserIdsParams` is required when calling `batchAssignUserIds`.");
    }

    Object bodyObj = batchAssignUserIdsParams;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/batch";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (xAlgoliaUserID != null) {
      headers.put("X-Algolia-User-ID", this.parameterToString(xAlgoliaUserID));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<CreatedAtResponse>() {});
  }

  /**
   * (asynchronously) Assign multiple userIDs to a cluster. Upon success, the response is 200 OK. A
   * successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @param xAlgoliaUserID userID to assign. (required)
   * @param batchAssignUserIdsParams (required)
   * @return CompletableFuture<CreatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<CreatedAtResponse> batchAssignUserIdsAsync(
    String xAlgoliaUserID,
    BatchAssignUserIdsParams batchAssignUserIdsParams
  ) throws AlgoliaRuntimeException {
    return this.batchAssignUserIdsAsync(xAlgoliaUserID, batchAssignUserIdsParams, null);
  }

  /**
   * Send a batch of dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param batchDictionaryEntriesParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse batchDictionaryEntries(
    DictionaryType dictionaryName,
    BatchDictionaryEntriesParams batchDictionaryEntriesParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(batchDictionaryEntriesAsync(dictionaryName, batchDictionaryEntriesParams, requestOptions));
  }

  /**
   * Send a batch of dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param batchDictionaryEntriesParams (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse batchDictionaryEntries(DictionaryType dictionaryName, BatchDictionaryEntriesParams batchDictionaryEntriesParams)
    throws AlgoliaRuntimeException {
    return this.batchDictionaryEntries(dictionaryName, batchDictionaryEntriesParams, null);
  }

  /**
   * (asynchronously) Send a batch of dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param batchDictionaryEntriesParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> batchDictionaryEntriesAsync(
    DictionaryType dictionaryName,
    BatchDictionaryEntriesParams batchDictionaryEntriesParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (dictionaryName == null) {
      throw new AlgoliaRuntimeException("Parameter `dictionaryName` is required when calling `batchDictionaryEntries`.");
    }

    if (batchDictionaryEntriesParams == null) {
      throw new AlgoliaRuntimeException("Parameter `batchDictionaryEntriesParams` is required when calling" + " `batchDictionaryEntries`.");
    }

    Object bodyObj = batchDictionaryEntriesParams;

    // create path and map variables
    String requestPath =
      "/1/dictionaries/{dictionaryName}/batch".replaceAll("\\{dictionaryName\\}", this.escapeString(dictionaryName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Send a batch of dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param batchDictionaryEntriesParams (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> batchDictionaryEntriesAsync(
    DictionaryType dictionaryName,
    BatchDictionaryEntriesParams batchDictionaryEntriesParams
  ) throws AlgoliaRuntimeException {
    return this.batchDictionaryEntriesAsync(dictionaryName, batchDictionaryEntriesParams, null);
  }

  /**
   * This method allows you to retrieve all index content. It can retrieve up to 1,000 records per
   * call and supports full text search and filters. For performance reasons, some features are not
   * supported, including `distinct`, sorting by `typos`, `words` or `geo distance`. When there is
   * more content to be browsed, the response contains a cursor field. This cursor has to be passed
   * to the subsequent call to browse in order to get the next page of results. When the end of the
   * index has been reached, the cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param browseParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> BrowseResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> BrowseResponse<T> browse(String indexName, BrowseParams browseParams, Class<T> innerType, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(browseAsync(indexName, browseParams, innerType, requestOptions));
  }

  /**
   * This method allows you to retrieve all index content. It can retrieve up to 1,000 records per
   * call and supports full text search and filters. For performance reasons, some features are not
   * supported, including `distinct`, sorting by `typos`, `words` or `geo distance`. When there is
   * more content to be browsed, the response contains a cursor field. This cursor has to be passed
   * to the subsequent call to browse in order to get the next page of results. When the end of the
   * index has been reached, the cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param browseParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> BrowseResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> BrowseResponse<T> browse(String indexName, BrowseParams browseParams, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.browse(indexName, browseParams, innerType, null);
  }

  /**
   * This method allows you to retrieve all index content. It can retrieve up to 1,000 records per
   * call and supports full text search and filters. For performance reasons, some features are not
   * supported, including `distinct`, sorting by `typos`, `words` or `geo distance`. When there is
   * more content to be browsed, the response contains a cursor field. This cursor has to be passed
   * to the subsequent call to browse in order to get the next page of results. When the end of the
   * index has been reached, the cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> BrowseResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> BrowseResponse<T> browse(String indexName, Class<T> innerType, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.browse(indexName, null, innerType, requestOptions);
  }

  /**
   * This method allows you to retrieve all index content. It can retrieve up to 1,000 records per
   * call and supports full text search and filters. For performance reasons, some features are not
   * supported, including `distinct`, sorting by `typos`, `words` or `geo distance`. When there is
   * more content to be browsed, the response contains a cursor field. This cursor has to be passed
   * to the subsequent call to browse in order to get the next page of results. When the end of the
   * index has been reached, the cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> BrowseResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> BrowseResponse<T> browse(String indexName, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.browse(indexName, null, innerType, null);
  }

  /**
   * (asynchronously) This method allows you to retrieve all index content. It can retrieve up to
   * 1,000 records per call and supports full text search and filters. For performance reasons, some
   * features are not supported, including &#x60;distinct&#x60;, sorting by &#x60;typos&#x60;,
   * &#x60;words&#x60; or &#x60;geo distance&#x60;. When there is more content to be browsed, the
   * response contains a cursor field. This cursor has to be passed to the subsequent call to browse
   * in order to get the next page of results. When the end of the index has been reached, the
   * cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param browseParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<BrowseResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<BrowseResponse<T>> browseAsync(
    String indexName,
    BrowseParams browseParams,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `browse`.");
    }

    Object bodyObj = browseParams != null ? browseParams : new Object();

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/browse".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, BrowseResponse.class, innerType);
  }

  /**
   * (asynchronously) This method allows you to retrieve all index content. It can retrieve up to
   * 1,000 records per call and supports full text search and filters. For performance reasons, some
   * features are not supported, including &#x60;distinct&#x60;, sorting by &#x60;typos&#x60;,
   * &#x60;words&#x60; or &#x60;geo distance&#x60;. When there is more content to be browsed, the
   * response contains a cursor field. This cursor has to be passed to the subsequent call to browse
   * in order to get the next page of results. When the end of the index has been reached, the
   * cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param browseParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<BrowseResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<BrowseResponse<T>> browseAsync(String indexName, BrowseParams browseParams, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.browseAsync(indexName, browseParams, innerType, null);
  }

  /**
   * (asynchronously) This method allows you to retrieve all index content. It can retrieve up to
   * 1,000 records per call and supports full text search and filters. For performance reasons, some
   * features are not supported, including &#x60;distinct&#x60;, sorting by &#x60;typos&#x60;,
   * &#x60;words&#x60; or &#x60;geo distance&#x60;. When there is more content to be browsed, the
   * response contains a cursor field. This cursor has to be passed to the subsequent call to browse
   * in order to get the next page of results. When the end of the index has been reached, the
   * cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<BrowseResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<BrowseResponse<T>> browseAsync(String indexName, Class<T> innerType, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.browseAsync(indexName, null, innerType, requestOptions);
  }

  /**
   * (asynchronously) This method allows you to retrieve all index content. It can retrieve up to
   * 1,000 records per call and supports full text search and filters. For performance reasons, some
   * features are not supported, including &#x60;distinct&#x60;, sorting by &#x60;typos&#x60;,
   * &#x60;words&#x60; or &#x60;geo distance&#x60;. When there is more content to be browsed, the
   * response contains a cursor field. This cursor has to be passed to the subsequent call to browse
   * in order to get the next page of results. When the end of the index has been reached, the
   * cursor field is absent from the response.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<BrowseResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<BrowseResponse<T>> browseAsync(String indexName, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.browseAsync(indexName, null, innerType, null);
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearAllSynonyms(String indexName, Boolean forwardToReplicas, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(clearAllSynonymsAsync(indexName, forwardToReplicas, requestOptions));
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearAllSynonyms(String indexName, Boolean forwardToReplicas) throws AlgoliaRuntimeException {
    return this.clearAllSynonyms(indexName, forwardToReplicas, null);
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearAllSynonyms(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.clearAllSynonyms(indexName, null, requestOptions);
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearAllSynonyms(String indexName) throws AlgoliaRuntimeException {
    return this.clearAllSynonyms(indexName, null, null);
  }

  /**
   * (asynchronously) Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearAllSynonymsAsync(
    String indexName,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `clearAllSynonyms`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/synonyms/clear".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearAllSynonymsAsync(String indexName, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.clearAllSynonymsAsync(indexName, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearAllSynonymsAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.clearAllSynonymsAsync(indexName, null, requestOptions);
  }

  /**
   * (asynchronously) Remove all synonyms from an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearAllSynonymsAsync(String indexName) throws AlgoliaRuntimeException {
    return this.clearAllSynonymsAsync(indexName, null, null);
  }

  /**
   * Delete an index's content, but leave settings and index-specific API keys untouched.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearObjects(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(clearObjectsAsync(indexName, requestOptions));
  }

  /**
   * Delete an index's content, but leave settings and index-specific API keys untouched.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearObjects(String indexName) throws AlgoliaRuntimeException {
    return this.clearObjects(indexName, null);
  }

  /**
   * (asynchronously) Delete an index&#39;s content, but leave settings and index-specific API keys
   * untouched.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearObjectsAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `clearObjects`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/clear".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete an index&#39;s content, but leave settings and index-specific API keys
   * untouched.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearObjectsAsync(String indexName) throws AlgoliaRuntimeException {
    return this.clearObjectsAsync(indexName, null);
  }

  /**
   * Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearRules(String indexName, Boolean forwardToReplicas, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(clearRulesAsync(indexName, forwardToReplicas, requestOptions));
  }

  /**
   * Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearRules(String indexName, Boolean forwardToReplicas) throws AlgoliaRuntimeException {
    return this.clearRules(indexName, forwardToReplicas, null);
  }

  /**
   * Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearRules(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.clearRules(indexName, null, requestOptions);
  }

  /**
   * Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse clearRules(String indexName) throws AlgoliaRuntimeException {
    return this.clearRules(indexName, null, null);
  }

  /**
   * (asynchronously) Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearRulesAsync(String indexName, Boolean forwardToReplicas, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `clearRules`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/rules/clear".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearRulesAsync(String indexName, Boolean forwardToReplicas) throws AlgoliaRuntimeException {
    return this.clearRulesAsync(indexName, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearRulesAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.clearRulesAsync(indexName, null, requestOptions);
  }

  /**
   * (asynchronously) Delete all Rules in the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> clearRulesAsync(String indexName) throws AlgoliaRuntimeException {
    return this.clearRulesAsync(indexName, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object del(String path, Map<String, Object> parameters, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(delAsync(path, parameters, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object del(String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.del(path, parameters, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object del(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.del(path, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object del(String path) throws AlgoliaRuntimeException {
    return this.del(path, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> delAsync(String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (path == null) {
      throw new AlgoliaRuntimeException("Parameter `path` is required when calling `del`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1{path}".replaceAll("\\{path\\}", path.toString());

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (parameters != null) {
      for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
        queryParameters.put(parameter.getKey().toString(), parameterToString(parameter.getValue()));
      }
    }

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> delAsync(String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.delAsync(path, parameters, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> delAsync(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.delAsync(path, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> delAsync(String path) throws AlgoliaRuntimeException {
    return this.delAsync(path, null, null);
  }

  /**
   * Delete an existing API Key.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeleteApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteApiKeyResponse deleteApiKey(String key, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteApiKeyAsync(key, requestOptions));
  }

  /**
   * Delete an existing API Key.
   *
   * @param key API Key string. (required)
   * @return DeleteApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteApiKeyResponse deleteApiKey(String key) throws AlgoliaRuntimeException {
    return this.deleteApiKey(key, null);
  }

  /**
   * (asynchronously) Delete an existing API Key.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeleteApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteApiKeyResponse> deleteApiKeyAsync(String key, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (key == null) {
      throw new AlgoliaRuntimeException("Parameter `key` is required when calling `deleteApiKey`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/keys/{key}".replaceAll("\\{key\\}", this.escapeString(key.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeleteApiKeyResponse>() {});
  }

  /**
   * (asynchronously) Delete an existing API Key.
   *
   * @param key API Key string. (required)
   * @return CompletableFuture<DeleteApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteApiKeyResponse> deleteApiKeyAsync(String key) throws AlgoliaRuntimeException {
    return this.deleteApiKeyAsync(key, null);
  }

  /**
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It doesn't accept
   * empty filters or a query.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param deleteByParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteBy(String indexName, DeleteByParams deleteByParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteByAsync(indexName, deleteByParams, requestOptions));
  }

  /**
   * Remove all objects matching a filter (including geo filters). This method enables you to delete
   * one or more objects based on filters (numeric, facet, tag or geo queries). It doesn't accept
   * empty filters or a query.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param deleteByParams (required)
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteBy(String indexName, DeleteByParams deleteByParams) throws AlgoliaRuntimeException {
    return this.deleteBy(indexName, deleteByParams, null);
  }

  /**
   * (asynchronously) Remove all objects matching a filter (including geo filters). This method
   * enables you to delete one or more objects based on filters (numeric, facet, tag or geo
   * queries). It doesn&#39;t accept empty filters or a query.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param deleteByParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteByAsync(String indexName, DeleteByParams deleteByParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `deleteBy`.");
    }

    if (deleteByParams == null) {
      throw new AlgoliaRuntimeException("Parameter `deleteByParams` is required when calling `deleteBy`.");
    }

    Object bodyObj = deleteByParams;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/deleteByQuery".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeletedAtResponse>() {});
  }

  /**
   * (asynchronously) Remove all objects matching a filter (including geo filters). This method
   * enables you to delete one or more objects based on filters (numeric, facet, tag or geo
   * queries). It doesn&#39;t accept empty filters or a query.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param deleteByParams (required)
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteByAsync(String indexName, DeleteByParams deleteByParams)
    throws AlgoliaRuntimeException {
    return this.deleteByAsync(indexName, deleteByParams, null);
  }

  /**
   * Delete an existing index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteIndex(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteIndexAsync(indexName, requestOptions));
  }

  /**
   * Delete an existing index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteIndex(String indexName) throws AlgoliaRuntimeException {
    return this.deleteIndex(indexName, null);
  }

  /**
   * (asynchronously) Delete an existing index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteIndexAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `deleteIndex`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeletedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete an existing index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteIndexAsync(String indexName) throws AlgoliaRuntimeException {
    return this.deleteIndexAsync(indexName, null);
  }

  /**
   * Delete an existing object.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteObject(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteObjectAsync(indexName, objectID, requestOptions));
  }

  /**
   * Delete an existing object.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteObject(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteObject(indexName, objectID, null);
  }

  /**
   * (asynchronously) Delete an existing object.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteObjectAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `deleteObject`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `deleteObject`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeletedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete an existing object.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteObjectAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteObjectAsync(indexName, objectID, null);
  }

  /**
   * Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse deleteRule(String indexName, String objectID, Boolean forwardToReplicas, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteRuleAsync(indexName, objectID, forwardToReplicas, requestOptions));
  }

  /**
   * Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse deleteRule(String indexName, String objectID, Boolean forwardToReplicas) throws AlgoliaRuntimeException {
    return this.deleteRule(indexName, objectID, forwardToReplicas, null);
  }

  /**
   * Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse deleteRule(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.deleteRule(indexName, objectID, null, requestOptions);
  }

  /**
   * Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse deleteRule(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteRule(indexName, objectID, null, null);
  }

  /**
   * (asynchronously) Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> deleteRuleAsync(
    String indexName,
    String objectID,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `deleteRule`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `deleteRule`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/rules/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> deleteRuleAsync(String indexName, String objectID, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.deleteRuleAsync(indexName, objectID, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> deleteRuleAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.deleteRuleAsync(indexName, objectID, null, requestOptions);
  }

  /**
   * (asynchronously) Delete the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> deleteRuleAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteRuleAsync(indexName, objectID, null, null);
  }

  /**
   * Remove a single source from the list of allowed sources.
   *
   * @param source The IP range of the source. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeleteSourceResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteSourceResponse deleteSource(String source, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteSourceAsync(source, requestOptions));
  }

  /**
   * Remove a single source from the list of allowed sources.
   *
   * @param source The IP range of the source. (required)
   * @return DeleteSourceResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteSourceResponse deleteSource(String source) throws AlgoliaRuntimeException {
    return this.deleteSource(source, null);
  }

  /**
   * (asynchronously) Remove a single source from the list of allowed sources.
   *
   * @param source The IP range of the source. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeleteSourceResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteSourceResponse> deleteSourceAsync(String source, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (source == null) {
      throw new AlgoliaRuntimeException("Parameter `source` is required when calling `deleteSource`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/security/sources/{source}".replaceAll("\\{source\\}", this.escapeString(source.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeleteSourceResponse>() {});
  }

  /**
   * (asynchronously) Remove a single source from the list of allowed sources.
   *
   * @param source The IP range of the source. (required)
   * @return CompletableFuture<DeleteSourceResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteSourceResponse> deleteSourceAsync(String source) throws AlgoliaRuntimeException {
    return this.deleteSourceAsync(source, null);
  }

  /**
   * Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteSynonym(String indexName, String objectID, Boolean forwardToReplicas, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteSynonymAsync(indexName, objectID, forwardToReplicas, requestOptions));
  }

  /**
   * Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteSynonym(String indexName, String objectID, Boolean forwardToReplicas) throws AlgoliaRuntimeException {
    return this.deleteSynonym(indexName, objectID, forwardToReplicas, null);
  }

  /**
   * Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteSynonym(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.deleteSynonym(indexName, objectID, null, requestOptions);
  }

  /**
   * Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return DeletedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeletedAtResponse deleteSynonym(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteSynonym(indexName, objectID, null, null);
  }

  /**
   * (asynchronously) Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteSynonymAsync(
    String indexName,
    String objectID,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `deleteSynonym`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `deleteSynonym`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/synonyms/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<DeletedAtResponse>() {});
  }

  /**
   * (asynchronously) Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteSynonymAsync(String indexName, String objectID, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.deleteSynonymAsync(indexName, objectID, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteSynonymAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.deleteSynonymAsync(indexName, objectID, null, requestOptions);
  }

  /**
   * (asynchronously) Delete a single synonyms set, identified by the given objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<DeletedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeletedAtResponse> deleteSynonymAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.deleteSynonymAsync(indexName, objectID, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object get(String path, Map<String, Object> parameters, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getAsync(path, parameters, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object get(String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.get(path, parameters, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object get(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.get(path, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object get(String path) throws AlgoliaRuntimeException {
    return this.get(path, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> getAsync(String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (path == null) {
      throw new AlgoliaRuntimeException("Parameter `path` is required when calling `get`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1{path}".replaceAll("\\{path\\}", path.toString());

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (parameters != null) {
      for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
        queryParameters.put(parameter.getKey().toString(), parameterToString(parameter.getValue()));
      }
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> getAsync(String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.getAsync(path, parameters, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> getAsync(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.getAsync(path, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> getAsync(String path) throws AlgoliaRuntimeException {
    return this.getAsync(path, null, null);
  }

  /**
   * Get the permissions of an API key.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetApiKeyResponse getApiKey(String key, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getApiKeyAsync(key, requestOptions));
  }

  /**
   * Get the permissions of an API key.
   *
   * @param key API Key string. (required)
   * @return GetApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetApiKeyResponse getApiKey(String key) throws AlgoliaRuntimeException {
    return this.getApiKey(key, null);
  }

  /**
   * (asynchronously) Get the permissions of an API key.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetApiKeyResponse> getApiKeyAsync(String key, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    if (key == null) {
      throw new AlgoliaRuntimeException("Parameter `key` is required when calling `getApiKey`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/keys/{key}".replaceAll("\\{key\\}", this.escapeString(key.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<GetApiKeyResponse>() {});
  }

  /**
   * (asynchronously) Get the permissions of an API key.
   *
   * @param key API Key string. (required)
   * @return CompletableFuture<GetApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetApiKeyResponse> getApiKeyAsync(String key) throws AlgoliaRuntimeException {
    return this.getApiKeyAsync(key, null);
  }

  /**
   * List dictionaries supported per language.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Map<String, Languages>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, Languages> getDictionaryLanguages(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getDictionaryLanguagesAsync(requestOptions));
  }

  /**
   * List dictionaries supported per language.
   *
   * @return Map<String, Languages>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, Languages> getDictionaryLanguages() throws AlgoliaRuntimeException {
    return this.getDictionaryLanguages(null);
  }

  /**
   * (asynchronously) List dictionaries supported per language.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Map<String, Languages>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, Languages>> getDictionaryLanguagesAsync(RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/dictionaries/*/languages";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Map<String, Languages>>() {});
  }

  /**
   * (asynchronously) List dictionaries supported per language.
   *
   * @return CompletableFuture<Map<String, Languages>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, Languages>> getDictionaryLanguagesAsync() throws AlgoliaRuntimeException {
    return this.getDictionaryLanguagesAsync(null);
  }

  /**
   * Retrieve dictionaries settings. The API stores languages whose standard entries are disabled.
   * Fetch settings does not return false values.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetDictionarySettingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetDictionarySettingsResponse getDictionarySettings(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getDictionarySettingsAsync(requestOptions));
  }

  /**
   * Retrieve dictionaries settings. The API stores languages whose standard entries are disabled.
   * Fetch settings does not return false values.
   *
   * @return GetDictionarySettingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetDictionarySettingsResponse getDictionarySettings() throws AlgoliaRuntimeException {
    return this.getDictionarySettings(null);
  }

  /**
   * (asynchronously) Retrieve dictionaries settings. The API stores languages whose standard
   * entries are disabled. Fetch settings does not return false values.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetDictionarySettingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetDictionarySettingsResponse> getDictionarySettingsAsync(RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/dictionaries/*/settings";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<GetDictionarySettingsResponse>() {});
  }

  /**
   * (asynchronously) Retrieve dictionaries settings. The API stores languages whose standard
   * entries are disabled. Fetch settings does not return false values.
   *
   * @return CompletableFuture<GetDictionarySettingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetDictionarySettingsResponse> getDictionarySettingsAsync() throws AlgoliaRuntimeException {
    return this.getDictionarySettingsAsync(null);
  }

  /**
   * Return the latest log entries.
   *
   * @param offset First entry to retrieve (zero-based). Log entries are sorted by decreasing date,
   *     therefore 0 designates the most recent log entry. (optional, default to 0)
   * @param length Maximum number of entries to retrieve. The maximum allowed value is 1000.
   *     (optional, default to 10)
   * @param indexName Index for which log entries should be retrieved. When omitted, log entries are
   *     retrieved across all indices. (optional)
   * @param type Type of log entries to retrieve. When omitted, all log entries are retrieved.
   *     (optional, default to all)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetLogsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetLogsResponse getLogs(Integer offset, Integer length, String indexName, LogType type, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getLogsAsync(offset, length, indexName, type, requestOptions));
  }

  /**
   * Return the latest log entries.
   *
   * @param offset First entry to retrieve (zero-based). Log entries are sorted by decreasing date,
   *     therefore 0 designates the most recent log entry. (optional, default to 0)
   * @param length Maximum number of entries to retrieve. The maximum allowed value is 1000.
   *     (optional, default to 10)
   * @param indexName Index for which log entries should be retrieved. When omitted, log entries are
   *     retrieved across all indices. (optional)
   * @param type Type of log entries to retrieve. When omitted, all log entries are retrieved.
   *     (optional, default to all)
   * @return GetLogsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetLogsResponse getLogs(Integer offset, Integer length, String indexName, LogType type) throws AlgoliaRuntimeException {
    return this.getLogs(offset, length, indexName, type, null);
  }

  /**
   * Return the latest log entries.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetLogsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetLogsResponse getLogs(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.getLogs(null, null, null, null, requestOptions);
  }

  /**
   * Return the latest log entries.
   *
   * @return GetLogsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetLogsResponse getLogs() throws AlgoliaRuntimeException {
    return this.getLogs(null, null, null, null, null);
  }

  /**
   * (asynchronously) Return the latest log entries.
   *
   * @param offset First entry to retrieve (zero-based). Log entries are sorted by decreasing date,
   *     therefore 0 designates the most recent log entry. (optional, default to 0)
   * @param length Maximum number of entries to retrieve. The maximum allowed value is 1000.
   *     (optional, default to 10)
   * @param indexName Index for which log entries should be retrieved. When omitted, log entries are
   *     retrieved across all indices. (optional)
   * @param type Type of log entries to retrieve. When omitted, all log entries are retrieved.
   *     (optional, default to all)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetLogsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetLogsResponse> getLogsAsync(
    Integer offset,
    Integer length,
    String indexName,
    LogType type,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/logs";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (offset != null) {
      queryParameters.put("offset", parameterToString(offset));
    }

    if (length != null) {
      queryParameters.put("length", parameterToString(length));
    }

    if (indexName != null) {
      queryParameters.put("indexName", parameterToString(indexName));
    }

    if (type != null) {
      queryParameters.put("type", parameterToString(type));
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<GetLogsResponse>() {});
  }

  /**
   * (asynchronously) Return the latest log entries.
   *
   * @param offset First entry to retrieve (zero-based). Log entries are sorted by decreasing date,
   *     therefore 0 designates the most recent log entry. (optional, default to 0)
   * @param length Maximum number of entries to retrieve. The maximum allowed value is 1000.
   *     (optional, default to 10)
   * @param indexName Index for which log entries should be retrieved. When omitted, log entries are
   *     retrieved across all indices. (optional)
   * @param type Type of log entries to retrieve. When omitted, all log entries are retrieved.
   *     (optional, default to all)
   * @return CompletableFuture<GetLogsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetLogsResponse> getLogsAsync(Integer offset, Integer length, String indexName, LogType type)
    throws AlgoliaRuntimeException {
    return this.getLogsAsync(offset, length, indexName, type, null);
  }

  /**
   * (asynchronously) Return the latest log entries.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetLogsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetLogsResponse> getLogsAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.getLogsAsync(null, null, null, null, requestOptions);
  }

  /**
   * (asynchronously) Return the latest log entries.
   *
   * @return CompletableFuture<GetLogsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetLogsResponse> getLogsAsync() throws AlgoliaRuntimeException {
    return this.getLogsAsync(null, null, null, null, null);
  }

  /**
   * Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToRetrieve List of attributes to retrieve. If not specified, all retrievable
   *     attributes are returned. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Map<String, String>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, String> getObject(String indexName, String objectID, List<String> attributesToRetrieve, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getObjectAsync(indexName, objectID, attributesToRetrieve, requestOptions));
  }

  /**
   * Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToRetrieve List of attributes to retrieve. If not specified, all retrievable
   *     attributes are returned. (optional)
   * @return Map<String, String>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, String> getObject(String indexName, String objectID, List<String> attributesToRetrieve)
    throws AlgoliaRuntimeException {
    return this.getObject(indexName, objectID, attributesToRetrieve, null);
  }

  /**
   * Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Map<String, String>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, String> getObject(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.getObject(indexName, objectID, null, requestOptions);
  }

  /**
   * Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return Map<String, String>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Map<String, String> getObject(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getObject(indexName, objectID, null, null);
  }

  /**
   * (asynchronously) Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToRetrieve List of attributes to retrieve. If not specified, all retrievable
   *     attributes are returned. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Map<String, String>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, String>> getObjectAsync(
    String indexName,
    String objectID,
    List<String> attributesToRetrieve,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `getObject`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `getObject`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (attributesToRetrieve != null) {
      queryParameters.put("attributesToRetrieve", parameterToString(attributesToRetrieve));
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Map<String, String>>() {});
  }

  /**
   * (asynchronously) Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToRetrieve List of attributes to retrieve. If not specified, all retrievable
   *     attributes are returned. (optional)
   * @return CompletableFuture<Map<String, String>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, String>> getObjectAsync(String indexName, String objectID, List<String> attributesToRetrieve)
    throws AlgoliaRuntimeException {
    return this.getObjectAsync(indexName, objectID, attributesToRetrieve, null);
  }

  /**
   * (asynchronously) Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Map<String, String>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, String>> getObjectAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.getObjectAsync(indexName, objectID, null, requestOptions);
  }

  /**
   * (asynchronously) Retrieve one object from the index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<Map<String, String>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Map<String, String>> getObjectAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getObjectAsync(indexName, objectID, null, null);
  }

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param getObjectsParams The Algolia object. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> GetObjectsResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> GetObjectsResponse<T> getObjects(GetObjectsParams getObjectsParams, Class<T> innerType, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getObjectsAsync(getObjectsParams, innerType, requestOptions));
  }

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param getObjectsParams The Algolia object. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> GetObjectsResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> GetObjectsResponse<T> getObjects(GetObjectsParams getObjectsParams, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.getObjects(getObjectsParams, innerType, null);
  }

  /**
   * (asynchronously) Retrieve one or more objects, potentially from different indices, in a single
   * API call.
   *
   * @param getObjectsParams The Algolia object. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<GetObjectsResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<GetObjectsResponse<T>> getObjectsAsync(
    GetObjectsParams getObjectsParams,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (getObjectsParams == null) {
      throw new AlgoliaRuntimeException("Parameter `getObjectsParams` is required when calling `getObjects`.");
    }

    Object bodyObj = getObjectsParams;

    // create path and map variables
    String requestPath = "/1/indexes/*/objects";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, GetObjectsResponse.class, innerType);
  }

  /**
   * (asynchronously) Retrieve one or more objects, potentially from different indices, in a single
   * API call.
   *
   * @param getObjectsParams The Algolia object. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<GetObjectsResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<GetObjectsResponse<T>> getObjectsAsync(GetObjectsParams getObjectsParams, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.getObjectsAsync(getObjectsParams, innerType, null);
  }

  /**
   * Retrieve the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Rule
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Rule getRule(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getRuleAsync(indexName, objectID, requestOptions));
  }

  /**
   * Retrieve the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return Rule
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Rule getRule(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getRule(indexName, objectID, null);
  }

  /**
   * (asynchronously) Retrieve the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Rule> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Rule> getRuleAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `getRule`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `getRule`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/rules/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Rule>() {});
  }

  /**
   * (asynchronously) Retrieve the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<Rule> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Rule> getRuleAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getRuleAsync(indexName, objectID, null);
  }

  /**
   * Retrieve settings of an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return IndexSettings
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public IndexSettings getSettings(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getSettingsAsync(indexName, requestOptions));
  }

  /**
   * Retrieve settings of an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return IndexSettings
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public IndexSettings getSettings(String indexName) throws AlgoliaRuntimeException {
    return this.getSettings(indexName, null);
  }

  /**
   * (asynchronously) Retrieve settings of an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<IndexSettings> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<IndexSettings> getSettingsAsync(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `getSettings`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/settings".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<IndexSettings>() {});
  }

  /**
   * (asynchronously) Retrieve settings of an index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<IndexSettings> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<IndexSettings> getSettingsAsync(String indexName) throws AlgoliaRuntimeException {
    return this.getSettingsAsync(indexName, null);
  }

  /**
   * List all allowed sources.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return List<Source>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public List<Source> getSources(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getSourcesAsync(requestOptions));
  }

  /**
   * List all allowed sources.
   *
   * @return List<Source>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public List<Source> getSources() throws AlgoliaRuntimeException {
    return this.getSources(null);
  }

  /**
   * (asynchronously) List all allowed sources.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<List<Source>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<List<Source>> getSourcesAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/security/sources";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<List<Source>>() {});
  }

  /**
   * (asynchronously) List all allowed sources.
   *
   * @return CompletableFuture<List<Source>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<List<Source>> getSourcesAsync() throws AlgoliaRuntimeException {
    return this.getSourcesAsync(null);
  }

  /**
   * Fetch a synonym object identified by its objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SynonymHit
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SynonymHit getSynonym(String indexName, String objectID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getSynonymAsync(indexName, objectID, requestOptions));
  }

  /**
   * Fetch a synonym object identified by its objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return SynonymHit
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SynonymHit getSynonym(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getSynonym(indexName, objectID, null);
  }

  /**
   * (asynchronously) Fetch a synonym object identified by its objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SynonymHit> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SynonymHit> getSynonymAsync(String indexName, String objectID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `getSynonym`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `getSynonym`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/synonyms/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<SynonymHit>() {});
  }

  /**
   * (asynchronously) Fetch a synonym object identified by its objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @return CompletableFuture<SynonymHit> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SynonymHit> getSynonymAsync(String indexName, String objectID) throws AlgoliaRuntimeException {
    return this.getSynonymAsync(indexName, objectID, null);
  }

  /**
   * Check the current status of a given task.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param taskID Unique identifier of an task. Numeric value (up to 64bits). (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetTaskResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetTaskResponse getTask(String indexName, Long taskID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getTaskAsync(indexName, taskID, requestOptions));
  }

  /**
   * Check the current status of a given task.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param taskID Unique identifier of an task. Numeric value (up to 64bits). (required)
   * @return GetTaskResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetTaskResponse getTask(String indexName, Long taskID) throws AlgoliaRuntimeException {
    return this.getTask(indexName, taskID, null);
  }

  /**
   * (asynchronously) Check the current status of a given task.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param taskID Unique identifier of an task. Numeric value (up to 64bits). (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetTaskResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetTaskResponse> getTaskAsync(String indexName, Long taskID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `getTask`.");
    }

    if (taskID == null) {
      throw new AlgoliaRuntimeException("Parameter `taskID` is required when calling `getTask`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/task/{taskID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{taskID\\}", this.escapeString(taskID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<GetTaskResponse>() {});
  }

  /**
   * (asynchronously) Check the current status of a given task.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param taskID Unique identifier of an task. Numeric value (up to 64bits). (required)
   * @return CompletableFuture<GetTaskResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetTaskResponse> getTaskAsync(String indexName, Long taskID) throws AlgoliaRuntimeException {
    return this.getTaskAsync(indexName, taskID, null);
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following array of userIDs and clusters.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return GetTopUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetTopUserIdsResponse getTopUserIds(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getTopUserIdsAsync(requestOptions));
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following array of userIDs and clusters.
   *
   * @return GetTopUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetTopUserIdsResponse getTopUserIds() throws AlgoliaRuntimeException {
    return this.getTopUserIds(null);
  }

  /**
   * (asynchronously) Get the top 10 userIDs with the highest number of records per cluster. The
   * data returned will usually be a few seconds behind real time, because userID usage may take up
   * to a few seconds to propagate to the different clusters. Upon success, the response is 200 OK
   * and contains the following array of userIDs and clusters.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<GetTopUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetTopUserIdsResponse> getTopUserIdsAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/top";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<GetTopUserIdsResponse>() {});
  }

  /**
   * (asynchronously) Get the top 10 userIDs with the highest number of records per cluster. The
   * data returned will usually be a few seconds behind real time, because userID usage may take up
   * to a few seconds to propagate to the different clusters. Upon success, the response is 200 OK
   * and contains the following array of userIDs and clusters.
   *
   * @return CompletableFuture<GetTopUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetTopUserIdsResponse> getTopUserIdsAsync() throws AlgoliaRuntimeException {
    return this.getTopUserIdsAsync(null);
  }

  /**
   * Returns the userID data stored in the mapping. The data returned will usually be a few seconds
   * behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userID
   * data.
   *
   * @param userID userID to assign. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UserId
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UserId getUserId(String userID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getUserIdAsync(userID, requestOptions));
  }

  /**
   * Returns the userID data stored in the mapping. The data returned will usually be a few seconds
   * behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userID
   * data.
   *
   * @param userID userID to assign. (required)
   * @return UserId
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UserId getUserId(String userID) throws AlgoliaRuntimeException {
    return this.getUserId(userID, null);
  }

  /**
   * (asynchronously) Returns the userID data stored in the mapping. The data returned will usually
   * be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userID data.
   *
   * @param userID userID to assign. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UserId> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UserId> getUserIdAsync(String userID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    if (userID == null) {
      throw new AlgoliaRuntimeException("Parameter `userID` is required when calling `getUserId`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/{userID}".replaceAll("\\{userID\\}", this.escapeString(userID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UserId>() {});
  }

  /**
   * (asynchronously) Returns the userID data stored in the mapping. The data returned will usually
   * be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userID data.
   *
   * @param userID userID to assign. (required)
   * @return CompletableFuture<UserId> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UserId> getUserIdAsync(String userID) throws AlgoliaRuntimeException {
    return this.getUserIdAsync(userID, null);
  }

  /**
   * Get the status of your clusters' migrations or user creations. Creating a large batch of users
   * or migrating your multi-cluster may take quite some time. This method lets you retrieve the
   * status of the migration, so you can know when it's done. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @param getClusters If the clusters pending mapping state should be on the response. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return HasPendingMappingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public HasPendingMappingsResponse hasPendingMappings(Boolean getClusters, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(hasPendingMappingsAsync(getClusters, requestOptions));
  }

  /**
   * Get the status of your clusters' migrations or user creations. Creating a large batch of users
   * or migrating your multi-cluster may take quite some time. This method lets you retrieve the
   * status of the migration, so you can know when it's done. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @param getClusters If the clusters pending mapping state should be on the response. (optional)
   * @return HasPendingMappingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public HasPendingMappingsResponse hasPendingMappings(Boolean getClusters) throws AlgoliaRuntimeException {
    return this.hasPendingMappings(getClusters, null);
  }

  /**
   * Get the status of your clusters' migrations or user creations. Creating a large batch of users
   * or migrating your multi-cluster may take quite some time. This method lets you retrieve the
   * status of the migration, so you can know when it's done. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return HasPendingMappingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public HasPendingMappingsResponse hasPendingMappings(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.hasPendingMappings(null, requestOptions);
  }

  /**
   * Get the status of your clusters' migrations or user creations. Creating a large batch of users
   * or migrating your multi-cluster may take quite some time. This method lets you retrieve the
   * status of the migration, so you can know when it's done. Upon success, the response is 200 OK.
   * A successful response indicates that the operation has been taken into account, and the userIDs
   * are directly usable.
   *
   * @return HasPendingMappingsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public HasPendingMappingsResponse hasPendingMappings() throws AlgoliaRuntimeException {
    return this.hasPendingMappings(null, null);
  }

  /**
   * (asynchronously) Get the status of your clusters&#39; migrations or user creations. Creating a
   * large batch of users or migrating your multi-cluster may take quite some time. This method lets
   * you retrieve the status of the migration, so you can know when it&#39;s done. Upon success, the
   * response is 200 OK. A successful response indicates that the operation has been taken into
   * account, and the userIDs are directly usable.
   *
   * @param getClusters If the clusters pending mapping state should be on the response. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<HasPendingMappingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(Boolean getClusters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/pending";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (getClusters != null) {
      queryParameters.put("getClusters", parameterToString(getClusters));
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<HasPendingMappingsResponse>() {});
  }

  /**
   * (asynchronously) Get the status of your clusters&#39; migrations or user creations. Creating a
   * large batch of users or migrating your multi-cluster may take quite some time. This method lets
   * you retrieve the status of the migration, so you can know when it&#39;s done. Upon success, the
   * response is 200 OK. A successful response indicates that the operation has been taken into
   * account, and the userIDs are directly usable.
   *
   * @param getClusters If the clusters pending mapping state should be on the response. (optional)
   * @return CompletableFuture<HasPendingMappingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(Boolean getClusters) throws AlgoliaRuntimeException {
    return this.hasPendingMappingsAsync(getClusters, null);
  }

  /**
   * (asynchronously) Get the status of your clusters&#39; migrations or user creations. Creating a
   * large batch of users or migrating your multi-cluster may take quite some time. This method lets
   * you retrieve the status of the migration, so you can know when it&#39;s done. Upon success, the
   * response is 200 OK. A successful response indicates that the operation has been taken into
   * account, and the userIDs are directly usable.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<HasPendingMappingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.hasPendingMappingsAsync(null, requestOptions);
  }

  /**
   * (asynchronously) Get the status of your clusters&#39; migrations or user creations. Creating a
   * large batch of users or migrating your multi-cluster may take quite some time. This method lets
   * you retrieve the status of the migration, so you can know when it&#39;s done. Upon success, the
   * response is 200 OK. A successful response indicates that the operation has been taken into
   * account, and the userIDs are directly usable.
   *
   * @return CompletableFuture<HasPendingMappingsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync() throws AlgoliaRuntimeException {
    return this.hasPendingMappingsAsync(null, null);
  }

  /**
   * List API keys, along with their associated rights.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListApiKeysResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListApiKeysResponse listApiKeys(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listApiKeysAsync(requestOptions));
  }

  /**
   * List API keys, along with their associated rights.
   *
   * @return ListApiKeysResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListApiKeysResponse listApiKeys() throws AlgoliaRuntimeException {
    return this.listApiKeys(null);
  }

  /**
   * (asynchronously) List API keys, along with their associated rights.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListApiKeysResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListApiKeysResponse> listApiKeysAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/keys";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<ListApiKeysResponse>() {});
  }

  /**
   * (asynchronously) List API keys, along with their associated rights.
   *
   * @return CompletableFuture<ListApiKeysResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListApiKeysResponse> listApiKeysAsync() throws AlgoliaRuntimeException {
    return this.listApiKeysAsync(null);
  }

  /**
   * List the clusters available in a multi-clusters setup for a single appID. Upon success, the
   * response is 200 OK and contains the following clusters.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListClustersResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListClustersResponse listClusters(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listClustersAsync(requestOptions));
  }

  /**
   * List the clusters available in a multi-clusters setup for a single appID. Upon success, the
   * response is 200 OK and contains the following clusters.
   *
   * @return ListClustersResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListClustersResponse listClusters() throws AlgoliaRuntimeException {
    return this.listClusters(null);
  }

  /**
   * (asynchronously) List the clusters available in a multi-clusters setup for a single appID. Upon
   * success, the response is 200 OK and contains the following clusters.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListClustersResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListClustersResponse> listClustersAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<ListClustersResponse>() {});
  }

  /**
   * (asynchronously) List the clusters available in a multi-clusters setup for a single appID. Upon
   * success, the response is 200 OK and contains the following clusters.
   *
   * @return CompletableFuture<ListClustersResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListClustersResponse> listClustersAsync() throws AlgoliaRuntimeException {
    return this.listClustersAsync(null);
  }

  /**
   * List existing indexes from an application.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListIndicesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListIndicesResponse listIndices(Integer page, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listIndicesAsync(page, requestOptions));
  }

  /**
   * List existing indexes from an application.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @return ListIndicesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListIndicesResponse listIndices(Integer page) throws AlgoliaRuntimeException {
    return this.listIndices(page, null);
  }

  /**
   * List existing indexes from an application.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListIndicesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListIndicesResponse listIndices(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listIndices(null, requestOptions);
  }

  /**
   * List existing indexes from an application.
   *
   * @return ListIndicesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListIndicesResponse listIndices() throws AlgoliaRuntimeException {
    return this.listIndices(null, null);
  }

  /**
   * (asynchronously) List existing indexes from an application.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListIndicesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListIndicesResponse> listIndicesAsync(Integer page, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/indexes";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (page != null) {
      queryParameters.put("page", parameterToString(page));
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<ListIndicesResponse>() {});
  }

  /**
   * (asynchronously) List existing indexes from an application.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @return CompletableFuture<ListIndicesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListIndicesResponse> listIndicesAsync(Integer page) throws AlgoliaRuntimeException {
    return this.listIndicesAsync(page, null);
  }

  /**
   * (asynchronously) List existing indexes from an application.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListIndicesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListIndicesResponse> listIndicesAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listIndicesAsync(null, requestOptions);
  }

  /**
   * (asynchronously) List existing indexes from an application.
   *
   * @return CompletableFuture<ListIndicesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListIndicesResponse> listIndicesAsync() throws AlgoliaRuntimeException {
    return this.listIndicesAsync(null, null);
  }

  /**
   * List the userIDs assigned to a multi-clusters appID. The data returned will usually be a few
   * seconds behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userIDs
   * data.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListUserIdsResponse listUserIds(Integer page, Integer hitsPerPage, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listUserIdsAsync(page, hitsPerPage, requestOptions));
  }

  /**
   * List the userIDs assigned to a multi-clusters appID. The data returned will usually be a few
   * seconds behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userIDs
   * data.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @return ListUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListUserIdsResponse listUserIds(Integer page, Integer hitsPerPage) throws AlgoliaRuntimeException {
    return this.listUserIds(page, hitsPerPage, null);
  }

  /**
   * List the userIDs assigned to a multi-clusters appID. The data returned will usually be a few
   * seconds behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userIDs
   * data.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ListUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListUserIdsResponse listUserIds(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listUserIds(null, null, requestOptions);
  }

  /**
   * List the userIDs assigned to a multi-clusters appID. The data returned will usually be a few
   * seconds behind real time, because userID usage may take up to a few seconds to propagate to the
   * different clusters. Upon success, the response is 200 OK and contains the following userIDs
   * data.
   *
   * @return ListUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListUserIdsResponse listUserIds() throws AlgoliaRuntimeException {
    return this.listUserIds(null, null, null);
  }

  /**
   * (asynchronously) List the userIDs assigned to a multi-clusters appID. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userIDs data.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListUserIdsResponse> listUserIdsAsync(Integer page, Integer hitsPerPage, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters/mapping";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (page != null) {
      queryParameters.put("page", parameterToString(page));
    }

    if (hitsPerPage != null) {
      queryParameters.put("hitsPerPage", parameterToString(hitsPerPage));
    }

    Call call = this.buildCall(requestPath, "GET", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<ListUserIdsResponse>() {});
  }

  /**
   * (asynchronously) List the userIDs assigned to a multi-clusters appID. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userIDs data.
   *
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @return CompletableFuture<ListUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListUserIdsResponse> listUserIdsAsync(Integer page, Integer hitsPerPage) throws AlgoliaRuntimeException {
    return this.listUserIdsAsync(page, hitsPerPage, null);
  }

  /**
   * (asynchronously) List the userIDs assigned to a multi-clusters appID. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userIDs data.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ListUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListUserIdsResponse> listUserIdsAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listUserIdsAsync(null, null, requestOptions);
  }

  /**
   * (asynchronously) List the userIDs assigned to a multi-clusters appID. The data returned will
   * usually be a few seconds behind real time, because userID usage may take up to a few seconds to
   * propagate to the different clusters. Upon success, the response is 200 OK and contains the
   * following userIDs data.
   *
   * @return CompletableFuture<ListUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListUserIdsResponse> listUserIdsAsync() throws AlgoliaRuntimeException {
    return this.listUserIdsAsync(null, null, null);
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param batchParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return MultipleBatchResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public MultipleBatchResponse multipleBatch(BatchParams batchParams, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(multipleBatchAsync(batchParams, requestOptions));
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param batchParams (required)
   * @return MultipleBatchResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public MultipleBatchResponse multipleBatch(BatchParams batchParams) throws AlgoliaRuntimeException {
    return this.multipleBatch(batchParams, null);
  }

  /**
   * (asynchronously) Perform multiple write operations, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param batchParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<MultipleBatchResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<MultipleBatchResponse> multipleBatchAsync(BatchParams batchParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (batchParams == null) {
      throw new AlgoliaRuntimeException("Parameter `batchParams` is required when calling `multipleBatch`.");
    }

    Object bodyObj = batchParams;

    // create path and map variables
    String requestPath = "/1/indexes/*/batch";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<MultipleBatchResponse>() {});
  }

  /**
   * (asynchronously) Perform multiple write operations, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param batchParams (required)
   * @return CompletableFuture<MultipleBatchResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<MultipleBatchResponse> multipleBatchAsync(BatchParams batchParams) throws AlgoliaRuntimeException {
    return this.multipleBatchAsync(batchParams, null);
  }

  /**
   * Performs a copy or a move operation on a index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param operationIndexParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse operationIndex(String indexName, OperationIndexParams operationIndexParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(operationIndexAsync(indexName, operationIndexParams, requestOptions));
  }

  /**
   * Performs a copy or a move operation on a index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param operationIndexParams (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse operationIndex(String indexName, OperationIndexParams operationIndexParams) throws AlgoliaRuntimeException {
    return this.operationIndex(indexName, operationIndexParams, null);
  }

  /**
   * (asynchronously) Performs a copy or a move operation on a index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param operationIndexParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> operationIndexAsync(
    String indexName,
    OperationIndexParams operationIndexParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `operationIndex`.");
    }

    if (operationIndexParams == null) {
      throw new AlgoliaRuntimeException("Parameter `operationIndexParams` is required when calling `operationIndex`.");
    }

    Object bodyObj = operationIndexParams;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/operation".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Performs a copy or a move operation on a index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param operationIndexParams (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> operationIndexAsync(String indexName, OperationIndexParams operationIndexParams)
    throws AlgoliaRuntimeException {
    return this.operationIndexAsync(indexName, operationIndexParams, null);
  }

  /**
   * Update one or more attributes of an existing object. This method lets you update only a part of
   * an existing object, either by adding new attributes or updating existing ones. You can
   * partially update several objects in a single method call. If the index targeted by this
   * operation doesn't exist yet, it's automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param createIfNotExists Creates the record if it does not exist yet. (optional, default to
   *     true)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse partialUpdateObject(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    Boolean createIfNotExists,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(partialUpdateObjectAsync(indexName, objectID, attributesToUpdate, createIfNotExists, requestOptions));
  }

  /**
   * Update one or more attributes of an existing object. This method lets you update only a part of
   * an existing object, either by adding new attributes or updating existing ones. You can
   * partially update several objects in a single method call. If the index targeted by this
   * operation doesn't exist yet, it's automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param createIfNotExists Creates the record if it does not exist yet. (optional, default to
   *     true)
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse partialUpdateObject(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    Boolean createIfNotExists
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObject(indexName, objectID, attributesToUpdate, createIfNotExists, null);
  }

  /**
   * Update one or more attributes of an existing object. This method lets you update only a part of
   * an existing object, either by adding new attributes or updating existing ones. You can
   * partially update several objects in a single method call. If the index targeted by this
   * operation doesn't exist yet, it's automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse partialUpdateObject(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObject(indexName, objectID, attributesToUpdate, null, requestOptions);
  }

  /**
   * Update one or more attributes of an existing object. This method lets you update only a part of
   * an existing object, either by adding new attributes or updating existing ones. You can
   * partially update several objects in a single method call. If the index targeted by this
   * operation doesn't exist yet, it's automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @return UpdatedAtWithObjectIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtWithObjectIdResponse partialUpdateObject(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObject(indexName, objectID, attributesToUpdate, null, null);
  }

  /**
   * (asynchronously) Update one or more attributes of an existing object. This method lets you
   * update only a part of an existing object, either by adding new attributes or updating existing
   * ones. You can partially update several objects in a single method call. If the index targeted
   * by this operation doesn&#39;t exist yet, it&#39;s automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param createIfNotExists Creates the record if it does not exist yet. (optional, default to
   *     true)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> partialUpdateObjectAsync(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    Boolean createIfNotExists,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `partialUpdateObject`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `partialUpdateObject`.");
    }

    if (attributesToUpdate == null) {
      throw new AlgoliaRuntimeException("Parameter `attributesToUpdate` is required when calling `partialUpdateObject`.");
    }

    Object bodyObj = attributesToUpdate;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/{objectID}/partial".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (createIfNotExists != null) {
      queryParameters.put("createIfNotExists", parameterToString(createIfNotExists));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtWithObjectIdResponse>() {});
  }

  /**
   * (asynchronously) Update one or more attributes of an existing object. This method lets you
   * update only a part of an existing object, either by adding new attributes or updating existing
   * ones. You can partially update several objects in a single method call. If the index targeted
   * by this operation doesn&#39;t exist yet, it&#39;s automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param createIfNotExists Creates the record if it does not exist yet. (optional, default to
   *     true)
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> partialUpdateObjectAsync(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    Boolean createIfNotExists
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObjectAsync(indexName, objectID, attributesToUpdate, createIfNotExists, null);
  }

  /**
   * (asynchronously) Update one or more attributes of an existing object. This method lets you
   * update only a part of an existing object, either by adding new attributes or updating existing
   * ones. You can partially update several objects in a single method call. If the index targeted
   * by this operation doesn&#39;t exist yet, it&#39;s automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> partialUpdateObjectAsync(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObjectAsync(indexName, objectID, attributesToUpdate, null, requestOptions);
  }

  /**
   * (asynchronously) Update one or more attributes of an existing object. This method lets you
   * update only a part of an existing object, either by adding new attributes or updating existing
   * ones. You can partially update several objects in a single method call. If the index targeted
   * by this operation doesn&#39;t exist yet, it&#39;s automatically created.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param attributesToUpdate Map of attribute(s) to update. (required)
   * @return CompletableFuture<UpdatedAtWithObjectIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtWithObjectIdResponse> partialUpdateObjectAsync(
    String indexName,
    String objectID,
    Map<String, AttributeToUpdate> attributesToUpdate
  ) throws AlgoliaRuntimeException {
    return this.partialUpdateObjectAsync(indexName, objectID, attributesToUpdate, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object post(String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(postAsync(path, parameters, body, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object post(String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.post(path, parameters, body, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object post(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.post(path, null, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object post(String path) throws AlgoliaRuntimeException {
    return this.post(path, null, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> postAsync(String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (path == null) {
      throw new AlgoliaRuntimeException("Parameter `path` is required when calling `post`.");
    }

    Object bodyObj = body != null ? body : new Object();

    // create path and map variables
    String requestPath = "/1{path}".replaceAll("\\{path\\}", path.toString());

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (parameters != null) {
      for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
        queryParameters.put(parameter.getKey().toString(), parameterToString(parameter.getValue()));
      }
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> postAsync(String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.postAsync(path, parameters, body, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> postAsync(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.postAsync(path, null, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> postAsync(String path) throws AlgoliaRuntimeException {
    return this.postAsync(path, null, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object put(String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(putAsync(path, parameters, body, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object put(String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.put(path, parameters, body, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object put(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.put(path, null, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return Object
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object put(String path) throws AlgoliaRuntimeException {
    return this.put(path, null, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> putAsync(String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (path == null) {
      throw new AlgoliaRuntimeException("Parameter `path` is required when calling `put`.");
    }

    Object bodyObj = body != null ? body : new Object();

    // create path and map variables
    String requestPath = "/1{path}".replaceAll("\\{path\\}", path.toString());

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (parameters != null) {
      for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
        queryParameters.put(parameter.getKey().toString(), parameterToString(parameter.getValue()));
      }
    }

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param parameters Query parameters to be applied to the current query. (optional)
   * @param body The parameters to send with the custom request. (optional)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> putAsync(String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.putAsync(path, parameters, body, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> putAsync(String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.putAsync(path, null, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path The path of the API endpoint to target, anything after the /1 needs to be
   *     specified. (required)
   * @return CompletableFuture<Object> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> putAsync(String path) throws AlgoliaRuntimeException {
    return this.putAsync(path, null, null, null);
  }

  /**
   * Remove a userID and its associated data from the multi-clusters. Upon success, the response is
   * 200 OK and a task is created to remove the userID data and mapping.
   *
   * @param userID userID to assign. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return RemoveUserIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public RemoveUserIdResponse removeUserId(String userID, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(removeUserIdAsync(userID, requestOptions));
  }

  /**
   * Remove a userID and its associated data from the multi-clusters. Upon success, the response is
   * 200 OK and a task is created to remove the userID data and mapping.
   *
   * @param userID userID to assign. (required)
   * @return RemoveUserIdResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public RemoveUserIdResponse removeUserId(String userID) throws AlgoliaRuntimeException {
    return this.removeUserId(userID, null);
  }

  /**
   * (asynchronously) Remove a userID and its associated data from the multi-clusters. Upon success,
   * the response is 200 OK and a task is created to remove the userID data and mapping.
   *
   * @param userID userID to assign. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<RemoveUserIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<RemoveUserIdResponse> removeUserIdAsync(String userID, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (userID == null) {
      throw new AlgoliaRuntimeException("Parameter `userID` is required when calling `removeUserId`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/{userID}".replaceAll("\\{userID\\}", this.escapeString(userID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "DELETE", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<RemoveUserIdResponse>() {});
  }

  /**
   * (asynchronously) Remove a userID and its associated data from the multi-clusters. Upon success,
   * the response is 200 OK and a task is created to remove the userID data and mapping.
   *
   * @param userID userID to assign. (required)
   * @return CompletableFuture<RemoveUserIdResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<RemoveUserIdResponse> removeUserIdAsync(String userID) throws AlgoliaRuntimeException {
    return this.removeUserIdAsync(userID, null);
  }

  /**
   * Replace all allowed sources.
   *
   * @param source The sources to allow. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return ReplaceSourceResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ReplaceSourceResponse replaceSources(List<Source> source, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(replaceSourcesAsync(source, requestOptions));
  }

  /**
   * Replace all allowed sources.
   *
   * @param source The sources to allow. (required)
   * @return ReplaceSourceResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ReplaceSourceResponse replaceSources(List<Source> source) throws AlgoliaRuntimeException {
    return this.replaceSources(source, null);
  }

  /**
   * (asynchronously) Replace all allowed sources.
   *
   * @param source The sources to allow. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<ReplaceSourceResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ReplaceSourceResponse> replaceSourcesAsync(List<Source> source, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (source == null) {
      throw new AlgoliaRuntimeException("Parameter `source` is required when calling `replaceSources`.");
    }

    Object bodyObj = source;

    // create path and map variables
    String requestPath = "/1/security/sources";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<ReplaceSourceResponse>() {});
  }

  /**
   * (asynchronously) Replace all allowed sources.
   *
   * @param source The sources to allow. (required)
   * @return CompletableFuture<ReplaceSourceResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ReplaceSourceResponse> replaceSourcesAsync(List<Source> source) throws AlgoliaRuntimeException {
    return this.replaceSourcesAsync(source, null);
  }

  /**
   * Restore a deleted API key, along with its associated rights.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return AddApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public AddApiKeyResponse restoreApiKey(String key, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(restoreApiKeyAsync(key, requestOptions));
  }

  /**
   * Restore a deleted API key, along with its associated rights.
   *
   * @param key API Key string. (required)
   * @return AddApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public AddApiKeyResponse restoreApiKey(String key) throws AlgoliaRuntimeException {
    return this.restoreApiKey(key, null);
  }

  /**
   * (asynchronously) Restore a deleted API key, along with its associated rights.
   *
   * @param key API Key string. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<AddApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<AddApiKeyResponse> restoreApiKeyAsync(String key, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    if (key == null) {
      throw new AlgoliaRuntimeException("Parameter `key` is required when calling `restoreApiKey`.");
    }

    Object bodyObj = null;

    // create path and map variables
    String requestPath = "/1/keys/{key}/restore".replaceAll("\\{key\\}", this.escapeString(key.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<AddApiKeyResponse>() {});
  }

  /**
   * (asynchronously) Restore a deleted API key, along with its associated rights.
   *
   * @param key API Key string. (required)
   * @return CompletableFuture<AddApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<AddApiKeyResponse> restoreApiKeyAsync(String key) throws AlgoliaRuntimeException {
    return this.restoreApiKeyAsync(key, null);
  }

  /**
   * Add an object to the index, automatically assigning it an object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param body The Algolia record. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SaveObjectResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveObjectResponse saveObject(String indexName, Object body, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(saveObjectAsync(indexName, body, requestOptions));
  }

  /**
   * Add an object to the index, automatically assigning it an object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param body The Algolia record. (required)
   * @return SaveObjectResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveObjectResponse saveObject(String indexName, Object body) throws AlgoliaRuntimeException {
    return this.saveObject(indexName, body, null);
  }

  /**
   * (asynchronously) Add an object to the index, automatically assigning it an object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param body The Algolia record. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SaveObjectResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveObjectResponse> saveObjectAsync(String indexName, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `saveObject`.");
    }

    if (body == null) {
      throw new AlgoliaRuntimeException("Parameter `body` is required when calling `saveObject`.");
    }

    Object bodyObj = body;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<SaveObjectResponse>() {});
  }

  /**
   * (asynchronously) Add an object to the index, automatically assigning it an object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param body The Algolia record. (required)
   * @return CompletableFuture<SaveObjectResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveObjectResponse> saveObjectAsync(String indexName, Object body) throws AlgoliaRuntimeException {
    return this.saveObjectAsync(indexName, body, null);
  }

  /**
   * Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedRuleResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedRuleResponse saveRule(
    String indexName,
    String objectID,
    Rule rule,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(saveRuleAsync(indexName, objectID, rule, forwardToReplicas, requestOptions));
  }

  /**
   * Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return UpdatedRuleResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedRuleResponse saveRule(String indexName, String objectID, Rule rule, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.saveRule(indexName, objectID, rule, forwardToReplicas, null);
  }

  /**
   * Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedRuleResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedRuleResponse saveRule(String indexName, String objectID, Rule rule, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.saveRule(indexName, objectID, rule, null, requestOptions);
  }

  /**
   * Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @return UpdatedRuleResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedRuleResponse saveRule(String indexName, String objectID, Rule rule) throws AlgoliaRuntimeException {
    return this.saveRule(indexName, objectID, rule, null, null);
  }

  /**
   * (asynchronously) Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedRuleResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedRuleResponse> saveRuleAsync(
    String indexName,
    String objectID,
    Rule rule,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `saveRule`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `saveRule`.");
    }

    if (rule == null) {
      throw new AlgoliaRuntimeException("Parameter `rule` is required when calling `saveRule`.");
    }

    Object bodyObj = rule;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/rules/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedRuleResponse>() {});
  }

  /**
   * (asynchronously) Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<UpdatedRuleResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedRuleResponse> saveRuleAsync(String indexName, String objectID, Rule rule, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.saveRuleAsync(indexName, objectID, rule, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedRuleResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedRuleResponse> saveRuleAsync(String indexName, String objectID, Rule rule, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.saveRuleAsync(indexName, objectID, rule, null, requestOptions);
  }

  /**
   * (asynchronously) Create or update the Rule with the specified objectID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param rule (required)
   * @return CompletableFuture<UpdatedRuleResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedRuleResponse> saveRuleAsync(String indexName, String objectID, Rule rule) throws AlgoliaRuntimeException {
    return this.saveRuleAsync(indexName, objectID, rule, null, null);
  }

  /**
   * Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param clearExistingRules When true, existing Rules are cleared before adding this batch. When
   *     false, existing Rules are kept. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveRules(
    String indexName,
    List<Rule> rules,
    Boolean forwardToReplicas,
    Boolean clearExistingRules,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(saveRulesAsync(indexName, rules, forwardToReplicas, clearExistingRules, requestOptions));
  }

  /**
   * Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param clearExistingRules When true, existing Rules are cleared before adding this batch. When
   *     false, existing Rules are kept. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveRules(String indexName, List<Rule> rules, Boolean forwardToReplicas, Boolean clearExistingRules)
    throws AlgoliaRuntimeException {
    return this.saveRules(indexName, rules, forwardToReplicas, clearExistingRules, null);
  }

  /**
   * Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveRules(String indexName, List<Rule> rules, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.saveRules(indexName, rules, null, null, requestOptions);
  }

  /**
   * Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveRules(String indexName, List<Rule> rules) throws AlgoliaRuntimeException {
    return this.saveRules(indexName, rules, null, null, null);
  }

  /**
   * (asynchronously) Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param clearExistingRules When true, existing Rules are cleared before adding this batch. When
   *     false, existing Rules are kept. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveRulesAsync(
    String indexName,
    List<Rule> rules,
    Boolean forwardToReplicas,
    Boolean clearExistingRules,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `saveRules`.");
    }

    if (rules == null) {
      throw new AlgoliaRuntimeException("Parameter `rules` is required when calling `saveRules`.");
    }

    Object bodyObj = rules;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/rules/batch".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    if (clearExistingRules != null) {
      queryParameters.put("clearExistingRules", parameterToString(clearExistingRules));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param clearExistingRules When true, existing Rules are cleared before adding this batch. When
   *     false, existing Rules are kept. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveRulesAsync(
    String indexName,
    List<Rule> rules,
    Boolean forwardToReplicas,
    Boolean clearExistingRules
  ) throws AlgoliaRuntimeException {
    return this.saveRulesAsync(indexName, rules, forwardToReplicas, clearExistingRules, null);
  }

  /**
   * (asynchronously) Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveRulesAsync(String indexName, List<Rule> rules, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.saveRulesAsync(indexName, rules, null, null, requestOptions);
  }

  /**
   * (asynchronously) Create/update multiple rules objects at once.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param rules (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveRulesAsync(String indexName, List<Rule> rules) throws AlgoliaRuntimeException {
    return this.saveRulesAsync(indexName, rules, null, null, null);
  }

  /**
   * Create a new synonym object or update the existing synonym object with the given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SaveSynonymResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveSynonymResponse saveSynonym(
    String indexName,
    String objectID,
    SynonymHit synonymHit,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(saveSynonymAsync(indexName, objectID, synonymHit, forwardToReplicas, requestOptions));
  }

  /**
   * Create a new synonym object or update the existing synonym object with the given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return SaveSynonymResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveSynonymResponse saveSynonym(String indexName, String objectID, SynonymHit synonymHit, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.saveSynonym(indexName, objectID, synonymHit, forwardToReplicas, null);
  }

  /**
   * Create a new synonym object or update the existing synonym object with the given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SaveSynonymResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveSynonymResponse saveSynonym(String indexName, String objectID, SynonymHit synonymHit, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.saveSynonym(indexName, objectID, synonymHit, null, requestOptions);
  }

  /**
   * Create a new synonym object or update the existing synonym object with the given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @return SaveSynonymResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SaveSynonymResponse saveSynonym(String indexName, String objectID, SynonymHit synonymHit) throws AlgoliaRuntimeException {
    return this.saveSynonym(indexName, objectID, synonymHit, null, null);
  }

  /**
   * (asynchronously) Create a new synonym object or update the existing synonym object with the
   * given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SaveSynonymResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
    String indexName,
    String objectID,
    SynonymHit synonymHit,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `saveSynonym`.");
    }

    if (objectID == null) {
      throw new AlgoliaRuntimeException("Parameter `objectID` is required when calling `saveSynonym`.");
    }

    if (synonymHit == null) {
      throw new AlgoliaRuntimeException("Parameter `synonymHit` is required when calling `saveSynonym`.");
    }

    Object bodyObj = synonymHit;

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/synonyms/{objectID}".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{objectID\\}", this.escapeString(objectID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<SaveSynonymResponse>() {});
  }

  /**
   * (asynchronously) Create a new synonym object or update the existing synonym object with the
   * given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<SaveSynonymResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
    String indexName,
    String objectID,
    SynonymHit synonymHit,
    Boolean forwardToReplicas
  ) throws AlgoliaRuntimeException {
    return this.saveSynonymAsync(indexName, objectID, synonymHit, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Create a new synonym object or update the existing synonym object with the
   * given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SaveSynonymResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
    String indexName,
    String objectID,
    SynonymHit synonymHit,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.saveSynonymAsync(indexName, objectID, synonymHit, null, requestOptions);
  }

  /**
   * (asynchronously) Create a new synonym object or update the existing synonym object with the
   * given object ID.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param objectID Unique identifier of an object. (required)
   * @param synonymHit (required)
   * @return CompletableFuture<SaveSynonymResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SaveSynonymResponse> saveSynonymAsync(String indexName, String objectID, SynonymHit synonymHit)
    throws AlgoliaRuntimeException {
    return this.saveSynonymAsync(indexName, objectID, synonymHit, null, null);
  }

  /**
   * Create/update multiple synonym objects at once, potentially replacing the entire list of
   * synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param replaceExistingSynonyms Replace all synonyms of the index with the ones sent with this
   *     request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveSynonyms(
    String indexName,
    List<SynonymHit> synonymHit,
    Boolean forwardToReplicas,
    Boolean replaceExistingSynonyms,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(saveSynonymsAsync(indexName, synonymHit, forwardToReplicas, replaceExistingSynonyms, requestOptions));
  }

  /**
   * Create/update multiple synonym objects at once, potentially replacing the entire list of
   * synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param replaceExistingSynonyms Replace all synonyms of the index with the ones sent with this
   *     request. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveSynonyms(
    String indexName,
    List<SynonymHit> synonymHit,
    Boolean forwardToReplicas,
    Boolean replaceExistingSynonyms
  ) throws AlgoliaRuntimeException {
    return this.saveSynonyms(indexName, synonymHit, forwardToReplicas, replaceExistingSynonyms, null);
  }

  /**
   * Create/update multiple synonym objects at once, potentially replacing the entire list of
   * synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveSynonyms(String indexName, List<SynonymHit> synonymHit, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.saveSynonyms(indexName, synonymHit, null, null, requestOptions);
  }

  /**
   * Create/update multiple synonym objects at once, potentially replacing the entire list of
   * synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse saveSynonyms(String indexName, List<SynonymHit> synonymHit) throws AlgoliaRuntimeException {
    return this.saveSynonyms(indexName, synonymHit, null, null, null);
  }

  /**
   * (asynchronously) Create/update multiple synonym objects at once, potentially replacing the
   * entire list of synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param replaceExistingSynonyms Replace all synonyms of the index with the ones sent with this
   *     request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveSynonymsAsync(
    String indexName,
    List<SynonymHit> synonymHit,
    Boolean forwardToReplicas,
    Boolean replaceExistingSynonyms,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `saveSynonyms`.");
    }

    if (synonymHit == null) {
      throw new AlgoliaRuntimeException("Parameter `synonymHit` is required when calling `saveSynonyms`.");
    }

    Object bodyObj = synonymHit;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/synonyms/batch".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    if (replaceExistingSynonyms != null) {
      queryParameters.put("replaceExistingSynonyms", parameterToString(replaceExistingSynonyms));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Create/update multiple synonym objects at once, potentially replacing the
   * entire list of synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param replaceExistingSynonyms Replace all synonyms of the index with the ones sent with this
   *     request. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveSynonymsAsync(
    String indexName,
    List<SynonymHit> synonymHit,
    Boolean forwardToReplicas,
    Boolean replaceExistingSynonyms
  ) throws AlgoliaRuntimeException {
    return this.saveSynonymsAsync(indexName, synonymHit, forwardToReplicas, replaceExistingSynonyms, null);
  }

  /**
   * (asynchronously) Create/update multiple synonym objects at once, potentially replacing the
   * entire list of synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveSynonymsAsync(
    String indexName,
    List<SynonymHit> synonymHit,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.saveSynonymsAsync(indexName, synonymHit, null, null, requestOptions);
  }

  /**
   * (asynchronously) Create/update multiple synonym objects at once, potentially replacing the
   * entire list of synonyms if replaceExistingSynonyms is true.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param synonymHit (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> saveSynonymsAsync(String indexName, List<SynonymHit> synonymHit)
    throws AlgoliaRuntimeException {
    return this.saveSynonymsAsync(indexName, synonymHit, null, null, null);
  }

  /**
   * Perform a search operation targeting one or many indices.
   *
   * @param searchMethodParams The `search` requests and strategy. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> SearchResponses<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponses<T> search(SearchMethodParams searchMethodParams, Class<T> innerType, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchAsync(searchMethodParams, innerType, requestOptions));
  }

  /**
   * Perform a search operation targeting one or many indices.
   *
   * @param searchMethodParams The `search` requests and strategy. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> SearchResponses<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponses<T> search(SearchMethodParams searchMethodParams, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.search(searchMethodParams, innerType, null);
  }

  /**
   * (asynchronously) Perform a search operation targeting one or many indices.
   *
   * @param searchMethodParams The `search` requests and strategy. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<SearchResponses<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponses<T>> searchAsync(
    SearchMethodParams searchMethodParams,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (searchMethodParams == null) {
      throw new AlgoliaRuntimeException("Parameter `searchMethodParams` is required when calling `search`.");
    }

    Object bodyObj = searchMethodParams;

    // create path and map variables
    String requestPath = "/1/indexes/*/queries";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, SearchResponses.class, innerType);
  }

  /**
   * (asynchronously) Perform a search operation targeting one or many indices.
   *
   * @param searchMethodParams The `search` requests and strategy. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<SearchResponses<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponses<T>> searchAsync(SearchMethodParams searchMethodParams, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.searchAsync(searchMethodParams, innerType, null);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param searchDictionaryEntriesParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse searchDictionaryEntries(
    DictionaryType dictionaryName,
    SearchDictionaryEntriesParams searchDictionaryEntriesParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchDictionaryEntriesAsync(dictionaryName, searchDictionaryEntriesParams, requestOptions));
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param searchDictionaryEntriesParams (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse searchDictionaryEntries(
    DictionaryType dictionaryName,
    SearchDictionaryEntriesParams searchDictionaryEntriesParams
  ) throws AlgoliaRuntimeException {
    return this.searchDictionaryEntries(dictionaryName, searchDictionaryEntriesParams, null);
  }

  /**
   * (asynchronously) Search the dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param searchDictionaryEntriesParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> searchDictionaryEntriesAsync(
    DictionaryType dictionaryName,
    SearchDictionaryEntriesParams searchDictionaryEntriesParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (dictionaryName == null) {
      throw new AlgoliaRuntimeException("Parameter `dictionaryName` is required when calling `searchDictionaryEntries`.");
    }

    if (searchDictionaryEntriesParams == null) {
      throw new AlgoliaRuntimeException(
        "Parameter `searchDictionaryEntriesParams` is required when calling" + " `searchDictionaryEntries`."
      );
    }

    Object bodyObj = searchDictionaryEntriesParams;

    // create path and map variables
    String requestPath =
      "/1/dictionaries/{dictionaryName}/search".replaceAll("\\{dictionaryName\\}", this.escapeString(dictionaryName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Search the dictionary entries.
   *
   * @param dictionaryName The dictionary to search in. (required)
   * @param searchDictionaryEntriesParams (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> searchDictionaryEntriesAsync(
    DictionaryType dictionaryName,
    SearchDictionaryEntriesParams searchDictionaryEntriesParams
  ) throws AlgoliaRuntimeException {
    return this.searchDictionaryEntriesAsync(dictionaryName, searchDictionaryEntriesParams, null);
  }

  /**
   * Search for values of a given facet, optionally restricting the returned values to those
   * contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param searchForFacetValuesRequest (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchForFacetValuesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchForFacetValuesResponse searchForFacetValues(
    String indexName,
    String facetName,
    SearchForFacetValuesRequest searchForFacetValuesRequest,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchForFacetValuesAsync(indexName, facetName, searchForFacetValuesRequest, requestOptions));
  }

  /**
   * Search for values of a given facet, optionally restricting the returned values to those
   * contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param searchForFacetValuesRequest (optional)
   * @return SearchForFacetValuesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchForFacetValuesResponse searchForFacetValues(
    String indexName,
    String facetName,
    SearchForFacetValuesRequest searchForFacetValuesRequest
  ) throws AlgoliaRuntimeException {
    return this.searchForFacetValues(indexName, facetName, searchForFacetValuesRequest, null);
  }

  /**
   * Search for values of a given facet, optionally restricting the returned values to those
   * contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchForFacetValuesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchForFacetValuesResponse searchForFacetValues(String indexName, String facetName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.searchForFacetValues(indexName, facetName, null, requestOptions);
  }

  /**
   * Search for values of a given facet, optionally restricting the returned values to those
   * contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @return SearchForFacetValuesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchForFacetValuesResponse searchForFacetValues(String indexName, String facetName) throws AlgoliaRuntimeException {
    return this.searchForFacetValues(indexName, facetName, null, null);
  }

  /**
   * (asynchronously) Search for values of a given facet, optionally restricting the returned values
   * to those contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param searchForFacetValuesRequest (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchForFacetValuesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchForFacetValuesResponse> searchForFacetValuesAsync(
    String indexName,
    String facetName,
    SearchForFacetValuesRequest searchForFacetValuesRequest,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `searchForFacetValues`.");
    }

    if (facetName == null) {
      throw new AlgoliaRuntimeException("Parameter `facetName` is required when calling `searchForFacetValues`.");
    }

    Object bodyObj = searchForFacetValuesRequest != null ? searchForFacetValuesRequest : new Object();

    // create path and map variables
    String requestPath =
      "/1/indexes/{indexName}/facets/{facetName}/query".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()))
        .replaceAll("\\{facetName\\}", this.escapeString(facetName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, new TypeReference<SearchForFacetValuesResponse>() {});
  }

  /**
   * (asynchronously) Search for values of a given facet, optionally restricting the returned values
   * to those contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param searchForFacetValuesRequest (optional)
   * @return CompletableFuture<SearchForFacetValuesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchForFacetValuesResponse> searchForFacetValuesAsync(
    String indexName,
    String facetName,
    SearchForFacetValuesRequest searchForFacetValuesRequest
  ) throws AlgoliaRuntimeException {
    return this.searchForFacetValuesAsync(indexName, facetName, searchForFacetValuesRequest, null);
  }

  /**
   * (asynchronously) Search for values of a given facet, optionally restricting the returned values
   * to those contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchForFacetValuesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchForFacetValuesResponse> searchForFacetValuesAsync(
    String indexName,
    String facetName,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.searchForFacetValuesAsync(indexName, facetName, null, requestOptions);
  }

  /**
   * (asynchronously) Search for values of a given facet, optionally restricting the returned values
   * to those contained in objects matching other search criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param facetName The facet name. (required)
   * @return CompletableFuture<SearchForFacetValuesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchForFacetValuesResponse> searchForFacetValuesAsync(String indexName, String facetName)
    throws AlgoliaRuntimeException {
    return this.searchForFacetValuesAsync(indexName, facetName, null, null);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchRulesParams (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchRulesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchRulesResponse searchRules(String indexName, SearchRulesParams searchRulesParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchRulesAsync(indexName, searchRulesParams, requestOptions));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchRulesParams (optional)
   * @return SearchRulesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchRulesResponse searchRules(String indexName, SearchRulesParams searchRulesParams) throws AlgoliaRuntimeException {
    return this.searchRules(indexName, searchRulesParams, null);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchRulesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchRulesResponse searchRules(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.searchRules(indexName, null, requestOptions);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return SearchRulesResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchRulesResponse searchRules(String indexName) throws AlgoliaRuntimeException {
    return this.searchRules(indexName, null, null);
  }

  /**
   * (asynchronously) Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchRulesParams (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchRulesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchRulesResponse> searchRulesAsync(
    String indexName,
    SearchRulesParams searchRulesParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `searchRules`.");
    }

    Object bodyObj = searchRulesParams != null ? searchRulesParams : new Object();

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/rules/search".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, new TypeReference<SearchRulesResponse>() {});
  }

  /**
   * (asynchronously) Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchRulesParams (optional)
   * @return CompletableFuture<SearchRulesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchRulesResponse> searchRulesAsync(String indexName, SearchRulesParams searchRulesParams)
    throws AlgoliaRuntimeException {
    return this.searchRulesAsync(indexName, searchRulesParams, null);
  }

  /**
   * (asynchronously) Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchRulesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchRulesResponse> searchRulesAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.searchRulesAsync(indexName, null, requestOptions);
  }

  /**
   * (asynchronously) Search for rules matching various criteria.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<SearchRulesResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchRulesResponse> searchRulesAsync(String indexName) throws AlgoliaRuntimeException {
    return this.searchRulesAsync(indexName, null, null);
  }

  /**
   * Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> SearchResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponse<T> searchSingleIndex(
    String indexName,
    SearchParams searchParams,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchSingleIndexAsync(indexName, searchParams, innerType, requestOptions));
  }

  /**
   * Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> SearchResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponse<T> searchSingleIndex(String indexName, SearchParams searchParams, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.searchSingleIndex(indexName, searchParams, innerType, null);
  }

  /**
   * Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> SearchResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponse<T> searchSingleIndex(String indexName, Class<T> innerType, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.searchSingleIndex(indexName, null, innerType, requestOptions);
  }

  /**
   * Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> SearchResponse<T>
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> SearchResponse<T> searchSingleIndex(String indexName, Class<T> innerType) throws AlgoliaRuntimeException {
    return this.searchSingleIndex(indexName, null, innerType, null);
  }

  /**
   * (asynchronously) Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<SearchResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponse<T>> searchSingleIndexAsync(
    String indexName,
    SearchParams searchParams,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `searchSingleIndex`.");
    }

    Object bodyObj = searchParams != null ? searchParams : new Object();

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/query".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, SearchResponse.class, innerType);
  }

  /**
   * (asynchronously) Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param searchParams (optional)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<SearchResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponse<T>> searchSingleIndexAsync(String indexName, SearchParams searchParams, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.searchSingleIndexAsync(indexName, searchParams, innerType, null);
  }

  /**
   * (asynchronously) Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return <T> CompletableFuture<SearchResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponse<T>> searchSingleIndexAsync(
    String indexName,
    Class<T> innerType,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.searchSingleIndexAsync(indexName, null, innerType, requestOptions);
  }

  /**
   * (asynchronously) Perform a search operation targeting one specific index.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @return <T> CompletableFuture<SearchResponse<T>> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public <T> CompletableFuture<SearchResponse<T>> searchSingleIndexAsync(String indexName, Class<T> innerType)
    throws AlgoliaRuntimeException {
    return this.searchSingleIndexAsync(indexName, null, innerType, null);
  }

  /**
   * Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param type Only search for specific types of synonyms. (optional)
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional, default to 0)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param searchSynonymsParams The body of the the `searchSynonyms` method. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchSynonymsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchSynonymsResponse searchSynonyms(
    String indexName,
    SynonymType type,
    Integer page,
    Integer hitsPerPage,
    SearchSynonymsParams searchSynonymsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchSynonymsAsync(indexName, type, page, hitsPerPage, searchSynonymsParams, requestOptions));
  }

  /**
   * Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param type Only search for specific types of synonyms. (optional)
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional, default to 0)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param searchSynonymsParams The body of the the `searchSynonyms` method. (optional)
   * @return SearchSynonymsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchSynonymsResponse searchSynonyms(
    String indexName,
    SynonymType type,
    Integer page,
    Integer hitsPerPage,
    SearchSynonymsParams searchSynonymsParams
  ) throws AlgoliaRuntimeException {
    return this.searchSynonyms(indexName, type, page, hitsPerPage, searchSynonymsParams, null);
  }

  /**
   * Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchSynonymsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchSynonymsResponse searchSynonyms(String indexName, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.searchSynonyms(indexName, null, null, null, null, requestOptions);
  }

  /**
   * Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return SearchSynonymsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchSynonymsResponse searchSynonyms(String indexName) throws AlgoliaRuntimeException {
    return this.searchSynonyms(indexName, null, null, null, null, null);
  }

  /**
   * (asynchronously) Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param type Only search for specific types of synonyms. (optional)
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional, default to 0)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param searchSynonymsParams The body of the the `searchSynonyms` method. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchSynonymsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchSynonymsResponse> searchSynonymsAsync(
    String indexName,
    SynonymType type,
    Integer page,
    Integer hitsPerPage,
    SearchSynonymsParams searchSynonymsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `searchSynonyms`.");
    }

    Object bodyObj = searchSynonymsParams != null ? searchSynonymsParams : new Object();

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/synonyms/search".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (type != null) {
      queryParameters.put("type", parameterToString(type));
    }

    if (page != null) {
      queryParameters.put("page", parameterToString(page));
    }

    if (hitsPerPage != null) {
      queryParameters.put("hitsPerPage", parameterToString(hitsPerPage));
    }

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, new TypeReference<SearchSynonymsResponse>() {});
  }

  /**
   * (asynchronously) Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param type Only search for specific types of synonyms. (optional)
   * @param page Requested page (zero-based). When specified, will retrieve a specific page; the
   *     page size is implicitly set to 100. When null, will retrieve all indices (no pagination).
   *     (optional, default to 0)
   * @param hitsPerPage Maximum number of objects to retrieve. (optional, default to 100)
   * @param searchSynonymsParams The body of the the `searchSynonyms` method. (optional)
   * @return CompletableFuture<SearchSynonymsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchSynonymsResponse> searchSynonymsAsync(
    String indexName,
    SynonymType type,
    Integer page,
    Integer hitsPerPage,
    SearchSynonymsParams searchSynonymsParams
  ) throws AlgoliaRuntimeException {
    return this.searchSynonymsAsync(indexName, type, page, hitsPerPage, searchSynonymsParams, null);
  }

  /**
   * (asynchronously) Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchSynonymsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchSynonymsResponse> searchSynonymsAsync(String indexName, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.searchSynonymsAsync(indexName, null, null, null, null, requestOptions);
  }

  /**
   * (asynchronously) Search or browse all synonyms, optionally filtering them by type.
   *
   * @param indexName The index in which to perform the request. (required)
   * @return CompletableFuture<SearchSynonymsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchSynonymsResponse> searchSynonymsAsync(String indexName) throws AlgoliaRuntimeException {
    return this.searchSynonymsAsync(indexName, null, null, null, null, null);
  }

  /**
   * Search for userIDs. The data returned will usually be a few seconds behind real time, because
   * userID usage may take up to a few seconds propagate to the different clusters. To keep updates
   * moving quickly, the index of userIDs isn't built synchronously with the mapping. Instead, the
   * index is built once every 12h, at the same time as the update of userID usage. For example,
   * when you perform a modification like adding or moving a userID, the search will report an
   * outdated value until the next rebuild of the mapping, which takes place every 12h. Upon
   * success, the response is 200 OK and contains the following userIDs data.
   *
   * @param searchUserIdsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return SearchUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchUserIdsResponse searchUserIds(SearchUserIdsParams searchUserIdsParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(searchUserIdsAsync(searchUserIdsParams, requestOptions));
  }

  /**
   * Search for userIDs. The data returned will usually be a few seconds behind real time, because
   * userID usage may take up to a few seconds propagate to the different clusters. To keep updates
   * moving quickly, the index of userIDs isn't built synchronously with the mapping. Instead, the
   * index is built once every 12h, at the same time as the update of userID usage. For example,
   * when you perform a modification like adding or moving a userID, the search will report an
   * outdated value until the next rebuild of the mapping, which takes place every 12h. Upon
   * success, the response is 200 OK and contains the following userIDs data.
   *
   * @param searchUserIdsParams (required)
   * @return SearchUserIdsResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SearchUserIdsResponse searchUserIds(SearchUserIdsParams searchUserIdsParams) throws AlgoliaRuntimeException {
    return this.searchUserIds(searchUserIdsParams, null);
  }

  /**
   * (asynchronously) Search for userIDs. The data returned will usually be a few seconds behind
   * real time, because userID usage may take up to a few seconds propagate to the different
   * clusters. To keep updates moving quickly, the index of userIDs isn&#39;t built synchronously
   * with the mapping. Instead, the index is built once every 12h, at the same time as the update of
   * userID usage. For example, when you perform a modification like adding or moving a userID, the
   * search will report an outdated value until the next rebuild of the mapping, which takes place
   * every 12h. Upon success, the response is 200 OK and contains the following userIDs data.
   *
   * @param searchUserIdsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<SearchUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchUserIdsResponse> searchUserIdsAsync(
    SearchUserIdsParams searchUserIdsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (searchUserIdsParams == null) {
      throw new AlgoliaRuntimeException("Parameter `searchUserIdsParams` is required when calling `searchUserIds`.");
    }

    Object bodyObj = searchUserIdsParams;

    // create path and map variables
    String requestPath = "/1/clusters/mapping/search";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, true);
    return this.executeAsync(call, new TypeReference<SearchUserIdsResponse>() {});
  }

  /**
   * (asynchronously) Search for userIDs. The data returned will usually be a few seconds behind
   * real time, because userID usage may take up to a few seconds propagate to the different
   * clusters. To keep updates moving quickly, the index of userIDs isn&#39;t built synchronously
   * with the mapping. Instead, the index is built once every 12h, at the same time as the update of
   * userID usage. For example, when you perform a modification like adding or moving a userID, the
   * search will report an outdated value until the next rebuild of the mapping, which takes place
   * every 12h. Upon success, the response is 200 OK and contains the following userIDs data.
   *
   * @param searchUserIdsParams (required)
   * @return CompletableFuture<SearchUserIdsResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SearchUserIdsResponse> searchUserIdsAsync(SearchUserIdsParams searchUserIdsParams)
    throws AlgoliaRuntimeException {
    return this.searchUserIdsAsync(searchUserIdsParams, null);
  }

  /**
   * Set dictionaries settings.
   *
   * @param dictionarySettingsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setDictionarySettings(DictionarySettingsParams dictionarySettingsParams, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(setDictionarySettingsAsync(dictionarySettingsParams, requestOptions));
  }

  /**
   * Set dictionaries settings.
   *
   * @param dictionarySettingsParams (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setDictionarySettings(DictionarySettingsParams dictionarySettingsParams) throws AlgoliaRuntimeException {
    return this.setDictionarySettings(dictionarySettingsParams, null);
  }

  /**
   * (asynchronously) Set dictionaries settings.
   *
   * @param dictionarySettingsParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setDictionarySettingsAsync(
    DictionarySettingsParams dictionarySettingsParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (dictionarySettingsParams == null) {
      throw new AlgoliaRuntimeException("Parameter `dictionarySettingsParams` is required when calling `setDictionarySettings`.");
    }

    Object bodyObj = dictionarySettingsParams;

    // create path and map variables
    String requestPath = "/1/dictionaries/*/settings";

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Set dictionaries settings.
   *
   * @param dictionarySettingsParams (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setDictionarySettingsAsync(DictionarySettingsParams dictionarySettingsParams)
    throws AlgoliaRuntimeException {
    return this.setDictionarySettingsAsync(dictionarySettingsParams, null);
  }

  /**
   * Update settings of an index. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying null for a setting resets it to its default value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setSettings(
    String indexName,
    IndexSettings indexSettings,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(setSettingsAsync(indexName, indexSettings, forwardToReplicas, requestOptions));
  }

  /**
   * Update settings of an index. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying null for a setting resets it to its default value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setSettings(String indexName, IndexSettings indexSettings, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.setSettings(indexName, indexSettings, forwardToReplicas, null);
  }

  /**
   * Update settings of an index. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying null for a setting resets it to its default value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setSettings(String indexName, IndexSettings indexSettings, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return this.setSettings(indexName, indexSettings, null, requestOptions);
  }

  /**
   * Update settings of an index. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying null for a setting resets it to its default value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @return UpdatedAtResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdatedAtResponse setSettings(String indexName, IndexSettings indexSettings) throws AlgoliaRuntimeException {
    return this.setSettings(indexName, indexSettings, null, null);
  }

  /**
   * (asynchronously) Update settings of an index. Only specified settings are overridden;
   * unspecified settings are left unchanged. Specifying null for a setting resets it to its default
   * value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setSettingsAsync(
    String indexName,
    IndexSettings indexSettings,
    Boolean forwardToReplicas,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    if (indexName == null) {
      throw new AlgoliaRuntimeException("Parameter `indexName` is required when calling `setSettings`.");
    }

    if (indexSettings == null) {
      throw new AlgoliaRuntimeException("Parameter `indexSettings` is required when calling `setSettings`.");
    }

    Object bodyObj = indexSettings;

    // create path and map variables
    String requestPath = "/1/indexes/{indexName}/settings".replaceAll("\\{indexName\\}", this.escapeString(indexName.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    if (forwardToReplicas != null) {
      queryParameters.put("forwardToReplicas", parameterToString(forwardToReplicas));
    }

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdatedAtResponse>() {});
  }

  /**
   * (asynchronously) Update settings of an index. Only specified settings are overridden;
   * unspecified settings are left unchanged. Specifying null for a setting resets it to its default
   * value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param forwardToReplicas When true, changes are also propagated to replicas of the given
   *     indexName. (optional)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setSettingsAsync(String indexName, IndexSettings indexSettings, Boolean forwardToReplicas)
    throws AlgoliaRuntimeException {
    return this.setSettingsAsync(indexName, indexSettings, forwardToReplicas, null);
  }

  /**
   * (asynchronously) Update settings of an index. Only specified settings are overridden;
   * unspecified settings are left unchanged. Specifying null for a setting resets it to its default
   * value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setSettingsAsync(
    String indexName,
    IndexSettings indexSettings,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return this.setSettingsAsync(indexName, indexSettings, null, requestOptions);
  }

  /**
   * (asynchronously) Update settings of an index. Only specified settings are overridden;
   * unspecified settings are left unchanged. Specifying null for a setting resets it to its default
   * value.
   *
   * @param indexName The index in which to perform the request. (required)
   * @param indexSettings (required)
   * @return CompletableFuture<UpdatedAtResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdatedAtResponse> setSettingsAsync(String indexName, IndexSettings indexSettings)
    throws AlgoliaRuntimeException {
    return this.setSettingsAsync(indexName, indexSettings, null, null);
  }

  /**
   * Replace every permission of an existing API key.
   *
   * @param key API Key string. (required)
   * @param apiKey (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return UpdateApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdateApiKeyResponse updateApiKey(String key, ApiKey apiKey, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(updateApiKeyAsync(key, apiKey, requestOptions));
  }

  /**
   * Replace every permission of an existing API key.
   *
   * @param key API Key string. (required)
   * @param apiKey (required)
   * @return UpdateApiKeyResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public UpdateApiKeyResponse updateApiKey(String key, ApiKey apiKey) throws AlgoliaRuntimeException {
    return this.updateApiKey(key, apiKey, null);
  }

  /**
   * (asynchronously) Replace every permission of an existing API key.
   *
   * @param key API Key string. (required)
   * @param apiKey (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<UpdateApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdateApiKeyResponse> updateApiKeyAsync(String key, ApiKey apiKey, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (key == null) {
      throw new AlgoliaRuntimeException("Parameter `key` is required when calling `updateApiKey`.");
    }

    if (apiKey == null) {
      throw new AlgoliaRuntimeException("Parameter `apiKey` is required when calling `updateApiKey`.");
    }

    Object bodyObj = apiKey;

    // create path and map variables
    String requestPath = "/1/keys/{key}".replaceAll("\\{key\\}", this.escapeString(key.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "PUT", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<UpdateApiKeyResponse>() {});
  }

  /**
   * (asynchronously) Replace every permission of an existing API key.
   *
   * @param key API Key string. (required)
   * @param apiKey (required)
   * @return CompletableFuture<UpdateApiKeyResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<UpdateApiKeyResponse> updateApiKeyAsync(String key, ApiKey apiKey) throws AlgoliaRuntimeException {
    return this.updateApiKeyAsync(key, apiKey, null);
  }

  /**
   * Helper: Wait for a task to complete with `indexName` and `taskID`.
   *
   * @summary Wait for a task to complete.
   * @param indexName The `indexName` where the operation was performed.
   * @param taskID The `taskID` returned in the method response.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public void waitForTask(String indexName, Long taskID, int maxRetries, IntUnaryOperator timeout, RequestOptions requestOptions) {
    TaskUtils.retryUntil(
      () -> {
        return this.getTask(indexName, taskID, requestOptions);
      },
      (GetTaskResponse task) -> {
        return task.getStatus() == TaskStatus.PUBLISHED;
      },
      maxRetries,
      timeout
    );
  }

  /**
   * Helper: Wait for a task to complete with `indexName` and `taskID`.
   *
   * @summary Wait for a task to complete.
   * @param indexName The `indexName` where the operation was performed.
   * @param taskID The `taskID` returned in the method response.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public void waitForTask(String indexName, Long taskID, RequestOptions requestOptions) {
    this.waitForTask(indexName, taskID, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, requestOptions);
  }

  /**
   * Helper: Wait for a task to complete with `indexName` and `taskID`.
   *
   * @summary Wait for a task to complete.
   * @param indexName The `indexName` where the operation was performed.
   * @param taskID The `taskID` returned in the method response.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   */
  public void waitForTask(String indexName, Long taskID, int maxRetries, IntUnaryOperator timeout) {
    this.waitForTask(indexName, taskID, maxRetries, timeout, null);
  }

  /**
   * Helper: Wait for a task to complete with `indexName` and `taskID`.
   *
   * @summary Wait for a task to complete.
   * @param indexName The `indexName` where the operation was performed.
   * @param taskID The `taskID` returned in the method response.
   */
  public void waitForTask(String indexName, Long taskID) {
    this.waitForTask(indexName, taskID, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, null);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`.
   * @param key The `key` that has been added, deleted or updated.
   * @param apiKey Necessary to know if an `update` operation has been processed, compare fields of
   *     the response with it.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public GetApiKeyResponse waitForApiKey(
    ApiKeyOperation operation,
    String key,
    ApiKey apiKey,
    int maxRetries,
    IntUnaryOperator timeout,
    RequestOptions requestOptions
  ) {
    if (operation == ApiKeyOperation.UPDATE) {
      if (apiKey == null) {
        throw new AlgoliaRetryException("`apiKey` is required when waiting for an `update` operation.");
      }

      // when updating an api key, we poll the api until we receive a different key
      return TaskUtils.retryUntil(
        () -> {
          return this.getApiKey(key, requestOptions);
        },
        (GetApiKeyResponse respKey) -> {
          // we need to convert to an ApiKey object to use the `equals` method
          ApiKey sameType = new ApiKey()
            .setAcl(respKey.getAcl())
            .setDescription(respKey.getDescription())
            .setIndexes(respKey.getIndexes())
            .setMaxHitsPerQuery(respKey.getMaxHitsPerQuery())
            .setMaxQueriesPerIPPerHour(respKey.getMaxQueriesPerIPPerHour())
            .setQueryParameters(respKey.getQueryParameters())
            .setReferers(respKey.getReferers())
            .setValidity(respKey.getValidity());

          return apiKey.equals(sameType);
        },
        maxRetries,
        timeout
      );
    }

    // bypass lambda restriction to modify final object
    final GetApiKeyResponse[] addedKey = new GetApiKeyResponse[] { null };

    // check the status of the getApiKey method
    TaskUtils.retryUntil(
      () -> {
        try {
          addedKey[0] = this.getApiKey(key, requestOptions);
          // magic number to signify we found the key
          return -2;
        } catch (AlgoliaApiException e) {
          return e.getHttpErrorCode();
        }
      },
      (Integer status) -> {
        switch (operation) {
          case ADD:
            // stop either when the key is created or when we don't receive 404
            return status == -2 || status != 404;
          case DELETE:
            // stop when the key is not found
            return status == 404;
          default:
            // continue
            return false;
        }
      },
      maxRetries,
      timeout
    );

    return addedKey[0];
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`. (ADD or DELETE only)
   * @param key The `key` that has been added, deleted or updated.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public GetApiKeyResponse waitForApiKey(
    ApiKeyOperation operation,
    String key,
    int maxRetries,
    IntUnaryOperator timeout,
    RequestOptions requestOptions
  ) {
    return this.waitForApiKey(operation, key, null, maxRetries, timeout, requestOptions);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`.
   * @param key The `key` that has been added, deleted or updated.
   * @param apiKey Necessary to know if an `update` operation has been processed, compare fields of
   *     the response with it.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key, ApiKey apiKey, RequestOptions requestOptions) {
    return this.waitForApiKey(operation, key, apiKey, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, requestOptions);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`. (ADD or DELETE only)
   * @param key The `key` that has been added, deleted or updated.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key, RequestOptions requestOptions) {
    return this.waitForApiKey(operation, key, null, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, requestOptions);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`.
   * @param key The `key` that has been added, deleted or updated.
   * @param apiKey Necessary to know if an `update` operation has been processed, compare fields of
   *     the response with it.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key, ApiKey apiKey, int maxRetries, IntUnaryOperator timeout) {
    return this.waitForApiKey(operation, key, apiKey, maxRetries, timeout, null);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`. (ADD or DELETE only)
   * @param key The `key` that has been added, deleted or updated.
   * @param maxRetries The maximum number of retry. 50 by default. (optional)
   * @param timeout The function to decide how long to wait between retries. min(retries * 200,
   *     5000) by default. (optional)
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key, int maxRetries, IntUnaryOperator timeout) {
    return this.waitForApiKey(operation, key, null, maxRetries, timeout, null);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`.
   * @param key The `key` that has been added, deleted or updated.
   * @param apiKey Necessary to know if an `update` operation has been processed, compare fields of
   *     the response with it.
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key, ApiKey apiKey) {
    return this.waitForApiKey(operation, key, apiKey, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, null);
  }

  /**
   * Helper: Wait for an API key to be added, updated or deleted based on a given `operation`.
   *
   * @summary Wait for an API key task to be processed.
   * @param operation The `operation` that was done on a `key`. (ADD or DELETE only)
   * @param key The `key` that has been added, deleted or updated.
   */
  public GetApiKeyResponse waitForApiKey(ApiKeyOperation operation, String key) {
    return this.waitForApiKey(operation, key, null, TaskUtils.DEFAULT_MAX_RETRIES, TaskUtils.DEFAULT_TIMEOUT, null);
  }

  /**
   * Helper: Returns an iterator on top of the `browse` method.
   *
   * @summary Returns an iterator on `browse`.
   * @param indexName The index in which to perform the request.
   * @param params The `browse` parameters.
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public <T> Iterable<T> browseObjects(String indexName, BrowseParamsObject params, Class<T> innerType, RequestOptions requestOptions) {
    final Holder<String> currentCursor = new Holder<>();

    return AlgoliaIterableHelper.createIterable(
      () -> {
        BrowseResponse<T> response = this.browse(indexName, BrowseParams.of(params), innerType, requestOptions);
        params.setCursor(response.getCursor());
        currentCursor.value = response.getCursor();
        return response.getHits().iterator();
      },
      () -> {
        return currentCursor.value != null;
      }
    );
  }

  /**
   * Helper: Returns an iterator on top of the `browse` method.
   *
   * @summary Returns an iterator on `browse`.
   * @param indexName The index in which to perform the request.
   * @param params The `browse` parameters.
   * @param innerType The class held by the index, could be your custom class or {@link Object}.
   */
  public <T> Iterable<T> browseObjects(String indexName, BrowseParamsObject params, Class<T> innerType) {
    return browseObjects(indexName, params, innerType, null);
  }

  /**
   * Helper: Returns an iterator on top of the `searchSynonyms` method.
   *
   * @summary Returns an iterator on `searchSynonyms`.
   * @param indexName The index in which to perform the request.
   * @param type The synonym type. (optional)
   * @param params The `searchSynonyms` parameters. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public Iterable<SynonymHit> browseSynonyms(
    String indexName,
    SynonymType type,
    SearchSynonymsParams params,
    RequestOptions requestOptions
  ) {
    final Holder<Integer> currentPage = new Holder<>(0);
    final int hitsPerPage = 1000;

    return AlgoliaIterableHelper.createIterable(
      () -> {
        SearchSynonymsResponse response = this.searchSynonyms(indexName, type, currentPage.value, hitsPerPage, params, requestOptions);
        currentPage.value = response.getNbHits() < hitsPerPage ? null : currentPage.value + 1;
        return response.getHits().iterator();
      },
      () -> {
        return currentPage.value != null;
      }
    );
  }

  /**
   * Helper: Returns an iterator on top of the `searchSynonyms` method.
   *
   * @summary Returns an iterator on `searchSynonyms`.
   * @param indexName The index in which to perform the request.
   * @param type The synonym type. (optional)
   * @param params The `searchSynonyms` parameters .(optional)
   */
  public Iterable<SynonymHit> browseSynonyms(String indexName, SynonymType type, SearchSynonymsParams params) {
    return browseSynonyms(indexName, type, params, null);
  }

  /**
   * Helper: Returns an iterator on top of the `searchSynonyms` method.
   *
   * @summary Returns an iterator on `searchSynonyms`.
   * @param indexName The index in which to perform the request.
   */
  public Iterable<SynonymHit> browseSynonyms(String indexName) {
    return browseSynonyms(indexName, null, null, null);
  }

  /**
   * Helper: Returns an iterator on top of the `searchRules` method.
   *
   * @summary Returns an iterator on `searchRules`.
   * @param indexName The index in which to perform the request.
   * @param params The `searchRules` parameters. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions. (optional)
   */
  public Iterable<Rule> browseRules(String indexName, SearchRulesParams params, RequestOptions requestOptions) {
    final Holder<Integer> currentPage = new Holder<>(0);
    final int hitsPerPage = 1000;
    params.setHitsPerPage(hitsPerPage);

    return AlgoliaIterableHelper.createIterable(
      () -> {
        SearchRulesResponse response = this.searchRules(indexName, params.setPage(currentPage.value), requestOptions);
        currentPage.value = response.getNbHits() < hitsPerPage ? null : currentPage.value + 1;
        return response.getHits().iterator();
      },
      () -> {
        return currentPage.value != null;
      }
    );
  }

  /**
   * Helper: Returns an iterator on top of the `searchRules` method.
   *
   * @summary Returns an iterator on `searchRules`.
   * @param indexName The index in which to perform the request.
   * @param params The `searchRules` parameters. (optional)
   */
  public Iterable<Rule> browseRules(String indexName, SearchRulesParams params) {
    return browseRules(indexName, params, null);
  }

  /**
   * Helper: Returns an iterator on top of the `searchRules` method.
   *
   * @summary Returns an iterator on `searchRules`.
   * @param indexName The index in which to perform the request.
   */
  public Iterable<Rule> browseRules(String indexName) {
    return browseRules(indexName, null, null);
  }
}
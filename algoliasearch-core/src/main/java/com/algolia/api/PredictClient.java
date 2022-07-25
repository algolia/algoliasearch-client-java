// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.api;

import com.algolia.ApiClient;
import com.algolia.exceptions.*;
import com.algolia.model.predict.*;
import com.algolia.utils.*;
import com.algolia.utils.retry.CallType;
import com.algolia.utils.retry.StatefulHost;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import okhttp3.Call;

public class PredictClient extends ApiClient {

  private static final String[] allowedRegions = { "ue", "ew" };

  public PredictClient(String appId, String apiKey, String region) {
    this(appId, apiKey, region, null);
  }

  public PredictClient(String appId, String apiKey, String region, ClientOptions options) {
    super(appId, apiKey, "Predict", "4.4.0-SNAPSHOT", options);
    if (options != null && options.getHosts() != null) {
      this.setHosts(options.getHosts());
    } else {
      this.setHosts(getDefaultHosts(region));
    }
    this.setConnectTimeout(2000);
    this.setReadTimeout(5000);
    this.setWriteTimeout(30000);
  }

  private static List<StatefulHost> getDefaultHosts(String region) throws AlgoliaRuntimeException {
    List<StatefulHost> hosts = new ArrayList<StatefulHost>();

    boolean found = false;
    if (region == null) {
      throw new AlgoliaRuntimeException("`region` is missing");
    }
    for (String allowed : allowedRegions) {
      if (allowed.equals(region)) {
        found = true;
        break;
      }
    }
    if (!found) {
      throw new AlgoliaRuntimeException("`region` must be one of the following: ue, ew");
    }

    String url = "predict-api-432xa6wemq-{region}.a.run.app".replace("{region}", region);

    hosts.add(new StatefulHost(url, "https", EnumSet.of(CallType.READ, CallType.WRITE)));
    return hosts;
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
   * Get predictions, properties (raw, computed or custom) and segments (computed or custom) for a
   * user profile.
   *
   * @param userID User ID for authenticated users or cookie ID for non-authenticated repeated users
   *     (visitors). (required)
   * @param params (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return FetchUserProfileResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public FetchUserProfileResponse fetchUserProfile(String userID, Params params, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(fetchUserProfileAsync(userID, params, requestOptions));
  }

  /**
   * Get predictions, properties (raw, computed or custom) and segments (computed or custom) for a
   * user profile.
   *
   * @param userID User ID for authenticated users or cookie ID for non-authenticated repeated users
   *     (visitors). (required)
   * @param params (required)
   * @return FetchUserProfileResponse
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public FetchUserProfileResponse fetchUserProfile(String userID, Params params) throws AlgoliaRuntimeException {
    return this.fetchUserProfile(userID, params, null);
  }

  /**
   * (asynchronously) Get predictions, properties (raw, computed or custom) and segments (computed
   * or custom) for a user profile.
   *
   * @param userID User ID for authenticated users or cookie ID for non-authenticated repeated users
   *     (visitors). (required)
   * @param params (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @return CompletableFuture<FetchUserProfileResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<FetchUserProfileResponse> fetchUserProfileAsync(String userID, Params params, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    if (userID == null) {
      throw new AlgoliaRuntimeException("Parameter `userID` is required when calling `fetchUserProfile`.");
    }

    if (params == null) {
      throw new AlgoliaRuntimeException("Parameter `params` is required when calling `fetchUserProfile`.");
    }

    Object bodyObj = params;

    // create path and map variables
    String requestPath = "/1/users/{userID}/fetch".replaceAll("\\{userID\\}", this.escapeString(userID.toString()));

    Map<String, Object> queryParameters = new HashMap<String, Object>();
    Map<String, String> headers = new HashMap<String, String>();

    Call call = this.buildCall(requestPath, "POST", queryParameters, bodyObj, headers, requestOptions, false);
    return this.executeAsync(call, new TypeReference<FetchUserProfileResponse>() {});
  }

  /**
   * (asynchronously) Get predictions, properties (raw, computed or custom) and segments (computed
   * or custom) for a user profile.
   *
   * @param userID User ID for authenticated users or cookie ID for non-authenticated repeated users
   *     (visitors). (required)
   * @param params (required)
   * @return CompletableFuture<FetchUserProfileResponse> The awaitable future
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<FetchUserProfileResponse> fetchUserProfileAsync(String userID, Params params) throws AlgoliaRuntimeException {
    return this.fetchUserProfileAsync(userID, params, null);
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
}

// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.api;

import com.algolia.ApiClient;
import com.algolia.config.*;
import com.algolia.config.ClientOptions;
import com.algolia.exceptions.*;
import com.algolia.model.personalization.*;
import com.algolia.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class PersonalizationClient extends ApiClient {

  private static final String[] allowedRegions = { "eu", "us" };

  public PersonalizationClient(String appId, String apiKey, String region) {
    this(appId, apiKey, region, null);
  }

  public PersonalizationClient(String appId, String apiKey, String region, ClientOptions options) {
    super(appId, apiKey, "Personalization", options, getDefaultHosts(region));
  }

  private static List<Host> getDefaultHosts(String region) throws AlgoliaRuntimeException {
    List<Host> hosts = new ArrayList<>();

    boolean found = false;
    if (region != null) {
      for (String allowed : allowedRegions) {
        if (allowed.equals(region)) {
          found = true;
          break;
        }
      }
    }

    if (region == null || !found) {
      throw new AlgoliaRuntimeException("`region` is required and must be one of the following: eu, us");
    }

    String url = "personalization.{region}.algolia.com".replace("{region}", region);

    hosts.add(new Host(url, EnumSet.of(CallType.READ, CallType.WRITE)));
    return hosts;
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customDelete(@Nonnull String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(customDeleteAsync(path, parameters, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customDelete(@Nonnull String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.customDelete(path, parameters, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customDelete(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customDelete(path, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customDelete(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customDelete(path, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customDeleteAsync(@Nonnull String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(path, "Parameter `path` is required when calling `customDelete`.");

    HttpRequest request = HttpRequest.builder().setPathEncoded("/{path}", path).setMethod("DELETE").addQueryParameters(parameters).build();
    return executeAsync(request, requestOptions, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customDeleteAsync(@Nonnull String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.customDeleteAsync(path, parameters, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customDeleteAsync(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customDeleteAsync(path, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customDeleteAsync(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customDeleteAsync(path, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customGet(@Nonnull String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(customGetAsync(path, parameters, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customGet(@Nonnull String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.customGet(path, parameters, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customGet(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customGet(path, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customGet(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customGet(path, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customGetAsync(@Nonnull String path, Map<String, Object> parameters, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(path, "Parameter `path` is required when calling `customGet`.");

    HttpRequest request = HttpRequest.builder().setPathEncoded("/{path}", path).setMethod("GET").addQueryParameters(parameters).build();
    return executeAsync(request, requestOptions, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customGetAsync(@Nonnull String path, Map<String, Object> parameters) throws AlgoliaRuntimeException {
    return this.customGetAsync(path, parameters, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customGetAsync(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customGetAsync(path, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customGetAsync(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customGetAsync(path, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPost(@Nonnull String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(customPostAsync(path, parameters, body, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPost(@Nonnull String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.customPost(path, parameters, body, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPost(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customPost(path, null, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPost(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customPost(path, null, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPostAsync(
    @Nonnull String path,
    Map<String, Object> parameters,
    Object body,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    Parameters.requireNonNull(path, "Parameter `path` is required when calling `customPost`.");

    HttpRequest request = HttpRequest.builder()
      .setPathEncoded("/{path}", path)
      .setMethod("POST")
      .setBody(body)
      .addQueryParameters(parameters)
      .build();
    return executeAsync(request, requestOptions, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPostAsync(@Nonnull String path, Map<String, Object> parameters, Object body)
    throws AlgoliaRuntimeException {
    return this.customPostAsync(path, parameters, body, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPostAsync(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customPostAsync(path, null, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPostAsync(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customPostAsync(path, null, null, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPut(@Nonnull String path, Map<String, Object> parameters, Object body, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(customPutAsync(path, parameters, body, requestOptions));
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPut(@Nonnull String path, Map<String, Object> parameters, Object body) throws AlgoliaRuntimeException {
    return this.customPut(path, parameters, body, null);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPut(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customPut(path, null, null, requestOptions);
  }

  /**
   * This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public Object customPut(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customPut(path, null, null, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPutAsync(
    @Nonnull String path,
    Map<String, Object> parameters,
    Object body,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    Parameters.requireNonNull(path, "Parameter `path` is required when calling `customPut`.");

    HttpRequest request = HttpRequest.builder()
      .setPathEncoded("/{path}", path)
      .setMethod("PUT")
      .setBody(body)
      .addQueryParameters(parameters)
      .build();
    return executeAsync(request, requestOptions, new TypeReference<Object>() {});
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param parameters Query parameters to apply to the current query. (optional)
   * @param body Parameters to send with the custom request. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPutAsync(@Nonnull String path, Map<String, Object> parameters, Object body)
    throws AlgoliaRuntimeException {
    return this.customPutAsync(path, parameters, body, null);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPutAsync(@Nonnull String path, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.customPutAsync(path, null, null, requestOptions);
  }

  /**
   * (asynchronously) This method allow you to send requests to the Algolia REST API.
   *
   * @param path Path of the endpoint, anything after \"/1\" must be specified. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<Object> customPutAsync(@Nonnull String path) throws AlgoliaRuntimeException {
    return this.customPutAsync(path, null, null, null);
  }

  /**
   * Deletes a user profile. The response includes a date and time when the user profile can safely
   * be considered deleted.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteUserProfileResponse deleteUserProfile(@Nonnull String userToken, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteUserProfileAsync(userToken, requestOptions));
  }

  /**
   * Deletes a user profile. The response includes a date and time when the user profile can safely
   * be considered deleted.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public DeleteUserProfileResponse deleteUserProfile(@Nonnull String userToken) throws AlgoliaRuntimeException {
    return this.deleteUserProfile(userToken, null);
  }

  /**
   * (asynchronously) Deletes a user profile. The response includes a date and time when the user
   * profile can safely be considered deleted.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteUserProfileResponse> deleteUserProfileAsync(@Nonnull String userToken, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(userToken, "Parameter `userToken` is required when calling `deleteUserProfile`.");

    HttpRequest request = HttpRequest.builder().setPath("/1/profiles/{userToken}", userToken).setMethod("DELETE").build();
    return executeAsync(request, requestOptions, new TypeReference<DeleteUserProfileResponse>() {});
  }

  /**
   * (asynchronously) Deletes a user profile. The response includes a date and time when the user
   * profile can safely be considered deleted.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<DeleteUserProfileResponse> deleteUserProfileAsync(@Nonnull String userToken) throws AlgoliaRuntimeException {
    return this.deleteUserProfileAsync(userToken, null);
  }

  /**
   * Retrieves the current personalization strategy.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public PersonalizationStrategyParams getPersonalizationStrategy(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getPersonalizationStrategyAsync(requestOptions));
  }

  /**
   * Retrieves the current personalization strategy.
   *
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public PersonalizationStrategyParams getPersonalizationStrategy() throws AlgoliaRuntimeException {
    return this.getPersonalizationStrategy(null);
  }

  /**
   * (asynchronously) Retrieves the current personalization strategy.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<PersonalizationStrategyParams> getPersonalizationStrategyAsync(RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    HttpRequest request = HttpRequest.builder().setPath("/1/strategies/personalization").setMethod("GET").build();

    return executeAsync(request, requestOptions, new TypeReference<PersonalizationStrategyParams>() {});
  }

  /**
   * (asynchronously) Retrieves the current personalization strategy.
   *
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<PersonalizationStrategyParams> getPersonalizationStrategyAsync() throws AlgoliaRuntimeException {
    return this.getPersonalizationStrategyAsync(null);
  }

  /**
   * Retrieves a user profile and their affinities for different facets.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetUserTokenResponse getUserTokenProfile(@Nonnull String userToken, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getUserTokenProfileAsync(userToken, requestOptions));
  }

  /**
   * Retrieves a user profile and their affinities for different facets.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public GetUserTokenResponse getUserTokenProfile(@Nonnull String userToken) throws AlgoliaRuntimeException {
    return this.getUserTokenProfile(userToken, null);
  }

  /**
   * (asynchronously) Retrieves a user profile and their affinities for different facets.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetUserTokenResponse> getUserTokenProfileAsync(@Nonnull String userToken, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(userToken, "Parameter `userToken` is required when calling `getUserTokenProfile`.");

    HttpRequest request = HttpRequest.builder().setPath("/1/profiles/personalization/{userToken}", userToken).setMethod("GET").build();
    return executeAsync(request, requestOptions, new TypeReference<GetUserTokenResponse>() {});
  }

  /**
   * (asynchronously) Retrieves a user profile and their affinities for different facets.
   *
   * @param userToken Unique identifier representing a user for which to fetch the personalization
   *     profile. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<GetUserTokenResponse> getUserTokenProfileAsync(@Nonnull String userToken) throws AlgoliaRuntimeException {
    return this.getUserTokenProfileAsync(userToken, null);
  }

  /**
   * Creates a new personalization strategy.
   *
   * @param personalizationStrategyParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SetPersonalizationStrategyResponse setPersonalizationStrategy(
    @Nonnull PersonalizationStrategyParams personalizationStrategyParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(setPersonalizationStrategyAsync(personalizationStrategyParams, requestOptions));
  }

  /**
   * Creates a new personalization strategy.
   *
   * @param personalizationStrategyParams (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public SetPersonalizationStrategyResponse setPersonalizationStrategy(
    @Nonnull PersonalizationStrategyParams personalizationStrategyParams
  ) throws AlgoliaRuntimeException {
    return this.setPersonalizationStrategy(personalizationStrategyParams, null);
  }

  /**
   * (asynchronously) Creates a new personalization strategy.
   *
   * @param personalizationStrategyParams (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SetPersonalizationStrategyResponse> setPersonalizationStrategyAsync(
    @Nonnull PersonalizationStrategyParams personalizationStrategyParams,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    Parameters.requireNonNull(
      personalizationStrategyParams,
      "Parameter `personalizationStrategyParams` is required when calling" + " `setPersonalizationStrategy`."
    );

    HttpRequest request = HttpRequest.builder()
      .setPath("/1/strategies/personalization")
      .setMethod("POST")
      .setBody(personalizationStrategyParams)
      .build();
    return executeAsync(request, requestOptions, new TypeReference<SetPersonalizationStrategyResponse>() {});
  }

  /**
   * (asynchronously) Creates a new personalization strategy.
   *
   * @param personalizationStrategyParams (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<SetPersonalizationStrategyResponse> setPersonalizationStrategyAsync(
    @Nonnull PersonalizationStrategyParams personalizationStrategyParams
  ) throws AlgoliaRuntimeException {
    return this.setPersonalizationStrategyAsync(personalizationStrategyParams, null);
  }
}

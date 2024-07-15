// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.api;

import com.algolia.ApiClient;
import com.algolia.config.*;
import com.algolia.config.ClientOptions;
import com.algolia.exceptions.*;
import com.algolia.model.abtesting.*;
import com.algolia.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class AbtestingClient extends ApiClient {

  private static final String[] allowedRegions = { "de", "us" };

  public AbtestingClient(String appId, String apiKey) {
    this(appId, apiKey, null, null);
  }

  public AbtestingClient(String appId, String apiKey, ClientOptions options) {
    this(appId, apiKey, null, options);
  }

  public AbtestingClient(String appId, String apiKey, String region) {
    this(appId, apiKey, region, null);
  }

  public AbtestingClient(String appId, String apiKey, String region, ClientOptions options) {
    super(appId, apiKey, "Abtesting", options, getDefaultHosts(region));
  }

  private static List<Host> getDefaultHosts(String region) throws AlgoliaRuntimeException {
    List<Host> hosts = new ArrayList<>();

    boolean found = region == null;
    if (region != null) {
      for (String allowed : allowedRegions) {
        if (allowed.equals(region)) {
          found = true;
          break;
        }
      }
    }

    if (!found) {
      throw new AlgoliaRuntimeException("`region` must be one of the following: de, us");
    }

    String url = region == null ? "analytics.algolia.com" : "analytics.{region}.algolia.com".replace("{region}", region);

    hosts.add(new Host(url, EnumSet.of(CallType.READ, CallType.WRITE)));
    return hosts;
  }

  /**
   * Creates a new A/B test.
   *
   * @param addABTestsRequest (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse addABTests(@Nonnull AddABTestsRequest addABTestsRequest, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    return LaunderThrowable.await(addABTestsAsync(addABTestsRequest, requestOptions));
  }

  /**
   * Creates a new A/B test.
   *
   * @param addABTestsRequest (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse addABTests(@Nonnull AddABTestsRequest addABTestsRequest) throws AlgoliaRuntimeException {
    return this.addABTests(addABTestsRequest, null);
  }

  /**
   * (asynchronously) Creates a new A/B test.
   *
   * @param addABTestsRequest (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> addABTestsAsync(@Nonnull AddABTestsRequest addABTestsRequest, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(addABTestsRequest, "Parameter `addABTestsRequest` is required when calling `addABTests`.");

    HttpRequest request = HttpRequest.builder().setPath("/2/abtests").setMethod("POST").setBody(addABTestsRequest).build();
    return executeAsync(request, requestOptions, new TypeReference<ABTestResponse>() {});
  }

  /**
   * (asynchronously) Creates a new A/B test.
   *
   * @param addABTestsRequest (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> addABTestsAsync(@Nonnull AddABTestsRequest addABTestsRequest) throws AlgoliaRuntimeException {
    return this.addABTestsAsync(addABTestsRequest, null);
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
   * Deletes an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse deleteABTest(@Nonnull Integer id, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(deleteABTestAsync(id, requestOptions));
  }

  /**
   * Deletes an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse deleteABTest(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.deleteABTest(id, null);
  }

  /**
   * (asynchronously) Deletes an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> deleteABTestAsync(@Nonnull Integer id, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(id, "Parameter `id` is required when calling `deleteABTest`.");

    HttpRequest request = HttpRequest.builder().setPath("/2/abtests/{id}", id).setMethod("DELETE").build();

    return executeAsync(request, requestOptions, new TypeReference<ABTestResponse>() {});
  }

  /**
   * (asynchronously) Deletes an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> deleteABTestAsync(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.deleteABTestAsync(id, null);
  }

  /**
   * Retrieves the details for an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTest getABTest(@Nonnull Integer id, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getABTestAsync(id, requestOptions));
  }

  /**
   * Retrieves the details for an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTest getABTest(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.getABTest(id, null);
  }

  /**
   * (asynchronously) Retrieves the details for an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTest> getABTestAsync(@Nonnull Integer id, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    Parameters.requireNonNull(id, "Parameter `id` is required when calling `getABTest`.");

    HttpRequest request = HttpRequest.builder().setPath("/2/abtests/{id}", id).setMethod("GET").build();

    return executeAsync(request, requestOptions, new TypeReference<ABTest>() {});
  }

  /**
   * (asynchronously) Retrieves the details for an A/B test by its ID.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTest> getABTestAsync(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.getABTestAsync(id, null);
  }

  /**
   * Lists all A/B tests you configured for this application.
   *
   * @param offset Position of the first item to return. (optional, default to 0)
   * @param limit Number of items to return. (optional, default to 10)
   * @param indexPrefix Index name prefix. Only A/B tests for indices starting with this string are
   *     included in the response. (optional)
   * @param indexSuffix Index name suffix. Only A/B tests for indices ending with this string are
   *     included in the response. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListABTestsResponse listABTests(
    Integer offset,
    Integer limit,
    String indexPrefix,
    String indexSuffix,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listABTestsAsync(offset, limit, indexPrefix, indexSuffix, requestOptions));
  }

  /**
   * Lists all A/B tests you configured for this application.
   *
   * @param offset Position of the first item to return. (optional, default to 0)
   * @param limit Number of items to return. (optional, default to 10)
   * @param indexPrefix Index name prefix. Only A/B tests for indices starting with this string are
   *     included in the response. (optional)
   * @param indexSuffix Index name suffix. Only A/B tests for indices ending with this string are
   *     included in the response. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListABTestsResponse listABTests(Integer offset, Integer limit, String indexPrefix, String indexSuffix)
    throws AlgoliaRuntimeException {
    return this.listABTests(offset, limit, indexPrefix, indexSuffix, null);
  }

  /**
   * Lists all A/B tests you configured for this application.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListABTestsResponse listABTests(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listABTests(null, null, null, null, requestOptions);
  }

  /**
   * Lists all A/B tests you configured for this application.
   *
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ListABTestsResponse listABTests() throws AlgoliaRuntimeException {
    return this.listABTests(null, null, null, null, null);
  }

  /**
   * (asynchronously) Lists all A/B tests you configured for this application.
   *
   * @param offset Position of the first item to return. (optional, default to 0)
   * @param limit Number of items to return. (optional, default to 10)
   * @param indexPrefix Index name prefix. Only A/B tests for indices starting with this string are
   *     included in the response. (optional)
   * @param indexSuffix Index name suffix. Only A/B tests for indices ending with this string are
   *     included in the response. (optional)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListABTestsResponse> listABTestsAsync(
    Integer offset,
    Integer limit,
    String indexPrefix,
    String indexSuffix,
    RequestOptions requestOptions
  ) throws AlgoliaRuntimeException {
    HttpRequest request = HttpRequest.builder()
      .setPath("/2/abtests")
      .setMethod("GET")
      .addQueryParameter("offset", offset)
      .addQueryParameter("limit", limit)
      .addQueryParameter("indexPrefix", indexPrefix)
      .addQueryParameter("indexSuffix", indexSuffix)
      .build();
    return executeAsync(request, requestOptions, new TypeReference<ListABTestsResponse>() {});
  }

  /**
   * (asynchronously) Lists all A/B tests you configured for this application.
   *
   * @param offset Position of the first item to return. (optional, default to 0)
   * @param limit Number of items to return. (optional, default to 10)
   * @param indexPrefix Index name prefix. Only A/B tests for indices starting with this string are
   *     included in the response. (optional)
   * @param indexSuffix Index name suffix. Only A/B tests for indices ending with this string are
   *     included in the response. (optional)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListABTestsResponse> listABTestsAsync(Integer offset, Integer limit, String indexPrefix, String indexSuffix)
    throws AlgoliaRuntimeException {
    return this.listABTestsAsync(offset, limit, indexPrefix, indexSuffix, null);
  }

  /**
   * (asynchronously) Lists all A/B tests you configured for this application.
   *
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListABTestsResponse> listABTestsAsync(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return this.listABTestsAsync(null, null, null, null, requestOptions);
  }

  /**
   * (asynchronously) Lists all A/B tests you configured for this application.
   *
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ListABTestsResponse> listABTestsAsync() throws AlgoliaRuntimeException {
    return this.listABTestsAsync(null, null, null, null, null);
  }

  /**
   * Stops an A/B test by its ID. You can't restart stopped A/B tests.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse stopABTest(@Nonnull Integer id, RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(stopABTestAsync(id, requestOptions));
  }

  /**
   * Stops an A/B test by its ID. You can't restart stopped A/B tests.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public ABTestResponse stopABTest(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.stopABTest(id, null);
  }

  /**
   * (asynchronously) Stops an A/B test by its ID. You can't restart stopped A/B tests.
   *
   * @param id Unique A/B test identifier. (required)
   * @param requestOptions The requestOptions to send along with the query, they will be merged with
   *     the transporter requestOptions.
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> stopABTestAsync(@Nonnull Integer id, RequestOptions requestOptions)
    throws AlgoliaRuntimeException {
    Parameters.requireNonNull(id, "Parameter `id` is required when calling `stopABTest`.");

    HttpRequest request = HttpRequest.builder().setPath("/2/abtests/{id}/stop", id).setMethod("POST").build();

    return executeAsync(request, requestOptions, new TypeReference<ABTestResponse>() {});
  }

  /**
   * (asynchronously) Stops an A/B test by its ID. You can't restart stopped A/B tests.
   *
   * @param id Unique A/B test identifier. (required)
   * @throws AlgoliaRuntimeException If it fails to process the API call
   */
  public CompletableFuture<ABTestResponse> stopABTestAsync(@Nonnull Integer id) throws AlgoliaRuntimeException {
    return this.stopABTestAsync(id, null);
  }
}

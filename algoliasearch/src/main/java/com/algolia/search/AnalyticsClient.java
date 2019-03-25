package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.analytics.*;
import com.algolia.search.models.common.CallType;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public final class AnalyticsClient {

  private final HttpTransport transport;

  public AnalyticsClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new AnalyticsConfigBase(applicationID, apiKey));
  }

  public AnalyticsClient(@Nonnull AnalyticsConfigBase config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  public AnalyticsClient(
      @Nonnull AnalyticsConfigBase config, @Nonnull IHttpRequester httpRequester) {

    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");
    Objects.requireNonNull(config.getApplicationID(), "An ApplicationID is required.");
    Objects.requireNonNull(config.getApiKey(), "An API key is required.");

    if (config.getApplicationID().trim().length() == 0) {
      throw new NullPointerException("ApplicationID can't be empty.");
    }

    if (config.getApiKey().trim().length() == 0) {
      throw new NullPointerException("APIKey can't be empty.");
    }

    this.transport = new HttpTransport(config, httpRequester);
  }

  /** Close the underlying Http Client */
  public void close() {
    transport.close();
  }

  /** Get an A/B test information and results. */
  public ABTests getABTests() {
    return LaunderThrowable.unwrap(getABTestsAsync(0, 10, null));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public ABTests getABTests(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestsAsync(0, 10, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public ABTests getABTests(int offset, int limit) {
    return LaunderThrowable.unwrap(getABTestsAsync(offset, limit, null));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public ABTests getABTests(int offset, int limit, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestsAsync(offset, limit, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTests> getABTestsAsync() {
    return getABTestsAsync(0, 10, null);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTests> getABTestsAsync(RequestOptions requestOptions) {
    return getABTestsAsync(0, 10, requestOptions);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTests> getABTestsAsync(int offset, int limit) {
    return getABTestsAsync(0, 10, null);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTests> getABTestsAsync(
      int offset, int limit, RequestOptions requestOptions) {

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters("offset", Integer.toString(offset));
    requestOptions.addExtraQueryParameters("limit", Integer.toString(limit));

    return transport.executeRequestAsync(
        HttpMethod.GET, "/2/abtests", CallType.READ, null, ABTests.class, requestOptions);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public ABTestResponse getABTest(long id) {
    return LaunderThrowable.unwrap(getABTestAsync(id, null));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public ABTestResponse getABTest(long id, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestAsync(id, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTestResponse> getABTestAsync(long id) {
    return getABTestAsync(id, null);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<ABTestResponse> getABTestAsync(long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET, "/2/abtests/" + id, CallType.READ, ABTestResponse.class, requestOptions);
  }

  /**
   * Marks the A/B Test as stopped. At this point, the test is over and cannot be restarted. As a
   * result, your application is back to normal: index A will perform as usual, receiving 100% of
   * all search requests. Associated metadata and metrics are still stored
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public StopAbTestResponse stopABTest(long id) {
    return LaunderThrowable.unwrap(stopABTestAsync(id, null));
  }

  /**
   * Marks the A/B Test as stopped. At this point, the test is over and cannot be restarted. As a
   * result, your application is back to normal: index A will perform as usual, receiving 100% of
   * all search requests. Associated metadata and metrics are still stored
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public StopAbTestResponse stopABTest(long id, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(stopABTestAsync(id, requestOptions));
  }

  /**
   * Marks the A/B Test as stopped. At this point, the test is over and cannot be restarted. As a
   * result, your application is back to normal: index A will perform as usual, receiving 100% of
   * all search requests. Associated metadata and metrics are still stored
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<StopAbTestResponse> stopABTestAsync(long id) {
    return stopABTestAsync(id, null);
  }

  /**
   * Marks the A/B Test as stopped. At this point, the test is over and cannot be restarted. As a
   * result, your application is back to normal: index A will perform as usual, receiving 100% of
   * all search requests. Associated metadata and metrics are still stored
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<StopAbTestResponse> stopABTestAsync(
      long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/2/abtests/" + id + "/stop",
        CallType.WRITE,
        StopAbTestResponse.class,
        requestOptions);
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public DeleteAbTestResponse deleteABTest(long id) {
    return LaunderThrowable.unwrap(deleteABTestAsync(id, null));
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public DeleteAbTestResponse deleteABTest(long id, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(deleteABTestAsync(id, requestOptions));
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteAbTestResponse> deleteABTestAsync(long id) {
    return deleteABTestAsync(id, null);
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<DeleteAbTestResponse> deleteABTestAsync(
      long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.DELETE,
        "/2/abtests/" + id,
        CallType.WRITE,
        DeleteAbTestResponse.class,
        requestOptions);
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public AddABTestResponse addABTest(@Nonnull ABTest abTest) throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(addABTestAsync(abTest, null));
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public AddABTestResponse addABTest(@Nonnull ABTest abTest, RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(addABTestAsync(abTest, requestOptions));
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<AddABTestResponse> addABTestAsync(@Nonnull ABTest abTest) {
    return addABTestAsync(abTest, null);
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<AddABTestResponse> addABTestAsync(
      @Nonnull ABTest abTest, RequestOptions requestOptions) {

    Objects.requireNonNull(abTest, "An ABTest is required.");

    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/2/abtests",
        CallType.WRITE,
        abTest,
        AddABTestResponse.class,
        requestOptions);
  }
}

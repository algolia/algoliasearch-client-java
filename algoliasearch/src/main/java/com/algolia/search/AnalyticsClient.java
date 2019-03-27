package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.analytics.*;
import com.algolia.search.models.common.CallType;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Algolia's REST analytics client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the Apache Http Client in {@link AlgoliaHttpRequester} This client allows to build
 * typed requests and read typed responses. Requests are made under the Algolia's retry-strategy.
 * This client is intended to be reused and it's thread-safe.
 */
@SuppressWarnings("WeakerAccess")
public final class AnalyticsClient implements Closeable {

  /** The transport layer. Must be reused. */
  private final HttpTransport transport;

  /**
   * Creates a {@link AnalyticsClient} with the given credentials
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException if ApplicationID/ApiKey is null
   */
  public AnalyticsClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new AnalyticsConfig(applicationID, apiKey));
  }

  /**
   * Creates a {@link AnalyticsClient} with the given {@link AnalyticsConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @throws NullPointerException if Config is null
   */
  public AnalyticsClient(@Nonnull AnalyticsConfig config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  /**
   * Creates a {@link AnalyticsClient} with the given {@link AnalyticsConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @param httpRequester Another HTTP Client than the default one.
   * @throws NullPointerException if ApplicationID/ApiKey/Config/Requester is null
   */
  public AnalyticsClient(@Nonnull AnalyticsConfig config, @Nonnull IHttpRequester httpRequester) {

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
  @Override
  public void close() throws IOException {
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public ABTests getABTests(int offset, int limit, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestsAsync(offset, limit, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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

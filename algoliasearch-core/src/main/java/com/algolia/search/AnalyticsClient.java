package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.analytics.ABTest;
import com.algolia.search.models.analytics.ABTestResponse;
import com.algolia.search.models.analytics.ABTests;
import com.algolia.search.models.analytics.AddABTestResponse;
import com.algolia.search.models.analytics.DeleteAbTestResponse;
import com.algolia.search.models.analytics.StopAbTestResponse;
import com.algolia.search.models.common.CallType;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Algolia's REST analytics client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the HTTP Client This client allows to build typed requests and read typed responses.
 * Requests are made under the Algolia's retry-strategy. This client is intended to be reused and
 * it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/analytics/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public final class AnalyticsClient implements Closeable {

  /** The transport layer. Must be reused. */
  private final HttpTransport transport;

  /** Client's configuration. Must be reused. */
  private final ConfigBase config;

  /**
   * Creates a custom {@link AnalyticsClient} with the given {@link AnalyticsConfig} and the given
   * {@link HttpRequester}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts and timeout.
   * @param httpRequester Another HTTP Client than the default one. Must be an implementation of
   *     {@link HttpRequester}.
   * @throws NullPointerException If one of the following
   *     ApplicationID/ApiKey/Configuration/Requester is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public AnalyticsClient(@Nonnull AnalyticsConfig config, @Nonnull HttpRequester httpRequester) {

    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  /**
   * Close the underlying Http Client
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    transport.close();
  }

  /** Get Client's configuration */
  public ConfigBase getConfig() {
    return config;
  }

  /** Transport object responsible for the serialization/deserialization and the retry strategy. */
  public HttpTransport getTransport() {
    return transport;
  }

  /** Get an A/B test information and results. */
  public ABTests getABTests() {
    return LaunderThrowable.await(getABTestsAsync(0, 10, null));
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
    return LaunderThrowable.await(getABTestsAsync(0, 10, requestOptions));
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
    return LaunderThrowable.await(getABTestsAsync(offset, limit, null));
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
    return LaunderThrowable.await(getABTestsAsync(offset, limit, requestOptions));
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
    return LaunderThrowable.await(getABTestAsync(id, null));
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
    return LaunderThrowable.await(getABTestAsync(id, requestOptions));
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
    return LaunderThrowable.await(stopABTestAsync(id, null));
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
    return LaunderThrowable.await(stopABTestAsync(id, requestOptions));
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
    return LaunderThrowable.await(deleteABTestAsync(id, null));
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
    return LaunderThrowable.await(deleteABTestAsync(id, requestOptions));
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
  public AddABTestResponse addABTest(@Nonnull ABTest abTest) {
    return LaunderThrowable.await(addABTestAsync(abTest, null));
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
  public AddABTestResponse addABTest(@Nonnull ABTest abTest, RequestOptions requestOptions) {
    return LaunderThrowable.await(addABTestAsync(abTest, requestOptions));
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

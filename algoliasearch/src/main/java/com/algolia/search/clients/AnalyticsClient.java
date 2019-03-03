package com.algolia.search.clients;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.models.*;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.ABTests;
import com.algolia.search.transport.HttpTransport;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class AnalyticsClient {

  private final HttpTransport transport;
  private final AlgoliaConfig config;

  public AnalyticsClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new AnalyticsConfig(applicationID, apiKey));
  }

  public AnalyticsClient(@Nonnull AnalyticsConfig config) {
    this(config, new AlgoliaHttpRequester(config));
  }

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

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  /** Get an A/B test information and results. */
  public ABTests getABTests() {
    return LaunderThrowable.unwrap(getABTestsAsync(0, 10, null));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param requestOptions Options to pass to this request
   */
  public ABTests getABTests(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestsAsync(0, 10, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
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
   */
  public ABTests getABTests(int offset, int limit, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestsAsync(offset, limit, requestOptions));
  }

  /** Get an A/B test information and results. */
  public CompletableFuture<ABTests> getABTestsAsync() {
    return getABTestsAsync(0, 10, null);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<ABTests> getABTestsAsync(RequestOptions requestOptions) {
    return getABTestsAsync(0, 10, requestOptions);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param offset Position of the starting record. Used for paging. 0 is the first record.
   * @param limit Number of records to return. +used for paging. Limit is the size of the page.
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
   */
  public ABTest getABTest(long id) {
    return LaunderThrowable.unwrap(getABTestAsync(id, null));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   */
  public ABTest getABTest(long id, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getABTestAsync(id, requestOptions));
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   */
  public CompletableFuture<ABTest> getABTestAsync(long id) {
    return getABTestAsync(id, null);
  }

  /**
   * Get an A/B test information and results.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<ABTest> getABTestAsync(long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET, "/2/abtests/" + id, CallType.READ, null, ABTest.class, requestOptions);
  }

  /**
   * Marks the A/B Test as stopped. At this point, the test is over and cannot be restarted. As a
   * result, your application is back to normal: index A will perform as usual, receiving 100% of
   * all search requests. Associated metadata and metrics are still stored
   *
   * @param id The ABTest ID
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
   */
  public CompletableFuture<StopAbTestResponse> stopABTestAsync(
      long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/2/abtests/" + id + "/stop",
        CallType.WRITE,
        null,
        StopAbTestResponse.class,
        requestOptions);
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   */
  public DeleteAbTestResponse deleteABTest(long id) {
    return LaunderThrowable.unwrap(deleteABTestAsync(id, null));
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   */
  public DeleteAbTestResponse deleteABTest(long id, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(deleteABTestAsync(id, requestOptions));
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   */
  public CompletableFuture<DeleteAbTestResponse> deleteABTestAsync(long id) {
    return deleteABTestAsync(id, null);
  }

  /**
   * Deletes the A/B Test and removes all associated metadata and metrics.
   *
   * @param id The ABTest ID
   * @param requestOptions Options to pass to this request
   */
  public CompletableFuture<DeleteAbTestResponse> deleteABTestAsync(
      long id, RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.DELETE,
        "/2/abtests/" + id,
        CallType.WRITE,
        null,
        DeleteAbTestResponse.class,
        requestOptions);
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   */
  public AddABTestResponse addABTest(@Nonnull ABTest abTest) throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(addABTestAsync(abTest, null));
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @param requestOptions Options to pass to this request
   */
  public AddABTestResponse addABTest(@Nonnull ABTest abTest, RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(addABTestAsync(abTest, requestOptions));
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   */
  public CompletableFuture<AddABTestResponse> addABTestAsync(@Nonnull ABTest abTest) {
    return addABTestAsync(abTest, null);
  }

  /**
   * Creates a new AB Test with provided configuration
   *
   * @param abTest The definition of the A/B test
   * @param requestOptions Options to pass to this request
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

package com.algolia.search;

import com.algolia.search.exceptions.*;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.recommendation.GetStrategyResponse;
import com.algolia.search.models.recommendation.SetStrategyRequest;
import com.algolia.search.models.recommendation.SetStrategyResponse;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Algolia's REST recommendation client that wraps an instance of the transporter {@link
 * HttpTransport} which wraps the HTTP Client This client allows to build typed requests and read
 * typed responses. Requests are made under the Algolia's retry-strategy. This client is intended to
 * be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/recommendation/">Algolia.com</a>
 */
@SuppressWarnings({"WeakerAccess", "Unused"})
public class RecommendationClient implements Closeable {

  /** The transport layer. Must be reused. */
  private final HttpTransport transport;

  /** Client's configuration. Must be reused. */
  private final ConfigBase config;

  /**
   * Creates a custom {@link RecommendationClient} with the given {@link RecommendationConfig} and
   * the given {@link HttpRequester}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts and timeout.
   * @param httpRequester Another HTTP Client than the default one. Must be an implementation of
   *     {@link HttpRequester}.
   * @throws NullPointerException If one of the following
   *     ApplicationID/ApiKey/Configuration/Requester is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public RecommendationClient(
      @Nonnull RecommendationConfig config, @Nonnull HttpRequester httpRequester) {

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

  /**
   * Returns the personalization strategy of the application
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public GetStrategyResponse getPersonalizationStrategy() {
    return LaunderThrowable.await(getPersonalizationStrategyAsync());
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public GetStrategyResponse getPersonalizationStrategy(RequestOptions requestOptions) {
    return LaunderThrowable.await(getPersonalizationStrategyAsync(requestOptions));
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<GetStrategyResponse> getPersonalizationStrategyAsync() {
    return getPersonalizationStrategyAsync(null);
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<GetStrategyResponse> getPersonalizationStrategyAsync(
      RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET,
        "/1/strategies/personalization",
        CallType.READ,
        GetStrategyResponse.class,
        requestOptions);
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public SetStrategyResponse setPersonalizationStrategy(@Nonnull SetStrategyRequest request) {
    return LaunderThrowable.await(setPersonalizationStrategyAsync(request));
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public SetStrategyResponse setPersonalizationStrategy(
      @Nonnull SetStrategyRequest request, RequestOptions requestOptions) {
    return LaunderThrowable.await(setPersonalizationStrategyAsync(request, requestOptions));
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<SetStrategyResponse> setPersonalizationStrategyAsync(
      @Nonnull SetStrategyRequest request) {
    return setPersonalizationStrategyAsync(request, null);
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<SetStrategyResponse> setPersonalizationStrategyAsync(
      @Nonnull SetStrategyRequest request, RequestOptions requestOptions) {
    Objects.requireNonNull(request, "strategy request is required.");

    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/1/strategies/personalization",
        CallType.WRITE,
        request,
        SetStrategyResponse.class,
        requestOptions);
  }

  /** Get Client's configuration */
  public ConfigBase getConfig() {
    return config;
  }
}

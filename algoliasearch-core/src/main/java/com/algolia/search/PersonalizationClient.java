package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.recommendation.DeletePersonalizationProfileResponse;
import com.algolia.search.models.recommendation.GetStrategyResponse;
import com.algolia.search.models.recommendation.PersonalizationProfileResponse;
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
 * @see <a href="https://www.algolia.com/doc/rest-api/personalization/">Algolia.com</a>
 */
@SuppressWarnings("unused")
public class PersonalizationClient implements Closeable {

  /** The transport layer. Must be reused. */
  private final HttpTransport transport;

  /** Client's configuration. Must be reused. */
  private final ConfigBase config;

  /**
   * Creates a custom {@link PersonalizationClient} with the given {@link PersonalizationConfig} and
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
  public PersonalizationClient(
      @Nonnull PersonalizationConfig config, @Nonnull HttpRequester httpRequester) {

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

  /**
   * Get the user profile built from Personalization strategy.
   *
   * <p>The profile is structured by facet name used in the strategy. Each facet value is mapped to
   * its score. Each score represents the user affinity for a specific facet value given the
   * userToken past events and the Personalization strategy defined. Scores are bounded to 20. The
   * last processed event timestamp is provided using the ISO 8601 format for debugging purposes.
   *
   * @param userToken userToken representing the user for which to fetch the Personalization profile
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public PersonalizationProfileResponse getPersonalizationProfile(String userToken) {
    return LaunderThrowable.await(getPersonalizationProfileAsync(userToken));
  }

  /**
   * Get the user profile built from Personalization strategy.
   *
   * <p>The profile is structured by facet name used in the strategy. Each facet value is mapped to
   * its score. Each score represents the user affinity for a specific facet value given the
   * userToken past events and the Personalization strategy defined. Scores are bounded to 20. The
   * last processed event timestamp is provided using the ISO 8601 format for debugging purposes.
   *
   * @param userToken userToken representing the user for which to fetch the Personalization profile
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public PersonalizationProfileResponse getPersonalizationProfile(
      String userToken, RequestOptions requestOptions) {
    return LaunderThrowable.await(getPersonalizationProfileAsync(userToken, requestOptions));
  }

  /**
   * Get the user profile built from Personalization strategy.
   *
   * <p>The profile is structured by facet name used in the strategy. Each facet value is mapped to
   * its score. Each score represents the user affinity for a specific facet value given the
   * userToken past events and the Personalization strategy defined. Scores are bounded to 20. The
   * last processed event timestamp is provided using the ISO 8601 format for debugging purposes.
   *
   * @param userToken userToken representing the user for which to fetch the Personalization profile
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<PersonalizationProfileResponse> getPersonalizationProfileAsync(
      String userToken) {
    return getPersonalizationProfileAsync(userToken, null);
  }

  /**
   * Get the user profile built from Personalization strategy.
   *
   * <p>The profile is structured by facet name used in the strategy. Each facet value is mapped to
   * its score. Each score represents the user affinity for a specific facet value given the
   * userToken past events and the Personalization strategy defined. Scores are bounded to 20. The
   * last processed event timestamp is provided using the ISO 8601 format for debugging purposes.
   *
   * @param userToken userToken representing the user for which to fetch the Personalization profile
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<PersonalizationProfileResponse> getPersonalizationProfileAsync(
      String userToken, RequestOptions requestOptions) {
    Objects.requireNonNull(userToken, "userToken is required.");
    return transport.executeRequestAsync(
        HttpMethod.GET,
        "1/profiles/personalization/" + userToken,
        CallType.READ,
        PersonalizationProfileResponse.class,
        requestOptions);
  }

  /**
   * Delete the user profile and all its associated data.
   *
   * <p>Returns, as part of the response, a date until which the data can safely be considered as
   * deleted for the given user. This means that if you send events for the given user before this
   * date, they will be ignored. Any data received after the deletedUntil date will start building a
   * new user profile.
   *
   * <p>It might take a couple hours before for the deletion request to be fully processed.
   *
   * @param userToken userToken representing the user for which to delete the Personalization
   *     profile and associated data.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeletePersonalizationProfileResponse deletePersonalizationProfile(String userToken) {
    return LaunderThrowable.await(deletePersonalizationProfileAsync(userToken));
  }

  /**
   * Delete the user profile and all its associated data.
   *
   * <p>Returns, as part of the response, a date until which the data can safely be considered as
   * deleted for the given user. This means that if you send events for the given user before this
   * date, they will be ignored. Any data received after the deletedUntil date will start building a
   * new user profile.
   *
   * <p>It might take a couple hours before for the deletion request to be fully processed.
   *
   * @param userToken userToken representing the user for which to delete the Personalization
   *     profile and associated data.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public DeletePersonalizationProfileResponse deletePersonalizationProfile(
      String userToken, RequestOptions requestOptions) {
    return LaunderThrowable.await(deletePersonalizationProfileAsync(userToken, requestOptions));
  }

  /**
   * Delete the user profile and all its associated data.
   *
   * <p>Returns, as part of the response, a date until which the data can safely be considered as
   * deleted for the given user. This means that if you send events for the given user before this
   * date, they will be ignored. Any data received after the deletedUntil date will start building a
   * new user profile.
   *
   * <p>It might take a couple hours before for the deletion request to be fully processed.
   *
   * @param userToken userToken representing the user for which to delete the Personalization
   *     profile and associated data.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeletePersonalizationProfileResponse> deletePersonalizationProfileAsync(
      String userToken) {
    return deletePersonalizationProfileAsync(userToken, null);
  }

  /**
   * Delete the user profile and all its associated data.
   *
   * <p>Returns, as part of the response, a date until which the data can safely be considered as
   * deleted for the given user. This means that if you send events for the given user before this
   * date, they will be ignored. Any data received after the deletedUntil date will start building a
   * new user profile.
   *
   * <p>It might take a couple hours before for the deletion request to be fully processed.
   *
   * @param userToken userToken representing the user for which to delete the Personalization
   *     profile and associated data.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<DeletePersonalizationProfileResponse> deletePersonalizationProfileAsync(
      String userToken, RequestOptions requestOptions) {
    Objects.requireNonNull(userToken, "userToken is required.");
    return transport.executeRequestAsync(
        HttpMethod.DELETE,
        "1/profiles/" + userToken,
        CallType.WRITE,
        DeletePersonalizationProfileResponse.class,
        requestOptions);
  }

  /** Get Client's configuration */
  public ConfigBase getConfig() {
    return config;
  }
}

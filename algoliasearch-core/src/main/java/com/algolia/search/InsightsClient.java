package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.insights.InsightsEvent;
import com.algolia.search.models.insights.InsightsRequest;
import com.algolia.search.models.insights.InsightsResult;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Algolia's REST insights client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the HTTP Client This client allows to build typed requests and read typed responses.
 * Requests are made under the Algolia's retry-strategy. This client is intended to be reused and
 * it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/insights/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public final class InsightsClient implements Closeable {

  /** The transport layer. Should be reused. */
  private final HttpTransport transport;

  /**
   * Creates a custom {@link InsightsClient} with the given {@link InsightsConfig} and the given
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
  public InsightsClient(@Nonnull InsightsConfig config, @Nonnull HttpRequester httpRequester) {

    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");

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

  /** @param userToken the user config */
  public UserInsightsClient user(@Nonnull String userToken) {
    return new UserInsightsClient(userToken, this);
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public InsightsResult sendEvent(@Nonnull InsightsEvent event) {
    return LaunderThrowable.await(sendEventAsync(event));
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public InsightsResult sendEvent(@Nonnull InsightsEvent event, RequestOptions requestOptions) {
    return LaunderThrowable.await(sendEventAsync(event, requestOptions));
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> sendEventAsync(@Nonnull InsightsEvent event) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEventsAsync(events, null);
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> sendEventAsync(
      @Nonnull InsightsEvent event, RequestOptions requestOptions) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEventsAsync(events, requestOptions);
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public InsightsResult sendEvents(@Nonnull List<InsightsEvent> events) {
    return LaunderThrowable.await(sendEventsAsync(events));
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public InsightsResult sendEvents(
      @Nonnull List<InsightsEvent> events, RequestOptions requestOptions) {
    return LaunderThrowable.await(sendEventsAsync(events, requestOptions));
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(@Nonnull List<InsightsEvent> events) {
    return sendEventsAsync(events, null);
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(
      @Nonnull List<InsightsEvent> events, RequestOptions requestOptions) {
    InsightsRequest request = new InsightsRequest().setEvents(events);
    return transport.executeRequestAsync(
        HttpMethod.POST,
        "/1/events",
        CallType.WRITE,
        request,
        InsightsResult.class,
        requestOptions);
  }
}

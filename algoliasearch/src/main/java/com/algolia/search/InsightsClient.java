package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.insights.InsightsEvent;
import com.algolia.search.models.insights.InsightsRequest;
import com.algolia.search.models.insights.InsightsResult;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class InsightsClient {

  private final HttpTransport transport;

  public InsightsClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new InsightsConfigBase(applicationID, apiKey));
  }

  public InsightsClient(@Nonnull InsightsConfigBase config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  public InsightsClient(@Nonnull InsightsConfigBase config, @Nonnull IHttpRequester httpRequester) {

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
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(@Nonnull InsightsEvent event) {
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
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(
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
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
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

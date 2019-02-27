package com.algolia.search.clients;

import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.inputs.insights.InsightsEvent;
import com.algolia.search.inputs.insights.InsightsRequest;
import com.algolia.search.inputs.insights.InsightsResult;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.transport.HttpTransport;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class InsightsClient {

  private final HttpTransport transport;
  private final AlgoliaConfig config;

  public InsightsClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new InsightsConfig(applicationID, apiKey));
  }

  public InsightsClient(@Nonnull InsightsConfig config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  public InsightsClient(@Nonnull InsightsConfig config, @Nonnull IHttpRequester httpRequester) {

    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");

    if (config.getApplicationID() == null || config.getApplicationID().trim().length() == 0) {
      throw new NullPointerException("An ApplicationID is required");
    }

    if (config.getApiKey() == null || config.getApiKey().trim().length() == 0) {
      throw new NullPointerException("An API key is required");
    }

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  /** @param userToken the user config */
  public UserInsightsClient user(@Nonnull String userToken) {
    return new UserInsightsClient(userToken, this);
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(@Nonnull InsightsEvent event) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEventsAsync(events, new RequestOptions());
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(
      @Nonnull InsightsEvent event, @Nonnull RequestOptions requestOptions) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEventsAsync(events, requestOptions);
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(@Nonnull List<InsightsEvent> events) {
    return sendEventsAsync(events, new RequestOptions());
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> sendEventsAsync(
      @Nonnull List<InsightsEvent> events, @Nonnull RequestOptions requestOptions) {
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

package com.algolia.search;

import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.http.AlgoliaRequestKind;
import com.algolia.search.http.HttpMethod;
import com.algolia.search.inputs.insights.InsightsEvent;
import com.algolia.search.inputs.insights.InsightsRequest;
import com.algolia.search.inputs.insights.InsightsResult;
import com.algolia.search.objects.InsightsConfig;
import com.algolia.search.objects.RequestOptions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class AsyncInsightsClient {

  private AsyncAPIClient client;
  private InsightsConfig config;
  private String host;

  /**
   * @param applicationId The application ID
   * @param apiKey The API Key
   * @param client API Client
   */
  public AsyncInsightsClient(
      @Nonnull String applicationId, @Nonnull String apiKey, @Nonnull AsyncAPIClient client) {
    this(applicationId, apiKey, "us", client);
  }

  /**
   * @param applicationId The applicationID
   * @param apiKey The api KEY
   * @param region The server region
   * @param client APIClient
   */
  public AsyncInsightsClient(
      @Nonnull String applicationId,
      @Nonnull String apiKey,
      @Nonnull String region,
      @Nonnull AsyncAPIClient client) {
    this(
        new InsightsConfig().setApplicationId(applicationId).setApiKey(apiKey).setRegion(region),
        client);
  }

  /**
   * @param config InsightsClient
   * @param client APIClient
   */
  public AsyncInsightsClient(@Nonnull InsightsConfig config, @Nonnull AsyncAPIClient client) {
    this.config = config;
    this.client = client;
    this.host = "insights." + config.getRegion() + ".algolia.io";
  }

  /** @param userToken the user config */
  public AsyncUserInsightsClient user(@Nonnull String userToken) {
    return new AsyncUserInsightsClient(userToken, this);
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   */
  public CompletableFuture<InsightsResult> sendEvent(@Nonnull InsightsEvent event) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEvents(events, new RequestOptions());
  }

  /**
   * This command pushes an event to the Insights API.
   *
   * @param event An event
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> sendEvent(
      @Nonnull InsightsEvent event, @Nonnull RequestOptions requestOptions) {
    List<InsightsEvent> events = Collections.singletonList(event);
    return sendEvents(events, requestOptions);
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   */
  public CompletableFuture<InsightsResult> sendEvents(@Nonnull List<InsightsEvent> events) {
    return sendEvents(events, new RequestOptions());
  }

  /**
   * This command pushes an array of events to the Insights API.
   *
   * @param events List of events
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> sendEvents(
      @Nonnull List<InsightsEvent> events, @Nonnull RequestOptions requestOptions) {
    InsightsRequest request = new InsightsRequest().setEvents(events);

    return client.httpClient.requestInsights(
        new AlgoliaRequest<>(
                HttpMethod.POST,
                AlgoliaRequestKind.INSIGHTS_API,
                Arrays.asList("1", "events"),
                requestOptions,
                InsightsResult.class)
            .setData(request),
        host);
  }
}

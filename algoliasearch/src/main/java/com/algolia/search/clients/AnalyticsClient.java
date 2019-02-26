package com.algolia.search.clients;

import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.transport.HttpTransport;
import java.util.Objects;
import javax.annotation.Nonnull;

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

    if (config.getApplicationID() == null || config.getApplicationID().trim().length() == 0) {
      throw new NullPointerException("An ApplicationID is required");
    }

    if (config.getApiKey() == null || config.getApiKey().trim().length() == 0) {
      throw new NullPointerException("An API key is required");
    }

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }
}

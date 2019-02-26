package com.algolia.search.clients;

import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.transport.HttpTransport;
import java.util.Objects;
import javax.annotation.Nonnull;

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
}

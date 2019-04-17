package com.algolia.search;

import javax.annotation.Nonnull;

/**
 * Algolia's REST insights client that wraps an instance of the transporter. which wraps the HTTP
 * Client This client allows to build typed requests and read typed responses. Requests are made
 * under the Algolia's retry-strategy. This client is intended to be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/insights/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public class DefaultInsightsClient {

  /**
   * Creates a {@link InsightsClient} with the given credentials
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException If one of the following ApplicationID/ApiKey is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static InsightsClient create(@Nonnull String applicationID, @Nonnull String apiKey) {
    return create(new InsightsConfig.Builder(applicationID, apiKey).build());
  }

  /**
   * Creates a {@link InsightsClient} with the given {@link InsightsConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @throws NullPointerException If one of the following ApplicationID/ApiKey/Config is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static InsightsClient create(@Nonnull InsightsConfig config) {
    return new InsightsClient(config, new ApacheHttpRequester(config));
  }
}

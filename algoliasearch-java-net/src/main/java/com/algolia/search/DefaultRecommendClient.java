package com.algolia.search;

import javax.annotation.Nonnull;

/**
 * Algolia's REST recommend client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the HTTP Client This client allows to build typed requests and read typed responses.
 * Requests are made under the Algolia's retry-strategy. This client is intended to be reused, and
 * it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/recommend">Algolia.com</a>
 */
public class DefaultRecommendClient {

  // Suppress default constructor for noninstantiability
  private DefaultRecommendClient() {
    throw new AssertionError();
  }

  /**
   * Creates a {@link RecommendClient} with the given credentials The default HttpClient
   * implementation is {@link ApacheHttpRequester}
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException If one of the following ApplicationID/ApiKey is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static RecommendClient create(@Nonnull String applicationID, @Nonnull String apiKey) {
    return create(new SearchConfig.Builder(applicationID, apiKey).build());
  }

  /**
   * Creates a default {@link RecommendClient} with the given {@link SearchConfig}. The default
   * HttpClient implementation is {@link ApacheHttpRequester}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts and timeout.
   * @throws NullPointerException If one of the following ApplicationID/ApiKey/Config is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static RecommendClient create(@Nonnull SearchConfig config) {
    return new RecommendClient(config, new JavaNetHttpRequester(config));
  }
}

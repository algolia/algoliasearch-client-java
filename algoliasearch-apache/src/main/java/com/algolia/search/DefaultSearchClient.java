package com.algolia.search;

import javax.annotation.Nonnull;

/**
 * Algolia's REST search client that wraps an instance of the transporter which wraps the HttpClient
 * This client allows to build typed requests and read typed responses. Requests are made under the
 * Algolia's retry-strategy. This client is intended to be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/search/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public class DefaultSearchClient {

  /**
   * Creates a {@link DefaultSearchClient} with the given credentials The default HttpClient
   * implementation is {@link ApacheHttpRequester}
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException If one of the following ApplicationID/ApiKey is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static SearchClient create(@Nonnull String applicationID, @Nonnull String apiKey) {
    return create(new SearchConfig.Builder(applicationID, apiKey).build());
  }

  /**
   * Creates a default {@link DefaultSearchClient} with the given {@link SearchConfig}. The default
   * HttpClient implementation is {@link ApacheHttpRequester}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts and timeout.
   * @throws NullPointerException If one of the following ApplicationID/ApiKey/Config is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static SearchClient create(@Nonnull SearchConfig config) {
    return new SearchClient(config, new ApacheHttpRequester(config));
  }
}

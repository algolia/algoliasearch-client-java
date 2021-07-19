package com.algolia.search;

import javax.annotation.Nonnull;

/**
 * Algolia's REST recommendation client that wraps an instance of the transporter {@link
 * HttpTransport} which wraps the HTTP Client This client allows to build typed requests and read
 * typed responses. Requests are made under the Algolia's retry-strategy. This client is intended to
 * be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/personalization/">Algolia.com</a>
 */
public class DefaultPersonalizationClient {

  // Suppress default constructor for noninstantiability
  private DefaultPersonalizationClient() {
    throw new AssertionError();
  }

  /**
   * Creates a {@link PersonalizationClient} with the given credentials The default HttpClient
   * implementation is {@link ApacheHttpRequester}
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @param region Region where your personalization data is stored and processed.
   * @throws NullPointerException If one of the following ApplicationID/ApiKey is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static PersonalizationClient create(
      @Nonnull String applicationID, @Nonnull String apiKey, @Nonnull String region) {
    return create(new PersonalizationConfig.Builder(applicationID, apiKey, region).build());
  }

  /**
   * Creates a default {@link PersonalizationClient} with the given {@link SearchConfig}. The
   * default HttpClient implementation is {@link ApacheHttpRequester}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts and timeout.
   * @throws NullPointerException If one of the following ApplicationID/ApiKey/Config is null
   * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
   */
  public static PersonalizationClient create(@Nonnull PersonalizationConfig config) {
    return new PersonalizationClient(config, new ApacheHttpRequester(config));
  }
}

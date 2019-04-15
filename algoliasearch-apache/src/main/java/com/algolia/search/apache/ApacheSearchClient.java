package com.algolia.search.apache;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import javax.annotation.Nonnull;

/**
 * Algolia's REST search client that wraps an instance of the transporter which wraps the HttpClient
 * This client allows to build typed requests and read typed responses. Requests are made under the
 * Algolia's retry-strategy. This client is intended to be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/search/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public class ApacheSearchClient {

  /**
   * Creates a {@link ApacheSearchClient} with the given credentials
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException if ApplicationID/ApiKey is null
   */
  public static SearchClient create(@Nonnull String applicationID, @Nonnull String apiKey) {
    return create(new SearchConfig.Builder(applicationID, apiKey).build());
  }

  /**
   * Creates a {@link ApacheSearchClient} with the given {@link SearchConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @throws NullPointerException if Config is null
   */
  public static SearchClient create(@Nonnull SearchConfig config) {
    return new SearchClient(config, new ApacheHttpRequester(config));
  }
}

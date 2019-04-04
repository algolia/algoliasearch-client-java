package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.util.AlgoliaUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Algolia's REST search client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the Apache Http Client in {@link AlgoliaHttpRequester} This client allows to build
 * typed requests and read typed responses. Requests are made under the Algolia's retry-strategy.
 * This client is intended to be reused and it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/search/">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
public final class SearchClient
    implements Closeable,
        SearchClientMultipleOperations,
        SearchClientCopyOperations,
        SearchClientMcm,
        SearchClientAPIKeys,
        SearchClientPersonalization,
        SearchClientAdvanced {

  /** The transport layer. Must be reused. */
  private final HttpTransport transport;

  /** Client's configuration */
  private final AlgoliaConfigBase config;

  /**
   * Creates a {@link SearchClient} with the given credentials
   *
   * @param applicationID The Algolia Application ID
   * @param apiKey The Algolia API Key
   * @throws NullPointerException if ApplicationID/ApiKey is null
   */
  public SearchClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new SearchConfig(applicationID, apiKey));
  }

  /**
   * Creates a {@link SearchClient} with the given {@link SearchConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @throws NullPointerException if Config is null
   */
  public SearchClient(@Nonnull SearchConfig config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  /**
   * Creates a {@link SearchClient} with the given {@link SearchConfig}
   *
   * @param config The configuration allows you to advanced configuration of the clients such as
   *     batch size or custom hosts.
   * @param httpRequester Another HTTP Client than the default one.
   * @throws NullPointerException if ApplicationID/ApiKey/Config/Requester is null
   */
  public SearchClient(@Nonnull SearchConfig config, @Nonnull IHttpRequester httpRequester) {

    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");
    Objects.requireNonNull(config.getApplicationID(), "An ApplicationID is required.");
    Objects.requireNonNull(config.getApiKey(), "An API key is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(config.getApplicationID())) {
      throw new NullPointerException("ApplicationID can't be empty.");
    }

    if (AlgoliaUtils.isEmptyWhiteSpace(config.getApiKey())) {
      throw new NullPointerException("APIKey can't be empty.");
    }

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  /**
   * Close the underlying Http Client
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    transport.close();
  }

  @Override
  public HttpTransport getTransport() {
    return transport;
  }

  @Override
  public AlgoliaConfigBase getConfig() {
    return config;
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param indexName The name of the Algolia index
   * @throws NullPointerException When indexName is null or empty
   */
  public SearchIndex<?> initIndex(@Nonnull String indexName) {

    Objects.requireNonNull(indexName, "indexName can't be null.");

    if (AlgoliaUtils.isEmptyWhiteSpace(indexName)) {
      throw new NullPointerException("The index name is required");
    }

    return new SearchIndex<>(transport, config, indexName, Object.class);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param indexName The name of the Algolia index
   * @param clazz class of the object in this index
   * @param <T> the type of the objects in this index
   * @throws NullPointerException When indexName is null or empty
   */
  public <T> SearchIndex<T> initIndex(@Nonnull String indexName, @Nonnull Class<T> clazz) {

    Objects.requireNonNull(indexName, "indexName can't be null.");
    Objects.requireNonNull(clazz, "A class is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(indexName)) {
      throw new NullPointerException("The index name is required");
    }

    return new SearchIndex<>(transport, config, indexName, clazz);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param indexName The indexName to wait on
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public void waitTask(@Nonnull String indexName, long taskID) {
    waitTask(indexName, taskID, 100, null);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param indexName The indexName to wait on
   * @param taskID The Algolia taskID
   * @param timeToWait The time to wait between each call
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public void waitTask(@Nonnull String indexName, long taskID, long timeToWait) {
    waitTask(indexName, taskID, timeToWait, null);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param indexName The indexName to wait on
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public void waitTask(@Nonnull String indexName, long taskID, RequestOptions requestOptions) {
    waitTask(indexName, taskID, 100, requestOptions);
  }

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates. All write operations in Algolia are asynchronous by design.
   *
   * @param indexName The indexName to wait on
   * @param taskID The Algolia taskID
   * @param timeToWait The time to wait between each call
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public void waitTask(
      @Nonnull String indexName, long taskID, long timeToWait, RequestOptions requestOptions) {

    Objects.requireNonNull(indexName, "The index name is required.");

    SearchIndex<?> indexToWait = initIndex(indexName);
    indexToWait.waitTask(taskID, timeToWait, requestOptions);
  }
}

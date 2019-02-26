package com.algolia.search.clients;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.inputs.ApiKeys;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.IndicesResponse;
import com.algolia.search.models.ListIndicesResponse;
import com.algolia.search.objects.ApiKey;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.transport.HttpTransport;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class SearchClient {

  private final HttpTransport transport;
  private final AlgoliaConfig config;

  public SearchClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new SearchConfig(applicationID, apiKey));
  }

  public SearchClient(@Nonnull AlgoliaConfig config) {
    this(config, new AlgoliaHttpRequester(config));
  }

  public SearchClient(@Nonnull AlgoliaConfig config, @Nonnull IHttpRequester httpRequester) {

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

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param indexName The name of the Algolia index
   * @throws NullPointerException When indexName is null or empty
   */
  public SearchIndex<?> initIndex(@Nonnull String indexName) {

    if (indexName == null || indexName.trim().length() == 0) {
      throw new NullPointerException("The index name is required");
    }

    return new SearchIndex<>(transport, config, indexName, Object.class);
  }

  /**
   * Get the index object initialized (no server call needed for initialization)
   *
   * @param indexName The name of the Algolia index
   * @param klass class of the object in this index
   * @param <T> the type of the objects in this index
   * @throws NullPointerException When indexName is null or empty
   */
  public <T> SearchIndex<T> initIndex(@Nonnull String indexName, @Nonnull Class<T> klass) {

    if (indexName == null || indexName.trim().length() == 0) {
      throw new NullPointerException("The index name is required");
    }

    return new SearchIndex<>(transport, config, indexName, klass);
  }

  /**
   * List all existing indexes
   *
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<IndicesResponse> listIndices() throws AlgoliaRuntimeException {
    return listIndices(new RequestOptions());
  }

  /**
   * List all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<IndicesResponse> listIndices(@Nonnull RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(listIndicesAsync(requestOptions));
  }

  /**
   * List asynchronously all existing indexes
   *
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<List<IndicesResponse>> listIndicesAsync() {
    return listIndicesAsync(new RequestOptions());
  }

  /**
   * List asynchronously all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<List<IndicesResponse>> listIndicesAsync(
      @Nonnull RequestOptions requestOptions) {
    return transport
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/indexes",
            CallType.READ,
            null,
            ListIndicesResponse.class,
            requestOptions)
        .thenApplyAsync(ListIndicesResponse::getIndices, config.getExecutor());
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<ApiKey> listApiKeys() throws AlgoliaRuntimeException {
    return listApiKeys(new RequestOptions());
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public List<ApiKey> listApiKeys(@Nonnull RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(listApiKeysAsync(requestOptions));
  }

  /**
   * List asynchronously all existing user keys with their associated ACLs
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<List<ApiKey>> listApiKeysAsync() {
    return listApiKeysAsync(new RequestOptions());
  }

  /**
   * List asynchronously all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<List<ApiKey>> listApiKeysAsync(@Nonnull RequestOptions requestOptions) {
    return transport
        .executeRequestAsync(
            HttpMethod.GET, "/1/keys", CallType.READ, null, ApiKeys.class, requestOptions)
        .thenApplyAsync(ApiKeys::getKeys, config.getExecutor());
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<ApiKey> getApiKeyAsync(@Nonnull String apiKey) {
    return getApiKeyAsync(apiKey);
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<ApiKey> getApiKeyAsync(
      @Nonnull String apiKey, @Nonnull RequestOptions requestOptions) {
    return transport.executeRequestAsync(
        HttpMethod.GET, "/1/keys/" + apiKey, CallType.READ, null, ApiKey.class, requestOptions);
  }
}

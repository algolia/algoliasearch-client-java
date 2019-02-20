package com.algolia.search.clients;

import com.algolia.search.exceptions.AlgoliaException;
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
import com.algolia.search.transport.IHttpTransport;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class SearchClient {

  private final IHttpTransport transport;
  private final AlgoliaConfig config;

  public SearchClient(@Nonnull String applicationID, @Nonnull String apiKey) {
    this(new SearchConfig(applicationID, apiKey));
  }

  public SearchClient(@Nonnull AlgoliaConfig config) {
    this(config, new AlgoliaHttpRequester());
  }

  public SearchClient(@Nonnull AlgoliaConfig config, @Nonnull IHttpRequester httpRequester) {

    if (httpRequester == null) {
      throw new NullPointerException("An httpRequester is required");
    }

    if (config == null) {
      throw new NullPointerException("A configuration is required");
    }

    if (config.getApplicationID() == null || config.getApplicationID().trim().length() == 0) {
      throw new NullPointerException("An ApplicationID is required");
    }

    if (config.getApiKey() == null || config.getApiKey().trim().length() == 0) {
      throw new NullPointerException("An API key is required");
    }

    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  public List<IndicesResponse> listIndices()
      throws InterruptedException, AlgoliaException, ExecutionException {
    return listIndicesAsync().get();
  }

  public CompletableFuture<List<IndicesResponse>> listIndicesAsync() throws AlgoliaException {
    return listIndicesAsync(null);
  }

  public CompletableFuture<List<IndicesResponse>> listIndicesAsync(RequestOptions requestOptions)
      throws AlgoliaException {
    return transport
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/indexes",
            CallType.READ,
            null,
            ListIndicesResponse.class,
            requestOptions)
        .thenApply(ListIndicesResponse::getIndices);
  }

  public CompletableFuture<List<ApiKey>> listApiKeysAsync() throws AlgoliaException {
    return transport
        .executeRequestAsync(HttpMethod.GET, "/1/keys", CallType.READ, null, ApiKeys.class, null)
        .thenApply(ApiKeys::getKeys);
  }
}

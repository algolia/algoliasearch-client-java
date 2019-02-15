package com.algolia.search.clients;

import com.algolia.search.http.AlgoliaHttpRequester;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.ListIndicesResponse;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.transport.HttpTransport;
import com.algolia.search.transport.IHttpTransport;
import java.io.IOException;
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

  public ListIndicesResponse listIndices() throws IOException {
    return transport.executeRequest(
        HttpMethod.GET, "/1/indexes", CallType.READ, null, ListIndicesResponse.class, null);
  }

  public ListIndicesResponse listIndices(RequestOptions requestOptions) throws IOException {
    return transport.executeRequest(
        HttpMethod.GET, "/1/indexes", CallType.READ, null, ListIndicesResponse.class, null);
  }
}

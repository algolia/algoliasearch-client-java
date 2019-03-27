package com.algolia.search;

import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * This contact allows you to inject your own HttpClient to any clients of this package. The default
 * implementation is {@link AlgoliaHttpRequester} which wraps the Apache async http client.
 */
public interface IHttpRequester {

  /**
   * Perform a request to the Algolia API.
   *
   * @param request The {@link AlgoliaHttpRequest} to send.
   * @return A completable future of a {@link AlgoliaHttpResponse}.
   */
  CompletableFuture<AlgoliaHttpResponse> performRequestAsync(AlgoliaHttpRequest request);

  /** Closes the resource. */
  void close() throws IOException;
}

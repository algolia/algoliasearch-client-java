package com.algolia.search;

import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Contract for the Http client. This interface allows you to inject your own http client to any
 * Algolia's client.
 */
public interface IHttpRequester {

  /**
   * Perform an asynchronous request to the Algolia API
   *
   * @param request the request to send
   * @return A completable future of an AlgoliaHttpResponse
   */
  CompletableFuture<AlgoliaHttpResponse> performRequestAsync(AlgoliaHttpRequest request);

  /** Closes the underlying http client. */
  void close() throws IOException;
}

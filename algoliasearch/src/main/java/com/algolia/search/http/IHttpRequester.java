package com.algolia.search.http;

import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.util.concurrent.CompletableFuture;

/** Contract for the Http client. */
public interface IHttpRequester {

  /**
   * Perform an asynchronous request to the Algolia API
   *
   * @param request the request to send
   */
  CompletableFuture<AlgoliaHttpResponse> performRequestAsync(AlgoliaHttpRequest request);
}

package com.algolia.search;

import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/** This contact allows you to inject your own HttpClient to any clients of this package. */
public interface HttpRequester {

  /**
   * Perform a request to the Algolia API.
   *
   * @param request The {@link HttpRequest} to send.
   * @return A completable future of a {@link HttpResponse}.
   */
  CompletableFuture<HttpResponse> performRequestAsync(HttpRequest request);

  /** Closes the resource. */
  void close() throws IOException;
}

package com.algolia.search;

import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/** This contract allows you to inject a custom HTTPClient to any Algolia clients of the library. */
public interface HttpRequester {

  /**
   * Perform an asynchronous request to the Algolia API.
   *
   * @param request The {@link HttpRequest} to send.
   * @return A completable future of a {@link HttpResponse}.
   */
  CompletableFuture<HttpResponse> performRequestAsync(HttpRequest request);

  /** Closes the underlying resources. */
  void close() throws IOException;
}

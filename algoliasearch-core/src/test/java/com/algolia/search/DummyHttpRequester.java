package com.algolia.search;

import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class DummyRequester implements HttpRequester {

  @Override
  public CompletableFuture<HttpResponse> performRequestAsync(HttpRequest request) {
    return null;
  }

  @Override
  public void close() throws IOException {}
}

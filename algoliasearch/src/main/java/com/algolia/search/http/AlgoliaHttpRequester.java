package com.algolia.search.http;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.helpers.HttpStatusCodeHelper;
import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import org.asynchttpclient.*;

public class AlgoliaHttpRequester implements IHttpRequester {

  private final AsyncHttpClient asyncHttpClient;

  public AlgoliaHttpRequester() {
    DefaultAsyncHttpClientConfig.Builder clientBuilder =
        Dsl.config().setCompressionEnforced(true).setConnectTimeout(500).setKeepAlive(true);

    asyncHttpClient = Dsl.asyncHttpClient(clientBuilder);
  }

  public CompletableFuture<AlgoliaHttpResponse> performRequestAsync(AlgoliaHttpRequest request) {
    BoundRequestBuilder requestToSend = buildRequest(request);
    return requestToSend
        .execute()
        .toCompletableFuture()
        .thenApplyAsync(this::buildResponse)
        .exceptionally(
            t -> {
              if (t.getCause() instanceof TimeoutException) {
                return new AlgoliaHttpResponse(true);
              }
              throw new AlgoliaRuntimeException(t);
            });
  }

  private AlgoliaHttpResponse buildResponse(Response response) {
    if (HttpStatusCodeHelper.isSuccess(response.getStatusCode())) {
      return new AlgoliaHttpResponse(response.getStatusCode(), response.getResponseBodyAsStream());
    }
    return new AlgoliaHttpResponse(response.getStatusCode(), response.getResponseBody());
  }

  private BoundRequestBuilder buildRequest(AlgoliaHttpRequest algoliaHttpRequest) {
    Request request =
        new RequestBuilder(algoliaHttpRequest.getMethod().toString())
            .setUrl(algoliaHttpRequest.getUri().toString())
            .setMethod(algoliaHttpRequest.getMethod().toString())
            .setSingleHeaders(algoliaHttpRequest.getHeaders())
            .setBody(algoliaHttpRequest.getBody())
            .setRequestTimeout(algoliaHttpRequest.getTimeout())
            .build();

    return asyncHttpClient.prepareRequest(request);
  }
}

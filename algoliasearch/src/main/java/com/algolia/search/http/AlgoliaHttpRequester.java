package com.algolia.search.http;

import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
        .execute(
            new AsyncCompletionHandler<AlgoliaHttpResponse>() {

              @Override
              public void onThrowable(Throwable t) {}

              @Override
              public AlgoliaHttpResponse onCompleted(Response response) {
                return new AlgoliaHttpResponse(
                    response.getStatusCode(), response.getResponseBodyAsStream());
              }
            })
        .toCompletableFuture();
  }

  public AlgoliaHttpResponse performRequest(AlgoliaHttpRequest request) {

    AlgoliaHttpResponse response = null;
    BoundRequestBuilder requestToSend = buildRequest(request);
    Future<Response> responseFuture = requestToSend.execute();

    try {

      Response resp = responseFuture.get();
      response = new AlgoliaHttpResponse(resp.getStatusCode(), resp.getResponseBodyAsStream());

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return response;
  }

  private BoundRequestBuilder buildRequest(AlgoliaHttpRequest algoliaHttpRequest) {
    Request request =
        new RequestBuilder(algoliaHttpRequest.getMethod().toString())
            .setUrl(algoliaHttpRequest.getUri().toString())
            .setMethod(algoliaHttpRequest.getMethod().toString())
            .setHeaders(algoliaHttpRequest.getHeaders())
            .build();

    return asyncHttpClient.prepareRequest(request);
  }
}

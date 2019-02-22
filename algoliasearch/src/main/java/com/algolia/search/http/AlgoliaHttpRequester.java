package com.algolia.search.http;

import com.algolia.search.clients.AlgoliaConfig;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.helpers.HttpStatusCodeHelper;
import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import org.asynchttpclient.*;

public class AlgoliaHttpRequester implements IHttpRequester {

  private final AsyncHttpClient asyncHttpClient;
  private final AlgoliaConfig config;

  public AlgoliaHttpRequester(AlgoliaConfig config) {
    this.config = config;
    DefaultAsyncHttpClientConfig.Builder clientBuilder =
        Dsl.config().setCompressionEnforced(true).setConnectTimeout(500).setKeepAlive(true);

    asyncHttpClient = Dsl.asyncHttpClient(clientBuilder);
  }

  /**
   * Sends the http request asynchronously to the API If the request is time out it creates a new
   * response object with timeout set to true Otherwise it throws a run time exception
   *
   * @param request the request to send
   * @throws AlgoliaRuntimeException When an error occurred processing the request on the server
   *     side
   */
  public CompletableFuture<AlgoliaHttpResponse> performRequestAsync(AlgoliaHttpRequest request) {
    BoundRequestBuilder requestToSend = buildRequest(request);
    return requestToSend
        .execute()
        .toCompletableFuture()
        .thenApplyAsync(this::buildResponse, config.getExecutor())
        .exceptionally(
            t -> {
              if (t.getCause() instanceof TimeoutException) {
                return new AlgoliaHttpResponse(true);
              }
              throw new AlgoliaRuntimeException(t);
            });
  }

  /**
   * Builds an Algolia response from the server response
   *
   * @param response The server response
   */
  private AlgoliaHttpResponse buildResponse(Response response) {
    if (HttpStatusCodeHelper.isSuccess(response.getStatusCode())) {
      return new AlgoliaHttpResponse(response.getStatusCode(), response.getResponseBodyAsStream());
    }
    return new AlgoliaHttpResponse(response.getStatusCode(), response.getResponseBody());
  }

  /**
   * Builds an http request from an AlgoliaRequest object
   *
   * @param algoliaHttpRequest The Algolia request object
   */
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

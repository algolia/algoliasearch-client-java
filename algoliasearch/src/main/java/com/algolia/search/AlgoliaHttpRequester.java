package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.helpers.AlgoliaHelper;
import com.algolia.search.helpers.HttpStatusCodeHelper;
import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

public class AlgoliaHttpRequester implements IHttpRequester {

  private final CloseableHttpAsyncClient asyncHttpClient;
  private final RequestConfig requestConfig;
  private final AlgoliaConfig config;

  AlgoliaHttpRequester(AlgoliaConfig config) {

    this.config = config;

    int connectTimeOut =
        config.getConnectTimeOut() != null
            ? config.getConnectTimeOut()
            : Defaults.CONNECT_TIMEOUT_MS;

    requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(connectTimeOut)
            .setContentCompressionEnabled(true)
            .build();

    asyncHttpClient = HttpAsyncClients.createDefault();
    asyncHttpClient.start();
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
    HttpRequestBase requestToSend = buildRequest(request);
    return AlgoliaHelper.toCompletableFuture(fc -> asyncHttpClient.execute(requestToSend, fc))
        .thenApplyAsync(this::buildResponse, config.getExecutor())
        .exceptionally(
            t -> {
              if (t.getCause() instanceof ConnectTimeoutException
                  || t.getCause() instanceof SocketTimeoutException
                  || t.getCause() instanceof ConnectException) {
                return new AlgoliaHttpResponse(true);
              }
              throw new AlgoliaRuntimeException(t);
            });
  }

  /**
   * Closes the underlying http client.
   *
   * @throws AlgoliaRuntimeException if an I/O error occurs
   */
  public void close() {
    try {
      asyncHttpClient.close();
    } catch (IOException e) {
      throw new AlgoliaRuntimeException(e);
    }
  }

  /**
   * Builds an Algolia response from the server response
   *
   * @param response The server response
   */
  private AlgoliaHttpResponse buildResponse(HttpResponse response) {
    try {
      if (HttpStatusCodeHelper.isSuccess(response.getStatusLine().getStatusCode())) {
        return new AlgoliaHttpResponse(
            response.getStatusLine().getStatusCode(), response.getEntity().getContent());
      }
      return new AlgoliaHttpResponse(
          response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
    } catch (IOException e) {
      throw new AlgoliaRuntimeException(e);
    }
  }

  /**
   * Builds an http request from an AlgoliaRequest object
   *
   * @param algoliaRequest The Algolia request object
   */
  private HttpRequestBase buildRequest(AlgoliaHttpRequest algoliaRequest) {

    switch (algoliaRequest.getMethod().toString()) {
      case HttpGet.METHOD_NAME:
        HttpGet get = new HttpGet(algoliaRequest.getUri().toString());
        get.setConfig(buildRequestConfig(algoliaRequest));
        return addHeaders(get, algoliaRequest.getHeaders());

      case HttpDelete.METHOD_NAME:
        HttpDelete delete = new HttpDelete(algoliaRequest.getUri().toString());
        delete.setConfig(buildRequestConfig(algoliaRequest));
        return addHeaders(delete, algoliaRequest.getHeaders());

      case HttpPost.METHOD_NAME:
        HttpPost post = new HttpPost(algoliaRequest.getUri().toString());
        if (algoliaRequest.getBody() != null) post.setEntity(addEntity(algoliaRequest.getBody()));
        post.setConfig(buildRequestConfig(algoliaRequest));
        return addHeaders(post, algoliaRequest.getHeaders());

      case HttpPut.METHOD_NAME:
        HttpPut put = new HttpPut(algoliaRequest.getUri().toString());
        if (algoliaRequest.getBody() != null) put.setEntity(addEntity(algoliaRequest.getBody()));
        put.setConfig(buildRequestConfig(algoliaRequest));
        return addHeaders(put, algoliaRequest.getHeaders());

      default:
        throw new UnsupportedOperationException(
            "HTTP method not supported: " + algoliaRequest.getMethod().toString());
    }
  }

  private RequestConfig buildRequestConfig(AlgoliaHttpRequest algoliaRequest) {
    return RequestConfig.copy(requestConfig).setSocketTimeout(algoliaRequest.getTimeout()).build();
  }

  private HttpRequestBase addHeaders(HttpRequestBase request, Map<String, String> headers) {
    headers.forEach(request::addHeader);
    return request;
  }

  private HttpEntity addEntity(InputStream data) {
    return EntityBuilder.create()
        .setStream(data)
        .setContentType(ContentType.APPLICATION_JSON)
        .build();
  }
}

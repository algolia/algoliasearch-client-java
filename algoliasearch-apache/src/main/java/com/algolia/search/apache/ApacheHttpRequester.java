package com.algolia.search.apache;

import com.algolia.search.ConfigBase;
import com.algolia.search.Defaults;
import com.algolia.search.HttpRequester;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import com.algolia.search.util.HttpStatusCodeUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

/**
 * The Algolia http requester is a wrapper on top of the HttpAsyncClient of Apache. It's an
 * implementation of {@link HttpRequester} It takes an {@link HttpRequest} as input. It returns an
 * {@link HttpResponse}.
 */
public class ApacheHttpRequester implements HttpRequester {

  private final CloseableHttpAsyncClient asyncHttpClient;
  private final RequestConfig requestConfig;
  private final ConfigBase config;

  ApacheHttpRequester(@Nonnull ConfigBase config) {

    this.config = config;

    requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeOut())
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
   * @throws AlgoliaRuntimeException When an error occurred while sending the request
   */
  public CompletableFuture<HttpResponse> performRequestAsync(HttpRequest request) {
    HttpRequestBase requestToSend = buildRequest(request);
    return toCompletableFuture(fc -> asyncHttpClient.execute(requestToSend, fc))
        .thenApplyAsync(this::buildResponse, config.getExecutor())
        .exceptionally(
            t -> {
              if (t.getCause() instanceof ConnectTimeoutException
                  || t.getCause() instanceof SocketTimeoutException
                  || t.getCause() instanceof ConnectException
                  || t.getCause() instanceof TimeoutException) {
                return new HttpResponse(true);
              }
              throw new AlgoliaRuntimeException(t);
            });
  }

  /** Closes the http client. */
  public void close() throws IOException {
    asyncHttpClient.close();
  }

  /**
   * Builds an Algolia response from the server response
   *
   * @param response The server response
   */
  private HttpResponse buildResponse(org.apache.http.HttpResponse response) {
    try {
      if (HttpStatusCodeUtils.isSuccess(response.getStatusLine().getStatusCode())) {

        HttpEntity entity = handleCompressedEntity(response.getEntity());

        return new HttpResponse(response.getStatusLine().getStatusCode(), entity.getContent());
      }
      return new HttpResponse(
          response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
    } catch (IOException e) {
      throw new AlgoliaRuntimeException(e);
    }
  }

  /**
   * Builds an Apache HttpRequest from an Algolia Request object
   *
   * @param algoliaRequest The Algolia request object
   */
  private HttpRequestBase buildRequest(HttpRequest algoliaRequest) {

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

      case HttpPatch.METHOD_NAME:
        HttpPatch patch = new HttpPatch(algoliaRequest.getUri().toString());
        if (algoliaRequest.getBody() != null) patch.setEntity(addEntity(algoliaRequest.getBody()));
        patch.setConfig(buildRequestConfig(algoliaRequest));
        return addHeaders(patch, algoliaRequest.getHeaders());

      default:
        throw new UnsupportedOperationException(
            "HTTP method not supported: " + algoliaRequest.getMethod().toString());
    }
  }

  private RequestConfig buildRequestConfig(HttpRequest algoliaRequest) {
    return RequestConfig.copy(requestConfig).setSocketTimeout(algoliaRequest.getTimeout()).build();
  }

  private HttpRequestBase addHeaders(
      org.apache.http.client.methods.HttpRequestBase request, Map<String, String> headers) {
    headers.forEach(request::addHeader);
    return request;
  }

  private HttpEntity addEntity(InputStream data) {
    return EntityBuilder.create()
        .setStream(data)
        .setContentType(ContentType.APPLICATION_JSON)
        .build();
  }

  private static HttpEntity handleCompressedEntity(org.apache.http.HttpEntity entity) {

    Header contentEncoding = entity.getContentEncoding();

    if (contentEncoding != null)
      for (HeaderElement e : contentEncoding.getElements()) {
        if (Defaults.CONTENT_ENCODING_GZIP.equalsIgnoreCase(e.getName())) {
          return new GzipDecompressingEntity(entity);
        }

        if (Defaults.CONTENT_ENCODING_DEFLATE.equalsIgnoreCase(e.getName())) {
          return new DeflateDecompressingEntity(entity);
        }
      }

    return entity;
  }

  private static CompletableFuture<org.apache.http.HttpResponse> toCompletableFuture(
      Consumer<FutureCallback<org.apache.http.HttpResponse>> c) {
    CompletableFuture<org.apache.http.HttpResponse> promise = new CompletableFuture<>();

    c.accept(
        new FutureCallback<org.apache.http.HttpResponse>() {
          @Override
          public void completed(org.apache.http.HttpResponse t) {
            promise.complete(t);
          }

          @Override
          public void failed(Exception e) {
            promise.completeExceptionally(e);
          }

          @Override
          public void cancelled() {
            promise.cancel(true);
          }
        });
    return promise;
  }
}

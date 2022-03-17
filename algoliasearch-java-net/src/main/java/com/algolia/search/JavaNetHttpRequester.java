package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import com.algolia.search.util.HttpStatusCodeUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;
import javax.annotation.Nonnull;

/** Implementation of {@code HttpRequester} for the built-in Java.net 11 HTTP Client */
public final class JavaNetHttpRequester implements HttpRequester {

  /** Reusable instance of the httpClient. */
  private final HttpClient client;

  /**
   * Build the reusable instance of httpClient with the given configuration.
   *
   * @param config HTTPClient agnostic Algolia's configuration.
   */
  public JavaNetHttpRequester(@Nonnull ConfigBase config) {
    client =
        HttpClient.newBuilder()
            .executor(config.getExecutor())
            .version(HttpClient.Version.HTTP_2)
            .sslParameters(SSLUtils.getDefaultSSLParameters())
            .followRedirects(HttpClient.Redirect.NEVER)
            .proxy(ProxySelector.getDefault())
            .connectTimeout(Duration.ofMillis(config.getConnectTimeOut()))
            .build();
  }

  /**
   * Sends the http request asynchronously to the API If the request is time out it creates a new
   * response object with timeout set to true Otherwise it throws a run time exception
   *
   * @param request the request to send
   * @throws AlgoliaRuntimeException When an error occurred processing the request on the server
   *     side
   */
  public CompletableFuture<HttpResponse> performRequestAsync(@Nonnull HttpRequest request) {
    return client
        .sendAsync(buildRequest(request), BodyHandlers.ofInputStream())
        .thenApply(this::buildResponse)
        .exceptionally(
            t -> {
              if (t.getCause() instanceof HttpConnectTimeoutException
                  || t.getCause() instanceof HttpTimeoutException) {
                return new HttpResponse(true);
              } else if (t.getCause() instanceof SecurityException
                  || t.getCause() instanceof IOException
                  || t.getCause() instanceof InterruptedException) {
                return new HttpResponse().setNetworkError(true);
              }
              throw new AlgoliaRuntimeException(t);
            });
  }

  /**
   * Builds an Algolia response from the server response
   *
   * @param response The server response
   */
  private HttpResponse buildResponse(java.net.http.HttpResponse<InputStream> response) {
    if (HttpStatusCodeUtils.isSuccess(response.statusCode())) {
      return new HttpResponse(response.statusCode(), responseBodyHandler(response));
    }

    return new HttpResponse(response.statusCode(), convertStreamToString(response.body()));
  }

  /**
   * Builds an http request from an AlgoliaRequest object
   *
   * @param algoliaRequest The Algolia request object
   */
  private java.net.http.HttpRequest buildRequest(@Nonnull HttpRequest algoliaRequest) {
    java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder();

    buildHeaders(builder, algoliaRequest.getHeaders());
    buildURI(builder, algoliaRequest.getUri());
    builder.timeout(Duration.ofMillis(algoliaRequest.getTimeout()));

    BodyPublisher body = buildRequestBody(builder, algoliaRequest);
    builder.method(algoliaRequest.getMethod().toString(), body);

    return builder.build();
  }

  /**
   * Build the body for the request builder. Handling compression type of the request.
   *
   * @param builder Request Builder
   * @param algoliaRequest HttpClient agnostic Algolia's request
   */
  private BodyPublisher buildRequestBody(
      @Nonnull Builder builder, @Nonnull HttpRequest algoliaRequest) {

    if (algoliaRequest.getBody() == null) {
      return java.net.http.HttpRequest.BodyPublishers.noBody();
    }

    if (algoliaRequest.canCompress()) {
      builder.header(Defaults.CONTENT_ENCODING_HEADER, Defaults.CONTENT_ENCODING_GZIP);
    } else {
      builder.header(Defaults.CONTENT_TYPE_HEADER, Defaults.APPLICATION_JSON);
    }

    return BodyPublishers.ofInputStream(algoliaRequest::getBody);
  }

  /**
   * Handles compressed response. Basically wraps the InputStream in a GZIPInputStream.
   *
   * @param response Server's response
   */
  private InputStream responseBodyHandler(java.net.http.HttpResponse<InputStream> response) {
    String encoding = response.headers().firstValue(Defaults.CONTENT_ENCODING_HEADER).orElse("");
    InputStream ret;

    if (encoding.equals(Defaults.CONTENT_ENCODING_GZIP)) {
      try {
        ret = new GZIPInputStream(response.body());
      } catch (IOException e) {
        throw new AlgoliaRuntimeException(e);
      }
    } else {
      ret = response.body();
    }

    return ret;
  }

  /**
   * Builds a friendly URI Object for Java.net HTTP Client
   *
   * @param builder Request Builder
   * @param url HttpClient agnostic Algolia's URL
   */
  private void buildURI(@Nonnull Builder builder, @Nonnull URL url) {
    try {
      builder.uri(url.toURI());
    } catch (URISyntaxException e) {
      throw new AlgoliaRuntimeException(e);
    }
  }

  /**
   * Builds a friendly Headers for Java's HTTPClient
   *
   * @param builder Request Builder
   * @param headers HttpClient agnostic Algolia's headers
   */
  private void buildHeaders(@Nonnull Builder builder, @Nonnull Map<String, String> headers) {
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      builder.header(entry.getKey(), entry.getValue());
    }
  }

  private String convertStreamToString(InputStream is) {
    Scanner s = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  /** Nothing to do here. Java.net HTTP Client is not closeable. */
  @Override
  public void close() {
    /*
     Nothing to do here. Java 11 HTTP Client is not closeable.
    */
  }
}

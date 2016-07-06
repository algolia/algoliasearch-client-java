package com.algolia.search;

import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaHttpRequest;
import com.algolia.search.http.AlgoliaHttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.urlfetch.*;
import com.palominolabs.http.url.UrlBuilder;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

class AppEngineHttpClient extends AlgoliaHttpClient {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private final ObjectMapper objectMapper;
  private final List<String> queryHosts;
  private final List<String> buildHosts;
  private final FetchOptions defaultFetchOptions;
  private final Map<String, String> headers;
  private final URLFetchService fetchService;

  AppEngineHttpClient(APIClientConfiguration configuration) {
    this.objectMapper = configuration.getObjectMapper();
    this.queryHosts = configuration.getQueryHosts();
    this.buildHosts = configuration.getBuildHosts();
    this.headers = configuration.getHeaders();

    this.fetchService = URLFetchServiceFactory.getURLFetchService();
    this.defaultFetchOptions = FetchOptions
      .Builder
      .withDeadline(configuration.getConnectTimeout() + configuration.getReadTimeout())
      .followRedirects()
      .validateCertificate();
  }

  @Override
  protected AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException {
    HTTPRequest httpRequest = build(request);
    HTTPResponse httpResponse = fetchService.fetch(httpRequest);

    return from(httpResponse);
  }

  private AlgoliaHttpResponse from(final HTTPResponse httpResponse) {


    return new AlgoliaHttpResponse() {
      @Override
      public int getStatusCode() {
        return httpResponse.getResponseCode();
      }

      @Override
      public Reader getBody() throws IOException {
        if(hasGzip(httpResponse.getHeaders())) {
          return new InputStreamReader(
            new GZIPInputStream(new ByteArrayInputStream(httpResponse.getContent())),
            UTF8
          );
        }
        return new InputStreamReader(new ByteArrayInputStream(httpResponse.getContent()), UTF8);
      }
    };
  }

  private boolean hasGzip(List<HTTPHeader> headers) {
    for (HTTPHeader header : headers) {
      if(header.getName().equalsIgnoreCase("Content-Encoding") && header.getValue().toLowerCase().contains("gzip")) {
        return true;
      }
    }
    return false;
  }

  protected HTTPRequest build(AlgoliaHttpRequest request) throws MalformedURLException, CharacterCodingException {
    HTTPRequest httpRequest = new HTTPRequest(
      toUrl(request),
      HTTPMethod.valueOf(request.getMethod().name),
      defaultFetchOptions);

    for (Map.Entry<String, String> entry : headers.entrySet()) {
      httpRequest.addHeader(new HTTPHeader(entry.getKey(), entry.getValue()));
    }

    if (request.hasContent()) {
      httpRequest.setPayload(request.getContent().getBytes());
    }

    return httpRequest;
  }

  private URL toUrl(AlgoliaHttpRequest request) throws CharacterCodingException, MalformedURLException {
    UrlBuilder urlBuilder = UrlBuilder.forHost("https", request.getHost());

    for (String p : request.getPath()) {
      urlBuilder = urlBuilder.pathSegment(p);
    }

    for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
      urlBuilder = urlBuilder.queryParam(entry.getKey(), entry.getValue());
    }

    return new URL(urlBuilder.toUrlString());
  }

  @Override
  protected ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  @Override
  public List<String> getQueryHosts() {
    return queryHosts;
  }

  @Override
  public List<String> getBuildHosts() {
    return buildHosts;
  }
}

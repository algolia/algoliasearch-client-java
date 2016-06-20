package com.algolia.search;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class APIClientBuilderTest {

  @Test
  public void buildAPIClient() throws IOException {
    APIClient apiClient =
      new ApacheAPIClientBuilder("appId", "apiKey")
        .setConnectTimeout(100)
        .setReadTimeout(200)
        .setExtraHeader("header", "value")
        .build();

    assertThat(apiClient.httpClient.buildHosts).containsOnly(
      "appId.algolia.net",
      "appId-1.algolianet.com",
      "appId-2.algolianet.com",
      "appId-3.algolianet.com"
    );
    assertThat(apiClient.httpClient.queryHosts).containsOnly(
      "appId-dsn.algolia.net",
      "appId-1.algolianet.com",
      "appId-2.algolianet.com",
      "appId-3.algolianet.com"
    );

    HttpRequest request = apiClient.httpClient.requestFactory.buildGetRequest(new GenericUrl("https://algolia.com"));
    assertThat(request.getConnectTimeout()).isEqualTo(100);
    assertThat(request.getReadTimeout()).isEqualTo(200);

    HttpHeaders headers = request.getHeaders();
    assertThat(headers.getUserAgent()).contains("Algolia for Java 1.8").contains("API 2.0.0");
    assertThat(headers).containsOnlyKeys(
      "x-algolia-api-key",
      "user-agent",
      "header",
      "accept-encoding",
      "content-type",
      "x-algolia-application-id"
    );
    assertThat(headers).contains(
      entry("x-algolia-api-key", "apiKey"),
      entry("header", "value"),
      entry("accept-encoding", Collections.singletonList("gzip")),
      entry("content-type", Collections.singletonList("application/json; charset=UTF-8")),
      entry("x-algolia-application-id", "appId")
    );
  }

  @Test
  public void buildAPIClientWithCustomUserAgent() throws IOException {
    APIClient apiClient =
      new ApacheAPIClientBuilder("appId", "apiKey")
        .setUserAgent("agent", "1.0.0")
        .build();

    HttpRequest request = apiClient.httpClient.requestFactory.buildGetRequest(new GenericUrl("https://algolia.com"));
    HttpHeaders headers = request.getHeaders();

    assertThat(headers.getUserAgent()).contains("Algolia for Java 1.8").contains("API 2.0.0 agent 1.0.0");
  }

}

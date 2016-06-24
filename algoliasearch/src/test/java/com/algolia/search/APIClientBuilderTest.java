package com.algolia.search;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class APIClientBuilderTest {

  @Test
  public void buildAPIClient() throws IOException {
    APIClient apiClient =
      new ApacheAPIClientBuilder("appId", "apiKey")
        .setConnectTimeout(100)
        .setReadTimeout(200)
        .setExtraHeader("header", "value")
        .build();

    assertThat(apiClient.httpClient.getBuildHosts()).containsOnly(
      "appId.algolia.net",
      "appId-1.algolianet.com",
      "appId-2.algolianet.com",
      "appId-3.algolianet.com"
    );
    assertThat(apiClient.httpClient.getQueryHosts()).containsOnly(
      "appId-dsn.algolia.net",
      "appId-1.algolianet.com",
      "appId-2.algolianet.com",
      "appId-3.algolianet.com"
    );
  }
}

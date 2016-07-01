package com.algolia.search;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

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

    assertThat(apiClient.configuration).isEqualToComparingFieldByField(new APIClientConfiguration()
      .setApplicationId("appId")
      .setApiKey("apiKey")
      .setConnectTimeout(100)
      .setReadTimeout(200)
      .setHeaders(ImmutableMap.<String, String>builder()
        .put("Accept-Encoding", "gzip")
        .put("Content-Type", "application")
        .put("User-Agent", "Algolia for Java 1.8.0_66 API 2.0.0")
        .put("X-Algolia-API-Key", "apiKey")
        .put("X-Algolia-Application-Id", "appId")
        .put("header", "value")
        .build()
      )
      .setBuildHosts(Arrays.asList(
        "appId.algolia.net",
        "appId-1.algolianet.com",
        "appId-2.algolianet.com",
        "appId-3.algolianet.com"
      ))
      .setQueryHosts(Arrays.asList(
        "appId-dsn.algolia.net",
        "appId-1.algolianet.com",
        "appId-2.algolianet.com",
        "appId-3.algolianet.com"
      ))
      .setObjectMapper(Defaults.DEFAULT_OBJECT_MAPPER)
    );
  }
}

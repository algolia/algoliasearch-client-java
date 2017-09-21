package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

public class AsyncHttpAPIClientBuilderTest {

  @Test
  public void buildAPIClient() throws IOException {
    AsyncAPIClient apiClient =
        new AsyncHttpAPIClientBuilder("appId", "apiKey")
            .setConnectTimeout(100)
            .setReadTimeout(200)
            .setExtraHeader("header", "value")
            .build();

    assertThat(apiClient.configuration)
        .isEqualToIgnoringGivenFields(
            new APIClientConfiguration()
                .setApplicationId("appId")
                .setApiKey("apiKey")
                .setConnectTimeout(100)
                .setReadTimeout(200)
                .setBuildHosts(
                    Arrays.asList(
                        "appId.algolia.net",
                        "appId-1.algolianet.com",
                        "appId-2.algolianet.com",
                        "appId-3.algolianet.com"))
                .setQueryHosts(
                    Arrays.asList(
                        "appId-dsn.algolia.net",
                        "appId-1.algolianet.com",
                        "appId-2.algolianet.com",
                        "appId-3.algolianet.com"))
                .setObjectMapper(Defaults.DEFAULT_OBJECT_MAPPER),
            "headers",
            "buildHosts",
            "queryHosts",
            "executor");

    assertThat(apiClient.configuration.getHeaders())
        .containsOnlyKeys(
            "Accept-Encoding",
            "Content-Type",
            "User-Agent",
            "X-Algolia-API-Key",
            "X-Algolia-Application-Id",
            "header")
        .containsEntry("Accept-Encoding", "gzip")
        .containsEntry("Content-Type", "application")
        .containsEntry("X-Algolia-API-Key", "apiKey")
        .containsEntry("X-Algolia-Application-Id", "appId")
        .containsEntry("header", "value");

    assertThat(apiClient.configuration.getBuildHosts())
        .containsOnly(
            "appId.algolia.net",
            "appId-1.algolianet.com",
            "appId-2.algolianet.com",
            "appId-3.algolianet.com");

    assertThat(apiClient.configuration.getQueryHosts())
        .containsOnly(
            "appId-dsn.algolia.net",
            "appId-1.algolianet.com",
            "appId-2.algolianet.com",
            "appId-3.algolianet.com");
  }
}

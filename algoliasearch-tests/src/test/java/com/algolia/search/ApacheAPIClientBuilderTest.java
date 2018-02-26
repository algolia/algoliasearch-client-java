package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.apache.http.HttpHost;
import org.junit.Test;

public class ApacheAPIClientBuilderTest {

  @Test
  public void buildAPIClient() {
    APIClient apiClient =
        new ApacheAPIClientBuilder("appId", "apiKey")
            .setConnectTimeout(100)
            .setReadTimeout(200)
            .addExtraHeader("header", "value")
            .setProxy(new HttpHost("toto.com"))
            .build();

    assertThat(apiClient.configuration)
        .isEqualToIgnoringGivenFields(
            new APIClientConfiguration()
                .setApplicationId("appId")
                .setApiKey("apiKey")
                .setMaxConnTotal(10)
                .setConnectTimeout(100)
                .setReadTimeout(200)
                .setHostDownTimeout(300000)
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
            "queryHosts");

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

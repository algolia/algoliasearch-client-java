package com.algolia.search;

import com.google.common.net.HttpHeaders;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;

public class APIClientBuilderTest {

  @Test
  public void randomizeQueryHosts() {
    APIClientBuilder builder = new APIClientBuilder("appid", "apikey") {

      @Override
      protected APIClient build(@Nonnull APIClientConfiguration configuration) {
        assertThat(configuration.getQueryHosts()).hasSize(4);
        assertThat(configuration.getQueryHosts().get(0)).isEqualTo("appid-dsn.algolia.net");
        assertThat(configuration.getQueryHosts().get(1)).matches("^appid-\\d.algolianet.com$");
        assertThat(configuration.getQueryHosts().get(2)).matches("^appid-\\d.algolianet.com$");
        assertThat(configuration.getQueryHosts().get(3)).matches("^appid-\\d.algolianet.com$");
        return null;
      }
    };

    builder.build();
  }

  @Test
  public void randomizeBuildHosts() {
    APIClientBuilder builder = new APIClientBuilder("appid", "apikey") {

      @Override
      protected APIClient build(@Nonnull APIClientConfiguration configuration) {
        assertThat(configuration.getBuildHosts()).hasSize(4);
        assertThat(configuration.getBuildHosts().get(0)).isEqualTo("appid.algolia.net");
        assertThat(configuration.getBuildHosts().get(1)).matches("^appid-\\d.algolianet.com$");
        assertThat(configuration.getBuildHosts().get(2)).matches("^appid-\\d.algolianet.com$");
        assertThat(configuration.getBuildHosts().get(3)).matches("^appid-\\d.algolianet.com$");
        return null;
      }
    };

    builder.build();
  }

  @Test
  public void getUserAgent() {
    APIClientBuilder builder = new APIClientBuilder("appid", "apikey") {
      @Override
      protected APIClient build(@Nonnull APIClientConfiguration configuration) {
        assertThat(configuration.getHeaders().get(HttpHeaders.USER_AGENT)).matches("Algolia for Java \\([^)]+\\); JVM \\([^)]+\\)");
        return null;
      }
    };

    builder.build();
  }

}
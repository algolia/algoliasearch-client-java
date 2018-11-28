package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import com.algolia.search.exceptions.AlgoliaException;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;
import org.junit.Before;
import org.junit.Test;

public class ApacheHttpClientTest {

  private static String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  private static String ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  private APIClientConfiguration defaultConfig;

  @Before
  public void checkEnvVariables() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_1 is not defined or empty");
    }
    if (ALGOLIA_API_KEY_1 == null || ALGOLIA_API_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_1 is not defined or empty");
    }
  }

  @Before
  public void before() {
    defaultConfig =
        new APIClientConfiguration(
            ALGOLIA_APPLICATION_ID_1,
            ALGOLIA_API_KEY_1,
            Defaults.DEFAULT_OBJECT_MAPPER,
            Defaults.ANALYTICS_HOST,
            Collections.singletonList(ALGOLIA_APPLICATION_ID_1 + ".algolia.net"),
            Collections.singletonList(ALGOLIA_APPLICATION_ID_1 + "-dsn.algolia.net"),
            ImmutableMap.of(
                "X-Algolia-Application-Id", ALGOLIA_APPLICATION_ID_1,
                "X-Algolia-API-Key", ALGOLIA_API_KEY_1),
            1000,
            2000,
            1000,
            10,
            5);
  }

  private APIClient build(String... hosts) {
    APIClientConfiguration configuration =
        new APIClientConfiguration(
            ALGOLIA_APPLICATION_ID_1,
            ALGOLIA_API_KEY_1,
            Defaults.DEFAULT_OBJECT_MAPPER,
            Defaults.ANALYTICS_HOST,
            Arrays.asList(hosts),
            Arrays.asList(hosts),
            ImmutableMap.of(
                "X-Algolia-Application-Id", ALGOLIA_APPLICATION_ID_1,
                "X-Algolia-API-Key", ALGOLIA_API_KEY_1),
            1000,
            2000,
            1000,
            10,
            5);
    ApacheHttpClient apache =
        new ApacheHttpClient(configuration, new ApacheHttpClientConfiguration());
    return new APIClient(apache, configuration);
  }

  private <T> void assertThatItTookLessThan(long duration, Callable<T> callable) throws Exception {
    Long start = System.currentTimeMillis();
    callable.call();
    Long end = System.currentTimeMillis();
    assertThat(end - start < duration)
        .overridingErrorMessage(
            "should have taken less than " + duration + "ms, but took " + (end - start) + "ms.")
        .isTrue();
  }

  @Test
  public void shouldHandleTimeoutsInDns() throws Exception {
    APIClient client = build("java-dsn.algolia.biz", ALGOLIA_APPLICATION_ID_1 + "-dsn.algolia.net");

    assertThatItTookLessThan(3 * 1000, () -> assertThat(client.listIndexes()).isNotNull());
  }

  @Test
  public void shouldHandleConnectTimeout() throws Exception {
    APIClient client =
        build("notcp-xx-1.algolianet.com", ALGOLIA_APPLICATION_ID_1 + "-dsn.algolia.net");

    assertThatItTookLessThan(3 * 1000, () -> assertThat(client.listIndexes()).isNotNull());
  }

  @Test
  public void shouldHandleMultipleConnectTimeout() throws Exception {
    APIClient client = build("notcp-xx-1.algolia.net", "notcp-xx-1.algolianet.com");

    assertThatItTookLessThan(
        3 * 1000,
        () -> assertThatExceptionOfType(AlgoliaException.class).isThrownBy(client::listIndexes));
  }

  @Test
  public void shouldHandleConnectionResetException() throws Exception {
    Thread runnable =
        new Thread(
            () -> {
              try {
                ServerSocket serverSocket = new ServerSocket(8080);
                Socket socket = serverSocket.accept();
                socket.setSoLinger(true, 0);
                socket.close();
              } catch (IOException ignored) {
              }
            });

    runnable.start();

    APIClient client = build("localhost:8080", ALGOLIA_APPLICATION_ID_1 + "-1.algolianet.com");

    assertThatItTookLessThan(2 * 1000, client::listIndexes);
  }

  @Test
  public void shouldHandleSNI() throws Exception {
    APIClient client = build(ALGOLIA_APPLICATION_ID_1 + "-1.algolianet.com");
    assertThat(client.listIndexes()).isNotEmpty();
  }
}

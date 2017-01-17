package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ApacheHttpClientTest {

  private static String APPLICATION_ID = System.getenv("APPLICATION_ID");
  private static String API_KEY = System.getenv("API_KEY");
  private APIClientConfiguration defaultConfig;

  @Before
  public void checkEnvVariables() throws Exception {
    if (APPLICATION_ID == null || APPLICATION_ID.isEmpty()) {
      throw new Exception("APPLICATION_ID is not defined or empty");
    }
    if (API_KEY == null || API_KEY.isEmpty()) {
      throw new Exception("API_KEY is not defined or empty");
    }
  }

  @Before
  public void before() {
    defaultConfig = new APIClientConfiguration()
      .setApplicationId(APPLICATION_ID)
      .setApiKey(API_KEY)
      .setBuildHosts(Collections.singletonList(APPLICATION_ID + ".algolia.net"))
      .setBuildHosts(Collections.singletonList(APPLICATION_ID + "-dsn.algolia.net"))
      .setConnectTimeout(1000)
      .setReadTimeout(2000)
      .setHeaders(ImmutableMap.of(
        "X-Algolia-Application-Id", APPLICATION_ID,
        "X-Algolia-API-Key", API_KEY
      ))
      .setObjectMapper(Defaults.DEFAULT_OBJECT_MAPPER);
  }

  private APIClient build(String... hosts) {
    APIClientConfiguration configuration = defaultConfig.setQueryHosts(Arrays.asList(hosts));
    ApacheHttpClient apache = new ApacheHttpClient(configuration);
    return new APIClient(apache, configuration);
  }

  private <T> void assertThatItTookLessThan(long duration, Callable<T> callable) throws Exception {
    Long start = System.currentTimeMillis();
    callable.call();
    Long end = System.currentTimeMillis();
    assertThat(end - start < duration)
      .isTrue()
      .overridingErrorMessage("should have taken less than " + duration + "ms, but took " + (end - start) + "ms.");
  }

  @Test
  public void shouldHandleTimeoutsInDns() throws Exception {
    APIClient client = build("java-dsn.algolia.biz", APPLICATION_ID + "-dsn.algolia.net");

    assertThatItTookLessThan(3 * 1000, () -> assertThat(client.listIndices()).isNotNull());
  }

  @Test
  public void shouldHandleConnectTimeout() throws Exception {
    APIClient client = build("notcp-xx-1.algolianet.com", APPLICATION_ID + "-dsn.algolia.net");

    assertThatItTookLessThan(3 * 1000, () -> assertThat(client.listIndices()).isNotNull());
  }

  @Test
  public void shouldHandleMultipleConnectTimeout() throws Exception {
    APIClient client = build("notcp-xx-1.algolia.net", "notcp-xx-1.algolianet.com");

    assertThatItTookLessThan(
      3 * 1000,
      () -> assertThatExceptionOfType(AlgoliaException.class).isThrownBy(client::listIndices)
    );
  }

  @Test
  public void shouldHandleConnectionResetException() throws Exception {
    Thread runnable = new Thread() {
      @Override
      public void run() {
        try {
          ServerSocket serverSocket = new ServerSocket(8080);
          Socket socket = serverSocket.accept();
          socket.setSoLinger(true, 0);
          socket.close();
        } catch (IOException ignored) {
          ignored.printStackTrace();
        }
      }
    };

    runnable.start();

    APIClient client = build("localhost:8080", APPLICATION_ID + "-1.algolianet.com");

    assertThatItTookLessThan(
      2 * 1000,
      client::listIndices
    );
  }

  @Test
  public void shouldHandleSNI() throws Exception {
    APIClient client = build(APPLICATION_ID + "-1.algolianet.com");
    assertThat(client.listKeys()).isNotEmpty();
  }


}
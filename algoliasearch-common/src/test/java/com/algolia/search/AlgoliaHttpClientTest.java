package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.algolia.search.Defaults.ALGOLIANET_COM;
import static com.algolia.search.Defaults.ALGOLIA_NET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AlgoliaHttpClientTest {

  private AlgoliaHttpClient client;
  private LowLevelHttpRequest mock;

  @Before
  public void before() {
    mock = mock(LowLevelHttpRequest.class);

    client = new AlgoliaHttpClient(
      new MockHttpTransport() {
        @Override
        public LowLevelHttpRequest buildRequest(String method, final String url) throws IOException {
          return mock;
        }
      }.createRequestFactory(),
      Defaults.DEFAULT_OBJECT_MAPPER,
      generateQueryHosts("APP_ID"),
      generateBuildHosts("APP_ID")
    );
  }

  @Test
  public void oneCallOne200() throws AlgoliaException, IOException {
    when(mock.execute()).thenReturn(response(200));

    client.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes"),
      Result.class
    );

    verify(mock, times(1)).execute();
  }

  @Test
  public void oneCallOne404() throws AlgoliaException, IOException {
    when(mock.execute()).thenReturn(response(404));

    Result result = client.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes"),
      Result.class
    );

    assertThat(result).isNull();

    verify(mock, times(1)).execute();
  }

  @Test
  public void oneCallOne400() throws AlgoliaException, IOException {
    when(mock.execute()).thenReturn(response(400));

    assertThatThrownBy(() ->
      client.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )).hasMessage("Bad buildRequest");

    verify(mock, times(1)).execute();
  }

  @Test
  public void oneCallOne403() throws AlgoliaException, IOException {
    when(mock.execute()).thenReturn(response(403));

    assertThatThrownBy(() ->
      client.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    ).hasMessage("Invalid Application-ID or API-Key");

    verify(mock, times(1)).execute();
  }

  @Test
  public void oneCallOne401() throws AlgoliaException, IOException {
    when(mock.execute()).thenReturn(response(401));

    assertThatThrownBy(() ->
      client.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )).hasMessage("Error");

    verify(mock, times(1)).execute();
  }

  @Test
  public void twoCallsOneExceptionOne200() throws AlgoliaException, IOException {
    when(mock.execute())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    client.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes"),
      Result.class
    );

    verify(mock, times(2)).execute();
  }

  @Test
  public void threeCallsTwoExceptionOne200() throws AlgoliaException, IOException {
    when(mock.execute())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    client.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes"),
      Result.class
    );

    verify(mock, times(3)).execute();
  }

  @Test
  public void fourCallsFourExceptions() throws AlgoliaException, IOException {
    when(mock.execute())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException());

    assertThatThrownBy(() ->
      client.requestWithRetry(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )).hasMessage("All retries failed, exceptions: [null,null,null,null]");

    verify(mock, times(4)).execute();
  }

  @Test
  public void fourCallsThreeExceptionOne200() throws AlgoliaException, IOException {
    when(mock.execute())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    client.requestWithRetry(
      HttpMethod.GET,
      false,
      Arrays.asList("1", "indexes"),
      Result.class
    );

    verify(mock, times(4)).execute();
  }

  private MockLowLevelHttpResponse response(int status) {
    return new MockLowLevelHttpResponse()
      .setStatusCode(status)
      .setContent("{\"a\":1}")
      .setContentEncoding(Json.MEDIA_TYPE);
  }

  private List<String> generateBuildHosts(String applicationId) {
    return Arrays.asList(
      applicationId + "." + ALGOLIA_NET,
      applicationId + "-1." + ALGOLIANET_COM,
      applicationId + "-2." + ALGOLIANET_COM,
      applicationId + "-3." + ALGOLIANET_COM
    );
  }

  private List<String> generateQueryHosts(String applicationId) {
    return Arrays.asList(
      applicationId + "-dsn." + ALGOLIA_NET,
      applicationId + "-1." + ALGOLIANET_COM,
      applicationId + "-2." + ALGOLIANET_COM,
      applicationId + "-3." + ALGOLIANET_COM
    );
  }

  private static class Result {

    public int a;

  }
}
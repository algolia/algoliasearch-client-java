package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.http.*;
import com.algolia.search.responses.AlgoliaError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static com.algolia.search.Defaults.ALGOLIANET_COM;
import static com.algolia.search.Defaults.ALGOLIA_NET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AlgoliaHttpClientTest {

  private final String applicationId = "APP_ID";
  private MockedAlgoliaHttpClient mockClient;

  @Before
  public void before() {
    mockClient = spy(new MockedAlgoliaHttpClient());
  }

  @Test
  public void oneCallOne200() throws AlgoliaException, IOException {
    when(makeMockRequest()).thenReturn(response(200));

    mockClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        true,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    );
  }

  @Test
  public void oneCallOne404() throws AlgoliaException, IOException {
    when(makeMockRequest()).thenReturn(response(404));

    Result result = mockClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    );

    assertThat(result).isNull();
  }

  @Test
  public void oneCallOne400() throws AlgoliaException, IOException {
    when(makeMockRequest()).thenReturn(response(400));

    assertThatThrownBy(() ->
      mockClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes"),
          Result.class
        )
      )).hasMessage("Bad buildRequest");
  }

  @Test
  public void oneCallOne403() throws AlgoliaException, IOException {
    when(makeMockRequest()).thenReturn(response(403));

    assertThatThrownBy(() ->
      mockClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes"),
          Result.class
        )
      )
    ).hasMessage("Invalid Application-ID or API-Key");
  }

  @Test
  public void oneCallOne401() throws AlgoliaException, IOException {
    when(makeMockRequest()).thenReturn(response(401));

    assertThatThrownBy(() ->
      mockClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes"),
          Result.class
        )
      )).hasMessage("Error");
  }

  @Test
  public void twoCallsOneExceptionOne200() throws AlgoliaException, IOException {
    when(makeMockRequest())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    mockClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    );
  }

  @Test
  public void threeCallsTwoExceptionOne200() throws AlgoliaException, IOException {
    when(makeMockRequest())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    mockClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    );
  }

  @Test
  public void fourCallsFourExceptions() throws AlgoliaException, IOException {
    when(makeMockRequest())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException());

    assertThatThrownBy(() ->
      mockClient.requestWithRetry(
        new AlgoliaRequest<>(
          HttpMethod.GET,
          false,
          Arrays.asList("1", "indexes"),
          Result.class
        )
      )).hasMessage("All retries failed, exceptions: [null,null,null,null]");
  }

  @Test
  public void fourCallsThreeExceptionOne200() throws AlgoliaException, IOException {
    when(makeMockRequest())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenThrow(new IOException())
      .thenReturn(response(200));

    mockClient.requestWithRetry(
      new AlgoliaRequest<>(
        HttpMethod.GET,
        false,
        Arrays.asList("1", "indexes"),
        Result.class
      )
    );
  }

  private AlgoliaHttpResponse makeMockRequest() throws IOException {
    return mockClient.request(
      any(AlgoliaHttpRequest.class)
    );
  }

  private AlgoliaHttpResponse response(int status) {
    return new AlgoliaHttpResponse() {
      @Override
      public int getStatusCode() {
        return status;
      }

      @Override
      public <T> T parseAs(Class<T> klass) throws IOException {
        if (status / 100 == 4) {
          return (T) new AlgoliaError();
        } else {
          return (T) new Result();
        }
      }

      @Override
      public Object parseAs(Type type) throws IOException {
        if (status / 100 == 4) {
          return new AlgoliaError();
        } else {
          return new Result();
        }
      }
    };
  }


  private static class Result {

    int a;

    Result() {
      this.a = 1;
    }

  }

  private class MockedAlgoliaHttpClient extends AlgoliaHttpClient {

    public MockedAlgoliaHttpClient() {
    }

    @Override
    public AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException {
      return null;
    }

    @Override
    public ObjectMapper getObjectMapper() {
      return Defaults.DEFAULT_OBJECT_MAPPER;
    }

    @Override
    public List<String> getQueryHosts() {
      return Arrays.asList(
        applicationId + "-dsn." + ALGOLIA_NET,
        applicationId + "-1." + ALGOLIANET_COM,
        applicationId + "-2." + ALGOLIANET_COM,
        applicationId + "-3." + ALGOLIANET_COM
      );
    }

    @Override
    public List<String> getBuildHosts() {
      return Arrays.asList(
        applicationId + "." + ALGOLIA_NET,
        applicationId + "-1." + ALGOLIANET_COM,
        applicationId + "-2." + ALGOLIANET_COM,
        applicationId + "-3." + ALGOLIANET_COM
      );
    }
  }
}


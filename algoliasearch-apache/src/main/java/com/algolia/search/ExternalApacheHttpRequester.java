package com.algolia.search;

import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

/**
 * The Algolia http requester is a wrapper on top of the HttpAsyncClient of Apache. It's an
 * implementation of {@link HttpRequester} It takes an {@link HttpRequest} as input. It returns an
 * {@link HttpResponse}.
 */
public class ExternalApacheHttpRequester extends AbstractApacheHttpRequester {

  private final CloseableHttpAsyncClient asyncHttpClient;
  private final RequestConfig requestConfig;
  private final ConfigBase config;

  public ExternalApacheHttpRequester(
      @Nonnull ConfigBase config, @Nonnull CloseableHttpAsyncClient client) {

    this.config = config;

    requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeOut())
            .setContentCompressionEnabled(true)
            .build();

    asyncHttpClient = Objects.requireNonNull(client);
  }

  @Override
  public void close() throws IOException {
    // noop
  }

  @Override
  protected CloseableHttpAsyncClient getAsyncHttpClient() {
    return asyncHttpClient;
  }

  @Override
  protected ConfigBase getConfig() {
    return config;
  }

  @Override
  protected RequestConfig getRequestConfig() {
    return requestConfig;
  }
}

package com.algolia.search;

import com.algolia.search.models.HttpRequest;
import com.algolia.search.models.HttpResponse;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * The Algolia http requester is a wrapper on top of the HttpAsyncClient of Apache. It's an
 * implementation of {@link HttpRequester} It takes an {@link HttpRequest} as input. It returns an
 * {@link HttpResponse}.
 */
final class ApacheHttpRequester extends AbstractApacheHttpRequester {

  private final CloseableHttpAsyncClient asyncHttpClient;
  private final RequestConfig requestConfig;
  private final ConfigBase config;
  private final boolean isSelfManagedClient;

  ApacheHttpRequester(@Nonnull ConfigBase config) {

    this.config = config;

    requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeOut())
            .setContentCompressionEnabled(true)
            .build();

    asyncHttpClient =
        config.getUseSystemProxy()
            ? HttpAsyncClients.createSystem()
            : HttpAsyncClients.createDefault();

    isSelfManagedClient = true;

    asyncHttpClient.start();
  }

  ApacheHttpRequester(
      @Nonnull ConfigBase config, @Nonnull CloseableHttpAsyncClient externalClient) {

    this.config = config;

    requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeOut())
            .setContentCompressionEnabled(true)
            .build();

    asyncHttpClient = Objects.requireNonNull(externalClient);

    isSelfManagedClient = false;
  }

  @Override
  protected CloseableHttpAsyncClient getAsyncHttpClient() {
    return asyncHttpClient;
  }

  @Override
  protected boolean getIsSelfManagedClient() {
    return isSelfManagedClient;
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

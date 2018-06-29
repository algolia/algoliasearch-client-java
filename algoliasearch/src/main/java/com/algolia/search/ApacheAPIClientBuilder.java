package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;

public final class ApacheAPIClientBuilder extends APIClientBuilder {

  public ApacheAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  public ApacheAPIClientBuilder setUserAgent(
      @Nonnull String customAgent, @Nonnull String customAgentVersion) {
    super.setUserAgent(customAgent, customAgentVersion);
    return this;
  }

  @Override
  @Deprecated
  public ApacheAPIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    super.setExtraHeader(key, value);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder addExtraHeader(@Nonnull String key, String value) {
    super.addExtraHeader(key, value);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setConnectTimeout(int connectTimeout) {
    super.setConnectTimeout(connectTimeout);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setReadTimeout(int readTimeout) {
    super.setReadTimeout(readTimeout);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setHostDownTimeout(int hostDownTimeout) {
    super.setHostDownTimeout(hostDownTimeout);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setAnalyticsHost(@Nonnull String analyticsHost) {
    this.analyticsHost = analyticsHost;
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setQueryHosts(List<String> queryHosts) {
    super.setQueryHosts(queryHosts);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setBuildHosts(List<String> buildHosts) {
    super.setBuildHosts(buildHosts);
    return this;
  }

  @Override
  public ApacheAPIClientBuilder setMaxConnTotal(int maxConnTotal) {
    super.setMaxConnTotal(maxConnTotal);
    return this;
  }

  @Override
  public GenericAPIClientBuilder setMaxConnPerRoute(int maxConnPerRoute) {
    super.setMaxConnPerRoute(maxConnPerRoute);
    return this;
  }

  private ApacheHttpClientConfiguration httpClientConfiguration =
      new ApacheHttpClientConfiguration();

  /**
   * Set the proxy for the underlying Apache HTTP Client
   *
   * <p>See https://hc.apache.org/httpcomponents-client-ga/examples.html for examples
   *
   * @param proxy
   * @return this
   */
  public ApacheAPIClientBuilder setProxy(HttpHost proxy) {
    httpClientConfiguration.setProxy(proxy);
    return this;
  }

  /**
   * Set the proxyPreferredAuthSchemes for the underlying Apache HTTP Client
   *
   * <p>See https://hc.apache.org/httpcomponents-client-ga/examples.html for examples
   *
   * @param proxyPreferredAuthSchemes
   * @return this
   */
  public ApacheAPIClientBuilder setProxyPreferredAuthSchemes(
      Collection<String> proxyPreferredAuthSchemes) {
    httpClientConfiguration.setProxyPreferredAuthSchemes(proxyPreferredAuthSchemes);
    return this;
  }

  /**
   * Set the CredentialsProvider for the underlying Apache HTTP Client
   *
   * <p>See https://hc.apache.org/httpcomponents-client-ga/examples.html for examples
   *
   * @param defaultCredentialsProvider
   * @return this
   */
  public ApacheAPIClientBuilder setDefaultCredentialsProvider(
      CredentialsProvider defaultCredentialsProvider) {
    httpClientConfiguration.setDefaultCredentialsProvider(defaultCredentialsProvider);
    return this;
  }

  @Override
  protected APIClient build(@Nonnull APIClientConfiguration configuration) {
    return new APIClient(
        new ApacheHttpClient(configuration, httpClientConfiguration), configuration);
  }
}

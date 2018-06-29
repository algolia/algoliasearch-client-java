package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.annotation.Nonnull;

/** Base class to create APIClient */
@SuppressWarnings("WeakerAccess")
public abstract class APIClientBuilder extends GenericAPIClientBuilder {

  /**
   * Initialize this builder with the applicationId and apiKey
   *
   * @param applicationId APP_ID can be found on https://www.algolia.com/api-keys
   * @param apiKey Algolia API_KEY can also be found on https://www.algolia.com/api-keys
   */
  public APIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  /**
   * Customize the user agent
   *
   * @param customAgent key to add to the user agent
   * @param customAgentVersion value of this key to add to the user agent
   * @return this
   */
  @Override
  public APIClientBuilder setUserAgent(
      @Nonnull String customAgent, @Nonnull String customAgentVersion) {
    super.setUserAgent(customAgent, customAgentVersion);
    return this;
  }

  @Override
  public APIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    super.addExtraHeader(key, value);
    return this;
  }

  @Override
  public GenericAPIClientBuilder addExtraHeader(@Nonnull String key, String value) {
    return super.addExtraHeader(key, value);
  }

  /**
   * Set the connect timeout of the HTTP client
   *
   * @param connectTimeout the value in ms
   * @return this
   */
  @Override
  public APIClientBuilder setConnectTimeout(int connectTimeout) {
    super.setConnectTimeout(connectTimeout);
    return this;
  }

  /**
   * Set the read timeout of the HTTP client
   *
   * @param readTimeout the value in ms
   * @return this
   */
  @Override
  public APIClientBuilder setReadTimeout(int readTimeout) {
    super.setReadTimeout(readTimeout);
    return this;
  }

  /**
   * Set the retry timeout to detect if a host is down
   *
   * @param hostDownTimeout the value in ms
   * @return this
   */
  @Override
  public APIClientBuilder setHostDownTimeout(int hostDownTimeout) {
    super.setHostDownTimeout(hostDownTimeout);
    return this;
  }

  /**
   * Set the Jackson ObjectMapper
   *
   * @param objectMapper the mapper
   * @return this
   */
  @Override
  public APIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
    return this;
  }

  @Override
  public APIClientBuilder setQueryHosts(List<String> queryHosts) {
    super.setQueryHosts(queryHosts);
    return this;
  }

  @Override
  public APIClientBuilder setBuildHosts(List<String> buildHosts) {
    super.setBuildHosts(buildHosts);
    return this;
  }

  @Override
  public APIClientBuilder setMaxConnTotal(int maxConnTotal) {
    super.setMaxConnTotal(maxConnTotal);
    return this;
  }

  @Override
  public GenericAPIClientBuilder setMaxConnPerRoute(int maxConnPerRoute) {
    super.setMaxConnPerRoute(maxConnPerRoute);
    return this;
  }

  protected abstract APIClient build(@Nonnull APIClientConfiguration configuration);

  /**
   * Build the APIClient
   *
   * @return the built APIClient
   */
  public APIClient build() {
    return build(
        new APIClientConfiguration(
            applicationId,
            apiKey,
            objectMapper,
            analyticsHost,
            generateBuildHosts(),
            generateQueryHosts(),
            generateHeaders(),
            connectTimeout,
            readTimeout,
            hostDownTimeout,
            maxConnTotal,
            maxConnPerRoute));
  }
}

package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.annotation.Nonnull;

public final class AppEngineAPIClientBuilder extends APIClientBuilder {

  public AppEngineAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(@Nonnull APIClientConfiguration configuration) {
    return new APIClient(new AppEngineHttpClient(configuration), configuration);
  }

  @Override
  public AppEngineAPIClientBuilder setUserAgent(
      @Nonnull String customAgent, @Nonnull String customAgentVersion) {
    super.setUserAgent(customAgent, customAgentVersion);
    return this;
  }

  @Override
  @Deprecated
  public AppEngineAPIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    super.setExtraHeader(key, value);
    return this;
  }

  @Override
  public GenericAPIClientBuilder addExtraHeader(@Nonnull String key, String value) {
    return super.addExtraHeader(key, value);
  }

  @Override
  public AppEngineAPIClientBuilder setConnectTimeout(int connectTimeout) {
    super.setConnectTimeout(connectTimeout);
    return this;
  }

  @Override
  public AppEngineAPIClientBuilder setReadTimeout(int readTimeout) {
    super.setReadTimeout(readTimeout);
    return this;
  }

  @Override
  public AppEngineAPIClientBuilder setHostDownTimeout(int hostDownTimeout) {
    super.setHostDownTimeout(hostDownTimeout);
    return this;
  }

  @Override
  public AppEngineAPIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
    return this;
  }

  @Override
  public AppEngineAPIClientBuilder setQueryHosts(List<String> queryHosts) {
    super.setQueryHosts(queryHosts);
    return this;
  }

  @Override
  public AppEngineAPIClientBuilder setBuildHosts(List<String> buildHosts) {
    super.setBuildHosts(buildHosts);
    return this;
  }
}

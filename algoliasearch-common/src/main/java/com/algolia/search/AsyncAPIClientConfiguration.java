package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AsyncAPIClientConfiguration extends APIClientConfiguration {

  private ExecutorService executor;

  public ExecutorService getExecutorService() {
    return executor;
  }

  public AsyncAPIClientConfiguration setExecutor(ExecutorService executor) {
    this.executor = executor;
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setApplicationId(String applicationId) {
    super.setApplicationId(applicationId);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setApiKey(String apiKey) {
    super.setApiKey(apiKey);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setObjectMapper(ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setBuildHosts(List<String> buildHosts) {
    super.setBuildHosts(buildHosts);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setQueryHosts(List<String> queryHosts) {
    super.setQueryHosts(queryHosts);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setHeaders(Map<String, String> headers) {
    super.setHeaders(headers);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setConnectTimeout(int connectTimeout) {
    super.setConnectTimeout(connectTimeout);
    return this;
  }

  @Override
  public AsyncAPIClientConfiguration setReadTimeout(int readTimeout) {
    super.setReadTimeout(readTimeout);
    return this;
  }
}

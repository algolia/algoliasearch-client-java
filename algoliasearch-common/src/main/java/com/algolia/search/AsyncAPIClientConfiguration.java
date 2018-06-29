package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AsyncAPIClientConfiguration extends APIClientConfiguration {

  private final ExecutorService executor;

  public AsyncAPIClientConfiguration(
      String applicationId,
      String apiKey,
      ObjectMapper objectMapper,
      String analyticsHost,
      List<String> buildHosts,
      List<String> queryHosts,
      Map<String, String> headers,
      int connectTimeout,
      int readTimeout,
      int hostDownTimeout,
      int maxConnTotal,
      int maxConnPerRoute,
      ExecutorService executor) {
    super(
        applicationId,
        apiKey,
        objectMapper,
        analyticsHost,
        buildHosts,
        queryHosts,
        headers,
        connectTimeout,
        readTimeout,
        hostDownTimeout,
        maxConnTotal,
        maxConnPerRoute);
    this.executor = executor;
  }

  public ExecutorService getExecutorService() {
    return executor;
  }
}

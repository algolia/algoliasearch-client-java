package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

public class APIClientConfiguration {

  private final String applicationId;
  private final String apiKey;
  private final ObjectMapper objectMapper;
  private final String analyticsHost;
  private final List<String> buildHosts;
  private final List<String> queryHosts;
  private final Map<String, String> headers;
  private final int connectTimeout;
  private final int readTimeout;
  private final int hostDownTimeout;
  private final int maxConnTotal;
  private final int maxConnPerRoute;

  public APIClientConfiguration(
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
      int maxConnPerRoute) {
    this.applicationId = applicationId;
    this.apiKey = apiKey;
    this.objectMapper = objectMapper;
    this.analyticsHost = analyticsHost;
    this.buildHosts = buildHosts;
    this.queryHosts = queryHosts;
    this.headers = headers;
    this.connectTimeout = connectTimeout;
    this.readTimeout = readTimeout;
    this.hostDownTimeout = hostDownTimeout;
    this.maxConnTotal = maxConnTotal;
    this.maxConnPerRoute = maxConnPerRoute;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public String getApiKey() {
    return apiKey;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public String getAnalyticsHost() {
    return analyticsHost;
  }

  public List<String> getBuildHosts() {
    return buildHosts;
  }

  public List<String> getQueryHosts() {
    return queryHosts;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public int getHostDownTimeout() {
    return hostDownTimeout;
  }

  public int getMaxConnTotal() {
    return maxConnTotal;
  }

  public int getMaxConnPerRoute() {
    return maxConnPerRoute;
  }
}

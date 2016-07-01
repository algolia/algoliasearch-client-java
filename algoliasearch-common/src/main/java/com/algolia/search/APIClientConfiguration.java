package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class APIClientConfiguration {

  private String applicationId;
  private String apiKey;
  private ObjectMapper objectMapper;
  private List<String> buildHosts;
  private List<String> queryHosts;
  private Map<String, String> headers;
  private int connectTimeout;
  private int readTimeout;

  public String getApplicationId() {
    return applicationId;
  }

  public APIClientConfiguration setApplicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public APIClientConfiguration setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public APIClientConfiguration setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    return this;
  }

  public List<String> getBuildHosts() {
    return buildHosts;
  }

  public APIClientConfiguration setBuildHosts(List<String> buildHosts) {
    this.buildHosts = buildHosts;
    return this;
  }

  public List<String> getQueryHosts() {
    return queryHosts;
  }

  public APIClientConfiguration setQueryHosts(List<String> queryHosts) {
    this.queryHosts = queryHosts;
    return this;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public APIClientConfiguration setHeaders(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public APIClientConfiguration setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
    return this;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public APIClientConfiguration setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
    return this;
  }


}

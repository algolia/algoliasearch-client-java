package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

public class APIClientConfiguration {

  protected String applicationId;
  protected String apiKey;
  protected ObjectMapper objectMapper;
  protected List<String> buildHosts;
  protected List<String> queryHosts;
  protected Map<String, String> headers;
  protected int connectTimeout;
  protected int readTimeout;
  protected int hostDownTimeout;
  protected int maxConnTotal;

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

  public int getHostDownTimeout() {
    return hostDownTimeout;
  }

  public APIClientConfiguration setHostDownTimeout(int hostDownTimeout) {
    this.hostDownTimeout = hostDownTimeout;
    return this;
  }

  public int getMaxConnTotal() {
    return maxConnTotal;
  }

  public APIClientConfiguration setMaxConnTotal(int maxConnTotal) {
    this.maxConnTotal = maxConnTotal;
    return this;
  }
}

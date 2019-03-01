package com.algolia.search.clients;

import com.algolia.search.transport.StatefulHost;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/** * Algolia's clients configuration */
@SuppressWarnings("WeakerAccess")
public abstract class AlgoliaConfig {

  public AlgoliaConfig(String applicationID, String apiKey) {
    this(applicationID, apiKey, ForkJoinPool.commonPool());
  }

  public AlgoliaConfig(String applicationID, String apiKey, ExecutorService executor) {
    this.applicationID = applicationID;
    this.apiKey = apiKey;
    this.executor = executor;
    this.batchSize = 1000;
    this.defaultHeaders =
        new HashMap<String, String>() {
          {
            put("X-Algolia-Application-Id", getApplicationID());
            put("X-Algolia-API-Key", getApiKey());
            put(
                "User-Agent",
                String.format("Algolia for Java (%s); JVM (%s)", clientVersion, javaVersion));
            put("Accept", "application/json");
          }
        };
  }

  public String getApplicationID() {
    return applicationID;
  }

  public String getApiKey() {
    return apiKey;
  }

  public HashMap<String, String> getDefaultHeaders() {
    return defaultHeaders;
  }

  public AlgoliaConfig setDefaultHeaders(HashMap<String, String> defaultHeaders) {
    this.defaultHeaders = defaultHeaders;
    return this;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public AlgoliaConfig setBatchSize(int batchSize) {
    this.batchSize = batchSize;
    return this;
  }

  public Integer getReadTimeOut() {
    return readTimeOut;
  }

  public AlgoliaConfig setReadTimeOut(Integer readTimeOut) {
    this.readTimeOut = readTimeOut;
    return this;
  }

  public Integer getWriteTimeOut() {
    return writeTimeOut;
  }

  public AlgoliaConfig setWriteTimeOut(Integer writeTimeOut) {
    this.writeTimeOut = writeTimeOut;
    return this;
  }

  public List<StatefulHost> getDefaultHosts() {
    return defaultHosts;
  }

  void setDefaultHosts(List<StatefulHost> defaultHosts) {
    this.defaultHosts = defaultHosts;
  }

  public List<StatefulHost> getCustomHosts() {
    return customHosts;
  }

  public AlgoliaConfig setCustomHosts(List<StatefulHost> customHosts) {
    this.customHosts = customHosts;
    return this;
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  private final String javaVersion = System.getProperty("java.version");
  private final String clientVersion = this.getClass().getPackage().getSpecificationVersion();
  private String applicationID;
  private String apiKey;
  private HashMap<String, String> defaultHeaders;
  private int batchSize;
  private Integer readTimeOut;
  private Integer writeTimeOut;
  private List<StatefulHost> defaultHosts;
  private List<StatefulHost> customHosts;
  private ExecutorService executor;
}

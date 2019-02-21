package com.algolia.search.clients;

import com.algolia.search.transport.StatefulHost;
import java.util.*;

/** * Algolia's clients configuration */
public class AlgoliaConfig {

  public AlgoliaConfig(String applicationID, String apiKey) {
    this.applicationID = applicationID;
    this.apiKey = apiKey;

    this.defaultHeaders =
        new HashMap<String, String>() {
          {
            put("X-Algolia-Application-Id", getApplicationID());
            put("X-Algolia-API-Key", getApiKey());
            put(
                "User-Agent",
                String.format("Algolia for Java (%s); JVM (%s)", cientVersion, javaVersion));
            put("Accept", "application/json");
          }
        };
  }

  public String getApplicationID() {
    return applicationID;
  }

  public AlgoliaConfig setApplicationID(String applicationID) {
    this.applicationID = applicationID;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public AlgoliaConfig setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
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

  AlgoliaConfig setDefaultHosts(List<StatefulHost> defaultHosts) {
    this.defaultHosts = defaultHosts;
    return this;
  }

  public List<StatefulHost> getCustomHosts() {
    return customHosts;
  }

  public AlgoliaConfig setCustomHosts(List<StatefulHost> customHosts) {
    this.customHosts = customHosts;
    return this;
  }

  private final String javaVersion = System.getProperty("java.version");
  private final String cientVersion = this.getClass().getPackage().getSpecificationVersion();
  private String applicationID;
  private String apiKey;
  private HashMap<String, String> defaultHeaders;
  private int batchSize;
  private Integer readTimeOut;
  private Integer writeTimeOut;
  private List<StatefulHost> defaultHosts;
  private List<StatefulHost> customHosts;
}

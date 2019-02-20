package com.algolia.search.clients;

import com.algolia.search.transport.StatefulHost;
import java.util.*;

/** * Algolia's clients configuration */
public class AlgoliaConfig {

  public AlgoliaConfig(String applicationID, String apiKey) {
    this.applicationID = applicationID;
    this.apiKey = apiKey;

    this.defaultHeaders =
        new HashMap<String, Collection<String>>() {
          {
            put("X-Algolia-Application-Id", Collections.singletonList(getApplicationID()));
            put("X-Algolia-API-Key", Collections.singletonList(getApiKey()));
            put("User-Agent", Collections.singletonList("Java v3"));
            put("Accept", Collections.singletonList("application/json"));
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

  public HashMap<String, Collection<String>> getDefaultHeaders() {
    return defaultHeaders;
  }

  public AlgoliaConfig setDefaultHeaders(HashMap<String, Collection<String>> defaultHeaders) {
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

  private String applicationID;
  private String apiKey;
  private HashMap<String, Collection<String>> defaultHeaders;
  private int batchSize;
  private Integer readTimeOut;
  private Integer writeTimeOut;
  private List<StatefulHost> defaultHosts;
  private List<StatefulHost> customHosts;
}

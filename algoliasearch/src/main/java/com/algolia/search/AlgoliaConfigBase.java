package com.algolia.search;

import com.algolia.search.models.StatefulHost;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/** * Algolia's clients configuration */
@SuppressWarnings("WeakerAccess")
public abstract class AlgoliaConfigBase {

  AlgoliaConfigBase(String applicationID, String apiKey) {
    this(applicationID, apiKey, ForkJoinPool.commonPool());
  }

  AlgoliaConfigBase(String applicationID, String apiKey, ExecutorService executor) {
    this.applicationID = applicationID;
    this.apiKey = apiKey;
    this.executor = executor;
    this.batchSize = 1000;
    this.defaultHeaders = new HashMap<>();
    this.defaultHeaders.put(Defaults.ALGOLIA_APPLICATION_HEADER, getApplicationID());
    this.defaultHeaders.put(Defaults.ALGOLIA_KEY_HEADER, getApiKey());
    this.defaultHeaders.put(
        Defaults.USER_AGENT_HEADER,
        String.format("Algolia for Java (%s); JVM (%s)", clientVersion, javaVersion));
    this.defaultHeaders.put(Defaults.ACCEPT_HEADER, Defaults.APPLICATION_JSON);
    this.defaultHeaders.put(Defaults.ACCEPT_ENCODING_HEADER, Defaults.CONTENT_ENCODING_GZIP);
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

  public int getBatchSize() {
    return batchSize;
  }

  /** Overrides the default batch size for save methods. Default = 1000 objects per chunk. */
  public AlgoliaConfigBase setBatchSize(int batchSize) {
    this.batchSize = batchSize;
    return this;
  }

  public Integer getReadTimeOut() {
    return readTimeOut;
  }

  /** Overrides the default read timeout. Default = 1000ms In milliseconds */
  public AlgoliaConfigBase setReadTimeOut(Integer readTimeOut) {
    this.readTimeOut = readTimeOut;
    return this;
  }

  public Integer getWriteTimeOut() {
    return writeTimeOut;
  }

  /** Overrides the default write timeout. Default = 30000ms In milliseconds */
  public AlgoliaConfigBase setWriteTimeOut(Integer writeTimeOut) {
    this.writeTimeOut = writeTimeOut;
    return this;
  }

  public Integer getConnectTimeOut() {
    return connectTimeOut;
  }

  /** Overrides the default connect timeout. Default = 2000ms In milliseconds */
  public AlgoliaConfigBase setConnectTimeOut(Integer connectTimeOut) {
    this.connectTimeOut = connectTimeOut;
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

  /** Sets a list of specific host to target. Default hosts will be overridden. */
  public AlgoliaConfigBase setCustomHosts(List<StatefulHost> customHosts) {
    this.customHosts = customHosts;
    return this;
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  private static final String javaVersion = System.getProperty("java.version");
  private final String clientVersion = "3.0.0.0";
  private final String applicationID;
  private final String apiKey;
  private HashMap<String, String> defaultHeaders;
  private int batchSize;
  private Integer readTimeOut;
  private Integer writeTimeOut;
  private Integer connectTimeOut;
  private List<StatefulHost> defaultHosts;
  private List<StatefulHost> customHosts;
  private final ExecutorService executor;
}

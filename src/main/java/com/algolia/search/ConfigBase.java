package com.algolia.search;

import com.algolia.search.util.AlgoliaUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.Nonnull;

/** Algolia's clients common configuration */
@SuppressWarnings("WeakerAccess")
class ConfigBase {

  private static final String javaVersion = System.getProperty("java.version");
  private static final String clientVersion = "3.0.0.0";
  private final String applicationID;
  private final String apiKey;
  private final HashMap<String, String> defaultHeaders;
  private final int batchSize;
  private final Integer readTimeOut;
  private final Integer writeTimeOut;
  private final Integer connectTimeOut;
  private final List<StatefulHost> defaultHosts;
  private final List<StatefulHost> customHosts;
  private final ExecutorService executor;

  /** Config base builder to ensure the immutability of the configuration. */
  public abstract static class Builder<T extends Builder<T>> {

    private final String applicationID;
    private final String apiKey;
    private final HashMap<String, String> defaultHeaders;
    private int batchSize;
    private Integer readTimeOut;
    private Integer writeTimeOut;
    private Integer connectTimeOut;
    private List<StatefulHost> defaultHosts;
    private List<StatefulHost> customHosts;
    private final ExecutorService executor;

    /**
     * Builds a base configuration
     *
     * @param applicationID The Algolia Application ID
     * @param apiKey The API Key: could be Admin API Key or Search API Key
     * @throws NullPointerException if ApplicationID/ApiKey/Config/Requester is null
     */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey) {

      Objects.requireNonNull(applicationID, "An ApplicationID is required.");
      Objects.requireNonNull(apiKey, "An API key is required.");

      if (AlgoliaUtils.isEmptyWhiteSpace(applicationID)) {
        throw new NullPointerException("The ApplicationID can't be empty.");
      }

      if (AlgoliaUtils.isEmptyWhiteSpace(apiKey)) {
        throw new NullPointerException("The APIKey can't be empty.");
      }

      this.applicationID = applicationID;
      this.apiKey = apiKey;
      this.batchSize = 1000;
      this.defaultHeaders = new HashMap<>();
      this.defaultHeaders.put(Defaults.ALGOLIA_APPLICATION_HEADER, applicationID);
      this.defaultHeaders.put(Defaults.ALGOLIA_KEY_HEADER, apiKey);
      this.defaultHeaders.put(
          Defaults.USER_AGENT_HEADER,
          String.format("Algolia for Java (%s); JVM (%s)", clientVersion, javaVersion));
      this.defaultHeaders.put(Defaults.ACCEPT_HEADER, Defaults.APPLICATION_JSON);
      this.defaultHeaders.put(Defaults.ACCEPT_ENCODING_HEADER, Defaults.CONTENT_ENCODING_GZIP);
      this.executor = ForkJoinPool.commonPool();
    }

    /** To prevent unchecked cast warning. */
    public abstract T getThis();

    /** Overrides the default batch size for save methods. Default = 1000 objects per chunk. */
    public T setBatchSize(int batchSize) {
      this.batchSize = batchSize;
      return getThis();
    }

    /** Overrides the default read timeout. Default = 1000ms In milliseconds */
    public T setReadTimeOut(Integer readTimeOut) {
      this.readTimeOut = readTimeOut;
      return getThis();
    }

    /** Overrides the default write timeout. Default = 30000ms In milliseconds */
    public T setWriteTimeOut(Integer writeTimeOut) {
      this.writeTimeOut = writeTimeOut;
      return getThis();
    }

    /** Overrides the default connect timeout. Default = 2000ms In milliseconds */
    public T setConnectTimeOut(Integer connectTimeOut) {
      this.connectTimeOut = connectTimeOut;
      return getThis();
    }

    void setDefaultHosts(List<StatefulHost> defaultHosts) {
      this.defaultHosts = defaultHosts;
    }

    /** Sets a list of specific host to target. Default hosts will be overridden. */
    public T setCustomHosts(List<StatefulHost> customHosts) {
      this.customHosts = customHosts;
      return getThis();
    }

    public ConfigBase build() {
      return new ConfigBase(this);
    }
  }

  protected ConfigBase(Builder<?> builder) {
    this.apiKey = builder.apiKey;
    this.applicationID = builder.applicationID;
    this.defaultHeaders = builder.defaultHeaders;
    this.batchSize = builder.batchSize;
    this.readTimeOut = builder.readTimeOut;
    this.writeTimeOut = builder.writeTimeOut;
    this.connectTimeOut = builder.connectTimeOut;
    this.defaultHosts = builder.defaultHosts;
    this.customHosts = builder.customHosts;
    this.executor = builder.executor;
  }

  public static String getJavaVersion() {
    return javaVersion;
  }

  public static String getClientVersion() {
    return clientVersion;
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

  public Integer getReadTimeOut() {
    return readTimeOut;
  }

  public Integer getWriteTimeOut() {
    return writeTimeOut;
  }

  public Integer getConnectTimeOut() {
    return connectTimeOut;
  }

  public List<StatefulHost> getDefaultHosts() {
    return defaultHosts;
  }

  public List<StatefulHost> getCustomHosts() {
    return customHosts;
  }

  public ExecutorService getExecutor() {
    return executor;
  }
}

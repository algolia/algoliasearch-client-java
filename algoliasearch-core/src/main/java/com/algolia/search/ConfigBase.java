package com.algolia.search;

import com.algolia.search.models.common.CompressionType;
import com.algolia.search.util.AlgoliaUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.Nonnull;

/** Algolia's clients common configuration */
@SuppressWarnings("WeakerAccess")
public abstract class ConfigBase {

  private static final String JAVA_VERSION = System.getProperty("java.version");
  private final String applicationID;
  private final String apiKey;
  private final Map<String, String> defaultHeaders;
  private final int batchSize;
  private final boolean useSystemProxy;
  private final Integer readTimeOut;
  private final Integer writeTimeOut;
  private final Integer connectTimeOut;
  private final List<StatefulHost> hosts;
  private final ExecutorService executor;
  private final CompressionType compressionType;

  /** Config base builder to ensure the immutability of the configuration. */
  public abstract static class Builder<T extends Builder<T>> {

    private final String applicationID;
    private final String apiKey;
    private final Map<String, String> defaultHeaders;
    private int batchSize;
    private boolean useSystemProxy;
    private Integer readTimeOut;
    private Integer writeTimeOut;
    private Integer connectTimeOut;
    private List<StatefulHost> hosts;
    private ExecutorService executor;
    protected CompressionType compressionType;

    /**
     * Builds a base configuration
     *
     * @param applicationID The Algolia Application ID
     * @param apiKey The API Key: could be Admin API Key or Search API Key
     * @throws NullPointerException If the ApplicationID or the APIKey or the hosts are null
     * @throws IllegalArgumentException If the ApplicationID or the APIKey are empty
     */
    public Builder(
        @Nonnull String applicationID,
        @Nonnull String apiKey,
        @Nonnull List<StatefulHost> defaultHosts,
        @Nonnull CompressionType compressionType) {

      this.applicationID = applicationID;
      this.apiKey = apiKey;

      this.useSystemProxy = false;
      this.batchSize = 1000;
      this.hosts = defaultHosts;
      this.connectTimeOut = Defaults.CONNECT_TIMEOUT_MS;
      this.compressionType = compressionType;

      this.defaultHeaders = new HashMap<>();
      this.defaultHeaders.put(Defaults.ALGOLIA_APPLICATION_HEADER, applicationID);
      this.defaultHeaders.put(Defaults.ALGOLIA_KEY_HEADER, apiKey);
      String clientVersion = this.getClass().getPackage().getImplementationVersion();
      this.defaultHeaders.put(
          Defaults.USER_AGENT_HEADER,
          String.format("Algolia for Java (%s); JVM (%s)", clientVersion, JAVA_VERSION));
      this.defaultHeaders.put(Defaults.ACCEPT_HEADER, Defaults.APPLICATION_JSON);
      this.defaultHeaders.put(Defaults.ACCEPT_ENCODING_HEADER, Defaults.CONTENT_ENCODING_GZIP);

      this.executor = ForkJoinPool.commonPool();
    }

    /** To prevent unchecked cast warning. */
    public abstract T getThis();

    /**
     * Makes the underlying Apache HTTP Client to use JVM/System settings for the proxy.
     *
     * @deprecated Please use a custom HttpAsyncClientBuilder when instantiating the
     *     ApacheHttpRequester instead.
     */
    public T setUseSystemProxy(boolean useSystemProxy) {
      this.useSystemProxy = useSystemProxy;
      return getThis();
    }

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

    /** Sets a list of specific host to target. Default hosts will be overridden. */
    public T setHosts(@Nonnull List<StatefulHost> customHosts) {
      this.hosts = customHosts;
      return getThis();
    }

    /** Sets a custom executor service. Default ForkJoinPool will be overridden. */
    public T setExecutorService(ExecutorService executorService) {
      this.executor = executorService;
      return getThis();
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously
     * contained a mapping for the key, the old value is replaced.
     */
    public T addExtraHeaders(@Nonnull String headerKey, @Nonnull String headerValue) {
      this.defaultHeaders.put(headerKey, headerValue);
      return getThis();
    }

    /**
     * Copies all of the mappings from the specified map to this map. These mappings will replace
     * any mappings that this map had for any of the keys currently in the specified map.
     */
    public T addExtraHeaders(Map<String, String> headers) {
      this.defaultHeaders.putAll(headers);
      return getThis();
    }
  }

  protected ConfigBase(Builder<?> builder) {

    Objects.requireNonNull(builder.applicationID, "An ApplicationID is required.");
    Objects.requireNonNull(builder.apiKey, "An API key is required.");
    Objects.requireNonNull(builder.hosts, "Default hosts are required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(builder.applicationID)) {
      throw new IllegalArgumentException("The ApplicationID can't be empty.");
    }

    if (AlgoliaUtils.isEmptyWhiteSpace(builder.apiKey)) {
      throw new IllegalArgumentException("The APIKey can't be empty.");
    }

    this.apiKey = builder.apiKey;
    this.applicationID = builder.applicationID;
    this.defaultHeaders = builder.defaultHeaders;
    this.useSystemProxy = builder.useSystemProxy;
    this.batchSize = builder.batchSize;
    this.compressionType = builder.compressionType;
    this.readTimeOut = builder.readTimeOut;
    this.writeTimeOut = builder.writeTimeOut;
    this.connectTimeOut = builder.connectTimeOut;
    this.hosts = builder.hosts;
    this.executor = builder.executor;
  }

  public String getApplicationID() {
    return applicationID;
  }

  public String getApiKey() {
    return apiKey;
  }

  public Map<String, String> getDefaultHeaders() {
    return defaultHeaders;
  }

  /**
   * Retrieve the settings which instructs the underlying Apache HTTP Client to use JVM/System
   * settings for the proxy.
   *
   * @deprecated Please use a custom HttpAsyncClientBuilder when instantiating the
   *     ApacheHttpRequester instead.
   */
  public boolean getUseSystemProxy() {
    return useSystemProxy;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public CompressionType getCompressionType() {
    return compressionType;
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

  public List<StatefulHost> getHosts() {
    return hosts;
  }

  public ExecutorService getExecutor() {
    return executor;
  }
}

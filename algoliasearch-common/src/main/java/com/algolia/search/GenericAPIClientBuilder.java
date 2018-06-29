package com.algolia.search;

import static com.algolia.search.Defaults.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public abstract class GenericAPIClientBuilder {

  protected final Random random = new Random();

  protected final String applicationId;
  protected final String apiKey;
  protected String customAgent;
  protected String customAgentVersion;
  protected String analyticsHost = ANALYTICS_HOST;
  protected List<String> queryHosts = generateQueryHosts();
  protected List<String> buildHosts = generateBuildHosts();
  protected Map<String, String> customHeaders = new HashMap<>();
  protected int connectTimeout = CONNECT_TIMEOUT_MS;
  protected int readTimeout = READ_TIMEOUT_MS;
  protected int hostDownTimeout = HOST_DOWN_TIMEOUT_MS;
  protected ObjectMapper objectMapper = DEFAULT_OBJECT_MAPPER;
  protected int maxConnTotal = 10;
  protected int maxConnPerRoute = maxConnTotal / 2;

  /**
   * Initialize this builder with the applicationId and apiKey
   *
   * @param applicationId APP_ID can be found on https://www.algolia.com/api-keys
   * @param apiKey Algolia API_KEY can also be found on https://www.algolia.com/api-keys
   */
  public GenericAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    this.applicationId = applicationId;
    this.apiKey = apiKey;
  }

  /**
   * Customize the user agent
   *
   * @param customAgent key to add to the user agent
   * @param customAgentVersion value of this key to add to the user agent
   * @return this
   */
  public GenericAPIClientBuilder setUserAgent(
      @Nonnull String customAgent, @Nonnull String customAgentVersion) {
    this.customAgent = customAgent;
    this.customAgentVersion = customAgentVersion;
    return this;
  }

  /** Deprecated: use {@link #addExtraHeader(String, String)} */
  @Deprecated
  public GenericAPIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    customHeaders.put(key, value);
    return this;
  }

  /**
   * Add extra headers to the requests
   *
   * @param key name of the header
   * @param value value of the header
   * @return this
   */
  public GenericAPIClientBuilder addExtraHeader(@Nonnull String key, String value) {
    customHeaders.put(key, value);
    return this;
  }

  /**
   * Set the connect timeout of the HTTP client
   *
   * @param connectTimeout the value in ms
   * @return this
   */
  public GenericAPIClientBuilder setConnectTimeout(int connectTimeout) {
    Preconditions.checkArgument(
        connectTimeout >= 0, "connectTimeout can not be < 0, but was %s", connectTimeout);

    this.connectTimeout = connectTimeout;
    return this;
  }

  /**
   * Set the read timeout of the HTTP client
   *
   * @param readTimeout the value in ms
   * @return this
   */
  public GenericAPIClientBuilder setReadTimeout(int readTimeout) {
    Preconditions.checkArgument(
        readTimeout >= 0, "readTimeout can not be < 0, but was %s", readTimeout);

    this.readTimeout = readTimeout;
    return this;
  }

  /**
   * Set the retry timeout to detect if a host is down
   *
   * @param hostDownTimeout the value in ms
   * @return this
   */
  public GenericAPIClientBuilder setHostDownTimeout(int hostDownTimeout) {
    Preconditions.checkArgument(
        hostDownTimeout >= 0, "hostDownTimeout can not be < 0, but was %s", hostDownTimeout);

    this.hostDownTimeout = hostDownTimeout;
    return this;
  }

  /**
   * Set the Jackson ObjectMapper, it overrides the default one and add 2 features: * Enables:
   * AUTO_CLOSE_JSON_CONTENT * Disables: FAIL_ON_UNKNOWN_PROPERTIES
   *
   * @param objectMapper the mapper
   * @return this
   */
  public GenericAPIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    this.objectMapper = objectMapper.copy();
    return this;
  }

  /**
   * Set the analytics host
   *
   * @param analyticsHost the analytics host to use
   * @return this
   */
  public GenericAPIClientBuilder setAnalyticsHost(@Nonnull String analyticsHost) {
    this.analyticsHost = analyticsHost;
    return this;
  }

  /**
   * Set the hosts for search & queries
   *
   * @param queryHosts the list of hosts for search
   * @return this
   */
  public GenericAPIClientBuilder setQueryHosts(List<String> queryHosts) {
    this.queryHosts = queryHosts;
    return this;
  }

  /**
   * Set the hosts for indexing & building
   *
   * @param buildHosts the list of hosts for indexing/building
   * @return this
   */
  public GenericAPIClientBuilder setBuildHosts(List<String> buildHosts) {
    this.buildHosts = buildHosts;
    return this;
  }

  /**
   * Set the maximum of connection, only available for {@link ApacheAPIClientBuilder} and {@link
   * AsyncAPIClientBuilder}
   *
   * @param maxConnTotal the max number of connections
   * @return this
   */
  public GenericAPIClientBuilder setMaxConnTotal(int maxConnTotal) {
    this.maxConnTotal = maxConnTotal;
    return this;
  }

  /**
   * Set the maximum of connection per route, only available for {@link ApacheAPIClientBuilder} and
   * {@link AsyncAPIClientBuilder}
   *
   * @param maxConnPerRoute the max number of connections per route
   * @return this
   */
  public GenericAPIClientBuilder setMaxConnPerRoute(int maxConnPerRoute) {
    this.maxConnPerRoute = maxConnPerRoute;
    return this;
  }

  private String getApiClientVersion() {
    try (InputStream versionStream = getClass().getResourceAsStream("version.properties")) {
      BufferedReader versionReader = new BufferedReader(new InputStreamReader(versionStream));
      return versionReader.readLine();
    } catch (IOException ignored) {
    }
    return "N/A";
  }

  protected List<String> generateBuildHosts() {
    List<String> hosts =
        Lists.newArrayList(
            applicationId + "-1." + ALGOLIANET_COM,
            applicationId + "-2." + ALGOLIANET_COM,
            applicationId + "-3." + ALGOLIANET_COM);
    Collections.shuffle(hosts, random);
    hosts.add(0, applicationId + "." + ALGOLIA_NET);

    return hosts;
  }

  protected List<String> generateQueryHosts() {
    List<String> hosts =
        Lists.newArrayList(
            applicationId + "-1." + ALGOLIANET_COM,
            applicationId + "-2." + ALGOLIANET_COM,
            applicationId + "-3." + ALGOLIANET_COM);
    Collections.shuffle(hosts, random);
    hosts.add(0, applicationId + "-dsn." + ALGOLIA_NET);

    return hosts;
  }

  protected Map<String, String> generateHeaders() {
    String userAgent =
        String.format(
            "Algolia for Java (%s); JVM (%s)",
            getApiClientVersion(), System.getProperty("java.version"));
    if (customAgent != null) {
      userAgent += "; " + customAgent + " (" + customAgentVersion + ")";
    }

    return ImmutableMap.<String, String>builder()
        .put(HttpHeaders.ACCEPT_ENCODING, "gzip")
        .put(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.type())
        .put(HttpHeaders.USER_AGENT, userAgent)
        .put("X-Algolia-Application-Id", applicationId)
        .put("X-Algolia-API-Key", apiKey)
        .putAll(customHeaders)
        .build();
  }
}

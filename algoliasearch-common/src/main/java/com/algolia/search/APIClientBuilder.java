package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.algolia.search.Defaults.*;

/**
 * Base class to create APIClient
 */
@SuppressWarnings("WeakerAccess")
public abstract class APIClientBuilder {

  private final String applicationId;
  private final String apiKey;
  private String customAgent;
  private String customAgentVersion;
  private Map<String, String> customHeaders = new HashMap<>();
  private int connectTimeout = CONNECT_TIMEOUT_MS;
  private int readTimeout = READ_TIMEOUT_MS;
  private ObjectMapper objectMapper = DEFAULT_OBJECT_MAPPER;

  /**
   * Initialize this builder with the applicationId and apiKey
   *
   * @param applicationId APP_ID can be found on https://www.algolia.com/api-keys
   * @param apiKey        Algolia API_KEY can also be found on https://www.algolia.com/api-keys
   */
  public APIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    this.applicationId = applicationId;
    this.apiKey = apiKey;
  }

  /**
   * Customize the user agent
   *
   * @param customAgent        key to add to the user agent
   * @param customAgentVersion value of this key to add to the user agent
   * @return this
   */
  public APIClientBuilder setUserAgent(@Nonnull String customAgent, @Nonnull String customAgentVersion) {
    this.customAgent = customAgent;
    this.customAgentVersion = customAgentVersion;
    return this;
  }

  /**
   * Set extra headers to the requests
   *
   * @param key   name of the header
   * @param value value of the header
   * @return this
   */
  public APIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    customHeaders.put(key, value);
    return this;
  }

  /**
   * Set the connect timeout of the HTTP client
   *
   * @param connectTimeout the value in ms
   * @return this
   */
  public APIClientBuilder setConnectTimeout(int connectTimeout) {
    Preconditions.checkArgument(connectTimeout >= 0, "connectTimeout can not be < 0, but was %s", connectTimeout);

    this.connectTimeout = connectTimeout;
    return this;
  }

  /**
   * Set the read timeout of the HTTP client
   *
   * @param readTimeout the value in ms
   * @return this
   */
  public APIClientBuilder setReadTimeout(int readTimeout) {
    Preconditions.checkArgument(readTimeout >= 0, "readTimeout can not be < 0, but was %s", readTimeout);

    this.readTimeout = readTimeout;
    return this;
  }

  /**
   * Set the Jackson ObjectMapper
   *
   * @param objectMapper the mapper
   * @return this
   */
  public APIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    return this;
  }

  private String getApiClientVersion() {
    try (InputStream versionStream = getClass().getResourceAsStream("/version.properties")) {
      BufferedReader versionReader = new BufferedReader(new InputStreamReader(versionStream));
      return versionReader.readLine();
    } catch (IOException ignored) {
    }
    return "N/A";
  }

  protected List<String> generateBuildHosts() {
    return Arrays.asList(
      applicationId + "." + ALGOLIA_NET,
      applicationId + "-1." + ALGOLIANET_COM,
      applicationId + "-2." + ALGOLIANET_COM,
      applicationId + "-3." + ALGOLIANET_COM
    );
  }

  protected List<String> generateQueryHosts() {
    return Arrays.asList(
      applicationId + "-dsn." + ALGOLIA_NET,
      applicationId + "-1." + ALGOLIANET_COM,
      applicationId + "-2." + ALGOLIANET_COM,
      applicationId + "-3." + ALGOLIANET_COM
    );
  }

  protected Map<String, String> generateHeaders() {
    String userAgent = String.format("Algolia for Java %s API %s", System.getProperty("java.version"), getApiClientVersion());
    if (customAgent != null) {
      userAgent += " " + customAgent + " " + customAgentVersion;
    }

    return ImmutableMap
      .<String, String>builder()
      .put(HttpHeaders.ACCEPT_ENCODING, "gzip")
      .put(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.type())
      .put(HttpHeaders.USER_AGENT, userAgent)
      .put("X-Algolia-Application-Id", applicationId)
      .put("X-Algolia-API-Key", apiKey)
      .putAll(customHeaders)
      .build();
  }

//  public AsyncAPIClient buildAsync() {
//    return buildAsync(
//      Executors.newFixedThreadPool(10),
//      applicationId,
//      apiKey,
//      generateBuildHosts(),
//      generateQueryHosts(),
//      buildHttpRequestInitializer(connectTimeout, readTimeout, generateHeaders())
//    );
//  }
//
//  public AsyncAPIClient buildAsync(Executor executor) {
//    return buildAsync(
//      executor,
//      applicationId,
//      apiKey,
//      generateBuildHosts(),
//      generateQueryHosts(),
//      buildHttpRequestInitializer(connectTimeout, readTimeout, generateHeaders())
//    );
//  }


  //  protected abstract AsyncAPIClient buildAsync(Executor executor, String applicationId, String apiKey, List<String> buildHosts, List<String> queryHosts, HttpRequestInitializer httpRequestInitializer);

  protected abstract APIClient build(@Nonnull APIClientConfiguration configuration);

  /**
   * Build the APIClient
   *
   * @return the built APIClient
   */
  public APIClient build() {
    return build(
      new APIClientConfiguration()
        .setApplicationId(applicationId)
        .setApiKey(apiKey)
        .setObjectMapper(objectMapper)
        .setBuildHosts(generateBuildHosts())
        .setQueryHosts(generateQueryHosts())
        .setHeaders(generateHeaders())
        .setConnectTimeout(connectTimeout)
        .setReadTimeout(readTimeout)
    );
  }

}

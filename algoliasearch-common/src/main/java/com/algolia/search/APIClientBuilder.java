package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.algolia.search.Defaults.*;

/**
 * Base class to create APIClient
 */
@SuppressWarnings("WeakerAccess")
public abstract class APIClientBuilder extends GenericAPIClientBuilder {

  /**
   * Initialize this builder with the applicationId and apiKey
   *
   * @param applicationId APP_ID can be found on https://www.algolia.com/api-keys
   * @param apiKey        Algolia API_KEY can also be found on https://www.algolia.com/api-keys
   */
  public APIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  /**
   * Customize the user agent
   *
   * @param customAgent        key to add to the user agent
   * @param customAgentVersion value of this key to add to the user agent
   * @return this
   */
  @Override
  public GenericAPIClientBuilder setUserAgent(@Nonnull String customAgent, @Nonnull String customAgentVersion) {
    super.setUserAgent(customAgent, customAgentVersion);
    return this;
  }

  /**
   * Set extra headers to the requests
   *
   * @param key   name of the header
   * @param value value of the header
   * @return this
   */
  @Override
  public GenericAPIClientBuilder setExtraHeader(@Nonnull String key, String value) {
    super.setExtraHeader(key, value);
    return this;
  }

  /**
   * Set the connect timeout of the HTTP client
   *
   * @param connectTimeout the value in ms
   * @return this
   */
  @Override
  public GenericAPIClientBuilder setConnectTimeout(int connectTimeout) {
    super.setConnectTimeout(connectTimeout);
    return this;
  }

  /**
   * Set the read timeout of the HTTP client
   *
   * @param readTimeout the value in ms
   * @return this
   */
  @Override
  public GenericAPIClientBuilder setReadTimeout(int readTimeout) {
    super.setReadTimeout(readTimeout);
    return this;
  }

  /**
   * Set the Jackson ObjectMapper
   *
   * @param objectMapper the mapper
   * @return this
   */
  @Override
  public GenericAPIClientBuilder setObjectMapper(@Nonnull ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
    return this;
  }

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

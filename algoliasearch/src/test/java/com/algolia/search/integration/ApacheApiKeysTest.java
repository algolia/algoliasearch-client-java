package com.algolia.search.integration;

import com.algolia.search.APIClient;
import com.algolia.search.ApacheAPIClientBuilder;
import com.algolia.search.integration.sync.SyncApiKeysTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApacheApiKeysTest extends SyncApiKeysTest {
  @Override
  public APIClient createInstance(String appId, String apiKey) {
    return new ApacheAPIClientBuilder(appId, apiKey).build();
  }

  @Override
  public APIClient createInstance(String appId, String apiKey, ObjectMapper objectMapper) {
    return new ApacheAPIClientBuilder(appId, apiKey).setObjectMapper(objectMapper).build();
  }
}

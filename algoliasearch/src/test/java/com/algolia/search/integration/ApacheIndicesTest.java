package com.algolia.search.integration;

import com.algolia.search.APIClient;
import com.algolia.search.ApacheAPIClientBuilder;
import com.algolia.search.integration.sync.SyncIndicesTest;

public class ApacheIndicesTest extends SyncIndicesTest {

  @Override
  public APIClient createInstance(String appId, String apiKey) {
    return new ApacheAPIClientBuilder(appId, apiKey).build();
  }
}

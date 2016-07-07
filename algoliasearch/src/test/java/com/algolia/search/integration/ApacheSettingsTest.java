package com.algolia.search.integration;

import com.algolia.search.APIClient;
import com.algolia.search.ApacheAPIClientBuilder;

public class ApacheSettingsTest extends SettingsTest {
  @Override
  public APIClient createInstance(String appId, String apiKey) {
    return new ApacheAPIClientBuilder(appId, apiKey).build();
  }

}

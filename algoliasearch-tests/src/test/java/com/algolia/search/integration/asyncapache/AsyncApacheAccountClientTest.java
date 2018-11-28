package com.algolia.search.integration.asyncapache;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;
import com.algolia.search.integration.common.async.AsyncAccountClientTest;

public class AsyncApacheAccountClientTest extends AsyncAccountClientTest {
  @Override
  public AsyncAPIClient createInstance(String appId, String apiKey) {
    return new AsyncHttpAPIClientBuilder(appId, apiKey).build();
  }
}

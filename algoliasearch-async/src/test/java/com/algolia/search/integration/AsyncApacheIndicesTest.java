package com.algolia.search.integration;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;
import com.algolia.search.integration.async.AsyncIndicesTest;

public class AsyncApacheIndicesTest extends AsyncIndicesTest {

  @Override
  public AsyncAPIClient createInstance(String appId, String apiKey) {
    return new AsyncHttpAPIClientBuilder(appId, apiKey).build();
  }

}

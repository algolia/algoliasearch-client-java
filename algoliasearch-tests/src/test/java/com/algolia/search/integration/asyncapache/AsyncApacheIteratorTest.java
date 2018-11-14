package com.algolia.search.integration.asyncapache;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;
import com.algolia.search.integration.common.async.AsyncIteratorTest;

public class AsyncApacheIteratorTest extends AsyncIteratorTest {

  @Override
  public AsyncAPIClient createInstance(String appId, String apiKey) {
    return new AsyncHttpAPIClientBuilder(appId, apiKey).build();
  }
}

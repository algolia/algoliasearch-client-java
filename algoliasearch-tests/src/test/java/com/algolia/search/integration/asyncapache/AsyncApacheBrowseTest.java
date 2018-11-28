package com.algolia.search.integration.asyncapache;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;
import com.algolia.search.integration.common.async.AsyncBrowseTest;

public class AsyncApacheBrowseTest extends AsyncBrowseTest {

  @Override
  public AsyncAPIClient createInstance(String appId, String apiKey) {
    return new AsyncHttpAPIClientBuilder(appId, apiKey).build();
  }
}

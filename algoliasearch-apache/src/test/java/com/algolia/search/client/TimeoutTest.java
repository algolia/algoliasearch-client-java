package com.algolia.search.client;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_API_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;

class TimeoutTest extends com.algolia.search.integration.client.TimeoutTest {
  protected SearchConfig.Builder createBuilder() {
    return new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
  }

  protected SearchClient createClient(SearchConfig config) {
    return DefaultSearchClient.create(config);
  }
}

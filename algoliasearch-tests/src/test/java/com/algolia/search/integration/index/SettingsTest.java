package com.algolia.search.integration.index;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import org.junit.jupiter.api.Test;

class SettingsTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex index;
  private String indexName;

  void init() {
    indexName = getTestIndexName("settings");
    index = searchClient.initIndex(indexName);
  }

  @Test
  void testSettings() {
    init();
    index.getSettingsAsync();
  }
}

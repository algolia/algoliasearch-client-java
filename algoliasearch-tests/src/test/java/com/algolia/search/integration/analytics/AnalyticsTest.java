package com.algolia.search.integration.analytics;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.responses.ABTests;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class AnalyticsTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex index1;
  private SearchIndex index2;
  private String index1Name;
  private String index2Name;

  void init() {
    index1Name = getTestIndexName("ab_testing");
    index2Name = getTestIndexName("ab_testing_dev");
    index1 = searchClient.initIndex(index1Name);
    index2 = searchClient.initIndex(index2Name);
  }

  @Test
  void abTestingTest() {
    init();
    CompletableFuture<ABTests> abTests = analyticsClient.getABTestsAsync();
    abTests.join();
  }
}

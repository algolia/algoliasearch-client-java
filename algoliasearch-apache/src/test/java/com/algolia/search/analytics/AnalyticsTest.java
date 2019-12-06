package com.algolia.search.analytics;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_API_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.AnalyticsClient;
import com.algolia.search.DefaultAnalyticsClient;
import com.algolia.search.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class AnalyticsTest extends com.algolia.search.integration.analytics.AnalyticsTest {

  private static AnalyticsClient analyticsClient =
      DefaultAnalyticsClient.create(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);

  AnalyticsTest() {
    super(IntegrationTestExtension.searchClient, analyticsClient);
  }

  @AfterAll
  static void afterAll() throws IOException {
    analyticsClient.close();
  }
}

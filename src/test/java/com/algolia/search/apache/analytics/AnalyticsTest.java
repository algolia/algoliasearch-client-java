package com.algolia.search.apache.analytics;

import static com.algolia.search.integration.IntegrationTestExtension.ALGOLIA_API_KEY_1;
import static com.algolia.search.integration.IntegrationTestExtension.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.AnalyticsClient;
import com.algolia.search.integration.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnalyticsTest extends com.algolia.search.integration.analytics.AnalyticsTest {

  AnalyticsTest() {
    super(
        IntegrationTestExtension.searchClient,
        new AnalyticsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1));
  }

  @AfterAll
  void afterAll() throws IOException {
    analyticsClient.close();
  }
}

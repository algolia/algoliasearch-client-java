package com.algolia.search.analytics;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.*;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class AnalyticsTest extends com.algolia.search.integration.analytics.AnalyticsTest {

  private static AnalyticsConfig analyticsConfig =
      new AnalyticsConfig.Builder(ALGOLIA_APPLICATION_ID_1, ALGOLIA_ADMIN_KEY_1).build();
  private static AnalyticsClient analyticsClient =
      new AnalyticsClient(analyticsConfig, new JavaNetHttpRequester(analyticsConfig));

  AnalyticsTest() {
    super(IntegrationTestExtension.searchClient, analyticsClient);
  }

  @AfterAll
  static void afterAll() throws IOException {
    analyticsClient.close();
  }
}

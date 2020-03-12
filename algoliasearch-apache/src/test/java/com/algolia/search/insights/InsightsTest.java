package com.algolia.search.insights;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.DefaultInsightsClient;
import com.algolia.search.InsightsClient;
import com.algolia.search.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class InsightsTest extends com.algolia.search.integration.insights.InsightsTest {

  private static InsightsClient insightsClient =
      DefaultInsightsClient.create(ALGOLIA_APPLICATION_ID_1, ALGOLIA_ADMIN_KEY_1);

  InsightsTest() {
    super(IntegrationTestExtension.searchClient, insightsClient);
  }

  @AfterAll
  static void close() throws IOException {
    insightsClient.close();
  }
}

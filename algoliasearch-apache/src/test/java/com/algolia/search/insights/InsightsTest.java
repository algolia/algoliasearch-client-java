package com.algolia.search.insights;

import com.algolia.search.DefaultInsightsClient;
import com.algolia.search.InsightsClient;
import com.algolia.search.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InsightsTest extends com.algolia.search.integration.insights.InsightsTest {

  private static InsightsClient insightsClient =
      DefaultInsightsClient.create(
          IntegrationTestExtension.ALGOLIA_APPLICATION_ID_1,
          IntegrationTestExtension.ALGOLIA_API_KEY_1);

  InsightsTest() {
    super(IntegrationTestExtension.searchClient, insightsClient);
  }

  @AfterAll
  void close() throws IOException {
    insightsClient.close();
  }
}

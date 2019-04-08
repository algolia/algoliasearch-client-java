package com.algolia.search.integration.client;

import static com.algolia.search.integration.IntegrationTestExtension.ALGOLIA_API_KEY_1;
import static com.algolia.search.integration.IntegrationTestExtension.ALGOLIA_APPLICATION_ID_1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.exceptions.AlgoliaRetryException;
import java.io.IOException;
import org.junit.jupiter.api.Disabled;

class TimeoutTest {

  @Disabled
  void testUnreachableHostExceptions() {
    try (SearchClient client =
        new SearchClient(
            new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1)
                .setConnectTimeOut(2)
                .setReadTimeOut(2)
                .build())) {
      assertThatThrownBy(() -> client.getLogsAsync().get())
          .hasCauseInstanceOf(AlgoliaRetryException.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

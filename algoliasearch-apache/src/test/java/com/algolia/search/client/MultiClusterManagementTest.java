package com.algolia.search.client;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MultiClusterManagementTest
    extends com.algolia.search.integration.client.MultiClusterManagementTest {

  MultiClusterManagementTest() {
    super(
        DefaultSearchClient.create(
            IntegrationTestExtension.ALGOLIA_APPLICATION_ID_MCM,
            IntegrationTestExtension.ALGOLIA_ADMIN_KEY_MCM));
  }

  @AfterAll
  void afterAll() throws IOException {
    mcmClient.close();
  }
}

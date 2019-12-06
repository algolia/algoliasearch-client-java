package com.algolia.search.client;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_MCM;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_MCM;

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
    super(DefaultSearchClient.create(ALGOLIA_APPLICATION_ID_MCM, ALGOLIA_ADMIN_KEY_MCM));
  }

  @AfterAll
  void afterAll() throws IOException {
    mcmClient.close();
  }
}

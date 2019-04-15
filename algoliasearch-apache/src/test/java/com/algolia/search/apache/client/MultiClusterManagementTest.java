package com.algolia.search.apache.client;

import static com.algolia.search.apache.IntegrationTestExtension.ALGOLIA_ADMIN_KEY_MCM;
import static com.algolia.search.apache.IntegrationTestExtension.ALGOLIA_APPLICATION_ID_MCM;

import com.algolia.search.apache.ApacheSearchClient;
import com.algolia.search.apache.IntegrationTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MultiClusterManagementTest
    extends com.algolia.search.integration.client.MultiClusterManagementTest {

  MultiClusterManagementTest() {
    super(ApacheSearchClient.create(ALGOLIA_APPLICATION_ID_MCM, ALGOLIA_ADMIN_KEY_MCM));
  }

  @AfterAll
  void afterAll() throws IOException {
    mcmClient.close();
  }
}

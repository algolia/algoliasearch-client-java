package com.algolia.search.client;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_MCM;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_MCM;

import com.algolia.search.IntegrationTestExtension;
import com.algolia.search.JavaNetHttpRequester;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MultiClusterManagementTest
    extends com.algolia.search.integration.client.MultiClusterManagementTest {

  private static SearchConfig mcmConfig =
      new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_MCM, ALGOLIA_ADMIN_KEY_MCM).build();

  MultiClusterManagementTest() {

    super(new SearchClient(mcmConfig, new JavaNetHttpRequester(mcmConfig)));
  }
}

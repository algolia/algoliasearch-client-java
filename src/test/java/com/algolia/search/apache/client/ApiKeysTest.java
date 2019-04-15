package com.algolia.search.apache.client;

import com.algolia.search.integration.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class ApiKeysTest extends com.algolia.search.integration.client.ApiKeysTest {
  ApiKeysTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

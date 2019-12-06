package com.algolia.search.client;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class ApiKeysTest extends com.algolia.search.integration.client.ApiKeysTest {
  ApiKeysTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

package com.algolia.search.client;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class MultipleOperationsTest extends com.algolia.search.integration.client.MultipleOperationsTest {
  MultipleOperationsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

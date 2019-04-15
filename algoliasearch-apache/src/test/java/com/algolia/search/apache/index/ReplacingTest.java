package com.algolia.search.apache.index;

import com.algolia.search.apache.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class ReplacingTest extends com.algolia.search.integration.index.ReplacingTest {
  ReplacingTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

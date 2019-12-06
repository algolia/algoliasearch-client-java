package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class ReplacingTest extends com.algolia.search.integration.index.ReplacingTest {
  ReplacingTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

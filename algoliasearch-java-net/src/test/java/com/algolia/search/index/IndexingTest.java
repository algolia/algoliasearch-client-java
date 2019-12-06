package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class IndexingTest extends com.algolia.search.integration.index.IndexingTest {
  IndexingTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

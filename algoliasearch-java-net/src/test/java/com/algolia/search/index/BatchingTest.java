package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class BatchingTest extends com.algolia.search.integration.index.BatchingTest {
  BatchingTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

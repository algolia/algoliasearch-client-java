package com.algolia.search.apache.index;

import com.algolia.search.apache.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class BatchingTest extends com.algolia.search.integration.index.BatchingTest {
  BatchingTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

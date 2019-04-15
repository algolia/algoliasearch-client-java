package com.algolia.search.apache.index;

import com.algolia.search.integration.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SynonymsTest extends com.algolia.search.integration.index.SynonymsTest {
  SynonymsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

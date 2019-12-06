package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SynonymsTest extends com.algolia.search.integration.index.SynonymsTest {
  SynonymsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

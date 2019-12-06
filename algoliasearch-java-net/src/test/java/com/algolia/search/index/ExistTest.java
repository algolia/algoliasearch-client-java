package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class ExistTest extends com.algolia.search.integration.index.ExistTest {
  ExistTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

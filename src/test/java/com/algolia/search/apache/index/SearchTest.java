package com.algolia.search.apache.index;

import com.algolia.search.integration.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SearchTest extends com.algolia.search.integration.index.SearchTest {
  SearchTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

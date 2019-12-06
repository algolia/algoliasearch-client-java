package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SearchTest extends com.algolia.search.integration.index.SearchTest {
  SearchTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

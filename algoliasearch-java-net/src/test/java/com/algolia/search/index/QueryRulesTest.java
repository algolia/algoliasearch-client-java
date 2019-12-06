package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class QueryRulesTest extends com.algolia.search.integration.index.QueryRulesTest {
  QueryRulesTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

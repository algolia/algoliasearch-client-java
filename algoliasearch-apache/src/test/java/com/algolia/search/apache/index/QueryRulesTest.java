package com.algolia.search.apache.index;

import com.algolia.search.apache.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class QueryRulesTest extends com.algolia.search.integration.index.QueryRulesTest {
  QueryRulesTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

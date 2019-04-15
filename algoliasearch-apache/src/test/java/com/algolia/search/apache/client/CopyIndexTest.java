package com.algolia.search.apache.client;

import com.algolia.search.apache.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class CopyIndexTest extends com.algolia.search.integration.client.CopyIndexTest {
  CopyIndexTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

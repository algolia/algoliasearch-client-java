package com.algolia.search.client;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class LogsTest extends com.algolia.search.integration.client.LogsTest {
  LogsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

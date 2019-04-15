package com.algolia.search.apache.client;

import com.algolia.search.apache.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class LogsTest extends com.algolia.search.integration.client.LogsTest {
  LogsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

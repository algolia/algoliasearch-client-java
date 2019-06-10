package com.algolia.search.index;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SettingsTest extends com.algolia.search.integration.index.SettingsTest {
  SettingsTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

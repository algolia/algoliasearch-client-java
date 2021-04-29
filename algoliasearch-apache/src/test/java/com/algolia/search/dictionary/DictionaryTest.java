package com.algolia.search.dictionary;

import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
public class DictionaryTest extends com.algolia.search.integration.dictionary.DictionaryTest {

  protected DictionaryTest() {
    super(IntegrationTestExtension.searchClient);
  }
}

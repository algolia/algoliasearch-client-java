package com.algolia.search.client;

import com.algolia.search.ApacheHttpRequester;
import com.algolia.search.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
public class RetryStrategyE2ETest extends com.algolia.search.RetryStrategyE2ETest {

  public RetryStrategyE2ETest() {
    super(IntegrationTestExtension.searchClient);
    httpRequester = new ApacheHttpRequester(config);
  }
}

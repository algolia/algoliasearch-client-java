package com.algolia.search;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
public class ApacheRetryStrategyE2ETest extends RetryStrategyE2ETest {

  public ApacheRetryStrategyE2ETest() {
    super(IntegrationTestExtension.searchClient);
    httpRequester = new ApacheHttpRequester(config);
  }
}

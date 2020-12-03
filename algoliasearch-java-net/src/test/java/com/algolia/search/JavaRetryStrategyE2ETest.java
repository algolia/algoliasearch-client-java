package com.algolia.search;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
public class JavaRetryStrategyE2ETest extends RetryStrategyE2ETest {

  public JavaRetryStrategyE2ETest() {
    super(IntegrationTestExtension.searchClient);
    httpRequester = new JavaNetHttpRequester(config);
  }
}

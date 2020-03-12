package com.algolia.search;

import com.algolia.search.integration.TestHelpers;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IntegrationTestExtension implements BeforeAllCallback {

  public static SearchClient searchClient;
  public static SearchClient searchClient2;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    TestHelpers.checkEnvironmentVariable();

    SearchConfig clientConfig =
        new SearchConfig.Builder(
                TestHelpers.ALGOLIA_APPLICATION_ID_1, TestHelpers.ALGOLIA_ADMIN_KEY_1)
            .build();
    searchClient = new SearchClient(clientConfig, new JavaNetHttpRequester(clientConfig));

    SearchConfig client2Config =
        new SearchConfig.Builder(
                TestHelpers.ALGOLIA_APPLICATION_ID_2, TestHelpers.ALGOLIA_ADMIN_KEY_2)
            .build();
    searchClient2 = new SearchClient(client2Config, new JavaNetHttpRequester(client2Config));
  }
}

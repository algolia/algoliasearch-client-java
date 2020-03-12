package com.algolia.search;


import com.algolia.search.integration.TestHelpers;
import com.algolia.search.models.common.CompressionType;
import java.io.IOException;

import static com.algolia.search.integration.TestHelpers.*;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IntegrationTestExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

  public static SearchClient searchClient;
  public static SearchClient searchClient2;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    TestHelpers.checkEnvironmentVariable();
    searchClient = DefaultSearchClient.create(ALGOLIA_APPLICATION_ID_1, ALGOLIA_ADMIN_KEY_1);
    // Disabling gzip for client2 because GZip not is not enabled yet on the server
    SearchConfig client2Config =
        new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_2, ALGOLIA_ADMIN_KEY_2)
            .setCompressionType(CompressionType.NONE)
            .build();
    searchClient2 = DefaultSearchClient.create(client2Config);
  }

  @Override
  public void close() {
    try {
      searchClient2.close();
      searchClient.close();
    } catch (IOException ignored) {
    }
  }

}

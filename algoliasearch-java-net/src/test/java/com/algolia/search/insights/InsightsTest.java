package com.algolia.search.insights;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

import com.algolia.search.*;
import java.net.http.HttpClient;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class InsightsTest extends com.algolia.search.integration.insights.InsightsTest {

  private static InsightsConfig config =
      new InsightsConfig.Builder(ALGOLIA_APPLICATION_ID_1, ALGOLIA_ADMIN_KEY_1).build();

  InsightsTest() {
    super(
        IntegrationTestExtension.searchClient,
        new InsightsClient(
            config,
            new JavaNetHttpRequester(
                config,
                HttpClient.newBuilder().sslParameters(SSLUtils.getDefaultSSLParameters()))));
  }
}

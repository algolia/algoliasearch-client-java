package com.algolia.search.client;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_SEARCH_KEY_1;

import com.algolia.search.IntegrationTestExtension;
import com.algolia.search.JavaNetHttpRequester;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class SecuredAPIKeyTest extends com.algolia.search.integration.client.SecuredAPIKeyTest {

  SecuredAPIKeyTest() {
    super(IntegrationTestExtension.searchClient);
  }

  @Override
  protected SearchClient createClientWithRestriction(SecuredApiKeyRestriction restriction)
      throws Exception {
    String key = searchClient.generateSecuredAPIKey(ALGOLIA_SEARCH_KEY_1, restriction);
    SearchConfig restrictedConfig = new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_1, key).build();
    return new SearchClient(restrictedConfig, new JavaNetHttpRequester(restrictedConfig));
  }
}

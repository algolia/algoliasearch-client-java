package com.algolia.search.apache.client;

import static com.algolia.search.integration.IntegrationTestExtension.*;

import com.algolia.search.SearchClient;
import com.algolia.search.integration.IntegrationTestExtension;
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
    return new SearchClient(ALGOLIA_APPLICATION_ID_1, key);
  }
}

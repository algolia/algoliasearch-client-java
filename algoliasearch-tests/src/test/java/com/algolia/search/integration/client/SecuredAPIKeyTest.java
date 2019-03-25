package com.algolia.search.integration.client;

import static com.algolia.search.integration.AlgoliaIntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.AlgoliaIntegrationTestExtension;
import com.algolia.search.integration.models.AlgoliaObject;
import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.Query;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({AlgoliaIntegrationTestExtension.class})
class SecuredAPIKeyTest {

  private SearchIndex<AlgoliaObject> index1;
  private SearchIndex<AlgoliaObject> index2;
  private String index1Name;
  private String index2Name;

  SecuredAPIKeyTest() {
    index1Name = getTestIndexName("secured_api_keys");
    index2Name = getTestIndexName("secured_api_keys_dev");
    index1 = searchClient.initIndex(index1Name, AlgoliaObject.class);
    index2 = searchClient2.initIndex(index2Name, AlgoliaObject.class);
  }

  @Test
  void testSecuredAPIKey() throws Exception {
    CompletableFuture<BatchIndexingResponse> addOneFuture =
        index1.saveObjectAsync(new AlgoliaObject().setObjectID("one"));
    CompletableFuture<BatchIndexingResponse> addTwoFuture =
        index2.saveObjectAsync(new AlgoliaObject().setObjectID("one"));

    SecuredApiKeyRestriction restriction =
        new SecuredApiKeyRestriction()
            .setRestrictIndices(Collections.singletonList(index1Name))
            .setValidUntil(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(10).toEpochSecond());

    addOneFuture.get().waitTask();
    addTwoFuture.get().waitTask();

    String key = searchClient.generateSecuredAPIKey(ALGOLIA_SEARCH_KEY_1, restriction);

    SearchClient clientWithRestriction = new SearchClient(ALGOLIA_APPLICATION_ID_1, key);

    SearchIndex<AlgoliaObject> index1WithoutRestriction =
        clientWithRestriction.initIndex(index1Name, AlgoliaObject.class);
    SearchIndex<AlgoliaObject> index2WithRestriction =
        clientWithRestriction.initIndex(index2Name, AlgoliaObject.class);

    index1WithoutRestriction.searchAsync(new Query()).get();

    assertThatThrownBy(() -> index2WithRestriction.search(new Query()))
        .hasMessageContaining("Index not allowed with this API key");
  }
}

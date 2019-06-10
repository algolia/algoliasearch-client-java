package com.algolia.search.integration.client;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.AlgoliaObject;
import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.Query;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

public abstract class SecuredAPIKeyTest {

  protected SearchClient searchClient;

  protected SecuredAPIKeyTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  protected abstract SearchClient createClientWithRestriction(SecuredApiKeyRestriction restriction)
      throws Exception;

  @Test
  void testSecuredAPIKey() throws Exception {
    String index1Name = getTestIndexName("secured_api_keys");
    String index2Name = getTestIndexName("secured_api_keys_dev");
    SearchIndex<AlgoliaObject> index1 = searchClient.initIndex(index1Name, AlgoliaObject.class);
    SearchIndex<AlgoliaObject> index2 = searchClient.initIndex(index2Name, AlgoliaObject.class);
    try {
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

      try (SearchClient clientWithRestriction = createClientWithRestriction(restriction)) {
        SearchIndex<AlgoliaObject> index1WithoutRestriction =
            clientWithRestriction.initIndex(index1Name, AlgoliaObject.class);
        SearchIndex<AlgoliaObject> index2WithRestriction =
            clientWithRestriction.initIndex(index2Name, AlgoliaObject.class);

        index1WithoutRestriction.searchAsync(new Query()).get();

        assertThatThrownBy(() -> index2WithRestriction.search(new Query()))
            .hasMessageContaining("Index not allowed with this API key");
      }
    } finally {
      index2.deleteAsync();
    }
  }
}

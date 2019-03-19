package com.algolia.search.integration.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.models.apikeys.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class ApiKeysTest extends AlgoliaBaseIntegrationTest {
  private String apiKey;

  @Test
  void testApiKeys() {

    ApiKey apiKeyToSend =
        new ApiKey()
            .setAcl(Collections.singletonList("search"))
            .setDescription("Description")
            .setIndexes(Collections.singletonList("indexes"))
            .setMaxHitsPerQuery(1000)
            .setMaxQueriesPerIPPerHour(1000)
            .setQueryParameters("typoTolerance=strict")
            .setReferers(Collections.singletonList("referer"))
            .setValidity(600L);

    CompletableFuture<AddApiKeyResponse> addApiKeyFuture =
        searchClient.addApiKeyAsync(apiKeyToSend);
    AddApiKeyResponse addKeyResponse = addApiKeyFuture.join();
    apiKey = addKeyResponse.getKey();
    apiKeyToSend.setValue(apiKey);
    addKeyResponse.waitTask();

    ApiKey addedKey = searchClient.getApiKeyAsync(apiKey).join();
    // Assert that the objects are deeply equal;
    assertThat(addedKey)
        .usingRecursiveComparison()
        .ignoringFields("createdAt", "validity", "value")
        .isEqualTo(apiKeyToSend);

    List<ApiKey> allKeys = searchClient.listApiKeysAsync().join();
    assertThat(allKeys).extracting(ApiKey::getValue).contains(apiKey);

    apiKeyToSend.setMaxHitsPerQuery(42);
    UpdateApiKeyResponse updateKey = searchClient.updateApiKeyAsync(apiKeyToSend).join();
    updateKey.waitTask();

    ApiKey updatedKey = searchClient.getApiKeyAsync(apiKey).join();
    assertThat(updatedKey.getMaxHitsPerQuery()).isEqualTo(42);

    DeleteApiKeyResponse deleteApiKey = searchClient.deleteApiKeyAsync(apiKey).join();
    deleteApiKey.waitTask();

    RestoreApiKeyResponse restoreApiKey = searchClient.restoreApiKeyAsync(apiKey).join();
    restoreApiKey.waitTask();

    searchClient
        .getApiKeyAsync(apiKey)
        .thenApply(r -> searchClient.deleteApiKeyAsync(apiKey))
        .join();
  }
}

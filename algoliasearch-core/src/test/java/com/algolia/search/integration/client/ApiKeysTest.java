package com.algolia.search.integration.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.SearchClient;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.integration.TestHelpers;
import com.algolia.search.models.apikeys.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

public abstract class ApiKeysTest {

  protected final SearchClient searchClient;

  protected ApiKeysTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testApiKeys() throws Exception {

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
    String apiKey = addKeyResponse.getKey();
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

    TestHelpers.retry(
        () -> {
          boolean shouldRetry;
          try {
            RestoreApiKeyResponse restoreApiKey = searchClient.restoreApiKeyAsync(apiKey).join();
            restoreApiKey.waitTask();
            shouldRetry = false;
          } catch (AlgoliaApiException e) {
            shouldRetry =
                e.getHttpErrorCode() == 404 && e.getMessage().equals("Key already exists");
          }
          return shouldRetry;
        },
        Duration.ofSeconds(1),
        10);

    searchClient
        .getApiKeyAsync(apiKey)
        .thenApply(r -> searchClient.deleteApiKeyAsync(apiKey))
        .join();
  }

  @Test
  void testExpiredKey() throws Exception {
    SecuredApiKeyRestriction restriction =
        new SecuredApiKeyRestriction()
            .setValidUntil(Instant.now().getEpochSecond() - 600)
            .setRestrictIndices(Collections.singletonList("index"));

    String expiredKey = searchClient.generateSecuredAPIKey("parentKey", restriction);

    Duration remainingValidity = searchClient.getSecuredApiKeyRemainingValidity(expiredKey);

    assertThat(remainingValidity.getSeconds()).isLessThan(0);
  }

  @Test
  void testValidKey() throws Exception {
    SecuredApiKeyRestriction restriction =
        new SecuredApiKeyRestriction()
            .setValidUntil(Instant.now().getEpochSecond() + 600)
            .setRestrictIndices(Collections.singletonList("index"));

    String expiredKey = searchClient.generateSecuredAPIKey("parentKey", restriction);

    Duration remainingValidity = searchClient.getSecuredApiKeyRemainingValidity(expiredKey);

    assertThat(remainingValidity.getSeconds()).isGreaterThan(0);
  }

  @Test
  void testRemainingValidityParameters() throws Exception {
    SecuredApiKeyRestriction restriction =
        new SecuredApiKeyRestriction().setRestrictIndices(Collections.singletonList("index"));

    String expiredKey = searchClient.generateSecuredAPIKey("parentKey", restriction);

    assertThatThrownBy(() -> searchClient.getSecuredApiKeyRemainingValidity(expiredKey))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining("The Secured API Key doesn't have a validUntil parameter");
  }
}

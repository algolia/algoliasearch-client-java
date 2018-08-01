package com.algolia.search.integration.common.async;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.objects.ApiKey;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import org.junit.Before;
import org.junit.Test;

public abstract class AsyncApiKeysTest extends AsyncAlgoliaIntegrationTest {

  /**
   * If the tests are run on Travis from a pull request of a contributor we need to bypass key
   * management tests since they require the Admin API key
   */
  @Before
  public void skipIfIsCommunity() {
    String isCommunity = System.getenv("IS_COMMUNITY");
    if (isCommunity != null && !isCommunity.isEmpty()) {
      assumeTrue(false);
    }
  }

  private void waitForKeyPresent(AsyncIndex<AlgoliaObject> index, @Nonnull String description)
      throws Exception {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      CompletableFuture<List<ApiKey>> apiKeys =
          index == null ? client.listApiKeys() : index.listApiKeys();
      boolean found =
          apiKeys
              .get(WAIT_TIME_IN_SECONDS, SECONDS)
              .stream()
              .map(ApiKey::getDescription)
              .anyMatch(description::equals);
      if (found) {
        return;
      }
    }

    // will fail
    futureAssertThat(client.listApiKeys()).extracting("description").contains(description);
  }

  private void waitForKeyNotPresent(AsyncIndex<AlgoliaObject> index, String description)
      throws Exception {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      CompletableFuture<List<ApiKey>> apiKeys =
          index == null ? client.listApiKeys() : index.listApiKeys();
      boolean found =
          apiKeys
              .get(WAIT_TIME_IN_SECONDS, SECONDS)
              .stream()
              .map(ApiKey::getDescription)
              .filter(k -> k != null)
              .anyMatch(k -> k.equals(description));
      if (!found) {
        return;
      }
    }

    // will fail
    futureAssertThat(client.listApiKeys()).extracting("description").doesNotContain(description);
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void manageKeys() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    // Fill index
    waitForCompletion(index.addObject(new AlgoliaObject("1", 1)));

    ApiKey apiKey =
        new ApiKey()
            .setDescription("toto" + System.currentTimeMillis())
            .setIndexes(Collections.singletonList(index.getName()));

    String keyName = client.addApiKey(apiKey).get().getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(null, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    client.updateApiKey(keyName, apiKey).get();

    waitForKeyPresent(null, apiKey.getDescription());

    assertThat(client.getApiKey(keyName).get().get().getDescription())
        .isEqualTo(apiKey.getDescription());

    client.deleteApiKey(keyName).get();

    waitForKeyNotPresent(null, apiKey.getDescription());
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void manageKeysForIndex() throws Exception {
    // Fill index
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("1", 1)));

    ApiKey apiKey = new ApiKey().setDescription("toto" + System.currentTimeMillis());

    String keyName = index.addApiKey(apiKey).get().getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(index, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    index.updateApiKey(keyName, apiKey).get();

    waitForKeyPresent(index, apiKey.getDescription());

    assertThat(index.getApiKey(keyName).get().get().getDescription())
        .isEqualTo(apiKey.getDescription());

    index.deleteApiKey(keyName).get();

    waitForKeyNotPresent(index, apiKey.getDescription());
  }
}

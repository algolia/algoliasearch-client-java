package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.ApiKey;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("ConstantConditions")
public abstract class SyncApiKeysTest extends SyncAlgoliaIntegrationTest {

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

  private void waitForKeyPresent(Index<AlgoliaObject> index, String description)
      throws AlgoliaException, InterruptedException {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      List<ApiKey> apiKeys = index == null ? client.listApiKeys() : index.listApiKeys();
      boolean found = apiKeys.stream().map(ApiKey::getDescription).anyMatch(description::equals);
      if (found) {
        return;
      }
    }

    // will fail
    assertThat(client.listApiKeys()).extracting("description").contains(description);
  }

  private void waitForKeyNotPresent(Index<AlgoliaObject> index, String description)
      throws AlgoliaException, InterruptedException {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      List<ApiKey> apiKeys = index == null ? client.listApiKeys() : index.listApiKeys();
      boolean found = apiKeys.stream().map(ApiKey::getDescription).anyMatch(description::equals);
      if (!found) {
        return;
      }
    }

    // will fail
    assertThat(client.listApiKeys()).extracting("description").doesNotContain(description);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeys() throws AlgoliaException, InterruptedException {
    // Fill index
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("1", 1)));

    ApiKey apiKey =
        new ApiKey()
            .setDescription("toto" + System.currentTimeMillis())
            .setIndexes(Collections.singletonList(index.getName()));

    String keyName = client.addApiKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(null, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    client.updateApiKey(keyName, apiKey);

    waitForKeyPresent(null, apiKey.getDescription());

    assertThat(client.getApiKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    client.deleteApiKey(keyName);

    waitForKeyNotPresent(null, apiKey.getDescription());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeysForIndex() throws AlgoliaException, InterruptedException {
    // Fill index
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("1", 1)).waitForCompletion();

    ApiKey apiKey = new ApiKey().setDescription("toto" + System.currentTimeMillis());

    String keyName = index.addApiKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(index, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    index.updateApiKey(keyName, apiKey);

    waitForKeyPresent(index, apiKey.getDescription());

    assertThat(index.getApiKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    index.deleteApiKey(keyName);

    waitForKeyNotPresent(index, apiKey.getDescription());
  }
}

package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.ApiKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("ConstantConditions")
public abstract class SyncApiKeysTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList("index1", "index2");

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean);
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
    client
        .initIndex("index1", AlgoliaObject.class)
        .addObject(new AlgoliaObject("1", 1));

    ApiKey apiKey =
        new ApiKey()
            .setDescription("toto" + System.currentTimeMillis())
            .setIndexes(Collections.singletonList("index1"));

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
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("1", 1));

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

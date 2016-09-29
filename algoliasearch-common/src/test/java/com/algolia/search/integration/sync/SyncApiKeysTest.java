package com.algolia.search.integration.sync;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.ApiKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class SyncApiKeysTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  private void waitForKeyPresent(Index<AlgoliaObject> index, String description) throws AlgoliaException, InterruptedException {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      List<ApiKey> apiKeys = index == null ? client.listKeys() : index.listKeys();
      boolean found = apiKeys.stream().map(ApiKey::getDescription).anyMatch(k -> k.equals(description));
      if (found) {
        return;
      }
    }

    //will fail
    assertThat(client.listKeys()).extracting("description").contains(description);
  }

  private void waitForKeyNotPresent(Index<AlgoliaObject> index, String description) throws AlgoliaException, InterruptedException {
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      List<ApiKey> apiKeys = index == null ? client.listKeys() : index.listKeys();
      boolean found = apiKeys.stream().map(ApiKey::getDescription).anyMatch(k -> k.equals(description));
      if (!found) {
        return;
      }
    }

    //will fail
    assertThat(client.listKeys()).extracting("description").doesNotContain(description);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeys() throws AlgoliaException, InterruptedException {
    //Fill index
    client.initIndex("index1", AlgoliaObject.class).addObject(new AlgoliaObject("1", 1)).waitForCompletion();

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis())
      .setIndexes(Collections.singletonList("index1"));

    String keyName = client.addKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(null, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    client.updateKey(keyName, apiKey);

    waitForKeyPresent(null, apiKey.getDescription());

    assertThat(client.getKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    client.deleteKey(keyName);

    waitForKeyNotPresent(null, apiKey.getDescription());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeysForIndex() throws AlgoliaException, InterruptedException {
    //Fill index
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("1", 1)).waitForCompletion();

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis());

    String keyName = index.addKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    waitForKeyPresent(index, apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    index.updateKey(keyName, apiKey);

    waitForKeyPresent(index, apiKey.getDescription());

    assertThat(index.getKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    index.deleteKey(keyName);

    waitForKeyNotPresent(index, apiKey.getDescription());
  }

}

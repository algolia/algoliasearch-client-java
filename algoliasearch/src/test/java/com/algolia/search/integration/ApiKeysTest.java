package com.algolia.search.integration;

import com.algolia.search.AlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.ApiKey;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiKeysTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @BeforeClass
  @AfterClass
  public static void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void manageKeys() throws AlgoliaException, InterruptedException {
    //Fill index
    client.initIndex("index1", AlgoliaObject.class).addObject(new AlgoliaObject("1", 1)).waitForCompletion();

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis())
      .setIndexes(Collections.singletonList("index1"));

    String keyName = client.addKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(client.listKeys()).extracting("description").contains(apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    client.updateKey(keyName, apiKey);

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(client.listKeys()).extracting("description").contains(apiKey.getDescription());

    assertThat(client.getKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    client.deleteKey(keyName);

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(client.listKeys()).extracting("description").doesNotContain(apiKey.getDescription());
  }

  @Test
  public void manageKeysForIndex() throws AlgoliaException, InterruptedException {
    //Fill index
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("1", 1)).waitForCompletion();

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis());

    String keyName = index.addKey(apiKey).getKey();
    assertThat(keyName).isNotNull();

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(index.listKeys()).extracting("description").contains(apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    index.updateKey(keyName, apiKey);

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(index.listKeys()).extracting("description").contains(apiKey.getDescription());

    assertThat(index.getKey(keyName).get().getDescription()).isEqualTo(apiKey.getDescription());

    index.deleteKey(keyName);

    Thread.sleep(5000); //wait for the key to propagate

    assertThat(index.listKeys()).extracting("description").doesNotContain(apiKey.getDescription());
  }

}

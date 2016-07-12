package com.algolia.search.integration.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
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

abstract public class AsyncApiKeysTest extends AsyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @Before
  @After
  public void cleanUp() throws Exception {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    waitForCompletion(client.batch(clean));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeys() throws Exception {
    //Fill index
    waitForCompletion(client.initIndex("index1", AlgoliaObject.class).addObject(new AlgoliaObject("1", 1)));

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis())
      .setIndexes(Collections.singletonList("index1"));

    String keyName = client.addKey(apiKey).get().getKey();
    assertThat(keyName).isNotNull();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(client.listKeys()).extracting("description").contains(apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    client.updateKey(keyName, apiKey).get();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(client.listKeys()).extracting("description").contains(apiKey.getDescription());

    assertThat(client.getKey(keyName).get().get().getDescription()).isEqualTo(apiKey.getDescription());

    client.deleteKey(keyName).get();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(client.listKeys()).extracting("description").doesNotContain(apiKey.getDescription());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void manageKeysForIndex() throws Exception {
    //Fill index
    AsyncIndex<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("1", 1)));

    ApiKey apiKey = new ApiKey()
      .setDescription("toto" + System.currentTimeMillis());

    String keyName = index.addKey(apiKey).get().getKey();
    assertThat(keyName).isNotNull();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(index.listKeys()).extracting("description").contains(apiKey.getDescription());

    apiKey = apiKey.setDescription("toto2" + System.currentTimeMillis());
    index.updateKey(keyName, apiKey).get();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(index.listKeys()).extracting("description").contains(apiKey.getDescription());

    assertThat(index.getKey(keyName).get().get().getDescription()).isEqualTo(apiKey.getDescription());

    index.deleteKey(keyName).get();

    Thread.sleep(5000); //wait for the key to propagate

    futureAssertThat(index.listKeys()).extracting("description").doesNotContain(apiKey.getDescription());
  }

}

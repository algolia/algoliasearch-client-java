package com.algolia.search.integration;

import com.algolia.search.AlgoliaIntegrationTest;
import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class IndicesTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2",
    "index3",
    "index4",
    "index5",
    "index6",
    "index7"
  );

  @Before
  @After
  public void cleanUP() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void getAllIndices() throws AlgoliaException {
    assertThat(client.listIndices()).isNotNull();

    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndices()).extracting("name").contains("index1");
  }

  @Test
  public void deleteIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndices()).extracting("name").contains("index2");

    index.delete().waitForCompletion();
    assertThat(client.listIndices()).extracting("name").doesNotContain("index2");
  }

  @Test
  public void moveIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index3", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndices()).extracting("name").contains("index3");

    index.moveTo("index4").waitForCompletion();
    assertThat(client.listIndices()).extracting("name").doesNotContain("index3").contains("index4");
  }

  @Test
  public void copyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index5", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndices()).extracting("name").contains("index5");

    index.copyTo("index6").waitForCompletion();
    assertThat(client.listIndices()).extracting("name").contains("index5", "index6");
  }

  @Test
  public void clearIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index7", AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    index.clear().waitForCompletion();

    SearchResult<AlgoliaObject> results = index.search(new Query(""));

    assertThat(results.getHits()).isEmpty();
  }

}

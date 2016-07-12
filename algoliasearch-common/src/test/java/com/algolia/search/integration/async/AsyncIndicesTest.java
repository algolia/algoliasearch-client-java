package com.algolia.search.integration.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
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

abstract public class AsyncIndicesTest extends AsyncAlgoliaIntegrationTest {

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
  public void cleanUp() throws Exception {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    waitForCompletion(client.batch(clean));
  }

  @Test
  public void getAllIndices() throws Exception {
    assertThat(client.listIndices()).isNotNull();

    AsyncIndex<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains("index1");
  }

  @Test
  public void deleteIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains("index2");

    waitForCompletion(index.delete());
    futureAssertThat(client.listIndices()).extracting("name").doesNotContain("index2");
  }

  @Test
  public void moveIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index3", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains("index3");

    waitForCompletion(index.moveTo("index4"));
    futureAssertThat(client.listIndices()).extracting("name").doesNotContain("index3").contains("index4");
  }

  @Test
  public void copyIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index5", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains("index5");

    waitForCompletion(index.copyTo("index6"));
    futureAssertThat(client.listIndices()).extracting("name").contains("index5", "index6");
  }

  @Test
  public void clearIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index7", AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    waitForCompletion(index.clear());

    SearchResult<AlgoliaObject> results = index.search(new Query("")).get();

    assertThat(results.getHits()).isEmpty();
  }

}

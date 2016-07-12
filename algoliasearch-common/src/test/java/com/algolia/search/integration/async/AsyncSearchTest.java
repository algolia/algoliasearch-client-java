package com.algolia.search.integration.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.IndexQuery;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.MultiQueriesResult;
import com.algolia.search.responses.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class AsyncSearchTest extends AsyncAlgoliaIntegrationTest {

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

  @Test
  public void search() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    waitForCompletion(index.addObjects(Arrays.asList(
      new AlgoliaObject("algolia1", 1),
      new AlgoliaObject("algolia2", 1),
      new AlgoliaObject("toto", 1)
    )));

    SearchResult<AlgoliaObject> search = index.search(new Query("algolia")).get();
    assertThat(search.getHits()).hasSize(2).extractingResultOf("getClass").containsOnly(AlgoliaObject.class);
  }

  @Test
  public void multiQuery() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);

    waitForCompletion(index.addObjects(Arrays.asList(
      new AlgoliaObject("algolia1", 1),
      new AlgoliaObject("algolia2", 1),
      new AlgoliaObject("toto", 1)
    )));

    MultiQueriesResult search = client.multipleQueries(Arrays.asList(
      new IndexQuery(index, new Query("al")),
      new IndexQuery(index, new Query("1"))
    )).get();

    assertThat(search.getResults()).hasSize(2);
  }

}

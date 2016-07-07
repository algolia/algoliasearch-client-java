package com.algolia.search.integration.sync;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
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

abstract public class SyncSearchTest extends SyncAlgoliaIntegrationTest {

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

  @Test
  public void search() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    index
      .addObjects(Arrays.asList(
        new AlgoliaObject("algolia1", 1),
        new AlgoliaObject("algolia2", 1),
        new AlgoliaObject("toto", 1)
      ))
      .waitForCompletion();

    SearchResult<AlgoliaObject> search = index.search(new Query("algolia"));
    assertThat(search.getHits()).hasSize(2).extractingResultOf("getClass").containsOnly(AlgoliaObject.class);
  }

  @Test
  public void multiQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);

    index
      .addObjects(Arrays.asList(
        new AlgoliaObject("algolia1", 1),
        new AlgoliaObject("algolia2", 1),
        new AlgoliaObject("toto", 1)
      ))
      .waitForCompletion();

    MultiQueriesResult search = client.multipleQueries(Arrays.asList(
      new IndexQuery(index, new Query("al")),
      new IndexQuery(index, new Query("1"))
    ));

    assertThat(search.getResults()).hasSize(2);
  }

}

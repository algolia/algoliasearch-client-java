package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectForFaceting;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.IndexQuery;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.MultiQueriesResult;
import com.algolia.search.responses.SearchFacetResult;
import com.algolia.search.responses.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncSearchTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames =
      Arrays.asList(
          "index1",
          "index2",
          // index3 is used for non existing index
          "index4");

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void search() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    index
        .addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1)))
        .waitForCompletion();

    SearchResult<AlgoliaObject> search = index.search(new Query("algolia"));
    assertThat(search.getHits())
        .hasSize(2)
        .extractingResultOf("getClass")
        .containsOnly(AlgoliaObject.class);
  }

  @Test
  public void multiQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);

    index
        .addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1)))
        .waitForCompletion();

    MultiQueriesResult search =
        client.multipleQueries(
            Arrays.asList(
                new IndexQuery(index, new Query("al")), new IndexQuery(index, new Query("1"))));

    assertThat(search.getResults()).hasSize(2);
  }

  @Test
  public void searchOnNonExistingIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index3", AlgoliaObject.class);
    assertThatExceptionOfType(AlgoliaIndexNotFoundException.class)
        .isThrownBy(() -> index.search(new Query((""))))
        .withMessage("index3 does not exist");
  }

  @Test
  public void searchInFacets() throws AlgoliaException {
    Index<AlgoliaObjectForFaceting> index =
        client.initIndex("index4", AlgoliaObjectForFaceting.class);
    index
        .setSettings(
            new IndexSettings()
                .setAttributesForFaceting(Collections.singletonList("searchable(series)")))
        .waitForCompletion();

    index
        .addObjects(
            Arrays.asList(
                new AlgoliaObjectForFaceting("snoopy", 12, "Peanuts"),
                new AlgoliaObjectForFaceting("woodstock", 12, "Peanuts"),
                new AlgoliaObjectForFaceting("Calvin", 12, "Calvin & Hobbes")))
        .waitForCompletion();

    SearchFacetResult result = index.searchForFacetValues("series", "Peanuts");
    assertThat(result.getFacetHits()).hasSize(1);
  }
}

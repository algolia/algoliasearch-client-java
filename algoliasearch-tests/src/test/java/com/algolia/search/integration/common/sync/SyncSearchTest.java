package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectForFaceting;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.objects.IndexQuery;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.MultiQueriesStrategy;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.FacetStats;
import com.algolia.search.responses.MultiQueriesResult;
import com.algolia.search.responses.SearchFacetResult;
import com.algolia.search.responses.SearchResult;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Map;
import org.junit.Test;

public abstract class SyncSearchTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void search() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1))));

    SearchResult<AlgoliaObject> search = index.search(new Query("algolia"));
    assertThat(search.getHits())
        .hasSize(2)
        .extractingResultOf("getClass")
        .containsOnly(AlgoliaObject.class);
  }

  @Test
  public void multiQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1))));

    MultiQueriesResult search =
        client.multipleQueries(
            Arrays.asList(
                new IndexQuery(index, new Query("al")), new IndexQuery(index, new Query("1"))));

    MultiQueriesResult searchWithStrategy =
        client.multipleQueries(
            Arrays.asList(
                new IndexQuery(index, new Query("al")), new IndexQuery(index, new Query("1"))),
            MultiQueriesStrategy.STOP_IF_ENOUGH_MATCHES);

    assertThat(search.getResults()).hasSize(2);
    assertThat(searchWithStrategy.getResults()).hasSize(2);
  }

  @Test
  public void searchOnNonExistingIndex() {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    assertThatExceptionOfType(AlgoliaIndexNotFoundException.class)
        .isThrownBy(() -> index.search(new Query((""))))
        .withMessage(index.getName() + " does not exist");
  }

  @Test
  public void searchAndFacets() throws AlgoliaException {
    Index<AlgoliaObjectForFaceting> index = createIndex(AlgoliaObjectForFaceting.class);
    waitForCompletion(
        index.setSettings(
            new IndexSettings()
                .setAttributesForFaceting(Arrays.asList("searchable(series)", "age"))));

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObjectForFaceting("snoopy", 10, "Peanuts"),
                new AlgoliaObjectForFaceting("woodstock", 20, "Peanuts"),
                new AlgoliaObjectForFaceting("Calvin", 30, "Calvin & Hobbes"))));

    SearchFacetResult result = index.searchForFacetValues("series", "Peanuts");
    assertThat(result.getFacetHits()).hasSize(1);

    SearchResult<AlgoliaObjectForFaceting> search = index.search(new Query("").setFacets("age"));
    Map<String, FacetStats> expected =
        ImmutableMap.of("age", new FacetStats().setMax(30f).setMin(10f).setSum(60f).setAvg(20f));
    assertThat(search.getFacets_stats()).isEqualToComparingFieldByFieldRecursively(expected);
  }
}

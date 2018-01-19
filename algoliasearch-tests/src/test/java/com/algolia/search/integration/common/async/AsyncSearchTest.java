package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectForFaceting;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.objects.IndexQuery;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.MultiQueriesResult;
import com.algolia.search.responses.SearchFacetResult;
import com.algolia.search.responses.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;

public abstract class AsyncSearchTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void search() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1))));

    SearchResult<AlgoliaObject> search = index.search(new Query("algolia")).get();
    assertThat(search.getHits())
        .hasSize(2)
        .extractingResultOf("getClass")
        .containsOnly(AlgoliaObject.class);
  }

  @Test
  public void multiQuery() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1))));

    MultiQueriesResult search =
        client
            .multipleQueries(
                Arrays.asList(
                    new IndexQuery(index, new Query("al")), new IndexQuery(index, new Query("1"))))
            .get();

    assertThat(search.getResults()).hasSize(2);
  }

  @Test
  public void searchOnNonExistingIndex() throws InterruptedException {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    CompletableFuture<SearchResult<AlgoliaObject>> search = index.search(new Query(("")));
    while (!search.isDone()) {
      Thread.sleep(1000);
    }
    assertThat(search)
        .hasFailedWithThrowableThat()
        .isExactlyInstanceOf(AlgoliaIndexNotFoundException.class)
        .hasMessage(index.getName() + " does not exist");
  }

  @Test
  public void searchInFacets() throws Exception {
    AsyncIndex<AlgoliaObjectForFaceting> index = createIndex(AlgoliaObjectForFaceting.class);
    waitForCompletion(
        index.setSettings(
            new IndexSettings()
                .setAttributesForFaceting(Collections.singletonList("searchable(series)"))));

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObjectForFaceting("snoopy", 12, "Peanuts"),
                new AlgoliaObjectForFaceting("woodstock", 12, "Peanuts"),
                new AlgoliaObjectForFaceting("Calvin", 12, "Calvin & Hobbes"))));

    SearchFacetResult result = index.searchForFacetValues("series", "Peanuts").get();
    assertThat(result.getFacetHits()).hasSize(1);
  }
}

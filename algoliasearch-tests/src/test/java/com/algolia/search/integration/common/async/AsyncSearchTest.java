package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.*;
import com.algolia.search.exceptions.AlgoliaIndexNotFoundException;
import com.algolia.search.objects.FacetFilters;
import com.algolia.search.objects.IndexQuery;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.MultiQueriesResult;
import com.algolia.search.responses.SearchFacetResult;
import com.algolia.search.responses.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
  public void multiQueryWithFacetFilters() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia", 1),
                new AlgoliaObject("algolia", 2),
                new AlgoliaObject("algolia", 3))));

    waitForCompletion(
        index.setSettings(
            new IndexSettings()
                .setAttributesForFaceting(Arrays.asList("searchable(name)", "searchable(age)"))));

    List<IndexQuery> queryFacetListOfString =
        Arrays.asList(
            new IndexQuery(
                index,
                new Query("").setFacetFilters(FacetFilters.ofList(Arrays.asList("name:algolia")))));

    List<IndexQuery> queryFacetListListOfString =
        Arrays.asList(
            new IndexQuery(
                index,
                new Query("")
                    .setFacetFilters(
                        FacetFilters.ofListOfList(
                            Arrays.asList(
                                Arrays.asList("name:algolia"), Arrays.asList("age:3"))))));

    MultiQueriesResult searchWithFacetListOfString =
        client.multipleQueries(queryFacetListOfString).get();
    MultiQueriesResult searchWithFacetListOfListOfString =
        client.multipleQueries(queryFacetListListOfString).get();

    assertThat(searchWithFacetListOfString.getResults().get(0).getParams()).contains("name");
    assertThat(searchWithFacetListOfListOfString.getResults().get(0).getParams()).contains("name");
    assertThat(searchWithFacetListOfListOfString.getResults().get(0).getParams()).contains("age");
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

  @Test
  public void geoSearch() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.addObjects(
            Arrays.asList(
                new AlgoliaObject("algolia1", 1),
                new AlgoliaObject("algolia2", 1),
                new AlgoliaObject("toto", 1))));

    SearchResult<AlgoliaObject> searchInsideBounding =
        index
            .search(
                new Query("algolia")
                    .setInsideBoundingBox(
                        Arrays.asList(
                            Arrays.asList(
                                46.650828100116044f,
                                7.123046875f,
                                45.17210966999772f,
                                1.009765625f))))
            .get();

    SearchResult<AlgoliaObject> searchInsindeBoundingList =
        index
            .search(
                new Query("algolia")
                    .setInsideBoundingBox(
                        Arrays.asList(
                            Arrays.asList(
                                46.650828100116044f,
                                7.123046875f,
                                45.17210966999772f,
                                1.009765625f),
                            Arrays.asList(
                                49.62625916704081f,
                                4.6181640625f,
                                47.715070300900194f,
                                0.482421875f))))
            .get();

    SearchResult<AlgoliaObject> searchInsidePolygon =
        index
            .search(
                new Query("algolia")
                    .setInsidePolygon(
                        Arrays.asList(
                            Arrays.asList(
                                46.650828100116044f,
                                7.123046875f,
                                45.17210966999772f,
                                1.009765625f,
                                49.62625916704081f,
                                4.6181640625f))))
            .get();

    SearchResult<AlgoliaObject> searchInsidePolygonList =
        index
            .search(
                new Query("algolia")
                    .setInsidePolygon(
                        Arrays.asList(
                            Arrays.asList(
                                46.650828100116044f,
                                7.123046875f,
                                45.17210966999772f,
                                1.009765625f,
                                49.62625916704081f,
                                4.6181640625f),
                            Arrays.asList(
                                49.62625916704081f,
                                4.6181640625f,
                                47.715070300900194f,
                                0.482421875f,
                                45.17210966999772f,
                                1.009765625f,
                                50.62626704081f,
                                4.6181640625f))))
            .get();
  }
}

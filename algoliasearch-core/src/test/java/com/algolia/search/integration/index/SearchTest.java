package com.algolia.search.integration.index;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.Employee;
import com.algolia.search.models.indexing.*;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public abstract class SearchTest {

  protected final SearchClient searchClient;

  protected SearchTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testSearch() throws ExecutionException, InterruptedException {
    SearchIndex<Employee> index =
        searchClient.initIndex(getTestIndexName("search"), Employee.class);
    List<Employee> employees =
        Arrays.asList(
            new Employee("Algolia", "Julien Lemoine", "julien-lemoine"),
            new Employee("Algolia", "Nicolas Dessaigne", "nicolas-dessaigne"),
            new Employee("Amazon", "Jeff Bezos"),
            new Employee("Apple", "Steve Jobs"),
            new Employee("Apple", "Steve Wozniak"),
            new Employee("Arista Networks", "Jayshree Ullal"),
            new Employee("Google", "Lary Page"),
            new Employee("Google", "Rob Pike"),
            new Employee("Google", "Sergueï Brin"),
            new Employee("Microsoft", "Bill Gates"),
            new Employee("SpaceX", "Elon Musk"),
            new Employee("Tesla", "Elon Musk"),
            new Employee("Yahoo", "Marissa Mayer"));

    CompletableFuture<BatchIndexingResponse> saveObjectsFuture =
        index.saveObjectsAsync(employees, true);
    IndexSettings settings =
        new IndexSettings()
            .setAttributesForFaceting(Collections.singletonList("searchable(company)"));

    saveObjectsFuture.get().waitTask();

    CompletableFuture<SetSettingsResponse> setSettingsFuture = index.setSettingsAsync(settings);
    setSettingsFuture.get().waitTask();

    CompletableFuture<SearchResult<Employee>> searchAlgoliaFuture =
        index.searchAsync(new Query("algolia"));

    CompletableFuture<SearchResult<Employee>> searchElonFuture =
        index.searchAsync(new Query("elon").setClickAnalytics(true));

    CompletableFuture<SearchResult<Employee>> searchElonFuture1 =
        index.searchAsync(
            new Query("elon")
                .setFacets(Collections.singletonList("*"))
                .setFacetFilters(
                    Collections.singletonList(Collections.singletonList("company:tesla"))));

    CompletableFuture<SearchResult<Employee>> searchElonFuture2 =
        index.searchAsync(
            new Query("elon")
                .setFacets(Collections.singletonList("*"))
                .setFilters("(company:tesla OR company:spacex)"));

    CompletableFuture<SearchForFacetResponse> searchFacetFuture =
        index.searchForFacetValuesAsync(
            new SearchForFacetRequest().setFacetName("company").setFacetQuery("a"));

    CompletableFuture.allOf(
        searchAlgoliaFuture,
        searchElonFuture,
        searchElonFuture1,
        searchElonFuture2,
        searchElonFuture2,
        searchFacetFuture);

    assertThat(searchAlgoliaFuture.get().getHits()).hasSize(2);
    assertThat(searchAlgoliaFuture.get().getObjectPosition("nicolas-dessaigne"))
        .isEqualTo(0);
    assertThat(searchAlgoliaFuture.get().getObjectPosition("julien-lemoine"))
        .isEqualTo(1);
    assertThat(searchAlgoliaFuture.get().getObjectPosition("unknown"))
        .isEqualTo(-1);
    assertTrue(searchAlgoliaFuture.get().getExhaustiveNbHits());
    assertThat(searchElonFuture.get().getQueryID()).isNotNull();
    assertThat(searchElonFuture1.get().getHits()).hasSize(1);
    assertThat(searchElonFuture2.get().getHits()).hasSize(2);
    assertThat(searchFacetFuture.get().getFacetHits())
        .extracting(FacetHit::getValue)
        .contains("Algolia");
    assertThat(searchFacetFuture.get().getFacetHits())
        .extracting(FacetHit::getValue)
        .contains("Amazon");
    assertThat(searchFacetFuture.get().getFacetHits())
        .extracting(FacetHit::getValue)
        .contains("Apple");
    assertThat(searchFacetFuture.get().getFacetHits())
        .extracting(FacetHit::getValue)
        .contains("Arista Networks");

    assertThat(index.findObject(x -> false, new Query(""))).isNull();

    HitsWithPosition<Employee> alwaysTrue = index.findObject(x -> true, new Query(""));
    assertThat(alwaysTrue.getPage()).isEqualTo(0);
    assertThat(alwaysTrue.getPosition()).isEqualTo(0);

    assertThat(index.findObject(x -> x.getCompany().equals("Apple"), new Query("Algolia")))
        .isNull();

    assertThat(
            index.findObject(
                x -> x.getCompany().equals("Apple"), new Query("Algolia").setHitsPerPage(5), false))
        .isNull();

    HitsWithPosition<Employee> foundObject =
        index.findObject(x -> x.getCompany().equals("Apple"), new Query("").setHitsPerPage(5));
    assertThat(foundObject.getPosition()).isEqualTo(0);
    assertThat(foundObject.getPage()).isEqualTo(2);
  }
}

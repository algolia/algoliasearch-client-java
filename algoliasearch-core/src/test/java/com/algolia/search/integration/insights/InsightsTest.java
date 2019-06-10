package com.algolia.search.integration.insights;

import com.algolia.search.InsightsClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.UserInsightsClient;
import com.algolia.search.integration.TestHelpers;
import com.algolia.search.integration.models.AlgoliaObject;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public abstract class InsightsTest {
  protected final SearchClient searchClient;
  protected final InsightsClient insightsClient;

  protected InsightsTest(SearchClient searchClient, InsightsClient insightsClient) {
    this.searchClient = searchClient;
    this.insightsClient = insightsClient;
  }

  @Test
  void testInsights() throws ExecutionException, InterruptedException {
    String indexName = TestHelpers.getTestIndexName("insights");
    SearchIndex<AlgoliaObject> index = searchClient.initIndex(indexName, AlgoliaObject.class);

    UserInsightsClient insights = insightsClient.user("test");

    // Click
    insights
        .clickedFiltersAsync("clickedFilters", indexName, Collections.singletonList("brand:apple"))
        .get();
    insights.clickedObjectIDsAsync("clickedObjectEvent", indexName, Arrays.asList("1", "2")).get();

    // Conversions
    insights
        .convertedObjectIDsAsync("convertedObjectIDs", indexName, Arrays.asList("1", "2"))
        .get();
    insights
        .convertedFiltersAsync(
            "converterdFilters", indexName, Collections.singletonList("brand:apple"))
        .get();

    // View
    insights
        .viewedFiltersAsync(
            "viewedFilters", indexName, Arrays.asList("brand:apple", "brand:google"))
        .get();
    insights.viewedObjectIDsAsync("viewedObjectIDs", indexName, Arrays.asList("1", "2")).get();

    index.saveObjectAsync(new AlgoliaObject().setObjectID("one")).get().waitTask();

    Query query = new Query().setEnablePersonalization(true).setClickAnalytics(true);

    SearchResult<AlgoliaObject> search1 = index.searchAsync(query).get();
    insights
        .clickedObjectIDsAfterSearchAsync(
            "clickedObjectIDsAfterSearch",
            indexName,
            Arrays.asList("1", "2"),
            Arrays.asList(17L, 19L),
            search1.getQueryID())
        .get();

    SearchResult<AlgoliaObject> search2 = index.searchAsync(query).get();
    insights
        .convertedObjectIDsAfterSearchAsync(
            "convertedObjectIDsAfterSearch",
            indexName,
            Arrays.asList("1", "2"),
            search2.getQueryID())
        .get();
  }
}

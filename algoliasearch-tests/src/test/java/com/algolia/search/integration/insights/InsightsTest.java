package com.algolia.search.integration.insights;

import com.algolia.search.clients.InsightsClient;
import com.algolia.search.clients.SearchIndex;
import com.algolia.search.clients.UserInsightsClient;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.integration.AlgoliaObject;
import com.algolia.search.models.search.Query;
import com.algolia.search.models.search.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class InsightsTest extends AlgoliaBaseIntegrationTest {
  private SearchIndex<AlgoliaObject> index;
  private String indexName;

  InsightsTest() {
    indexName = getTestIndexName("insights");
    index = searchClient.initIndex(indexName, AlgoliaObject.class);
  }

  @Test
  void testInsights() throws ExecutionException, InterruptedException {
    InsightsClient insightsClient = new InsightsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
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

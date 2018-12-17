package com.algolia.search.integration.common.async;

import com.algolia.search.*;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public abstract class AsyncInsightsClientTest extends AsyncAlgoliaIntegrationTest {
  @Test
  public void TestInsightsMethods() throws Exception {
    AsyncUserInsightsClient insights =
        new AsyncInsightsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1, client)
            .user("userToken");

    // click
    insights
        .clickedFilters("clickedFilters", "index", Collections.singletonList("brand:apple"))
        .get();
    insights
        .clickedObjectIDs("clickedFilters", "index", Arrays.asList("objectID1", "objectID2"))
        .get();

    // conversion
    insights
        .convertedObjectIDs("convertedObjectIds", "index", Arrays.asList("objectDI1", "objectID2"))
        .get();
    insights
        .convertedFilters("convertedFilters", "index", Collections.singletonList("brand:apple"))
        .get();

    // view
    insights
        .viewedFilters("viewedFilters", "index", Arrays.asList("brand:apple", "brand:google"))
        .get();
    insights.viewedObjectIDs("viewedObjectIDs", "index", Arrays.asList("1", "2")).get();

    // We need to create a queryID with a search query
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));
    SearchResult<AlgoliaObject> result = index.search(new Query().setClickAnalytics(true)).get();
    insights.clickedObjectIDsAfterSearch(
        "eventName",
        "index",
        Arrays.asList("objectDI1", "objectID2"),
        new ArrayList<>(Arrays.asList(17l, 19l)),
        result.getQueryID());

    SearchResult<AlgoliaObject> result2 =
        index.search(new Query("al").setClickAnalytics(true)).get();
    insights.clickedObjectIDsAfterSearch(
        "eventName",
        "index",
        Arrays.asList("objectDI1", "objectID2"),
        new ArrayList<>(Arrays.asList(17l, 19l)),
        result.getQueryID());

    insights.convertedObjectIDsAfterSearch(
        "eventName", "index", Arrays.asList("objectDI1", "objectID2"), result2.getQueryID());
  }
}

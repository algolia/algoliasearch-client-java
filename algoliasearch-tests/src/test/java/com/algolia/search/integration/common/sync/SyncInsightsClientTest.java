package com.algolia.search.integration.common.sync;

import com.algolia.search.*;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public abstract class SyncInsightsClientTest extends SyncAlgoliaIntegrationTest {
  @Test
  public void TestInsightsMethods() throws AlgoliaException {
    SyncUserInsightsClient insights =
        new SyncInsightsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1, client)
            .user("userToken");

    // click
    insights.clickedFilters("clickedFilters", "index", Collections.singletonList("brand:apple"));
    insights.clickedObjectIDs("clickedFilters", "index", Arrays.asList("objectDI1", "objectID2"));

    // conversion
    insights.convertedObjectIDs(
        "convertedObjectIds", "index", Arrays.asList("objectID1", "objectID2"));
    insights.convertedFilters(
        "convertedFilters", "index", Collections.singletonList("brand:apple"));

    // view
    insights.viewedFilters("viewedFilters", "index", Arrays.asList("brand:apple", "brand:google"));
    insights.viewedObjectIDs("viewedObjectIDs", "index", Arrays.asList("objectID11", "objectID2"));

    // We need to create a queryID with a search query
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));
    SearchResult<AlgoliaObject> result =
        index.search(new Query().setClickAnalytics(true).setEnablePersonalization(true));
    insights.clickedObjectIDsAfterSearch(
        "eventName",
        "index",
        Arrays.asList("objectDI1", "objectID2"),
        new ArrayList<>(Arrays.asList(17l, 19l)),
        result.getQueryID());

    SearchResult<AlgoliaObject> result2 =
        index.search(new Query("al").setClickAnalytics(true).setEnablePersonalization(true));
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

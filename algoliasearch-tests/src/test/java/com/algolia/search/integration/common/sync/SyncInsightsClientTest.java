package com.algolia.search.integration.common.sync;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.SyncInsightsClient;
import com.algolia.search.SyncUserInsightsClient;
import com.algolia.search.exceptions.AlgoliaException;
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
    insights.clickedObjectIDs("clickedFilters", "index", Collections.singletonList("brand:apple"));

    // conversion
    insights.convertedObjectIDs("convertedObjectIds", "indexName", Arrays.asList("1", "2"));

    // view
    insights.viewedFilters(
        "viewedFilters", "indexName", Arrays.asList("brand:apple", "brand:google"));
    insights.viewedObjectIDs("viewedObjectIDs", "indexName", Arrays.asList("1", "2"));
  }
}

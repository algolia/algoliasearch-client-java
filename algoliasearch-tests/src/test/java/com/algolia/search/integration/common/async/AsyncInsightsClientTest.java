package com.algolia.search.integration.common.async;

import com.algolia.search.*;
import com.algolia.search.exceptions.AlgoliaException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public abstract class AsyncInsightsClientTest extends AsyncAlgoliaIntegrationTest {
  @Test
  public void TestInsightsMethods()
      throws AlgoliaException, ExecutionException, InterruptedException {
    AsyncUserInsightsClient insights =
        new AsyncInsightsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1, client)
            .user("userToken");

    // click
    insights
        .clickedFilters("clickedFilters", "index", Collections.singletonList("brand:apple"))
        .get();
    insights
        .clickedObjectIDs("clickedFilters", "index", Collections.singletonList("brand:apple"))
        .get();

    // conversion
    insights.convertedObjectIDs("convertedObjectIds", "indexName", Arrays.asList("1", "2")).get();
    insights.convertedFilters("convertedFilters","index", Collections.singletonList("brand:apple")).get();

    // view
    insights
        .viewedFilters("viewedFilters", "indexName", Arrays.asList("brand:apple", "brand:google"))
        .get();
    insights.viewedObjectIDs("viewedObjectIDs", "indexName", Arrays.asList("1", "2")).get();
  }
}

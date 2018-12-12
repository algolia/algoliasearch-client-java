package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.inputs.personalization.EventScoring;
import com.algolia.search.inputs.personalization.FacetScoring;
import com.algolia.search.inputs.personalization.PersonalizationStrategyRequest;
import com.algolia.search.responses.PersonalizationStrategyResult;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public abstract class AsyncPersonalizationTest extends AsyncAlgoliaIntegrationTest {
  @Test
  public void TestPersonalization() throws ExecutionException, InterruptedException {
    HashMap<String, EventScoring> eventsScoring = new HashMap<>();
    eventsScoring.put("Add to cart", new EventScoring().setScore(50).setType("conversion"));
    eventsScoring.put("Purchase", new EventScoring().setScore(100).setType("conversion"));

    HashMap<String, FacetScoring> facetsScoring = new HashMap<>();
    facetsScoring.put("brand", new FacetScoring().setScore(100));
    facetsScoring.put("categories", new FacetScoring().setScore(10));

    PersonalizationStrategyRequest request = new PersonalizationStrategyRequest();

    request.setEventsScoring(eventsScoring);
    request.setFacetsScoring(facetsScoring);

    client.setStrategy(request).get();

    PersonalizationStrategyResult result = client.getStrategy().get();

    assertThat(result.getEventsScoring()).isEqualToComparingFieldByFieldRecursively(eventsScoring);
    assertThat(result.getFacetsScoring()).isEqualToComparingFieldByFieldRecursively(facetsScoring);
  }
}

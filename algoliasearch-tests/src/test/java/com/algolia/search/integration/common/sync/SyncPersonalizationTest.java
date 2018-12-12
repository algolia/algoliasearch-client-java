package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.personalization.EventScoring;
import com.algolia.search.inputs.personalization.FacetScoring;
import com.algolia.search.inputs.personalization.PersonalizationStrategyRequest;
import com.algolia.search.responses.PersonalizationStrategyResult;
import java.util.HashMap;
import org.junit.Test;

public abstract class SyncPersonalizationTest extends SyncAlgoliaIntegrationTest {
  @Test
  public void TestPersonalization() throws AlgoliaException {
    HashMap<String, EventScoring> eventsScoring = new HashMap<>();
    eventsScoring.put("Add to cart", new EventScoring().setScore(50).setType("conversion"));
    eventsScoring.put("Purchase", new EventScoring().setScore(100).setType("conversion"));

    HashMap<String, FacetScoring> facetsScoring = new HashMap<>();
    facetsScoring.put("brand", new FacetScoring().setScore(100));
    facetsScoring.put("categories", new FacetScoring().setScore(10));

    PersonalizationStrategyRequest request = new PersonalizationStrategyRequest();

    request.setEventsScoring(eventsScoring);
    request.setFacetsScoring(facetsScoring);

    client.setStrategy(request);

    PersonalizationStrategyResult result = client.getStrategy();

    assertThat(result.getEventsScoring()).isEqualToComparingFieldByFieldRecursively(eventsScoring);
    assertThat(result.getFacetsScoring()).isEqualToComparingFieldByFieldRecursively(facetsScoring);
  }
}

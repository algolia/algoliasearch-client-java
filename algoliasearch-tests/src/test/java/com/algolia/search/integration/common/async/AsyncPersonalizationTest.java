package com.algolia.search.integration.common.async;

import static com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.inputs.personalization.EventScoring;
import com.algolia.search.inputs.personalization.FacetScoring;
import com.algolia.search.inputs.personalization.PersonalizationStrategyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import org.junit.Test;

public abstract class AsyncPersonalizationTest extends AsyncAlgoliaIntegrationTest {
  @Test
  public void TestPersonalization() throws JsonProcessingException {
    HashMap<String, EventScoring> eventsScoring = new HashMap<>();
    eventsScoring.put("Add to cart", new EventScoring().setScore(50).setType("conversion"));
    eventsScoring.put("Purchase", new EventScoring().setScore(100).setType("conversion"));

    HashMap<String, FacetScoring> facetsScoring = new HashMap<>();
    facetsScoring.put("brand", new FacetScoring().setScore(100));
    facetsScoring.put("categories", new FacetScoring().setScore(10));

    PersonalizationStrategyRequest request = new PersonalizationStrategyRequest().setEventsScoring(eventsScoring).setFacetsScoring(facetsScoring);
    String strategy = DEFAULT_OBJECT_MAPPER.writeValueAsString(request);

    // Here we test the payload, as this settings are at app level all tests could overlap
    assertThat(strategy)
        .isEqualTo(
            "{\"eventsScoring\":{\"Purchase\":{\"score\":100,\"type\":\"conversion\"},\"Add to cart\":{\"score\":50,\"type\":\"conversion\"}},\"facetsScoring\":{\"categories\":{\"score\":10},\"brand\":{\"score\":100}}}");
  }
}

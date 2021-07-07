package com.algolia.search.integration.personalization;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.PersonalizationClient;
import com.algolia.search.RecommendationClient;
import com.algolia.search.models.recommendation.EventsScoring;
import com.algolia.search.models.recommendation.FacetsScoring;
import com.algolia.search.models.recommendation.GetStrategyResponse;
import com.algolia.search.models.recommendation.SetStrategyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class PersonalizationTest {

  private final PersonalizationClient personalizationClient;

  protected PersonalizationTest(PersonalizationClient personalizationClient) {
    this.personalizationClient = personalizationClient;
  }

  @Test
  void testPersonalizationClient() {
    SetStrategyRequest request =
        new SetStrategyRequest()
            .setEventsScoring(
                Arrays.asList(
                    new EventsScoring("Add to cart", "conversion", 50),
                    new EventsScoring("Purchase", "conversion", 100)))
            .setFacetsScoring(
                Arrays.asList(new FacetsScoring("brand", 100), new FacetsScoring("categories", 10)))
            .setPersonalizationImpact(0);
    Assertions.assertDoesNotThrow(() -> personalizationClient.setPersonalizationStrategy(request));

    GetStrategyResponse response = personalizationClient.getPersonalizationStrategy();

    assertThat(response.getEventsScoring())
        .usingRecursiveComparison()
        .isEqualTo(request.getEventsScoring());

    assertThat(response.getFacetsScoring())
        .usingRecursiveComparison()
        .isEqualTo(request.getFacetsScoring());

    assertThat(response.getPersonalizationImpact()).isEqualTo(request.getPersonalizationImpact());
  }

  /*
   * The payload are tested locally because the strategy is set at application level.
   * Multiple tests running in // on the same application could lead to a flaky test suite.
   */
  @Test
  void testSetStrategyPayload() throws JsonProcessingException {

    // test valid JSON strategy
    List<EventsScoring> events =
        Arrays.asList(
            new EventsScoring("buy", "conversion", 10),
            new EventsScoring("add to cart", "conversion", 20));

    List<FacetsScoring> facets =
        Arrays.asList(new FacetsScoring("brand", 10), new FacetsScoring("category", 20));

    SetStrategyRequest validStrategy = new SetStrategyRequest(events, facets, 75);

    assertThat(Defaults.getObjectMapper().writeValueAsString(validStrategy))
        .isEqualTo(
            "{\"eventsScoring\":[{\"eventName\":\"buy\",\"eventType\":\"conversion\",\"score\":10},{\"eventName\":\"add to cart\",\"eventType\":\"conversion\",\"score\":20}],\"facetsScoring\":[{\"facetName\":\"brand\",\"score\":10},{\"facetName\":\"category\",\"score\":20}],\"personalizationImpact\":75}");
  }
}

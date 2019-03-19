package com.algolia.search.models.personalization;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrategyRequest {
  public Map<String, EventScoring> getEventsScoring() {
    return eventsScoring;
  }

  public StrategyRequest setEventsScoring(Map<String, EventScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public Map<String, FacetScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public StrategyRequest setFacetsScoring(Map<String, FacetScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  private Map<String, EventScoring> eventsScoring;
  private Map<String, FacetScoring> facetsScoring;
}

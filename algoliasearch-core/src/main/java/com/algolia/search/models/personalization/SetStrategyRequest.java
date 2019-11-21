package com.algolia.search.models.personalization;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Deprecated
public class SetStrategyRequest implements Serializable {

  public SetStrategyRequest() {}

  public SetStrategyRequest(
      Map<String, EventScoring> eventsScoring, Map<String, FacetScoring> facetsScoring) {
    this.eventsScoring = eventsScoring;
    this.facetsScoring = facetsScoring;
  }

  public Map<String, EventScoring> getEventsScoring() {
    return eventsScoring;
  }

  public SetStrategyRequest setEventsScoring(Map<String, EventScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public Map<String, FacetScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public SetStrategyRequest setFacetsScoring(Map<String, FacetScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  private Map<String, EventScoring> eventsScoring;
  private Map<String, FacetScoring> facetsScoring;
}

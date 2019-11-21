package com.algolia.search.models.recommendation;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class GetStrategyResponse implements Serializable {

  public GetStrategyResponse() {}

  public List<EventsScoring> getEventsScoring() {
    return eventsScoring;
  }

  public GetStrategyResponse setEventsScoring(List<EventsScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public List<FacetsScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public GetStrategyResponse setFacetsScoring(List<FacetsScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  public Integer getPersonalizationImpact() {
    return personalizationImpact;
  }

  public GetStrategyResponse setPersonalizationImpact(Integer personalizationImpact) {
    this.personalizationImpact = personalizationImpact;
    return this;
  }

  private List<EventsScoring> eventsScoring;
  private List<FacetsScoring> facetsScoring;
  private Integer personalizationImpact;
}

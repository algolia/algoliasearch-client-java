package com.algolia.search.models.recommendation;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class SetStrategyRequest implements Serializable {

  public SetStrategyRequest() {}

  public SetStrategyRequest(
      List<EventsScoring> eventsScoring,
      List<FacetsScoring> facetsScoring,
      Integer personalizationImpact) {
    this.eventsScoring = eventsScoring;
    this.facetsScoring = facetsScoring;
    this.personalizationImpact = personalizationImpact;
  }

  public List<EventsScoring> getEventsScoring() {
    return eventsScoring;
  }

  public SetStrategyRequest setEventsScoring(List<EventsScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public List<FacetsScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public SetStrategyRequest setFacetsScoring(List<FacetsScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  public int getPersonalizationImpact() {
    return personalizationImpact;
  }

  public SetStrategyRequest setPersonalizationImpact(int personalizationImpact) {
    this.personalizationImpact = personalizationImpact;
    return this;
  }

  private List<EventsScoring> eventsScoring;
  private List<FacetsScoring> facetsScoring;
  private int personalizationImpact;
}

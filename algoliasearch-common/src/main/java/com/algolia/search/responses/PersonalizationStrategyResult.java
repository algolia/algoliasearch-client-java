package com.algolia.search.responses;

import com.algolia.search.inputs.personalization.EventScoring;
import com.algolia.search.inputs.personalization.FacetScoring;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalizationStrategyResult {
  public Map<String, EventScoring> getEventsScoring() {
    return eventsScoring;
  }

  public PersonalizationStrategyResult setEventsScoring(Map<String, EventScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public Map<String, FacetScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public PersonalizationStrategyResult setFacetsScoring(Map<String, FacetScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  public long getTaskId() {
    return taskId;
  }

  public PersonalizationStrategyResult setTaskId(long taskId) {
    this.taskId = taskId;
    return this;
  }

  private Map<String, EventScoring> eventsScoring;
  private Map<String, FacetScoring> facetsScoring;
  private long taskId;
}

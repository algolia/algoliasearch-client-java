package com.algolia.search.responses.personalization;

import com.algolia.search.inputs.personalization.EventScoring;
import com.algolia.search.inputs.personalization.FacetScoring;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetStrategyResult {
  public Map<String, EventScoring> getEventsScoring() {
    return eventsScoring;
  }

  public GetStrategyResult setEventsScoring(Map<String, EventScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public Map<String, FacetScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public GetStrategyResult setFacetsScoring(Map<String, FacetScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  public long getTaskId() {
    return taskId;
  }

  public GetStrategyResult setTaskId(long taskId) {
    this.taskId = taskId;
    return this;
  }

  private Map<String, EventScoring> eventsScoring;
  private Map<String, FacetScoring> facetsScoring;
  private long taskId;
}

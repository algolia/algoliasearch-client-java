package com.algolia.search.models.personalization;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Deprecated
public class GetStrategyResponse implements Serializable {

  public GetStrategyResponse() {}

  public Map<String, EventScoring> getEventsScoring() {
    return eventsScoring;
  }

  public GetStrategyResponse setEventsScoring(Map<String, EventScoring> eventsScoring) {
    this.eventsScoring = eventsScoring;
    return this;
  }

  public Map<String, FacetScoring> getFacetsScoring() {
    return facetsScoring;
  }

  public GetStrategyResponse setFacetsScoring(Map<String, FacetScoring> facetsScoring) {
    this.facetsScoring = facetsScoring;
    return this;
  }

  public Long getTaskId() {
    return taskId;
  }

  public GetStrategyResponse setTaskId(Long taskId) {
    this.taskId = taskId;
    return this;
  }

  private Map<String, EventScoring> eventsScoring;
  private Map<String, FacetScoring> facetsScoring;
  private Long taskId;
}

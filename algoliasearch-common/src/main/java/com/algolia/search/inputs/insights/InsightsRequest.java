package com.algolia.search.inputs.insights;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsightsRequest implements Serializable {
  public List<InsightsEvent> getEvents() {
    return requests;
  }

  public InsightsRequest setEvents(List<InsightsEvent> requests) {
    this.requests = requests;
    return this;
  }

  private List<InsightsEvent> requests;
}

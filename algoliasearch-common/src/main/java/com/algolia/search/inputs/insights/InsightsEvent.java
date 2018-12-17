package com.algolia.search.inputs.insights;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsightsEvent implements Serializable {

  private String eventType;
  private String eventName;
  private String index;
  private String userToken;
  private Long timestamp;
  private String queryID;
  private List<String> objectIDs;
  private List<String> filters;
  private List<Long> positions;

  public String getEventType() {
    return eventType;
  }

  public InsightsEvent setEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public String getEventName() {
    return eventName;
  }

  public InsightsEvent setEventName(String eventName) {
    this.eventName = eventName;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public InsightsEvent setIndex(String index) {
    this.index = index;
    return this;
  }

  public String getUserToken() {
    return userToken;
  }

  public InsightsEvent setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public InsightsEvent setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public String getQueryID() {
    return queryID;
  }

  public InsightsEvent setQueryID(String queryID) {
    this.queryID = queryID;
    return this;
  }

  public List<String> getObjectIDs() {
    return objectIDs;
  }

  public InsightsEvent setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  public List<String> getFilters() {
    return filters;
  }

  public InsightsEvent setFilters(List<String> filters) {
    this.filters = filters;
    return this;
  }

  public List<Long> getPositions() {
    return positions;
  }

  public InsightsEvent setPositions(List<Long> positions) {
    this.positions = positions;
    return this;
  }
}

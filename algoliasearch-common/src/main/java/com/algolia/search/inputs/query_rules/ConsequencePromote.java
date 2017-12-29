package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequencePromote implements Serializable {
  private String objectID;
  private Integer position;

  public ConsequencePromote() {}

  public String getObjectID() {
    return objectID;
  }

  public ConsequencePromote setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public Integer getPosition() {
    return position;
  }

  public ConsequencePromote setPosition(Integer position) {
    this.position = position;
    return this;
  }
}

package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ConsequencePromote implements Serializable {

  private Integer position;

  public Integer getPosition() {
    return position;
  }

  public ConsequencePromote setPosition(Integer position) {
    this.position = position;
    return this;
  }

  public static class Single extends ConsequencePromote {

    private String objectID;

    public String getObjectID() {
      return objectID;
    }

    public Single setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }
  }

  public static class Multiple extends ConsequencePromote {

    private List<String> objectIDs;

    public List<String> getObjectIDs() {
      return objectIDs;
    }

    public Multiple setObjectID(List<String> objectIDs) {
      this.objectIDs = objectIDs;
      return this;
    }
  }
}

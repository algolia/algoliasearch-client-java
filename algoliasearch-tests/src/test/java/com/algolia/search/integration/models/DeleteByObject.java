package com.algolia.search.integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DeleteByObject extends AlgoliaObject {


  public DeleteByObject(){

  }

  public DeleteByObject(String objectID, List<String> tags) {
    this.setObjectID(objectID);
    this.tags = tags;
  }

  public List<String> getTags() {
    return tags;
  }

  public DeleteByObject setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  @JsonProperty("_tags")
  private List<String> tags;
}

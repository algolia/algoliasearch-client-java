package com.algolia.search.models;

import com.algolia.search.serializer.ConsequenceQueryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ConsequenceQueryDeserializer.class)
public class ConsequenceQuery implements Serializable {

  private List<Edit> edits;

  public List<Edit> getEdits() {
    return edits;
  }

  public ConsequenceQuery setEdits(List<Edit> edits) {
    this.edits = edits;
    return this;
  }
}

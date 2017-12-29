package com.algolia.search.inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Requests implements Serializable {

  private final List<Request> requests;

  public Requests(List<Request> requests) {
    this.requests = requests;
  }

  @SuppressWarnings("unused")
  public List<Request> getRequests() {
    return requests;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Request {

    private String indexName;
    private String objectID;
    private String attributesToRetrieve;

    @SuppressWarnings("unused")
    public String getIndexName() {
      return indexName;
    }

    @SuppressWarnings("unused")
    public Request setIndexName(String indexName) {
      this.indexName = indexName;
      return this;
    }

    @SuppressWarnings("unused")
    public String getObjectID() {
      return objectID;
    }

    @SuppressWarnings("unused")
    public Request setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }

    @SuppressWarnings("unused")
    public Request setAttributesToRetrieve(String attributesToRetrieve) {
      this.attributesToRetrieve = attributesToRetrieve;
      return this;
    }

    @SuppressWarnings("unused")
    public String getAttributesToRetrieve() {
      return attributesToRetrieve;
    }
  }
}

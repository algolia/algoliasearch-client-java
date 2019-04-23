package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleGetObject implements Serializable {

  public MultipleGetObject(
      @Nonnull String indexName, @Nonnull String objectID, List<String> attributesToRetrieve) {
    this.indexName = indexName;
    this.objectID = objectID;
    this.attributesToRetrieve = attributesToRetrieve;
  }

  public MultipleGetObject(@Nonnull String indexName, @Nonnull String objectID) {
    this.indexName = indexName;
    this.objectID = objectID;
  }

  public MultipleGetObject setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  public MultipleGetObject setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public MultipleGetObject setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return this;
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public String getIndexName() {
    return indexName;
  }

  public String getObjectID() {
    return objectID;
  }

  @Override
  public String toString() {
    return "MultipleGetObject{"
        + "indexName='"
        + indexName
        + '\''
        + ", objectID='"
        + objectID
        + '\''
        + ", attributesToRetrieve="
        + attributesToRetrieve
        + '}';
  }

  private String indexName;
  private String objectID;
  private List<String> attributesToRetrieve;
}

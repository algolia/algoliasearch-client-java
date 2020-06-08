package com.algolia.search.models.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InnerQuery implements Serializable {

  private String indexName;

  private String queryID;

  private Integer offset;

  private String userToken;

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public String getQueryID() {
    return queryID;
  }

  public void setQueryID(String queryID) {
    this.queryID = queryID;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  @Override
  public String toString() {
    return "InnerQuery{"
        + "indexName='"
        + indexName
        + '\''
        + ", QueryID='"
        + queryID
        + '\''
        + ", offset="
        + offset
        + ", userToken='"
        + userToken
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InnerQuery that = (InnerQuery) o;
    return Objects.equals(queryID, that.queryID);
  }

  @Override
  public int hashCode() {
    return queryID != null ? queryID.hashCode() : 0;
  }
}

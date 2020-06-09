package com.algolia.search.models.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InnerQuery implements Serializable {

  @JsonCreator
  public InnerQuery(@JsonProperty(value = "index_name") @Nonnull String indexName) {
    this.indexName = indexName;
  }

  private String indexName;

  @JsonProperty("query_id")
  private String queryID;

  private Integer offset;

  @JsonProperty("user_token")
  private String userToken;

  public String getIndexName() {
    return indexName;
  }

  public InnerQuery setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  public String getQueryID() {
    return queryID;
  }

  public InnerQuery setQueryID(String queryID) {
    this.queryID = queryID;
    return this;
  }

  public Integer getOffset() {
    return offset;
  }

  public InnerQuery setOffset(Integer offset) {
    this.offset = offset;
    return this;
  }

  public String getUserToken() {
    return userToken;
  }

  public InnerQuery setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
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

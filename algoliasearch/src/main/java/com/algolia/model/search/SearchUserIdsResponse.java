// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** userIDs data. */
public class SearchUserIdsResponse {

  @JsonProperty("hits")
  private List<UserHit> hits = new ArrayList<>();

  @JsonProperty("nbHits")
  private Integer nbHits;

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("hitsPerPage")
  private Integer hitsPerPage;

  @JsonProperty("updatedAt")
  private String updatedAt;

  public SearchUserIdsResponse setHits(List<UserHit> hits) {
    this.hits = hits;
    return this;
  }

  public SearchUserIdsResponse addHits(UserHit hitsItem) {
    this.hits.add(hitsItem);
    return this;
  }

  /** User objects that match the query. */
  @javax.annotation.Nonnull
  public List<UserHit> getHits() {
    return hits;
  }

  public SearchUserIdsResponse setNbHits(Integer nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  /** Number of hits the search query matched. */
  @javax.annotation.Nonnull
  public Integer getNbHits() {
    return nbHits;
  }

  public SearchUserIdsResponse setPage(Integer page) {
    this.page = page;
    return this;
  }

  /** Page to retrieve (the first page is `0`, not `1`). */
  @javax.annotation.Nonnull
  public Integer getPage() {
    return page;
  }

  public SearchUserIdsResponse setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  /** Maximum number of hits per page. minimum: 1 maximum: 1000 */
  @javax.annotation.Nonnull
  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchUserIdsResponse setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /** Timestamp of the last update in [ISO 8601](https://wikipedia.org/wiki/ISO_8601) format. */
  @javax.annotation.Nonnull
  public String getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchUserIdsResponse searchUserIdsResponse = (SearchUserIdsResponse) o;
    return (
      Objects.equals(this.hits, searchUserIdsResponse.hits) &&
      Objects.equals(this.nbHits, searchUserIdsResponse.nbHits) &&
      Objects.equals(this.page, searchUserIdsResponse.page) &&
      Objects.equals(this.hitsPerPage, searchUserIdsResponse.hitsPerPage) &&
      Objects.equals(this.updatedAt, searchUserIdsResponse.updatedAt)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(hits, nbHits, page, hitsPerPage, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchUserIdsResponse {\n");
    sb.append("    hits: ").append(toIndentedString(hits)).append("\n");
    sb.append("    nbHits: ").append(toIndentedString(nbHits)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    hitsPerPage: ").append(toIndentedString(hitsPerPage)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

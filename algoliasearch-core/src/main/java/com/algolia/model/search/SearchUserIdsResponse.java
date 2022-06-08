package com.algolia.model.search;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** userIDs data. */
public class SearchUserIdsResponse {

  @SerializedName("hits")
  private List<SearchUserIdsResponseHits> hits = new ArrayList<>();

  @SerializedName("nbHits")
  private Integer nbHits;

  @SerializedName("page")
  private Integer page = 0;

  @SerializedName("hitsPerPage")
  private Integer hitsPerPage = 20;

  @SerializedName("updatedAt")
  private String updatedAt;

  public SearchUserIdsResponse setHits(List<SearchUserIdsResponseHits> hits) {
    this.hits = hits;
    return this;
  }

  public SearchUserIdsResponse addHits(SearchUserIdsResponseHits hitsItem) {
    this.hits.add(hitsItem);
    return this;
  }

  /**
   * List of user object matching the query.
   *
   * @return hits
   */
  @javax.annotation.Nonnull
  public List<SearchUserIdsResponseHits> getHits() {
    return hits;
  }

  public SearchUserIdsResponse setNbHits(Integer nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  /**
   * Number of hits that the search query matched.
   *
   * @return nbHits
   */
  @javax.annotation.Nonnull
  public Integer getNbHits() {
    return nbHits;
  }

  public SearchUserIdsResponse setPage(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Specify the page to retrieve.
   *
   * @return page
   */
  @javax.annotation.Nonnull
  public Integer getPage() {
    return page;
  }

  public SearchUserIdsResponse setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  /**
   * Maximum number of hits in a page. Minimum is 1, maximum is 1000.
   *
   * @return hitsPerPage
   */
  @javax.annotation.Nonnull
  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchUserIdsResponse setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Date of last update (ISO-8601 format).
   *
   * @return updatedAt
   */
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
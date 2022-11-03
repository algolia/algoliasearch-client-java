// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.analytics;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** TopSearchesResponseWithAnalytics */
public class TopSearchesResponseWithAnalytics {

  @JsonProperty("searches")
  private List<TopSearchWithAnalytics> searches = new ArrayList<>();

  public TopSearchesResponseWithAnalytics setSearches(List<TopSearchWithAnalytics> searches) {
    this.searches = searches;
    return this;
  }

  public TopSearchesResponseWithAnalytics addSearches(TopSearchWithAnalytics searchesItem) {
    this.searches.add(searchesItem);
    return this;
  }

  /**
   * A list of top searches with their count and analytics.
   *
   * @return searches
   */
  @javax.annotation.Nonnull
  public List<TopSearchWithAnalytics> getSearches() {
    return searches;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopSearchesResponseWithAnalytics topSearchesResponseWithAnalytics = (TopSearchesResponseWithAnalytics) o;
    return Objects.equals(this.searches, topSearchesResponseWithAnalytics.searches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(searches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopSearchesResponseWithAnalytics {\n");
    sb.append("    searches: ").append(toIndentedString(searches)).append("\n");
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
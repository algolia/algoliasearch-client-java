// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Recommend rules parameters. */
public class SearchRecommendRulesParams {

  @JsonProperty("query")
  private String query;

  @JsonProperty("context")
  private String context;

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("hitsPerPage")
  private Integer hitsPerPage;

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("filters")
  private String filters;

  @JsonProperty("facets")
  private List<String> facets;

  @JsonProperty("maxValuesPerFacet")
  private Integer maxValuesPerFacet;

  public SearchRecommendRulesParams setQuery(String query) {
    this.query = query;
    return this;
  }

  /** Search query. */
  @javax.annotation.Nullable
  public String getQuery() {
    return query;
  }

  public SearchRecommendRulesParams setContext(String context) {
    this.context = context;
    return this;
  }

  /** Only search for rules with matching context. */
  @javax.annotation.Nullable
  public String getContext() {
    return context;
  }

  public SearchRecommendRulesParams setPage(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Requested page of the API response. Algolia uses `page` and `hitsPerPage` to control how search
   * results are displayed
   * ([paginated](https://www.algolia.com/doc/guides/building-search-ui/ui-and-ux-patterns/pagination/js/)).
   * - `hitsPerPage`: sets the number of search results (_hits_) displayed per page. - `page`:
   * specifies the page number of the search results you want to retrieve. Page numbering starts at
   * 0, so the first page is `page=0`, the second is `page=1`, and so on. For example, to display 10
   * results per page starting from the third page, set `hitsPerPage` to 10 and `page` to 2.
   * minimum: 0
   */
  @javax.annotation.Nullable
  public Integer getPage() {
    return page;
  }

  public SearchRecommendRulesParams setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  /**
   * Maximum number of hits per page. Algolia uses `page` and `hitsPerPage` to control how search
   * results are displayed
   * ([paginated](https://www.algolia.com/doc/guides/building-search-ui/ui-and-ux-patterns/pagination/js/)).
   * - `hitsPerPage`: sets the number of search results (_hits_) displayed per page. - `page`:
   * specifies the page number of the search results you want to retrieve. Page numbering starts at
   * 0, so the first page is `page=0`, the second is `page=1`, and so on. For example, to display 10
   * results per page starting from the third page, set `hitsPerPage` to 10 and `page` to 2.
   * minimum: 1 maximum: 1000
   */
  @javax.annotation.Nullable
  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchRecommendRulesParams setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Whether to only show rules where the value of their `enabled` property matches this parameter.
   * If absent, show all rules, regardless of their `enabled` property.
   */
  @javax.annotation.Nullable
  public Boolean getEnabled() {
    return enabled;
  }

  public SearchRecommendRulesParams setFilters(String filters) {
    this.filters = filters;
    return this;
  }

  /** Filter expression. This only searches for rules matching the filter expression. */
  @javax.annotation.Nullable
  public String getFilters() {
    return filters;
  }

  public SearchRecommendRulesParams setFacets(List<String> facets) {
    this.facets = facets;
    return this;
  }

  public SearchRecommendRulesParams addFacets(String facetsItem) {
    if (this.facets == null) {
      this.facets = new ArrayList<>();
    }
    this.facets.add(facetsItem);
    return this;
  }

  /** Include facets and facet values in the response. Use `['*']` to include all facets. */
  @javax.annotation.Nullable
  public List<String> getFacets() {
    return facets;
  }

  public SearchRecommendRulesParams setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  /** Maximum number of values to return for each facet. minimum: 1 maximum: 1000 */
  @javax.annotation.Nullable
  public Integer getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchRecommendRulesParams searchRecommendRulesParams = (SearchRecommendRulesParams) o;
    return (
      Objects.equals(this.query, searchRecommendRulesParams.query) &&
      Objects.equals(this.context, searchRecommendRulesParams.context) &&
      Objects.equals(this.page, searchRecommendRulesParams.page) &&
      Objects.equals(this.hitsPerPage, searchRecommendRulesParams.hitsPerPage) &&
      Objects.equals(this.enabled, searchRecommendRulesParams.enabled) &&
      Objects.equals(this.filters, searchRecommendRulesParams.filters) &&
      Objects.equals(this.facets, searchRecommendRulesParams.facets) &&
      Objects.equals(this.maxValuesPerFacet, searchRecommendRulesParams.maxValuesPerFacet)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(query, context, page, hitsPerPage, enabled, filters, facets, maxValuesPerFacet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchRecommendRulesParams {\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    hitsPerPage: ").append(toIndentedString(hitsPerPage)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    facets: ").append(toIndentedString(facets)).append("\n");
    sb.append("    maxValuesPerFacet: ").append(toIndentedString(maxValuesPerFacet)).append("\n");
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

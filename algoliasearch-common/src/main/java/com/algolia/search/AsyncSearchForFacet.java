package com.algolia.search;

import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.SearchFacetResult;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncSearchForFacet<T> extends AsyncBaseIndex<T> {

  /**
   * Search in a facet
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /**
   * Search in a facet
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(
      @Nonnull String facetName,
      @Nonnull String facetQuery,
      Query query,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient()
        .searchForFacetValues(getName(), facetName, facetQuery, query, requestOptions);
  }

  /**
   * Search in a facet
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  /**
   * Search in a facet
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(
      @Nonnull String facetName,
      @Nonnull String facetQuery,
      @Nonnull RequestOptions requestOptions) {
    return this.searchForFacetValues(facetName, facetQuery, null, requestOptions);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String, Query)} */
  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchForFacetValues(facetName, facetQuery, query);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String)} */
  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String, Query)} */
  @Deprecated
  default CompletableFuture<SearchFacetResult> searchFacet(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchForFacetValues(facetName, facetQuery, query);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String)} */
  @Deprecated
  default CompletableFuture<SearchFacetResult> searchFacet(
      @Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery);
  }
}

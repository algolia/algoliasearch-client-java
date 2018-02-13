package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.SearchFacetResult;
import javax.annotation.Nonnull;

public interface SyncSearchForFacet<T> extends SyncBaseIndex<T> {

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @return the result of the search
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName,
      @Nonnull String facetQuery,
      Query query,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .searchForFacetValues(getName(), facetName, facetQuery, query, requestOptions);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) throws AlgoliaException {
    return searchForFacetValues(facetName, facetQuery, RequestOptions.empty);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .searchForFacetValues(getName(), facetName, facetQuery, null, requestOptions);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String, Query)} */
  @Deprecated
  default SearchFacetResult searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String)} */
  @Deprecated
  default SearchFacetResult searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String, Query)} */
  @Deprecated
  default SearchFacetResult searchFacet(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /** Deprecated: use {@link #searchForFacetValues(String, String)} */
  @Deprecated
  default SearchFacetResult searchFacet(@Nonnull String facetName, @Nonnull String facetQuery)
      throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery);
  }
}

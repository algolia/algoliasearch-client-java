package com.algolia.search.clients;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.search.Query;
import com.algolia.search.models.search.SearchForFacetRequest;
import com.algolia.search.models.search.SearchForFacetResponse;
import com.algolia.search.models.search.SearchResult;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchIndexSearching<T> extends SearchIndexBase<T> {
  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   */
  default SearchResult<T> search(@Nonnull Query query) {
    return LaunderThrowable.unwrap(searchAsync(query));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @param requestOptions Options to pass to this request
   */
  default SearchResult<T> search(@Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchAsync(query, requestOptions));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   */
  default CompletableFuture<SearchResult<T>> searchAsync(@Nonnull Query query) {
    return searchAsync(query, null);
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  default CompletableFuture<SearchResult<T>> searchAsync(
      @Nonnull Query query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A query key is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/query",
            CallType.READ,
            query,
            SearchResult.class,
            getKlass(),
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   */
  default CompletableFuture<SearchForFacetResponse> searchForFacetValuesAsync(
      @Nonnull SearchForFacetRequest query) {
    return searchForFacetValuesAsync(query, null);
  }

  /**
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SearchForFacetResponse> searchForFacetValuesAsync(
      @Nonnull SearchForFacetRequest query, RequestOptions requestOptions) {
    Objects.requireNonNull(query, "query is required.");

    if (query.getFacetName() == null || query.getFacetName().trim().length() == 0) {
      throw new AlgoliaRuntimeException("facetName is required.");
    }

    if (query.getFacetQuery() == null || query.getFacetQuery().trim().length() == 0) {
      throw new AlgoliaRuntimeException("facetQuery is required.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/facets/" + query.getFacetName() + "/query",
            CallType.READ,
            query,
            SearchForFacetResponse.class,
            requestOptions);
  }
}

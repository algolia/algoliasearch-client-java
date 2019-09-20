package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.*;
import com.algolia.search.util.AlgoliaUtils;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

/**
 * This interface holds all endpoints related to search.
 *
 * @param <T>
 */
public interface SearchIndexSearching<T> extends SearchIndexBase<T> {
  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<T> search(@Nonnull Query query) {
    return LaunderThrowable.await(searchAsync(query));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<T> search(@Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.await(searchAsync(query, requestOptions));
  }

  /**
   * Method used for querying an index. The search query only allows for the retrieval of up to 1000
   * hits. If you need to retrieve more than 1000 hits (e.g. for SEO), you can either leverage the
   * Browse index method or increase the paginationLimitedTo parameter.
   *
   * @param query The search query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
            getClazz(),
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchForFacetResponse searchForFacetValues(@Nonnull SearchForFacetRequest query) {
    return LaunderThrowable.await(searchForFacetValuesAsync(query));
  }

  /**
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchForFacetResponse searchForFacetValues(
      @Nonnull SearchForFacetRequest query, RequestOptions requestOptions) {
    return LaunderThrowable.await(searchForFacetValuesAsync(query, requestOptions));
  }

  /**
   * Search for a set of values within a given facet attribute. Can be combined with a query. This
   * method enables you to search through the values of a facet attribute, selecting only a subset
   * of those values that meet a given criteria.
   *
   * @param query Search for facet query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
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
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SearchForFacetResponse> searchForFacetValuesAsync(
      @Nonnull SearchForFacetRequest query, RequestOptions requestOptions) {
    Objects.requireNonNull(query, "query is required.");

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(query.getFacetName())) {
      throw new AlgoliaRuntimeException("facetName must not be null, empty or white spaces.");
    }

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(query.getFacetQuery())) {
      throw new AlgoliaRuntimeException("facetQuery must not be null, empty or white spaces.");
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

  /**
   * FindFirstObject searches iteratively through the search response `Hits` field to find the first
   * response hit that would match against the given `filterFunc` function.
   *
   * <p>If no object has been found within the first result set, the function will perform a new
   * search operation on the next page of results, if any, until a matching object is found or the
   * end of results, whichever happens first.
   *
   * <p>To prevent the iteration through pages of results, `doNotPaginate` parameter can be set to
   * true. This will stop the function at the end of the first page of search results even if no
   * object does match.
   *
   * <p>If no result found `null` will be returned
   *
   * @param match The predicate to match
   * @param query The search Query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @return HitsWithPosition
   */
  default HitsWithPosition<T> findObject(Predicate<T> match, Query query) {
    return findObject(match, query, true, null);
  }

  /**
   * FindFirstObject searches iteratively through the search response `Hits` field to find the first
   * response hit that would match against the given `filterFunc` function.
   *
   * <p>If no object has been found within the first result set, the function will perform a new
   * search operation on the next page of results, if any, until a matching object is found or the
   * end of results, whichever happens first.
   *
   * <p>To prevent the iteration through pages of results, `doNotPaginate` parameter can be set to
   * true. This will stop the function at the end of the first page of search results even if no
   * object does match.
   *
   * <p>If no result found `null` will be returned
   *
   * @param match The predicate to match
   * @param query The search Query
   * @param paginate Should the method paginate or not
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @return HitsWithPosition
   */
  default HitsWithPosition<T> findObject(Predicate<T> match, Query query, boolean paginate) {
    return findObject(match, query, paginate, null);
  }

  /**
   * FindFirstObject searches iteratively through the search response `Hits` field to find the first
   * response hit that would match against the given `filterFunc` function.
   *
   * <p>If no object has been found within the first result set, the function will perform a new
   * search operation on the next page of results, if any, until a matching object is found or the
   * end of results, whichever happens first.
   *
   * <p>To prevent the iteration through pages of results, `doNotPaginate` parameter can be set to
   * true. This will stop the function at the end of the first page of search results even if no
   * object does match.
   *
   * <p>If no result found `null` will be returned
   *
   * @param match The predicate to match
   * @param query The search Query
   * @param paginate Should the method paginate or not
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @return HitsWithPosition
   */
  default HitsWithPosition<T> findObject(
      Predicate<T> match, Query query, boolean paginate, RequestOptions requestOptions) {

    SearchResult<T> res = search(query, requestOptions);

    if (res.getHits().stream().anyMatch(match)) {
      return res.getHits().stream()
          .filter(match)
          .findFirst()
          .map(x -> new HitsWithPosition<>(x, res.getPage(), res.getHits().indexOf(x)))
          .orElse(null);
    }

    boolean hasNextPage = res.getPage() + 1 < res.getNbPages();

    if (!paginate || !hasNextPage) {
      return null;
    }

    query.setPage(res.getPage().intValue() + 1);

    return findObject(match, query, paginate, requestOptions);
  }
}

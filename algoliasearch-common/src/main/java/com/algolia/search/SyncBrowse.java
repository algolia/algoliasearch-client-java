package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.BrowseResult;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface SyncBrowse<T> extends SyncBaseIndex<T> {

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @return the iterator on top of this index
   */
  default IndexIterable<T> browse(@Nonnull Query query) {
    return browse(query, RequestOptions.empty);
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   */
  default IndexIterable<T> browse(@Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return new IndexIterable<>(getApiClient(), getName(), query, requestOptions, getKlass());
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @return browse result
   */
  default BrowseResult<T> browseFrom(@Nonnull Query query, @Nullable String cursor)
      throws AlgoliaException {
    return browseFrom(query, cursor, RequestOptions.empty);
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   */
  default BrowseResult<T> browseFrom(
      @Nonnull Query query, @Nullable String cursor, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().browse(getName(), query, cursor, getKlass(), requestOptions);
  }
}

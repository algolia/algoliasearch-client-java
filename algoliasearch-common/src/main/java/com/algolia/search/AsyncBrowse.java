package com.algolia.search;

import com.algolia.search.iterators.AsyncIndexIterable;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.BrowseResult;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface AsyncBrowse<T> extends AsyncBaseIndex<T> {
  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @return the iterator on top of this index
   */
  default AsyncIndexIterable<T> browse(@Nonnull Query query) {
    return browse(query, new RequestOptions());
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   */
  default AsyncIndexIterable<T> browse(
      @Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return new AsyncIndexIterable<>(getApiClient(), getName(), query, requestOptions, getKlass());
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @return browse result
   */
  default CompletableFuture<BrowseResult<T>> browseFrom(
      @Nonnull Query query, @Nullable String cursor) {
    return browseFrom(query, cursor, new RequestOptions());
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   */
  default CompletableFuture<BrowseResult<T>> browseFrom(
      @Nonnull Query query, @Nullable String cursor, @Nonnull RequestOptions requestOptions) {
    return getApiClient().browse(getName(), query, cursor, getKlass(), requestOptions);
  }
}

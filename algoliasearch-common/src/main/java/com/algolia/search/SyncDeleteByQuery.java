package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import javax.annotation.Nonnull;

@SuppressWarnings("SameParameterValue")
public interface SyncDeleteByQuery<T> extends SyncBaseIndex<T> {

  /** Deprecated: use {@link #deleteBy(Query)} */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query) throws AlgoliaException {
    deleteByQuery(query, RequestOptions.empty);
  }

  /**
   * Delete records matching a query
   *
   * @param query The query
   */
  default Task deleteBy(@Nonnull Query query) throws AlgoliaException {
    return deleteBy(query, RequestOptions.empty);
  }

  /** Deprecated: use {@link #deleteBy(Query, RequestOptions)} */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().deleteByQuery(getName(), query, 1000, requestOptions);
  }

  /**
   * Delete records matching a query
   *
   * @param query The query
   * @param requestOptions Options to pass to this request
   */
  default Task deleteBy(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteBy(getName(), query, requestOptions);
  }

  /** Deprecated: use {@link #deleteBy(Query)} */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query, int batchSize) throws AlgoliaException {
    deleteByQuery(query, batchSize, RequestOptions.empty);
  }

  /** Deprecated: use {@link #deleteBy(Query, RequestOptions)} */
  @Deprecated
  default void deleteByQuery(
      @Nonnull Query query, int batchSize, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().deleteByQuery(getName(), query, batchSize, requestOptions);
  }
}

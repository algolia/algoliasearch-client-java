package com.algolia.search;

import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncDeleteByQuery<T> extends AsyncBaseIndex<T> {

  /**
   * Delete records matching a query
   *
   * @param query The query
   */
  default CompletableFuture<AsyncTask> deleteBy(@Nonnull Query query) {
    return deleteBy(query, RequestOptions.empty);
  }

  /**
   * Delete records matching a query Deprecated, use deleteBy
   *
   * @param query The query
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<AsyncTask> deleteBy(
      @Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteBy(getName(), query, requestOptions);
  }
}

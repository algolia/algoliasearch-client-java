package com.algolia.search;

import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncIndexCRUD<T> extends AsyncBaseIndex<T> {

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  default CompletableFuture<AsyncTask> moveTo(@Nonnull String dstIndexName) {
    return moveTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  default CompletableFuture<AsyncTask> moveTo(
      @Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions) {
    return getApiClient().moveIndex(getName(), dstIndexName, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  default CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName) {
    return copyTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  default CompletableFuture<AsyncTask> copyTo(
      @Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions) {
    return getApiClient().copyIndex(getName(), dstIndexName, null, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scope the list of scope to copy
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  default CompletableFuture<AsyncTask> copyTo(
      @Nonnull String dstIndexName,
      @Nonnull List<String> scope,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient().copyIndex(getName(), dstIndexName, scope, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scope the list of scope to copy
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> copyTo(
      @Nonnull String dstIndexName, @Nonnull List<String> scope) {
    return copyTo(dstIndexName, scope, RequestOptions.empty);
  }

  /**
   * Deletes the index
   *
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> delete() {
    return delete(RequestOptions.empty);
  }

  /**
   * Deletes the index
   *
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> delete(@Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteIndex(getName(), requestOptions);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> clear() {
    return clear(RequestOptions.empty);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> clear(@Nonnull RequestOptions requestOptions) {
    return getApiClient().clearIndex(getName(), requestOptions);
  }
}

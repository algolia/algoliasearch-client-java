package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface SyncIndexCRUD<T> extends SyncBaseIndex<T> {

  /**
   * Deletes the index
   *
   * @return the related Task
   */
  default Task delete() throws AlgoliaException {
    return delete(RequestOptions.empty);
  }

  /**
   * Deletes the index
   *
   * @param requestOptions Options to pass to this request
   * @return the related Task
   */
  default Task delete(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().deleteIndex(getName(), requestOptions);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related Task
   */
  default Task clear() throws AlgoliaException {
    return clear(RequestOptions.empty);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @param requestOptions Options to pass to this request
   * @return the related Task
   */
  default Task clear(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().clearIndex(getName(), requestOptions);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The task associated
   */
  default Task moveTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return moveTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   */
  default Task moveTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().moveIndex(getName(), dstIndexName, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The task associated
   */
  default Task copyTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return copyTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   */
  default Task copyTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().copyIndex(getName(), dstIndexName, null, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scopes the list of scopes to copy
   * @param requestOptions Options to pass to this request
   * @return The task associated
   */
  default Task copyTo(
      @Nonnull String dstIndexName,
      @Nonnull List<String> scopes,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().copyIndex(getName(), dstIndexName, scopes, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param scopes the list of scopes to copy
   * @return The task associated
   */
  default Task copyTo(@Nonnull String dstIndexName, @Nonnull List<String> scopes)
      throws AlgoliaException {
    return copyTo(dstIndexName, scopes, RequestOptions.empty);
  }
}

package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.IndexContent;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface SyncIndexCRUD<T> extends SyncBaseIndex<T> {

  /**
   * Deletes the index
   *
   * @return the associated Task
   */
  default Task delete() throws AlgoliaException {
    return delete(new RequestOptions());
  }

  /**
   * Deletes the index
   *
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task delete(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().deleteIndex(getName(), requestOptions);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the associated Task
   */
  default Task clear() throws AlgoliaException {
    return clear(new RequestOptions());
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task clear(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().clearIndex(getName(), requestOptions);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  default Task moveTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return moveTo(dstIndexName, new RequestOptions());
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
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
   * @return The associated task
   */
  default Task copyTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return copyTo(dstIndexName, new RequestOptions());
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
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
   * @param scope the list of scope to copy
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  default Task copyTo(
      @Nonnull String dstIndexName,
      @Nonnull List<String> scope,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
  default Task copyTo(@Nonnull String dstIndexName, @Nonnull List<String> scope)
      throws AlgoliaException {
    return copyTo(dstIndexName, scope, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @throws AlgoliaException AlgoliaException
   */
  default void reIndex(@Nonnull IndexContent<T> indexContent) throws AlgoliaException {
    reIndex(indexContent, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @param requestOptions request options
   * @throws AlgoliaException Algolia Exception
   */
  default void reIndex(
      @Nonnull IndexContent<T> indexContent, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

    // 1. Init temps Index
    Index tmpIndex = this.getApiClient().initIndex(getName() + "_tmp");
    List<Long> taskIds = new ArrayList<>();

    // 2. Copy the settings, synonyms and rules (but not the records)
    List<String> scopes = new ArrayList<>();

    if (indexContent.isRules()) {
      scopes.add("rules");
    }

    if (indexContent.isSettings()) {
      scopes.add("settings");
    }

    if (indexContent.isSynonyms()) {
      scopes.add("synonyms");
    }

    if (scopes.size() > 0) {
      Task copyIndexTask = copyTo(tmpIndex.getName(), scopes, requestOptions);
      taskIds.add(copyIndexTask.getTaskID());
    }

    // 3. Fetch your data with the iterator and push it to the temporary index
    ArrayList<T> records = new ArrayList<>();

    for (T object : indexContent.getObjects()) {

      if (records.size() == 10000) {
        Task task = tmpIndex.addObjects(records, requestOptions);
        taskIds.add(task.getTaskID());
        records.clear();
      }

      records.add(object);
    }

    if (records.size() > 0) {
      Task task = tmpIndex.addObjects(records, requestOptions);
      taskIds.add(task.getTaskID());
    }

    // 4. Wait for all the tasks to finish asynchronously
    if (indexContent.isSafeOperation()) {
      for (Long taskId : taskIds) {
        tmpIndex.waitTask(taskId);
      }
    }

    // 5. Move the temporary index to the target index
    Task moveIndexResponse = moveTo(tmpIndex.getName(), requestOptions);

    if (indexContent.isSafeOperation()) {
      getApiClient().waitTask(moveIndexResponse);
    }
  }
}

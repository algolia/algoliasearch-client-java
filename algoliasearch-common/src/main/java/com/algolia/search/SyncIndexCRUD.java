package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.IndexContent;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
   * @param newIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @return The associated task
   */
  default Task moveTo(@Nonnull String newIndexName) throws AlgoliaException {
    return moveTo(newIndexName, new RequestOptions());
  }

  /**
   * Moves an existing index
   *
   * @param newIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overwritten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The associated task
   */
  default Task moveTo(@Nonnull String newIndexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().moveIndex(getName(), newIndexName, requestOptions);
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
  default List<Long> reIndex(@Nonnull IndexContent<T> indexContent) throws AlgoliaException {
    return reIndex(indexContent, false, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @param safe runs the reindex safely
   * @throws AlgoliaException AlgoliaException
   */
  default List<Long> reIndex(@Nonnull IndexContent<T> indexContent, boolean safe)
      throws AlgoliaException {
    return reIndex(indexContent, safe, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @param safe runs the reindex safely
   * @param requestOptions request options
   * @throws AlgoliaException Algolia Exception
   */
  default List<Long> reIndex(
      @Nonnull IndexContent<T> indexContent, boolean safe, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

    // 1. Init temps Index
    Index<T> tmpIndex =
        this.getApiClient()
            .initIndex(
                getName() + "_tmp_" + UUID.randomUUID().toString(), indexContent.getObjectClass());

    // 2. Copy the settings, synonyms and rules (but not the records)
    List<Long> taskIds = new ArrayList<>();
    List<String> scopes = indexContent.getScopes();

    if (scopes.size() > 0) {
      Task copyIndexTask = copyTo(tmpIndex.getName(), scopes, requestOptions);
      taskIds.add(copyIndexTask.getTaskID());
      if (safe) {
        tmpIndex.waitTask(copyIndexTask.getTaskID());
      }
    }

    if (indexContent.getRules() != null) {
      Task task = tmpIndex.batchRules(indexContent.getRules(), requestOptions);
      taskIds.add(task.getTaskID());
    }

    if (indexContent.getSettings() != null) {
      Task task = tmpIndex.setSettings(indexContent.getSettings(), requestOptions);
      taskIds.add(task.getTaskID());
    }

    if (indexContent.getSynonyms() != null) {
      Task task = tmpIndex.batchSynonyms(indexContent.getSynonyms(), requestOptions);
      taskIds.add(task.getTaskID());
    }

    // 3. Fetch your data with the iterator and push it to the temporary index
    if (indexContent.getObjects() != null) {
      ArrayList<T> records = new ArrayList<>();

      for (T object : indexContent.getObjects()) {

        if (records.size() == 1000) {
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
    }

    // 4. Wait for all the tasks to finish asynchronously
    if (safe) {
      for (Long taskId : taskIds) {
        tmpIndex.waitTask(taskId);
      }
    }

    // 5. Move the temporary index to the target index
    Task moveIndexResponse =
        getApiClient().moveIndex(tmpIndex.getName(), getName(), requestOptions);
    taskIds.add(moveIndexResponse.getTaskID());

    if (safe) {
      tmpIndex.waitTask(moveIndexResponse);
    }

    return taskIds;
  }
}

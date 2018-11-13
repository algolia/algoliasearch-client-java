package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.IndexContent;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
    return copyTo(dstIndexName, scope, new RequestOptions());
  }

  /**
   * Deletes the index
   *
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> delete() {
    return delete(new RequestOptions());
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
    return clear(new RequestOptions());
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

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   */
  default List<Long> reIndex(@Nonnull IndexContent<T> indexContent)
      throws InterruptedException, ExecutionException, AlgoliaException {
    return reIndex(indexContent, false, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @param safe runs the reindex safely
   */
  default List<Long> reIndex(@Nonnull IndexContent<T> indexContent, boolean safe)
          throws InterruptedException, ExecutionException, AlgoliaException {
    return reIndex(indexContent, safe, new RequestOptions());
  }

  /**
   * Rebuild the entirety of your data atomically, to ensure that searches performed on the index
   * during the rebuild will not be interrupted.
   *
   * @param indexContent the content of the index to reindex
   * @param safe runs the reindex safely
   * @param requestOptions request options
   */
  default List<Long> reIndex(
      @Nonnull IndexContent<T> indexContent, boolean safe, @Nonnull RequestOptions requestOptions)
      throws ExecutionException, InterruptedException, AlgoliaException {

    // 1. Init temps Index
    AsyncIndex<T> tmpIndex =
        getApiClient()
            .initIndex(getName() + "_tmp_" + UUID.randomUUID().toString(), indexContent.getObjectClass());

    // 2. Copy the settings, synonyms and rules (but not the records)
    List<Long> taskIds = new ArrayList<>();
    List<String> scopes = indexContent.getScopes();

    // Copy index
    if (scopes.size() > 0) {
      AsyncTask copyIndexTask = copyTo(tmpIndex.getName(), scopes, requestOptions).get();
      taskIds.add(copyIndexTask.getTaskID());
      if (safe) {
        tmpIndex.waitTask(copyIndexTask.getTaskID());
      }
    }

    // Rules
    if (indexContent.getRules() != null) {
      AsyncTask task = tmpIndex.batchRules(indexContent.getRules(), requestOptions).get();
      taskIds.add(task.getTaskID());
    }

    // Settings
    if (indexContent.getSettings() != null) {
      AsyncTask task = tmpIndex.setSettings(indexContent.getSettings(), requestOptions).get();
      taskIds.add(task.getTaskID());
    }

    // Synonyms
    if (indexContent.getSynonyms() != null) {
      AsyncTask task = tmpIndex.batchSynonyms(indexContent.getSynonyms(), requestOptions).get();
      taskIds.add(task.getTaskID());
    }

    // 3. Fetch your data with the iterator and push it to the temporary index
    if (indexContent.getObjects() != null) {
      ArrayList<T> records = new ArrayList<>();

      for (T object : indexContent.getObjects()) {

        if (records.size() == 1000) {
          AsyncTaskSingleIndex task = tmpIndex.addObjects(records, requestOptions).get();
          taskIds.add(task.getTaskID());
          records.clear();
        }

        records.add(object);
      }

      if (records.size() > 0) {
        AsyncTaskSingleIndex task = tmpIndex.addObjects(records, requestOptions).get();
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
    AsyncTask moveIndexResponse =
        getApiClient().moveIndex(tmpIndex.getName(), getName(), requestOptions).get();
    taskIds.add(moveIndexResponse.getTaskID());

    if (safe) {
      tmpIndex.waitTask(moveIndexResponse);
    }

    return taskIds;
  }
}

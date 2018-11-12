package com.algolia.search;

import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.objects.tasks.async.AsyncTaskIndexing;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
import com.google.common.collect.Lists;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public interface AsyncObjects<T> extends AsyncBaseIndex<T> {

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object) {
    return addObject(object, new RequestOptions());
  }

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(
      @Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addObject(getName(), object, requestOptions);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object (if this objectID already exist the old
   *     object will be overridden)
   * @param object object to add
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(
      @Nonnull String objectID, @Nonnull T object) {
    return addObject(objectID, object, new RequestOptions());
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object (if this objectID already exist the old
   *     object will be overridden)
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(
      @Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects) {
    return addObjects(objects, new RequestOptions());
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> addObjects(
      @Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addObjects(getName(), objects, requestOptions);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   */
  default CompletableFuture<Optional<T>> getObject(@Nonnull String objectID) {
    return getObject(objectID, new RequestOptions());
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return The object
   */
  default CompletableFuture<Optional<T>> getObject(
      @Nonnull String objectID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getObject(getName(), objectID, getKlass(), requestOptions);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs) {
    return getObjects(objectIDs, new RequestOptions());
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(
      @Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getObjects(getName(), objectIDs, getKlass(), requestOptions);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(
      @Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve) {
    return getObjects(objectIDs, attributesToRetrieve, new RequestOptions());
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(
      @Nonnull List<String> objectIDs,
      @Nonnull List<String> attributesToRetrieve,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient()
        .getObjects(getName(), objectIDs, attributesToRetrieve, getKlass(), requestOptions);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object the object to update
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object) {
    return saveObject(objectID, object, new RequestOptions());
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object the object to update
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> saveObject(
      @Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects) {
    return saveObjects(objects, new RequestOptions());
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> saveObjects(
      @Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveObjects(getName(), objects, requestOptions);
  }

  /**
   * Replace all objects
   *
   * @param objects the list objects to update
   * @param safe run the method in a safe way
   */
  default void replaceAllObjects(@Nonnull List<T> objects, boolean safe)
      throws ExecutionException, InterruptedException {
    replaceAllObjects(objects, safe, new RequestOptions());
  }

  /**
   * Replace all objects
   *
   * @param objects the list objects to update
   * @param safe run the method in a safe way
   * @param requestOptions Options to pass to this request
   */
  default void replaceAllObjects(
      @Nonnull List<T> objects, boolean safe, @Nonnull RequestOptions requestOptions)
      throws ExecutionException, InterruptedException {

    String tmpName = this.getName() + "_tmp_" + UUID.randomUUID().toString();
    List<String> scope = new ArrayList<>(Arrays.asList("rules", "settings", "synonyms"));
    List<AsyncTaskSingleIndex> batchResponses = new ArrayList<>();

    // Copy all index resources from production index
    AsyncTask copyIndexResponse =
        getApiClient().copyIndex(getName(), tmpName, scope, requestOptions).get();

    if (safe) {
      getApiClient().waitTask(copyIndexResponse);
    }

    // Send records (batched automatically)
    List<List<T>> chunks = Lists.partition(objects, 1000);

    for (List<T> chunk : chunks) {
      AsyncTaskSingleIndex batchResponse =
          getApiClient().saveObjects(tmpName, chunk, requestOptions).get();
      if (safe) {
        batchResponses.add(batchResponse);
      }
    }

    // if safe waits for task to finish
    if (safe) {
      for (AsyncTaskSingleIndex response : batchResponses) {
        getApiClient().waitTask(response);
      }
    }

    // Move temporary index to production
    AsyncTask moveIndexResponse =
        getApiClient().moveIndex(tmpName, getName(), requestOptions).get();

    // if safe waits for task to finish
    if (safe) {
      getApiClient().waitTask(moveIndexResponse);
    }
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) {
    return deleteObject(objectID, new RequestOptions());
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(
      @Nonnull String objectID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteObject(getName(), objectID, requestOptions);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs) {
    return deleteObjects(objectIDs, new RequestOptions());
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> deleteObjects(
      @Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteObjects(getName(), objectIDs, requestOptions);
  }
}

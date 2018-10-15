package com.algolia.search;

import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.objects.tasks.async.AsyncTaskIndexing;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncObjects<T> extends AsyncBaseIndex<T> {

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object) {
    return addObject(object, RequestOptions.empty);
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
    return addObject(objectID, object, RequestOptions.empty);
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
    return addObjects(objects, RequestOptions.empty);
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
    return getObject(objectID, RequestOptions.empty);
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
    return getObjects(objectIDs, RequestOptions.empty);
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
    return getObjects(objectIDs, attributesToRetrieve, RequestOptions.empty);
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
    return saveObject(objectID, object, RequestOptions.empty);
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
    return saveObjects(objects, RequestOptions.empty);
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
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) {
    return deleteObject(objectID, RequestOptions.empty);
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
    return deleteObjects(objectIDs, RequestOptions.empty);
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

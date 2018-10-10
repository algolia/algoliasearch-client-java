package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.objects.tasks.sync.TaskIndexing;
import com.algolia.search.objects.tasks.sync.TaskSingleIndex;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

@SuppressWarnings("SameParameterValue")
public interface SyncObjects<T> extends SyncBaseIndex<T> {

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the associated Task
   */
  default TaskIndexing addObject(@Nonnull T object) throws AlgoliaException {
    return addObject(object, new RequestOptions());
  }

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default TaskIndexing addObject(@Nonnull T object, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().addObject(getName(), object, requestOptions);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object (if this objectID already exist the old
   *     object will be overridden)
   * @param object object to add
   * @return the associated Task
   */
  default TaskIndexing addObject(@Nonnull String objectID, @Nonnull T object)
      throws AlgoliaException {
    return addObject(objectID, object, new RequestOptions());
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object (if this objectID already exist the old
   *     object will be overridden)
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default TaskIndexing addObject(
      @Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().addObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the associated Task
   */
  default TaskSingleIndex addObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return addObjects(objects, new RequestOptions());
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default TaskSingleIndex addObjects(
      @Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().addObjects(getName(), objects, requestOptions);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   */
  default Optional<T> getObject(@Nonnull String objectID) throws AlgoliaException {
    return getObject(objectID, new RequestOptions());
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return The object
   */
  default Optional<T> getObject(@Nonnull String objectID, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getObject(getName(), objectID, getKlass(), requestOptions);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   */
  default List<T> getObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return getObjects(objectIDs, new RequestOptions());
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   */
  default List<T> getObjects(
      @Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getObjects(getName(), objectIDs, getKlass(), requestOptions);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   */
  default List<T> getObjects(
      @Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve)
      throws AlgoliaException {
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
  default List<T> getObjects(
      @Nonnull List<String> objectIDs,
      @Nonnull List<String> attributesToRetrieve,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .getObjects(getName(), objectIDs, attributesToRetrieve, getKlass(), requestOptions);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object the object to update
   * @return the associated Task
   */
  default Task saveObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return saveObject(objectID, object, new RequestOptions());
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object the object to update
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task saveObject(
      @Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().saveObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the associated Task
   */
  default TaskSingleIndex saveObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return saveObjects(objects, new RequestOptions());
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default TaskSingleIndex saveObjects(
      @Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().saveObjects(getName(), objects, requestOptions);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the associated Task
   */
  default Task deleteObject(@Nonnull String objectID) throws AlgoliaException {
    return deleteObject(objectID, new RequestOptions());
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task deleteObject(@Nonnull String objectID, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteObject(getName(), objectID, requestOptions);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the associated Task
   */
  default TaskSingleIndex deleteObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return deleteObjects(objectIDs, new RequestOptions());
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default TaskSingleIndex deleteObjects(
      @Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteObjects(getName(), objectIDs, requestOptions);
  }
}

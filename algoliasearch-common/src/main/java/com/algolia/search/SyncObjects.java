package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.objects.tasks.sync.TaskIndexing;
import com.algolia.search.objects.tasks.sync.TaskSingleIndex;
import java.util.*;
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
   * Replace all objects
   *
   * @param objects the list objects to update
   * @param safe run the method in a safe way
   */
  default void replaceAllObjects(@Nonnull Iterable<T> objects, boolean safe)
      throws AlgoliaException {
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
      @Nonnull Iterable<T> objects, boolean safe, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

    String tmpName = this.getName() + "_tmp_" + UUID.randomUUID().toString();
    List<String> scope = new ArrayList<>(Arrays.asList("rules", "settings", "synonyms"));
    List<Task> batchResponses = new ArrayList<>();

    // Copy all index resources from index
    Task copyIndexResponse = getApiClient().copyIndex(getName(), tmpName, scope, requestOptions);

    if (safe) {
      getApiClient().waitTask(copyIndexResponse);
    }

    // Send records (batched automatically)
    ArrayList<T> records = new ArrayList<>();

    for (T object : objects) {

      if (records.size() == 1000) {
        Task batchResponse = getApiClient().saveObjects(tmpName, records, requestOptions);
        if (safe) {
          batchResponses.add(batchResponse);
        }
        records.clear();
      }

      records.add(object);
    }

    if (records.size() > 0) {
      Task batchResponse = getApiClient().saveObjects(tmpName, records, requestOptions);
      if (safe) {
        batchResponses.add(batchResponse);
      }
    }

    // if safe waits for task to finish
    if (safe) {
      for (Task response : batchResponses) {
        getApiClient().waitTask(response);
      }
    }

    // Move temporary index
    Task moveIndexResponse = getApiClient().moveIndex(tmpName, getName(), requestOptions);

    // if safe waits for task to finish
    if (safe) {
      getApiClient().waitTask(moveIndexResponse);
    }
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

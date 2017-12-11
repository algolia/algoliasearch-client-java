package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.objects.tasks.sync.TaskIndexing;
import com.algolia.search.objects.tasks.sync.TaskSingleIndex;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

interface BaseSyncIndex<T> extends AbstractIndex<T> {

  APIClient getApiClient();
}

@SuppressWarnings("UnusedReturnValue")
interface IndexCRUD<T> extends BaseSyncIndex<T> {

  /**
   * Deletes the index
   *
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task delete() throws AlgoliaException {
    return delete(RequestOptions.empty);
  }

  /**
   * Deletes the index
   *
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task delete(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().deleteIndex(getName(), requestOptions);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task clear() throws AlgoliaException {
    return clear(RequestOptions.empty);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task clear(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().clearIndex(getName(), requestOptions);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overriten if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  default Task moveTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return moveTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overriten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   * @throws AlgoliaException
   */
  default Task moveTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().moveIndex(getName(), dstIndexName, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overridden if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  default Task copyTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return copyTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overridden if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   * @throws AlgoliaException
   */
  default Task copyTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().copyIndex(getName(), dstIndexName, null, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination
   *     will be overridden if it already exist)
   * @param scopes the list of scopes to copy
   * @param requestOptions Options to pass to this request
   * @return The task associated
   * @throws AlgoliaException
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
   *     will be overridden if it already exist)
   * @param scopes the list of scopes to copy
   * @return The task associated
   * @throws AlgoliaException
   */
  default Task copyTo(@Nonnull String dstIndexName, @Nonnull List<String> scopes)
      throws AlgoliaException {
    return copyTo(dstIndexName, scopes, RequestOptions.empty);
  }
}

interface Tasks<T> extends BaseSyncIndex<T> {

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @throws AlgoliaException
   */
  default void waitTask(@Nonnull Task task, long timeToWait) throws AlgoliaException {
    waitTask(task, timeToWait, RequestOptions.empty);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaException
   */
  default void waitTask(@Nonnull Task task, long timeToWait, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);

    getApiClient().waitTask(task, timeToWait, requestOptions);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @throws AlgoliaException
   */
  default void waitTask(@Nonnull Task task) throws AlgoliaException {
    getApiClient().waitTask(task, 100);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaException
   */
  default void waitTask(@Nonnull Task task, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().waitTask(task, 100, requestOptions);
  }
}

@SuppressWarnings("SameParameterValue")
interface Objects<T> extends BaseSyncIndex<T> {

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskIndexing addObject(@Nonnull T object) throws AlgoliaException {
    return addObject(object, RequestOptions.empty);
  }

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
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
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskIndexing addObject(@Nonnull String objectID, @Nonnull T object)
      throws AlgoliaException {
    return addObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object (if this objectID already exist the old
   *     object will be overridden)
   * @param object object to add
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
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
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskSingleIndex addObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return addObjects(objects, RequestOptions.empty);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
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
   * @throws AlgoliaException
   */
  default Optional<T> getObject(@Nonnull String objectID) throws AlgoliaException {
    return getObject(objectID, RequestOptions.empty);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return The object
   * @throws AlgoliaException
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
   * @throws AlgoliaException
   */
  default List<T> getObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return getObjects(objectIDs, RequestOptions.empty);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   * @throws AlgoliaException
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
   * @throws AlgoliaException
   */
  default List<T> getObjects(
      @Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve)
      throws AlgoliaException {
    return getObjects(objectIDs, attributesToRetrieve, RequestOptions.empty);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   * @throws AlgoliaException
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
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task saveObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return saveObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object the object to update
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
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
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskSingleIndex saveObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return saveObjects(objects, RequestOptions.empty);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskSingleIndex saveObjects(
      @Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().saveObjects(getName(), objects, requestOptions);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task deleteObject(@Nonnull String objectID) throws AlgoliaException {
    return deleteObject(objectID, RequestOptions.empty);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task deleteObject(@Nonnull String objectID, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteObject(getName(), objectID, requestOptions);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskSingleIndex deleteObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return deleteObjects(objectIDs, RequestOptions.empty);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default TaskSingleIndex deleteObjects(
      @Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteObjects(getName(), objectIDs, requestOptions);
  }
}

interface Settings<T> extends BaseSyncIndex<T> {

  /**
   * Get settings of this index
   *
   * @return the settings
   * @throws AlgoliaException
   */
  default IndexSettings getSettings() throws AlgoliaException {
    return getSettings(RequestOptions.empty);
  }

  /**
   * Get settings of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the settings
   * @throws AlgoliaException
   */
  default IndexSettings getSettings(@Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getSettings(getName(), requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task setSettings(@Nonnull IndexSettings settings) throws AlgoliaException {
    return setSettings(settings, RequestOptions.empty);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task setSettings(@Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return setSettings(settings, false, requestOptions);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the replicas
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas)
      throws AlgoliaException {
    return setSettings(settings, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @param requestOptions Options to pass to this request
   * @return the related Task
   * @throws AlgoliaException
   */
  default Task setSettings(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().setSettings(getName(), settings, forwardToReplicas, requestOptions);
  }
}

@SuppressWarnings("UnusedReturnValue")
interface Key<T> extends BaseSyncIndex<T> {

  /** Deprecated: use listApiKeys */
  @Deprecated
  default List<ApiKey> listKeys() throws AlgoliaException {
    return listApiKeys();
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   * @throws AlgoliaException
   */
  default List<ApiKey> listApiKeys() throws AlgoliaException {
    return listApiKeys(RequestOptions.empty);
  }

  /**
   * List keys of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the list of keys
   * @throws AlgoliaException
   */
  default List<ApiKey> listApiKeys(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /** Deprecated: use getApiKey */
  @Deprecated
  default Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   * @throws AlgoliaException
   */
  default Optional<ApiKey> getApiKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the key
   * @throws AlgoliaException
   */
  default Optional<ApiKey> getApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /** Deprecated: use deleteApiKey */
  @Deprecated
  default DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   * @throws AlgoliaException
   */
  default DeleteKey deleteApiKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the deleted key
   * @throws AlgoliaException
   */
  default DeleteKey deleteApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /** Deprecated: use addApiKey */
  @Deprecated
  default CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   * @throws AlgoliaException
   */
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @param requestOptions Options to pass to this request
   * @return the created key
   * @throws AlgoliaException
   */
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @return the updated key
   * @throws AlgoliaException
   */
  default CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateKey(keyName, key, RequestOptions.empty);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @param requestOptions Options to pass to this request
   * @return the updated key
   * @throws AlgoliaException
   */
  default CreateUpdateKey updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().updateKey(getName(), keyName, key, requestOptions);
  }
}

interface SearchForFacet<T> extends BaseSyncIndex<T> {

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @return the result of the search
   * @throws AlgoliaException
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query the query (not required)
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   * @throws AlgoliaException
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName,
      @Nonnull String facetQuery,
      Query query,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .searchForFacetValues(getName(), facetName, facetQuery, query, requestOptions);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   * @throws AlgoliaException
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) throws AlgoliaException {
    return searchForFacetValues(facetName, facetQuery, RequestOptions.empty);
  }

  /**
   * Search in a facet throws a {@link com.algolia.search.exceptions.AlgoliaIndexNotFoundException}
   * if the index does not exists
   *
   * @param facetName The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   * @throws AlgoliaException
   */
  default SearchFacetResult searchForFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .searchForFacetValues(getName(), facetName, facetQuery, null, requestOptions);
  }

  @Deprecated
  default SearchFacetResult searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  @Deprecated
  default SearchFacetResult searchInFacetValues(
      @Nonnull String facetName, @Nonnull String facetQuery) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  @Deprecated
  default SearchFacetResult searchFacet(
      @Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  @Deprecated
  default SearchFacetResult searchFacet(@Nonnull String facetName, @Nonnull String facetQuery)
      throws AlgoliaException {
    return this.searchForFacetValues(facetName, facetQuery);
  }
}

interface PartialUpdate<T> extends BaseSyncIndex<T> {

  /**
   * Partially update an object
   *
   * @param objectID the ID of object to update
   * @param object the object to update
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(@Nonnull String objectID, @Nonnull Object object)
      throws AlgoliaException {
    return partialUpdateObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Partially update an object
   *
   * @param objectID the ID of object to update
   * @param object the object to update
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(
      @Nonnull String objectID, @Nonnull Object object, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().partialUpdateObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   * @throws AlgoliaException
   */
  default TaskSingleIndex partialUpdateObjects(@Nonnull List<Object> objects)
      throws AlgoliaException {
    return partialUpdateObjects(objects, RequestOptions.empty);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default TaskSingleIndex partialUpdateObjects(
      @Nonnull List<Object> objects, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return partialUpdateObjects(objects, true, requestOptions);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @param requestOptions Options to pass to this request
   * @param createIfNotExists Value that indicates the object is created if the objectID doesn't
   *     exists
   * @return the associated task
   * @throws AlgoliaException
   */
  default TaskSingleIndex partialUpdateObjects(
      @Nonnull List<Object> objects,
      boolean createIfNotExists,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .partialUpdateObjects(getName(), objects, requestOptions, createIfNotExists);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation the operation to perform on this object
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(@Nonnull PartialUpdateOperation operation)
      throws AlgoliaException {
    return partialUpdateObject(operation, true);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation the operation to perform on this object
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(
      @Nonnull PartialUpdateOperation operation, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return partialUpdateObject(operation, true, requestOptions);
  }

  /**
   * Partially update an object
   *
   * @param operation the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(
      @Nonnull PartialUpdateOperation operation, boolean createIfNotExists)
      throws AlgoliaException {
    return partialUpdateObject(operation, createIfNotExists, RequestOptions.empty);
  }

  /**
   * Partially update an object
   *
   * @param operation the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  default TaskSingleIndex partialUpdateObject(
      @Nonnull PartialUpdateOperation operation,
      boolean createIfNotExists,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .partialUpdateObject(getName(), operation, createIfNotExists, requestOptions);
  }
}

@SuppressWarnings("SameParameterValue")
interface Synonyms<T> extends BaseSyncIndex<T> {

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, false);
  }

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, false, requestOptions);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(
      @Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, forwardToReplicas, false);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, forwardToReplicas, false, requestOptions);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms)
      throws AlgoliaException {
    return saveSynonym(
        synonymID, content, forwardToReplicas, replaceExistingSynonyms, RequestOptions.empty);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .saveSynonym(
            getName(),
            synonymID,
            content,
            forwardToReplicas,
            replaceExistingSynonyms,
            requestOptions);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  default Optional<AbstractSynonym> getSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return getSynonym(synonymID, RequestOptions.empty);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Optional<AbstractSynonym> getSynonym(
      @Nonnull String synonymID, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().getSynonym(getName(), synonymID, requestOptions);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteSynonym(@Nonnull String synonymID, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return deleteSynonym(synonymID, false, requestOptions);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID the id of the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas)
      throws AlgoliaException {
    return deleteSynonym(synonymID, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID the id of the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteSynonym(
      @Nonnull String synonymID, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteSynonym(getName(), synonymID, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the replicas
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearSynonyms() throws AlgoliaException {
    return clearSynonyms(false);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearSynonyms(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return clearSynonyms(false, requestOptions);
  }

  /**
   * Clears all synonyms
   *
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearSynonyms(boolean forwardToReplicas) throws AlgoliaException {
    return clearSynonyms(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all synonyms
   *
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearSynonyms(boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().clearSynonyms(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   * @throws AlgoliaException
   */
  default SearchSynonymResult searchSynonyms(@Nonnull SynonymQuery query) throws AlgoliaException {
    return searchSynonyms(query, RequestOptions.empty);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @param requestOptions Options to pass to this request
   * @return the results of the query
   * @throws AlgoliaException
   */
  default SearchSynonymResult searchSynonyms(
      @Nonnull SynonymQuery query, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().searchSynonyms(getName(), query, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms)
      throws AlgoliaException {
    return batchSynonyms(
        synonyms, forwardToReplicas, replaceExistingSynonyms, RequestOptions.empty);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .batchSynonyms(
            getName(), synonyms, forwardToReplicas, replaceExistingSynonyms, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas)
      throws AlgoliaException {
    return batchSynonyms(synonyms, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSynonyms(synonyms, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(@Nonnull List<AbstractSynonym> synonyms) throws AlgoliaException {
    return batchSynonyms(synonyms, false, false);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSynonyms(synonyms, false, false, requestOptions);
  }
}

interface Rules<T> extends BaseSyncIndex<T> {

  /**
   * Saves/updates a query rule without replacing it and NOT forwarding it to the replicas
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveRule(@Nonnull String queryRuleID, @Nonnull Rule content)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, false);
  }

  /**
   * Saves/updates a query rule without replacing it and NOT forwarding it to the replicas
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveRule(
      @Nonnull String queryRuleID, @Nonnull Rule content, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, false, requestOptions);
  }

  /**
   * Saves/updates a queryRule without replacing
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveRule(
      @Nonnull String queryRuleID, @Nonnull Rule content, boolean forwardToReplicas)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Saves/updates a queryRule without replacing
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task saveRule(
      @Nonnull String queryRuleID,
      @Nonnull Rule content,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .saveRule(getName(), queryRuleID, content, forwardToReplicas, requestOptions);
  }

  /**
   * Get a rule by Id
   *
   * @param ruleId the id of the query rule
   * @return the associated task
   * @throws AlgoliaException
   */
  default Optional<Rule> getRule(@Nonnull String ruleId) throws AlgoliaException {
    return getRule(ruleId, RequestOptions.empty);
  }

  /**
   * Get a rule by Id
   *
   * @param ruleId the id of the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Optional<Rule> getRule(@Nonnull String ruleId, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getRule(getName(), ruleId, requestOptions);
  }

  /**
   * Deletes a query rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleId the id of the queryRule
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteRule(@Nonnull String ruleId) throws AlgoliaException {
    return deleteRule(ruleId, false, RequestOptions.empty);
  }

  /**
   * Deletes a query rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleId the id of the queryRule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteRule(@Nonnull String ruleId, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return deleteRule(ruleId, false, requestOptions);
  }

  /**
   * Deletes a query rule
   *
   * @param ruleId the id of the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task deleteRule(
      @Nonnull String ruleId, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteRule(getName(), ruleId, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all query Rules and NOT forwarding it to the replicas
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearRules() throws AlgoliaException {
    return clearRules(false);
  }

  /**
   * Clear all query Rules and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearRules(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return clearRules(false, requestOptions);
  }

  /**
   * Clears all Rules
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearRules(boolean forwardToReplicas) throws AlgoliaException {
    return clearRules(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all Rules
   *
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task clearRules(boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().clearRules(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @return the results of the query
   * @throws AlgoliaException
   */
  default SearchRuleResult searchRules(@Nonnull RuleQuery query) throws AlgoliaException {
    return searchRules(query, RequestOptions.empty);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @param requestOptions Options to pass to this request
   * @return the results of the query
   * @throws AlgoliaException
   */
  default SearchRuleResult searchRules(
      @Nonnull RuleQuery query, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().searchRules(getName(), query, requestOptions);
  }

  /**
   * Add or replace a list of query Rules
   *
   * @param rules List of query Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param clearExistingRules Replace the existing query Rules with this batch
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas, boolean clearExistingRules)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, clearExistingRules, RequestOptions.empty);
  }

  /**
   * Add or replace a list of query Rules
   *
   * @param rules List of query Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param clearExistingRules Replace the existing query Rules with this batch
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(
      @Nonnull List<Rule> rules,
      boolean forwardToReplicas,
      boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .batchRules(getName(), rules, forwardToReplicas, clearExistingRules, requestOptions);
  }

  /**
   * Add or replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, false);
  }

  /**
   * Add or replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(@Nonnull List<Rule> rules) throws AlgoliaException {
    return batchRules(rules, false, false);
  }

  /**
   * Add or replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   */
  default Task batchRules(@Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchRules(rules, false, false, requestOptions);
  }
}

interface Browse<T> extends BaseSyncIndex<T> {

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @return the iterator on top of this index
   * @throws AlgoliaException
   */
  default IndexIterable<T> browse(@Nonnull Query query) throws AlgoliaException {
    return browse(query, RequestOptions.empty);
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   * @throws AlgoliaException
   */
  default IndexIterable<T> browse(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return new IndexIterable<>(getApiClient(), getName(), query, requestOptions, getKlass());
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @return the iterator on top of this index
   * @throws AlgoliaException
   */
  default IndexIterable<T> browseFrom(@Nonnull Query query, @Nullable String cursor)
      throws AlgoliaException {
    return browseFrom(query, cursor, RequestOptions.empty);
  }

  /**
   * Browse all the content of this index
   *
   * @param query The query to use to browse
   * @param cursor the cursor to start from
   * @param requestOptions Options to pass to this request
   * @return the iterator on top of this index
   * @throws AlgoliaException
   */
  default IndexIterable<T> browseFrom(
      @Nonnull Query query, @Nullable String cursor, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return new IndexIterable<>(
        getApiClient(), getName(), query, requestOptions, cursor, getKlass());
  }
}

@SuppressWarnings("SameParameterValue")
interface DeleteByQuery<T> extends BaseSyncIndex<T> {

  /**
   * Delete records matching a query, with a batch size of 1000, internally uses browse
   *
   * @param query The query
   * @throws AlgoliaException
   * @deprecated, use deleteBy
   */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query) throws AlgoliaException {
    deleteByQuery(query, RequestOptions.empty);
  }

  /**
   * Delete records matching a query
   *
   * @param query The query
   * @throws AlgoliaException
   */
  default Task deleteBy(@Nonnull Query query) throws AlgoliaException {
    return deleteBy(query, RequestOptions.empty);
  }

  /**
   * Delete records matching a query, with a batch size of 1000, internally uses browse
   *
   * @param query The query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaException
   * @deprecated, use deleteBy
   */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().deleteByQuery(getName(), query, 1000, requestOptions);
  }

  /**
   * Delete records matching a query
   *
   * @param query The query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaException
   */
  default Task deleteBy(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteBy(getName(), query, requestOptions);
  }

  /**
   * Delete records matching a query, internally uses browse
   *
   * @param query The query
   * @param batchSize the size of the batches
   * @throws AlgoliaException
   * @deprecated use deleteBy
   */
  @Deprecated
  default void deleteByQuery(@Nonnull Query query, int batchSize) throws AlgoliaException {
    deleteByQuery(query, batchSize, RequestOptions.empty);
  }

  /**
   * Delete records matching a query, internally uses browse
   *
   * @param query The query
   * @param batchSize the size of the batches
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaException
   * @deprecated use deleteBy
   */
  @Deprecated
  default void deleteByQuery(
      @Nonnull Query query, int batchSize, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    getApiClient().deleteByQuery(getName(), query, batchSize, requestOptions);
  }
}

@SuppressWarnings("WeakerAccess")
public class Index<T>
    implements DeleteByQuery<T>,
        Browse<T>,
        Synonyms<T>,
        PartialUpdate<T>,
        SearchForFacet<T>,
        Key<T>,
        Settings<T>,
        Objects<T>,
        Tasks<T>,
        IndexCRUD<T>,
        Rules<T> {

  /** Index name */
  private final String name;

  /** The type of the objects in this Index */
  private final Class<T> klass;

  private final APIClient client;

  Index(String name, Class<T> klass, APIClient client) {
    this.name = name;
    this.klass = klass;
    this.client = client;
  }

  public String getName() {
    return name;
  }

  public Class<T> getKlass() {
    return klass;
  }

  @Override
  public APIClient getApiClient() {
    return client;
  }

  /**
   * Search in the index throws a {@link
   * com.algolia.search.exceptions.AlgoliaIndexNotFoundException} if the index does not exists
   *
   * @param query the query
   * @return the result of the search
   * @throws AlgoliaException
   */
  public SearchResult<T> search(@Nonnull Query query) throws AlgoliaException {
    return search(query, RequestOptions.empty);
  }

  /**
   * Search in the index throws a {@link
   * com.algolia.search.exceptions.AlgoliaIndexNotFoundException} if the index does not exists
   *
   * @param query the query
   * @return the result of the search
   * @throws AlgoliaException
   */
  public SearchResult<T> search(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return client.search(name, query, klass, requestOptions);
  }

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @return the associated task
   * @throws AlgoliaException
   * @see BatchOperation & subclasses
   */
  public TaskSingleIndex batch(@Nonnull List<BatchOperation> operations) throws AlgoliaException {
    return batch(operations, RequestOptions.empty);
  }

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @throws AlgoliaException
   * @see BatchOperation & subclasses
   */
  public TaskSingleIndex batch(
      @Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return client.batch(name, operations, requestOptions);
  }

  @Override
  public String toString() {
    return "Index{" + "name='" + name + '\'' + ", klass=" + klass + '}';
  }

  @SuppressWarnings("unused")
  public static class Attributes {
    private String name;
    private String createdAt;
    private String updatedAt;
    private Long entries;
    private Long dataSize;
    private Long fileSize;
    private Long lastBuildTimeS;
    private Long numberOfPendingTasks;
    private Boolean pendingTask;

    public String getName() {
      return name;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public String getUpdatedAt() {
      return updatedAt;
    }

    public Long getEntries() {
      return entries;
    }

    public Long getDataSize() {
      return dataSize;
    }

    public Long getFileSize() {
      return fileSize;
    }

    public Long getLastBuildTimeS() {
      return lastBuildTimeS;
    }

    public Long getNumberOfPendingTasks() {
      return numberOfPendingTasks;
    }

    public Boolean getPendingTask() {
      return pendingTask;
    }
  }
}

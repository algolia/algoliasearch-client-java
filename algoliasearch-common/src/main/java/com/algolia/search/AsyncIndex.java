package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.objects.tasks.async.AsyncTaskIndexing;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.responses.*;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

interface BaseAsyncIndex<T> extends AbstractIndex<T> {

  AsyncAPIClient getApiClient();

}

interface AsyncIndexCRUD<T> extends BaseAsyncIndex<T> {

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist)
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> moveTo(@Nonnull String dstIndexName) {
    return moveTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName   the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> moveTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions) {
    return getApiClient().moveIndex(getName(), dstIndexName, requestOptions);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName) {
    return copyTo(dstIndexName, RequestOptions.empty);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName   the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @param requestOptions Options to pass to this request
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName, @Nonnull RequestOptions requestOptions) {
    return getApiClient().copyIndex(getName(), dstIndexName, requestOptions);
  }

  /**
   * Deletes the index
   *
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> delete() {
    return delete(RequestOptions.empty);
  }

  /**
   * Deletes the index
   *
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> delete(@Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteIndex(getName(), requestOptions);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> clear() {
    return clear(RequestOptions.empty);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> clear(@Nonnull RequestOptions requestOptions) {
    return getApiClient().clearIndex(getName(), requestOptions);
  }

}

interface AsyncTasks<T> extends BaseAsyncIndex<T> {

  /**
   * Wait for the completion of a task
   *
   * @param task       task to wait for
   * @param timeToWait the time to wait in milliseconds
   */
  default void waitTask(@Nonnull AsyncTask task, long timeToWait) {
    waitTask(task, timeToWait);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task           task to wait for
   * @param timeToWait     the time to wait in milliseconds
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull AsyncTask task, long timeToWait, @Nonnull RequestOptions requestOptions) {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    getApiClient().waitTask(task, timeToWait, requestOptions);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   */
  default void waitTask(@Nonnull AsyncTask task) {
    waitTask(task, RequestOptions.empty);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task           task to wait for
   * @param requestOptions Options to pass to this request
   */
  default void waitTask(@Nonnull AsyncTask task, @Nonnull RequestOptions requestOptions) {
    getApiClient().waitTask(task, 100, requestOptions);
  }

}

interface AsyncObjects<T> extends BaseAsyncIndex<T> {

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object) {
    return addObject(object, RequestOptions.empty);
  }

  /**
   * Add an object in this index
   *
   * @param object         object to add
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addObject(getName(), object, requestOptions);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object
   *                 (if this objectID already exist the old object will be overridden)
   * @param object   object to add
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull String objectID, @Nonnull T object) {
    return addObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID       the objectID associated to this object
   *                       (if this objectID already exist the old object will be overridden)
   * @param object         object to add
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects) {
    return addObjects(objects, RequestOptions.empty);
  }

  /**
   * Add several objects
   *
   * @param objects        objects to add
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) {
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
   * @param objectID       the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return The object
   */
  default CompletableFuture<Optional<T>> getObject(@Nonnull String objectID, @Nonnull RequestOptions requestOptions) {
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
   * @param objectIDs      the list of unique identifier of objects to retrieve
   * @param requestOptions Options to pass to this request
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getObjects(getName(), objectIDs, getKlass(), requestOptions);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs            the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve) {
    return getObjects(objectIDs, attributesToRetrieve, RequestOptions.empty);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs            the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @param requestOptions       Options to pass to this request
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getObjects(getName(), objectIDs, attributesToRetrieve, getKlass(), requestOptions);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object   the object to update
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object) {
    return saveObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Override the content of object
   *
   * @param objectID       the unique identifier of the object to retrieve
   * @param object         the object to update
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects) {
    return saveObjects(objects, RequestOptions.empty);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects        the list objects to update
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveObjects(getName(), objects, requestOptions);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) {
    return deleteObject(objectID, RequestOptions.empty);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID       the unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteObject(getName(), objectID, requestOptions);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs) {
    return deleteObjects(objectIDs, RequestOptions.empty);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs      the list of unique identifier of the object to retrieve
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteObjects(getName(), objectIDs, requestOptions);
  }

}

interface AsyncSettings<T> extends BaseAsyncIndex<T> {

  /**
   * Get settings of this index
   *
   * @return the settings
   */
  default CompletableFuture<IndexSettings> getSettings() {
    return getSettings(RequestOptions.empty);
  }

  /**
   * Get settings of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the settings
   */
  default CompletableFuture<IndexSettings> getSettings(@Nonnull RequestOptions requestOptions) {
    return getApiClient().getSettings(getName(), requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings) {
    return setSettings(settings, false);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings       the settings to set
   * @param requestOptions Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions) {
    return setSettings(settings, false, requestOptions);
  }

  /**
   * Set settings of this index
   *
   * @param settings          the settings to set
   * @param forwardToReplicas should these updates be forwarded to the replicas
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {
    return setSettings(settings, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Set settings of this index
   *
   * @param settings          the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @param requestOptions    Options to pass to this request
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().setSettings(getName(), settings, forwardToReplicas, requestOptions);
  }

}

interface AsyncKey<T> extends BaseAsyncIndex<T> {

  /**
   * Deprecated: use listApiKeys
   */
  @Deprecated
  default CompletableFuture<List<ApiKey>> listKeys() {
    return listApiKeys();
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   */
  default CompletableFuture<List<ApiKey>> listApiKeys() {
    return listApiKeys(RequestOptions.empty);
  }

  /**
   * List keys of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the list of keys
   */
  default CompletableFuture<List<ApiKey>> listApiKeys(@Nonnull RequestOptions requestOptions) {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /**
   * Deprecated: use getApiKey
   */
  @Deprecated
  default CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) {
    return getApiKey(key);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   */
  default CompletableFuture<Optional<ApiKey>> getApiKey(@Nonnull String key) {
    return getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get a key by name from this index
   *
   * @param key            the key name
   * @param requestOptions Options to pass to this request
   * @return the key
   */
  default CompletableFuture<Optional<ApiKey>> getApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /**
   * Deprecated: use deleteApiKey
   */
  @Deprecated
  default CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) {
    return deleteApiKey(key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   */
  default CompletableFuture<DeleteKey> deleteApiKey(@Nonnull String key) {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key            the key name
   * @param requestOptions Options to pass to this request
   * @return the deleted key
   */
  default CompletableFuture<DeleteKey> deleteApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /**
   * Deprecated: use addApiKey
   */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) {
    return addApiKey(key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   */
  default CompletableFuture<CreateUpdateKey> addApiKey(@Nonnull ApiKey key) {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Add a key to this index
   *
   * @param key            the key
   * @param requestOptions Options to pass to this request
   * @return the created key
   */
  default CompletableFuture<CreateUpdateKey> addApiKey(@Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /**
   * Deprecated: use updateApiKey
   */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> updateKey(@Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key     the key to update
   * @return the updated key
   */
  default CompletableFuture<CreateUpdateKey> updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName        the key name
   * @param key            the key to update
   * @param requestOptions Options to pass to this request
   * @return the updated key
   */
  default CompletableFuture<CreateUpdateKey> updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().updateKey(getName(), keyName, key, requestOptions);
  }

}

interface AsyncSearchForFacet<T> extends BaseAsyncIndex<T> {

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query      the query (not required)
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return searchForFacetValues(facetName, facetQuery, query, RequestOptions.empty);
  }

  /**
   * Search in a facet
   *
   * @param facetName      The name of the facet to search in
   * @param facetQuery     The search query for this facet
   * @param query          the query (not required)
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, Query query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().searchForFacetValues(getName(), facetName, facetQuery, query, requestOptions);
  }

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  /**
   * Search in a facet
   *
   * @param facetName      The name of the facet to search in
   * @param facetQuery     The search query for this facet
   * @param requestOptions Options to pass to this request
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, @Nonnull RequestOptions requestOptions) {
    return this.searchForFacetValues(facetName, facetQuery, null, requestOptions);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchForFacetValues(facetName, facetQuery, query);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null, RequestOptions.empty);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchForFacetValues(facetName, facetQuery, query);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery);
  }

}

interface AsyncPartialUpdate<T> extends BaseAsyncIndex<T> {

  /**
   * Partially update an object
   *
   * @param objectID the ID of object to update
   * @param object   the object to update
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull String objectID, @Nonnull Object object) {
    return partialUpdateObject(objectID, object, RequestOptions.empty);
  }

  /**
   * Partially update an object
   *
   * @param objectID       the ID of object to update
   * @param object         the object to update
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull String objectID, @Nonnull Object object, @Nonnull RequestOptions requestOptions) {
    return getApiClient().partialUpdateObject(getName(), objectID, object, requestOptions);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(@Nonnull List<Object> objects) {
    return partialUpdateObjects(objects, RequestOptions.empty);
  }

  /**
   * Partially update a objects
   *
   * @param objects        the list of objects to update (with an objectID)
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(@Nonnull List<Object> objects, @Nonnull RequestOptions requestOptions) {
    return getApiClient().partialUpdateObjects(getName(), objects, requestOptions);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation the operation to perform on this object
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation) {
    return partialUpdateObject(operation, true);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation      the operation to perform on this object
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, @Nonnull RequestOptions requestOptions) {
    return partialUpdateObject(operation, true, requestOptions);
  }

  /**
   * Partially update an object
   *
   * @param operation         the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists) {
    return partialUpdateObject(operation, createIfNotExists, RequestOptions.empty);
  }

  /**
   * Partially update an object
   *
   * @param operation         the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists, @Nonnull RequestOptions requestOptions) {
    return getApiClient().partialUpdateObject(getName(), operation, createIfNotExists, requestOptions);
  }

}

interface AsyncSynonyms<T> extends BaseAsyncIndex<T> {

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @param content   the synonym
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content) {
    return saveSynonym(synonymID, content, false);
  }

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID      the id of the synonym
   * @param content        the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, @Nonnull RequestOptions requestOptions) {
    return saveSynonym(synonymID, content, false, requestOptions);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID         the id of the synonym
   * @param content           the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas) {
    return saveSynonym(synonymID, content, forwardToReplicas, false);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID         the id of the synonym
   * @param content           the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return saveSynonym(synonymID, content, forwardToReplicas, false, requestOptions);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID               the id of the synonym
   * @param content                 the synonym
   * @param forwardToReplicas       should this request be forwarded to replicas
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, boolean replaceExistingSynonyms) {
    return saveSynonym(synonymID, content, forwardToReplicas, replaceExistingSynonyms, RequestOptions.empty);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID               the id of the synonym
   * @param content                 the synonym
   * @param forwardToReplicas       should this request be forwarded to slaves
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @param requestOptions          Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, boolean replaceExistingSynonyms, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveSynonym(getName(), synonymID, content, forwardToReplicas, replaceExistingSynonyms, requestOptions);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  default CompletableFuture<Optional<AbstractSynonym>> getSynonym(@Nonnull String synonymID) {
    return getSynonym(synonymID, RequestOptions.empty);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID      the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<Optional<AbstractSynonym>> getSynonym(@Nonnull String synonymID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getSynonym(getName(), synonymID, requestOptions);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID) {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the slaves
   *
   * @param synonymID      the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, @Nonnull RequestOptions requestOptions) {
    return deleteSynonym(synonymID, false, requestOptions);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID         the id of the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas) {
    return deleteSynonym(synonymID, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID         the id of the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteSynonym(getName(), synonymID, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the replicas
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms() {
    return clearSynonyms(false);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the slaves
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms(@Nonnull RequestOptions requestOptions) {
    return clearSynonyms(false, requestOptions);
  }

  /**
   * Clears all synonyms
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms(boolean forwardToReplicas) {
    return clearSynonyms(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all synonyms
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms(boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().clearSynonyms(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   */
  default CompletableFuture<SearchSynonymResult> searchSynonyms(@Nonnull SynonymQuery query) {
    return searchSynonyms(query, RequestOptions.empty);
  }

  /**
   * Search for synonyms
   *
   * @param query          the query
   * @param requestOptions Options to pass to this request
   * @return the results of the query
   */
  default CompletableFuture<SearchSynonymResult> searchSynonyms(@Nonnull SynonymQuery query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().searchSynonyms(getName(), query, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms                List of synonyms
   * @param forwardToReplicas       Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas, boolean replaceExistingSynonyms) {
    return batchSynonyms(synonyms, forwardToReplicas, replaceExistingSynonyms, RequestOptions.empty);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms                List of synonyms
   * @param forwardToReplicas       Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @param requestOptions          Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas, boolean replaceExistingSynonyms, @Nonnull RequestOptions requestOptions) {
    return getApiClient().batchSynonyms(getName(), synonyms, forwardToReplicas, replaceExistingSynonyms, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms          List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas) {
    return batchSynonyms(synonyms, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms          List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return batchSynonyms(synonyms, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms) {
    return batchSynonyms(synonyms, false, false);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms       List of synonyms
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, @Nonnull RequestOptions requestOptions) {
    return batchSynonyms(synonyms, false, false, requestOptions);
  }

}

interface AsyncRules<T> extends BaseAsyncIndex<T> {

  /**
   * Saves/updates a rule without replacing it and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @param rule   the query rule
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(@Nonnull String ruleID, @Nonnull Rule rule) {
    return saveRule(ruleID, rule, false);
  }

  /**
   * Saves/updates a rule without replacing it and NOT forwarding it to the replicas
   *
   * @param ruleID         the id of the query rule
   * @param rule           the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(@Nonnull String ruleID, @Nonnull Rule rule, @Nonnull RequestOptions requestOptions) {
    return saveRule(ruleID, rule, false, requestOptions);
  }

  /**
   * Saves/updates a rule
   *
   * @param ruleID            the id of the query rule
   * @param rule              the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(@Nonnull String ruleID, @Nonnull Rule rule, boolean forwardToReplicas) {
    return saveRule(ruleID, rule, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Saves/updates a rule
   *
   * @param ruleID            the id of the query rule
   * @param rule              the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(@Nonnull String ruleID, @Nonnull Rule rule, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveRule(getName(), ruleID, rule, forwardToReplicas, requestOptions);
  }

  /**
   * Get a rule by ID
   *
   * @param ruleID the id of the rule
   * @return the associated task
   */
  default CompletableFuture<Optional<Rule>> getRule(@Nonnull String ruleID) {
    return getRule(ruleID, RequestOptions.empty);
  }

  /**
   * Get a rule by ID
   *
   * @param ruleID         the id of the rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<Optional<Rule>> getRule(@Nonnull String ruleID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getRule(getName(), ruleID, requestOptions);
  }

  /**
   * Deletes a rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(@Nonnull String ruleID) {
    return deleteRule(ruleID, false);
  }

  /**
   * Deletes a rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleID         the id of the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(@Nonnull String ruleID, @Nonnull RequestOptions requestOptions) {
    return deleteRule(ruleID, false, requestOptions);
  }

  /**
   * Deletes a rule
   *
   * @param ruleID            the id of the rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(@Nonnull String ruleID, boolean forwardToReplicas) {
    return deleteRule(ruleID, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Deletes a rule
   *
   * @param ruleID            the id of the rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(@Nonnull String ruleID, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteRule(getName(), ruleID, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all Rules and NOT forwarding it to the replicas
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules() {
    return clearRules(false);
  }

  /**
   * Clear all Rules and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(@Nonnull RequestOptions requestOptions) {
    return clearRules(false, requestOptions);
  }

  /**
   * Clears all Rules
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(boolean forwardToReplicas) {
    return clearRules(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all Rules
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().clearRules(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @return the results of the query
   */
  default CompletableFuture<SearchRuleResult> searchRules(@Nonnull RuleQuery query) {
    return searchRules(query, RequestOptions.empty);
  }

  /**
   * Search for Rules
   *
   * @param requestOptions Options to pass to this request
   * @param query          the query
   * @return the results of the query
   */
  default CompletableFuture<SearchRuleResult> searchRules(@Nonnull RuleQuery query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().searchRules(getName(), query, requestOptions);
  }

  /**
   * Add or replace a list of Rules
   *
   * @param rules              List of Rules
   * @param forwardToReplicas  Forward the operation to the replicas indices
   * @param clearExistingRules Replace the existing Rules with this batch
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas, boolean clearExistingRules) {
    return batchRules(rules, forwardToReplicas, clearExistingRules, RequestOptions.empty);
  }

  /**
   * Add or replace a list of Rules
   *
   * @param rules              List of Rules
   * @param forwardToReplicas  Forward the operation to the replicas indices
   * @param clearExistingRules Replace the existing Rules with this batch
   * @param requestOptions     Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas, boolean clearExistingRules, @Nonnull RequestOptions requestOptions) {
    return getApiClient().batchRules(getName(), rules, forwardToReplicas, clearExistingRules, requestOptions);
  }

  /**
   * Add or Replace a list of Rules, no replacement
   *
   * @param rules             List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas) {
    return batchRules(rules, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of Rules, no replacement
   *
   * @param rules             List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions    Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return batchRules(rules, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or Replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules) {
    return batchRules(rules, false, false);
  }

  /**
   * Add or Replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules          List of Rules
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions) {
    return batchRules(rules, false, false, requestOptions);
  }

}

interface AsyncDeleteByQuery<T> extends BaseAsyncIndex<T> {

  /**
   * Delete records matching a query
   *
   * @param query The query
   */
  default CompletableFuture<AsyncTask> deleteBy(@Nonnull Query query) {
    return deleteBy(query, RequestOptions.empty);
  }

  /**
   * Delete records matching a query
   * Deprecated, use deleteBy
   *
   * @param query          The query
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<AsyncTask> deleteBy(@Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteBy(getName(), query, requestOptions);
  }

}

@SuppressWarnings("WeakerAccess")
public class AsyncIndex<T> implements
  AsyncIndexCRUD<T>,
  AsyncTasks<T>,
  AsyncObjects<T>,
  AsyncSettings<T>,
  AsyncKey<T>,
  AsyncSearchForFacet<T>,
  AsyncPartialUpdate<T>,
  AsyncSynonyms<T>,
  AsyncRules<T>,
  AsyncDeleteByQuery<T> {

  /**
   * Index name
   */
  private final String name;

  /**
   * The type of the objects in this Index
   */
  private final Class<T> klass;

  private final AsyncAPIClient client;

  AsyncIndex(String name, Class<T> klass, AsyncAPIClient client) {
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

  public AsyncAPIClient getApiClient() {
    return client;
  }

  /**
   * Search in the index
   *
   * @param query the query
   * @return the result of the search, or a failed Future if the index does not exists
   */
  public CompletableFuture<SearchResult<T>> search(@Nonnull Query query) {
    return search(query, RequestOptions.empty);
  }

  /**
   * Search in the index
   *
   * @param query          the query
   * @param requestOptions Options to pass to this request
   * @return the result of the search, or a failed Future if the index does not exists
   */
  public CompletableFuture<SearchResult<T>> search(@Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return client.search(name, query, klass, requestOptions);
  }

  /**
   * Custom batch
   * <p>
   * All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> batch(@Nonnull List<BatchOperation> operations) {
    return batch(operations, RequestOptions.empty);
  }

  /**
   * Custom batch
   * <p>
   * All operations must have index name set to <code>null</code>
   *
   * @param operations     the list of operations to perform on this index
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> batch(@Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions) {
    return client.batch(name, operations, requestOptions);
  }

  @Override
  public String toString() {
    return "AsyncIndex{" +
      "name='" + name + '\'' +
      ", klass=" + klass +
      '}';
  }
}
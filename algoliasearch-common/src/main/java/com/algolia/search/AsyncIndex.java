package com.algolia.search;

import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.ApiKey;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.objects.tasks.async.AsyncTaskIndexing;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
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
    return getApiClient().moveIndex(getName(), dstIndexName);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @return The task associated
   */
  default CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName) {
    return getApiClient().copyIndex(getName(), dstIndexName);
  }

  /**
   * Deletes the index
   *
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> delete() {
    return getApiClient().deleteIndex(getName());
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> clear() {
    return getApiClient().clearIndex(getName());
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
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);
    getApiClient().waitTask(task, timeToWait);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   */
  default void waitTask(@Nonnull AsyncTask task) {
    getApiClient().waitTask(task, 100);
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
    return getApiClient().addObject(getName(), object);
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
    return getApiClient().addObject(getName(), objectID, object);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects) {
    return getApiClient().addObjects(getName(), objects);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   */
  default CompletableFuture<Optional<T>> getObject(@Nonnull String objectID) {
    return getApiClient().getObject(getName(), objectID, getKlass());
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs) {
    return getApiClient().getObjects(getName(), objectIDs, getKlass());
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs            the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   */
  default CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve) {
    return getApiClient().getObjects(getName(), objectIDs, attributesToRetrieve, getKlass());
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object   the object to update
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object) {
    return getApiClient().saveObject(getName(), objectID, object);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects) {
    return getApiClient().saveObjects(getName(), objects);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) {
    return getApiClient().deleteObject(getName(), objectID);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs) {
    return getApiClient().deleteObjects(getName(), objectIDs);
  }

}

interface AsyncSettings<T> extends BaseAsyncIndex<T> {

  /**
   * Get settings of this index
   *
   * @return the settings
   */
  default CompletableFuture<IndexSettings> getSettings() {
    return getApiClient().getSettings(getName());
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings) {
    return setSettings(settings, false);
  }

  /**
   * Set settings of this index
   *
   * @param settings          the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @return the related AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {
    return getApiClient().setSettings(getName(), settings, forwardToReplicas);
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
    return getApiClient().listKeys(getName());
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
    return getApiClient().getKey(getName(), key);
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
    return getApiClient().deleteKey(getName(), key);
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
    return getApiClient().addKey(getName(), key);
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
    return getApiClient().updateKey(getName(), keyName, key);
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
    return getApiClient().searchForFacetValues(getName(), facetName, facetQuery, query);
  }

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   */
  default CompletableFuture<SearchFacetResult> searchForFacetValues(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchForFacetValues(facetName, facetQuery, query);
  }

  @Deprecated
  default CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchForFacetValues(facetName, facetQuery, null);
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
    return getApiClient().partialUpdateObject(getName(), objectID, object);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(@Nonnull List<Object> objects) {
    return getApiClient().partialUpdateObjects(getName(), objects);
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
   * Partially update an object
   *
   * @param operation         the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  default CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists) {
    return getApiClient().partialUpdateObject(getName(), operation, createIfNotExists);
  }

}

interface AsyncSynonyms<T> extends BaseAsyncIndex<T> {

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @param content   the synonym
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content) {
    return saveSynonym(synonymID, content, false);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID         the id of the synonym
   * @param content           the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas) {
    return saveSynonym(synonymID, content, forwardToReplicas, false);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID               the id of the synonym
   * @param content                 the synonym
   * @param forwardToReplicas       should this request be forwarded to slaves
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, boolean replaceExistingSynonyms) {
    return getApiClient().saveSynonym(getName(), synonymID, content, forwardToReplicas, replaceExistingSynonyms);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  default CompletableFuture<Optional<AbstractSynonym>> getSynonym(@Nonnull String synonymID) {
    return getApiClient().getSynonym(getName(), synonymID);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID) {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID         the id of the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas) {
    return getApiClient().deleteSynonym(getName(), synonymID, forwardToReplicas);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the slaves
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms() {
    return clearSynonyms(false);
  }

  /**
   * Clears all synonyms
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearSynonyms(boolean forwardToReplicas) {
    return getApiClient().clearSynonyms(getName(), forwardToReplicas);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   */
  default CompletableFuture<SearchSynonymResult> searchSynonyms(@Nonnull SynonymQuery query) {
    return getApiClient().searchSynonyms(getName(), query);
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
    return getApiClient().batchSynonyms(getName(), synonyms, forwardToReplicas, replaceExistingSynonyms);
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
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms) {
    return batchSynonyms(synonyms, false, false);
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
  AsyncSynonyms<T> {

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
    return client.search(name, query, klass);
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
    return client.batch(name, operations);
  }

}
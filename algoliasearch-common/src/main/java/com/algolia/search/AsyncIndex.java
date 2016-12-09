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

@SuppressWarnings("WeakerAccess")
public class AsyncIndex<T> extends AbstractIndex {

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

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object) {
    return client.addObject(name, object);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object
   *                 (if this objectID already exist the old object will be overridden)
   * @param object   object to add
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull String objectID, @Nonnull T object) {
    return client.addObject(name, objectID, object);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects) {
    return client.addObjects(name, objects);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   */
  public CompletableFuture<Optional<T>> getObject(@Nonnull String objectID) {
    return client.getObject(name, objectID, klass);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   */
  public CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs) {
    return client.getObjects(name, objectIDs, klass);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs            the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   */
  public CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve) {
    return client.getObjects(name, objectIDs, attributesToRetrieve, klass);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task       task to wait for
   * @param timeToWait the time to wait in milliseconds
   */
  public void waitTask(@Nonnull AsyncTask task, long timeToWait) {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);

    client.waitTask(task, timeToWait);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   */
  public void waitTask(@Nonnull AsyncTask task) {
    client.waitTask(task, 100);
  }

  /**
   * Deletes the index
   *
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> delete() {
    return client.deleteIndex(name);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> clear() {
    return client.clearIndex(name);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object   the object to update
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object) {
    return client.saveObject(name, objectID, object);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects) {
    return client.saveObjects(name, objects);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) {
    return client.deleteObject(name, objectID);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs) {
    return client.deleteObjects(name, objectIDs);
  }

  /**
   * Get settings of this index
   *
   * @return the settings
   */
  public CompletableFuture<IndexSettings> getSettings() {
    return client.getSettings(name);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings) {
    return setSettings(settings, false);
  }

  /**
   * Set settings of this index
   *
   * @param settings          the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @return the related AsyncTask
   */
  public CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {
    return client.setSettings(name, settings, forwardToReplicas);
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   */
  public CompletableFuture<List<ApiKey>> listKeys() {
    return client.listKeys(name);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   */
  public CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) {
    return client.getKey(name, key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   */
  public CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) {
    return client.deleteKey(name, key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   */
  public CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) {
    return client.addKey(name, key);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key     the key to update
   * @return the updated key
   */
  public CompletableFuture<CreateUpdateKey> updateKey(@Nonnull String keyName, @Nonnull ApiKey key) {
    return client.updateKey(name, keyName, key);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist)
   * @return The task associated
   */
  public CompletableFuture<AsyncTask> moveTo(@Nonnull String dstIndexName) {
    return client.moveIndex(name, dstIndexName);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @return The task associated
   */
  public CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName) {
    return client.copyIndex(name, dstIndexName);
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
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query      the query (not required)
   * @return the result of the search
   */
  public CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return client.searchFacet(name, facetName, facetQuery, query);
  }

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   */
  public CompletableFuture<SearchFacetResult> searchInFacetValues(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchInFacetValues(facetName, facetQuery, null);
  }

  @Deprecated
  public CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery, Query query) {
    return this.searchInFacetValues(facetName, facetQuery, query);
  }

  @Deprecated
  public CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery) {
    return this.searchInFacetValues(facetName, facetQuery);
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

  /**
   * Partially update an object
   *
   * @param objectID the ID of object to update
   * @param object   the object to update
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull String objectID, @Nonnull Object object) {
    return client.partialUpdateObject(name, objectID, object);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(@Nonnull List<Object> objects) {
    return client.partialUpdateObjects(name, objects);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation the operation to perform on this object
   * @return the associated task
   * @see PartialUpdateOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation) {
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
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists) {
    return client.partialUpdateObject(name, operation, createIfNotExists);
  }

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @param content   the synonym
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content) {
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
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas) {
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
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, boolean replaceExistingSynonyms) {
    return client.saveSynonym(name, synonymID, content, forwardToReplicas, replaceExistingSynonyms);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  public CompletableFuture<Optional<AbstractSynonym>> getSynonym(@Nonnull String synonymID) {
    return client.getSynonym(name, synonymID);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID) {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID         the id of the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas) {
    return client.deleteSynonym(name, synonymID, forwardToReplicas);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the slaves
   *
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> clearSynonyms() {
    return clearSynonyms(false);
  }

  /**
   * Clears all synonyms
   *
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> clearSynonyms(boolean forwardToReplicas) {
    return client.clearSynonyms(name, forwardToReplicas);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   */
  public CompletableFuture<SearchSynonymResult> searchSynonyms(@Nonnull SynonymQuery query) {
    return client.searchSynonyms(name, query);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms                List of synonyms
   * @param forwardToReplicas       Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas, boolean replaceExistingSynonyms) {
    return client.batchSynonyms(name, synonyms, forwardToReplicas, replaceExistingSynonyms);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms          List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas) {
    return batchSynonyms(synonyms, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @return the associated task
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms) {
    return batchSynonyms(synonyms, false, false);
  }

}

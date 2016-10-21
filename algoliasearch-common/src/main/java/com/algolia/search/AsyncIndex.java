package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
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
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull T object) throws AlgoliaException {
    return client.addObject(name, object);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object
   *                 (if this objectID already exist the old object will be overridden)
   * @param object   object to add
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskIndexing> addObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return client.addObject(name, objectID, object);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskSingleIndex> addObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return client.addObjects(name, objects);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   * @throws AlgoliaException
   */
  public CompletableFuture<Optional<T>> getObject(@Nonnull String objectID) throws AlgoliaException {
    return client.getObject(name, objectID, klass);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   * @throws AlgoliaException
   */
  public CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return client.getObjects(name, objectIDs, klass);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs            the list of unique identifier of objects to retrieve
   * @param attributesToRetrieve the list of attributes to retrieve for these objects
   * @return the list of objects
   * @throws AlgoliaException
   */
  public CompletableFuture<List<T>> getObjects(@Nonnull List<String> objectIDs, @Nonnull List<String> attributesToRetrieve) throws AlgoliaException {
    return client.getObjects(name, objectIDs, attributesToRetrieve, klass);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task       task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @throws AlgoliaException
   */
  public void waitTask(@Nonnull AsyncTask task, long timeToWait) throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);

    client.waitTask(task, timeToWait);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @throws AlgoliaException
   */
  public void waitTask(@Nonnull AsyncTask task) throws AlgoliaException {
    client.waitTask(task, 100);
  }

  /**
   * Deletes the index
   *
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> delete() throws AlgoliaException {
    return client.deleteIndex(name);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> clear() throws AlgoliaException {
    return client.clearIndex(name);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object   the object to update
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> saveObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return client.saveObject(name, objectID, object);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskSingleIndex> saveObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return client.saveObjects(name, objects);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> deleteObject(@Nonnull String objectID) throws AlgoliaException {
    return client.deleteObject(name, objectID);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskSingleIndex> deleteObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return client.deleteObjects(name, objectIDs);
  }

  /**
   * Get settings of this index
   *
   * @return the settings
   * @throws AlgoliaException
   */
  public CompletableFuture<IndexSettings> getSettings() throws AlgoliaException {
    return client.getSettings(name);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings) throws AlgoliaException {
    return setSettings(settings, false);
  }

  /**
   * Set settings of this index
   *
   * @param settings        the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @return the related AsyncTask
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) throws AlgoliaException {
    return client.setSettings(name, settings, forwardToReplicas);
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   * @throws AlgoliaException
   */
  public CompletableFuture<List<ApiKey>> listKeys() throws AlgoliaException {
    return client.listKeys(name);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   * @throws AlgoliaException
   */
  public CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) throws AlgoliaException {
    return client.getKey(name, key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   * @throws AlgoliaException
   */
  public CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) throws AlgoliaException {
    return client.deleteKey(name, key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   * @throws AlgoliaException
   */
  public CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return client.addKey(name, key);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key     the key to update
   * @return the updated key
   * @throws AlgoliaException
   */
  public CompletableFuture<CreateUpdateKey> updateKey(@Nonnull String keyName, @Nonnull ApiKey key) throws AlgoliaException {
    return client.updateKey(name, keyName, key);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> moveTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return client.moveIndex(name, dstIndexName);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> copyTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return client.copyIndex(name, dstIndexName);
  }

  /**
   * Search in the index
   *
   * @param query the query
   * @return the result of the search, or a failed Future if the index does not exists
   * @throws AlgoliaException
   */
  public CompletableFuture<SearchResult<T>> search(@Nonnull Query query) throws AlgoliaException {
    return client.search(name, query, klass);
  }

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @param query      the query (not required)
   * @return the result of the search
   * @throws AlgoliaException
   */
  public CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery, Query query) throws AlgoliaException {
    return client.searchFacet(name, facetName, facetQuery, query);
  }

  /**
   * Search in a facet
   *
   * @param facetName  The name of the facet to search in
   * @param facetQuery The search query for this facet
   * @return the result of the search
   * @throws AlgoliaException
   */
  public CompletableFuture<SearchFacetResult> searchFacet(@Nonnull String facetName, @Nonnull String facetQuery) throws AlgoliaException {
    return this.searchFacet(facetName, facetQuery, null);
  }

  /**
   * Custom batch
   * <p>
   * All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @return the associated task
   * @throws AlgoliaException
   * @see BatchOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> batch(@Nonnull List<BatchOperation> operations) throws AlgoliaException {
    return client.batch(name, operations);
  }

  /**
   * Partially update an object
   *
   * @param objectID the ID of object to update
   * @param object   the object to update
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull String objectID, @Nonnull Object object) throws AlgoliaException {
    return client.partialUpdateObject(name, objectID, object);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObjects(@Nonnull List<Object> objects) throws AlgoliaException {
    return client.partialUpdateObjects(name, objects);
  }

  /**
   * Partially update an object, create the object if it does not exist
   *
   * @param operation the operation to perform on this object
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation) throws AlgoliaException {
    return partialUpdateObject(operation, true);
  }

  /**
   * Partially update an object
   *
   * @param operation         the operation to perform on this object
   * @param createIfNotExists should the object be created or not
   * @return the associated task
   * @throws AlgoliaException
   * @see PartialUpdateOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists) throws AlgoliaException {
    return client.partialUpdateObject(name, operation, createIfNotExists);
  }

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @param content   the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content) throws AlgoliaException {
    return saveSynonym(synonymID, content, false);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID       the id of the synonym
   * @param content         the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas) throws AlgoliaException {
    return saveSynonym(synonymID, content, forwardToReplicas, false);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID               the id of the synonym
   * @param content                 the synonym
   * @param forwardToReplicas         should this request be forwarded to slaves
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas, boolean replaceExistingSynonyms) throws AlgoliaException {
    return client.saveSynonym(name, synonymID, content, forwardToReplicas, replaceExistingSynonyms);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<Optional<AbstractSynonym>> getSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return client.getSynonym(name, synonymID);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID       the id of the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> deleteSynonym(@Nonnull String synonymID, boolean forwardToReplicas) throws AlgoliaException {
    return client.deleteSynonym(name, synonymID, forwardToReplicas);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the slaves
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> clearSynonyms() throws AlgoliaException {
    return clearSynonyms(false);
  }

  /**
   * Clears all synonyms
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> clearSynonyms(boolean forwardToReplicas) throws AlgoliaException {
    return client.clearSynonyms(name, forwardToReplicas);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   * @throws AlgoliaException
   */
  public CompletableFuture<SearchSynonymResult> searchSynonyms(@Nonnull SynonymQuery query) throws AlgoliaException {
    return client.searchSynonyms(name, query);
  }

  /**
   * Add or Replace a list of synonyms
   *
   * @param synonyms                List of synonyms
   * @param forwardToReplicas         Forward the operation to the slave indices
   * @param replaceExistingSynonyms Replace the existing synonyms with this batch
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas, boolean replaceExistingSynonyms) throws AlgoliaException {
    return client.batchSynonyms(name, synonyms, forwardToReplicas, replaceExistingSynonyms);
  }

  /**
   * Add or Replace a list of synonyms, no replacement
   *
   * @param synonyms        List of synonyms
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas) throws AlgoliaException {
    return batchSynonyms(synonyms, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of synonyms, no forward to slaves, and no replacement
   *
   * @param synonyms List of synonyms
   * @return the associated task
   * @throws AlgoliaException
   */
  public CompletableFuture<AsyncTask> batchSynonyms(@Nonnull List<AbstractSynonym> synonyms) throws AlgoliaException {
    return batchSynonyms(synonyms, false, false);
  }

}

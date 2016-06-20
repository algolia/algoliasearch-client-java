package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.partial_update.PartialUpdateOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.*;
import com.algolia.search.responses.CreateUpdateKey;
import com.algolia.search.responses.DeleteKey;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchSynonymResult;
import com.google.api.client.util.Preconditions;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class Index<T> {

  /**
   * Index name
   */
  private final String name;

  /**
   * The type of the objects in this Index
   */
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

  /**
   * Add an object in this index
   *
   * @param object object to add
   * @return the related Task
   * @throws AlgoliaException
   */
  public TaskIndexing addObject(@Nonnull T object) throws AlgoliaException {
    return client.addObject(name, object);
  }

  /**
   * Add an object in this index with a unique identifier
   *
   * @param objectID the objectID associated to this object
   *                 (if this objectID already exist the old object will be overridden)
   * @param object   object to add
   * @return the related Task
   * @throws AlgoliaException
   */
  public TaskIndexing addObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return client.addObject(name, objectID, object);
  }

  /**
   * Add several objects
   *
   * @param objects objects to add
   * @return the related Task
   * @throws AlgoliaException
   */
  public TaskSingleIndex addObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return client.addObjects(name, objects);
  }

  /**
   * Get an object from this index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return The object
   * @throws AlgoliaException
   */
  public Optional<T> getObject(@Nonnull String objectID) throws AlgoliaException {
    return client.getObject(name, objectID, klass);
  }

  /**
   * Get several objects from this index
   *
   * @param objectIDs the list of unique identifier of objects to retrieve
   * @return the list of objects
   * @throws AlgoliaException
   */
  public List<T> getObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return client.getObjects(name, objectIDs, klass);
  }

  /**
   * Wait for the completion of a task
   *
   * @param task       task to wait for
   * @param timeToWait the time to wait in milliseconds
   * @throws AlgoliaException
   */
  public void waitTask(@Nonnull Task task, long timeToWait) throws AlgoliaException {
    Preconditions.checkArgument(timeToWait >= 0, "timeToWait must be >= 0, was %s", timeToWait);

    client.waitTask(name, task, timeToWait);
  }

  /**
   * Wait for the completion of a task, for 100ms
   *
   * @param task task to wait for
   * @throws AlgoliaException
   */
  public void waitTask(@Nonnull Task task) throws AlgoliaException {
    client.waitTask(name, task, 100);
  }

  /**
   * Deletes the index
   *
   * @return the related Task
   * @throws AlgoliaException
   */
  public Task delete() throws AlgoliaException {
    return client.deleteIndex(name);
  }

  /**
   * Delete the index content without removing settings and index specific API keys.
   *
   * @return the related Task
   * @throws AlgoliaException
   */
  public Task clear() throws AlgoliaException {
    return client.clearIndex(name);
  }

  /**
   * Override the content of object
   *
   * @param objectID the unique identifier of the object to retrieve
   * @param object   the object to update
   * @return the related Task
   * @throws AlgoliaException
   */
  public Task saveObject(@Nonnull String objectID, @Nonnull T object) throws AlgoliaException {
    return client.saveObject(name, objectID, object);
  }

  /**
   * Override the content the list of objects
   *
   * @param objects the list objects to update
   * @return the related Task
   * @throws AlgoliaException
   */
  public TaskSingleIndex saveObjects(@Nonnull List<T> objects) throws AlgoliaException {
    return client.saveObjects(name, objects);
  }

  /**
   * Delete an object from the index
   *
   * @param objectID the unique identifier of the object to retrieve
   * @return the related Task
   * @throws AlgoliaException
   */
  public Task deleteObject(@Nonnull String objectID) throws AlgoliaException {
    return client.deleteObject(name, objectID);
  }

  /**
   * Delete objects from the index
   *
   * @param objectIDs the list of unique identifier of the object to retrieve
   * @return the related Task
   * @throws AlgoliaException
   */
  public TaskSingleIndex deleteObjects(@Nonnull List<String> objectIDs) throws AlgoliaException {
    return client.deleteObjects(name, objectIDs);
  }

  /**
   * Get settings of this index
   *
   * @return the settings
   * @throws AlgoliaException
   */
  public IndexSettings getSettings() throws AlgoliaException {
    return client.getSettings(name);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @return the related Task
   * @throws AlgoliaException
   */
  public Task setSettings(@Nonnull IndexSettings settings) throws AlgoliaException {
    return client.setSettings(name, settings);
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   * @throws AlgoliaException
   */
  public List<ApiKey> listKeys() throws AlgoliaException {
    return client.listKeys(name);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   * @throws AlgoliaException
   */
  public Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return client.getKey(name, key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   * @throws AlgoliaException
   */
  public DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
    return client.deleteKey(name, key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   * @throws AlgoliaException
   */
  public CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
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
  public CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key) throws AlgoliaException {
    return client.updateKey(name, keyName, key);
  }

  /**
   * Moves an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  public Task moveTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return client.moveIndex(name, dstIndexName);
  }

  /**
   * Copy an existing index
   *
   * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overridden if it already exist)
   * @return The task associated
   * @throws AlgoliaException
   */
  public Task copyTo(@Nonnull String dstIndexName) throws AlgoliaException {
    return client.copyIndex(name, dstIndexName);
  }

  /**
   * Search in the index
   *
   * @param query the query
   * @return the result of the search
   * @throws AlgoliaException
   */
  public SearchResult<T> search(@Nonnull Query query) throws AlgoliaException {
    return client.search(name, query, klass);
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
  public TaskSingleIndex batch(@Nonnull List<BatchOperation> operations) throws AlgoliaException {
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
  public TaskSingleIndex partialUpdateObject(@Nonnull String objectID, @Nonnull Object object) throws AlgoliaException {
    return client.partialUpdateObject(name, objectID, object);
  }

  /**
   * Partially update a objects
   *
   * @param objects the list of objects to update (with an objectID)
   * @return the associated task
   * @throws AlgoliaException
   */
  public TaskSingleIndex partialUpdateObjects(@Nonnull List<Object> objects) throws AlgoliaException {
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
  public TaskSingleIndex partialUpdateObject(@Nonnull PartialUpdateOperation operation) throws AlgoliaException {
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
  public TaskSingleIndex partialUpdateObject(@Nonnull PartialUpdateOperation operation, boolean createIfNotExists) throws AlgoliaException {
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
  public Task saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content) throws AlgoliaException {
    return saveSynonym(synonymID, content, false);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID       the id of the synonym
   * @param content         the synonym
   * @param forwardToSlaves should this request be forwarded to slaves
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToSlaves) throws AlgoliaException {
    return saveSynonym(synonymID, content, forwardToSlaves, false);
  }

  /**
   * Saves/updates a synonym
   *
   * @param synonymID               the id of the synonym
   * @param content                 the synonym
   * @param forwardToSlaves         should this request be forwarded to slaves
   * @param replaceExistingSynonyms should replace if this synonyms exists
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToSlaves, boolean replaceExistingSynonyms) throws AlgoliaException {
    return client.saveSynonym(name, synonymID, content, forwardToSlaves, replaceExistingSynonyms);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  public Optional<AbstractSynonym> getSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return client.getSynonym(name, synonymID);
  }

  /**
   * Deletes a synonym by ID and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task deleteSynonym(@Nonnull String synonymID) throws AlgoliaException {
    return deleteSynonym(synonymID, false);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID       the id of the synonym
   * @param forwardToSlaves should this request be forwarded to slaves
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task deleteSynonym(@Nonnull String synonymID, boolean forwardToSlaves) throws AlgoliaException {
    return client.deleteSynonym(name, synonymID, forwardToSlaves);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the slaves
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task clearSynonyms() throws AlgoliaException {
    return clearSynonyms(false);
  }

  /**
   * Clears all synonyms
   *
   * @return the associated task
   * @throws AlgoliaException
   */
  public Task clearSynonyms(boolean forwardToSlaves) throws AlgoliaException {
    return client.clearSynonyms(name, forwardToSlaves);
  }

  /**
   * Search for synonyms
   *
   * @param query the query
   * @return the results of the query
   * @throws AlgoliaException
   */
  public SearchSynonymResult searchSynonyms(@Nonnull SynonymQuery query) throws AlgoliaException {
    return client.searchSynonyms(name, query);
  }

  /**
   * Delete records matching a query
   *
   * @param query The query
   * @return the associated task
   * @throws AlgoliaException
   */
  public TaskSingleIndex deleteByQuery(@Nonnull Query query) throws AlgoliaException {
    return client.deleteByQuery(name, query);
  }

  @SuppressWarnings("unused")
  public static class Attributes {
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer entries;
    private Integer dataSize;
    private Integer fileSize;
    private Integer lastBuildTimeS;
    private Integer numberOfPendingTask;
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

    public Integer getEntries() {
      return entries;
    }

    public Integer getDataSize() {
      return dataSize;
    }

    public Integer getFileSize() {
      return fileSize;
    }

    public Integer getLastBuildTimeS() {
      return lastBuildTimeS;
    }

    public Integer getNumberOfPendingTask() {
      return numberOfPendingTask;
    }

    public Boolean getPendingTask() {
      return pendingTask;
    }
  }

}

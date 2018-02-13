package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

@SuppressWarnings("SameParameterValue")
public interface SyncSynonyms<T> extends SyncBaseIndex<T> {

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @return the associated task
   */
  default Task saveSynonym(@Nonnull String synonymID, @Nonnull AbstractSynonym content)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, false, RequestOptions.empty);
  }

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the slaves
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
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
   */
  default Task saveSynonym(
      @Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas)
      throws AlgoliaException {
    return saveSynonym(synonymID, content, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Saves/updates a synonym without replacing
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .saveSynonym(getName(), synonymID, content, forwardToReplicas, requestOptions);
  }

  /**
   * Get a synonym by ID
   *
   * @param synonymID the id of the synonym
   * @return the associated task
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
   */
  default Task clearSynonyms() throws AlgoliaException {
    return clearSynonyms(false);
  }

  /**
   * Clear all synonyms and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task clearSynonyms(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return clearSynonyms(false, requestOptions);
  }

  /**
   * Clears all synonyms
   *
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
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
   */
  default Task batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchSynonyms(synonyms, false, false, requestOptions);
  }
}

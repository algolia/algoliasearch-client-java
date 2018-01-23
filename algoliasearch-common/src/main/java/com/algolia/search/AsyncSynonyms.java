package com.algolia.search;

import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncSynonyms<T> extends AsyncBaseIndex<T> {

  /**
   * Saves/updates a synonym without replacing it and NOT forwarding it to the replicas
   *
   * @param synonymID the id of the synonym
   * @param content the synonym
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveSynonym(
      @Nonnull String synonymID, @Nonnull AbstractSynonym content) {
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
  default CompletableFuture<AsyncTask> saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      @Nonnull RequestOptions requestOptions) {
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
  default CompletableFuture<AsyncTask> saveSynonym(
      @Nonnull String synonymID, @Nonnull AbstractSynonym content, boolean forwardToReplicas) {
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
  default CompletableFuture<AsyncTask> saveSynonym(
      @Nonnull String synonymID,
      @Nonnull AbstractSynonym content,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient()
        .saveSynonym(getName(), synonymID, content, forwardToReplicas, requestOptions);
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
   * @param synonymID the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<Optional<AbstractSynonym>> getSynonym(
      @Nonnull String synonymID, @Nonnull RequestOptions requestOptions) {
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
   * @param synonymID the id of the synonym
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(
      @Nonnull String synonymID, @Nonnull RequestOptions requestOptions) {
    return deleteSynonym(synonymID, false, requestOptions);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID the id of the synonym
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(
      @Nonnull String synonymID, boolean forwardToReplicas) {
    return deleteSynonym(synonymID, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Deletes a synonym
   *
   * @param synonymID the id of the synonym
   * @param forwardToReplicas should this request be forwarded to slaves
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteSynonym(
      @Nonnull String synonymID,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
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
  default CompletableFuture<AsyncTask> clearSynonyms(
      boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
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
   * @param query the query
   * @param requestOptions Options to pass to this request
   * @return the results of the query
   */
  default CompletableFuture<SearchSynonymResult> searchSynonyms(
      @Nonnull SynonymQuery query, @Nonnull RequestOptions requestOptions) {
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
  default CompletableFuture<AsyncTask> batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms) {
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
  default CompletableFuture<AsyncTask> batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      boolean replaceExistingSynonyms,
      @Nonnull RequestOptions requestOptions) {
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
  default CompletableFuture<AsyncTask> batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms, boolean forwardToReplicas) {
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
  default CompletableFuture<AsyncTask> batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
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
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchSynonyms(
      @Nonnull List<AbstractSynonym> synonyms, @Nonnull RequestOptions requestOptions) {
    return batchSynonyms(synonyms, false, false, requestOptions);
  }
}

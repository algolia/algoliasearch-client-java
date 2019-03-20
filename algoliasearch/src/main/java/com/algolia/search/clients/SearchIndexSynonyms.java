package com.algolia.search.clients;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.DeleteResponse;
import com.algolia.search.models.search.SearchResult;
import com.algolia.search.models.synonyms.ClearSynonymsResponse;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import com.algolia.search.models.synonyms.Synonym;
import com.algolia.search.models.synonyms.SynonymQuery;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchIndexSynonyms<T> extends SearchIndexBase<T> {
  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   */
  default SearchResult<Synonym> searchSynonyms(SynonymQuery query) {
    return LaunderThrowable.unwrap(searchSynonymsAsync(query, null));
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   * @param requestOptions Options to pass to this request
   */
  default SearchResult<Synonym> searchSynonyms(SynonymQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchSynonymsAsync(query, requestOptions));
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   */
  default CompletableFuture<SearchResult<Synonym>> searchSynonymsAsync(SynonymQuery query) {
    return searchSynonymsAsync(query, null);
  }

  /**
   * Get all synonyms that match a query.
   *
   * @param query Synonym query
   * @param requestOptions Options to pass to this request
   */
  @SuppressWarnings("unchecked")
  default CompletableFuture<SearchResult<Synonym>> searchSynonymsAsync(
      SynonymQuery query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A query is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/search",
            CallType.READ,
            query,
            SearchResult.class,
            Synonym.class,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<Synonym>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * Get a single synonym using its object id.
   *
   * @param objectID Algolia's objectID
   */
  default CompletableFuture<Synonym> getSynonymAsync(@Nonnull String objectID) {
    return getSynonymAsync(objectID, null);
  }

  /**
   * Get a single synonym using its object id.
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<Synonym> getSynonymAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The synonym ID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/" + objectID,
            CallType.READ,
            Synonym.class,
            requestOptions);
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymAsync(@Nonnull Synonym synonym) {
    return saveSynonymAsync(synonym, false, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   * @param forwardToReplicas Forward the request to the replicas
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym, @Nonnull Boolean forwardToReplicas) {
    return saveSynonymAsync(synonym, forwardToReplicas, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param synonym Algolia's synonym
   * @param forwardToReplicas Forward the request to the replicas
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return saveSynonymAsync(synonym, requestOptions);
  }

  /**
   * Create or update a single synonym on an index.
   *
   * @param synonym Algolia's synonym
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymAsync(
      @Nonnull Synonym synonym, @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(synonym, "A synonym is required.");
    Objects.requireNonNull(requestOptions, "RequestOptions are required.");

    if (synonym.getObjectID().trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/" + synonym.getObjectID(),
            CallType.WRITE,
            synonym,
            SaveSynonymResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms) {
    return saveSynonymsAsync(synonyms, false, false, new RequestOptions());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   * @param replaceExistingSynonyms Replace all existing synonyms
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean replaceExistingSynonyms) {
    return saveSynonymsAsync(
        synonyms, forwardToReplicas, replaceExistingSynonyms, new RequestOptions());
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   * @param replaceExistingSynonyms Replace all existing synonyms
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean replaceExistingSynonyms,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    Objects.requireNonNull(replaceExistingSynonyms, "replaceExistingSynonyms is required.");

    requestOptions
        .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString())
        .addExtraQueryParameters("replaceExistingSynonyms", replaceExistingSynonyms.toString());

    return saveSynonymsAsync(synonyms, requestOptions);
  }

  /**
   * Create or update multiple synonyms.
   *
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SaveSynonymResponse> saveSynonymsAsync(
      @Nonnull List<Synonym> synonyms, RequestOptions requestOptions) {

    Objects.requireNonNull(synonyms, "synonyms are required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/batch",
            CallType.WRITE,
            synonyms,
            SaveSynonymResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   */
  default CompletableFuture<DeleteResponse> deleteSynonymAsync(@Nonnull String objectID) {
    return deleteSynonymAsync(objectID, false);
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   * @param forwardToReplicas Forward the request to the replicas
   */
  default CompletableFuture<DeleteResponse> deleteSynonymAsync(
      @Nonnull String objectID, @Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return deleteSynonymAsync(objectID, requestOptions);
  }

  /**
   * Remove a single synonym from an index using its object id.
   *
   * @param objectID The synonym objectID
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<DeleteResponse> deleteSynonymAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (objectID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/" + objectID,
            CallType.WRITE,
            DeleteResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /** Remove all synonyms from an index. */
  default CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync() {
    return clearSynonymsAsync(new RequestOptions());
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param forwardToReplicas Forward the request to the replicas
   */
  default CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync(
      @Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return clearSynonymsAsync(requestOptions);
  }

  /**
   * Remove all synonyms from an index.
   *
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<ClearSynonymsResponse> clearSynonymsAsync(
      RequestOptions requestOptions) {

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/synonyms/clear",
            CallType.WRITE,
            ClearSynonymsResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   */
  default CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms) {
    return saveSynonymsAsync(synonyms, false, true, new RequestOptions());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   * @param forwardToReplicas Forward to the replicas the request
   */
  default CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms, @Nonnull Boolean forwardToReplicas) {
    return saveSynonymsAsync(synonyms, forwardToReplicas, true, new RequestOptions());
  }

  /**
   * Push a new set of synonyms and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing synonyms are deleted and replaced with the new ones, in
   * a single, atomic operation
   *
   * @param synonyms List of synonyms
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SaveSynonymResponse> replaceAllSynonymsAsync(
      @Nonnull List<Synonym> synonyms, @Nonnull RequestOptions requestOptions) {
    return saveSynonymsAsync(synonyms, false, true, requestOptions);
  }
}

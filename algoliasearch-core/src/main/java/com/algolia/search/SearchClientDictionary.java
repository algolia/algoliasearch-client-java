package com.algolia.search;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.DictionaryEntry;
import com.algolia.search.models.dictionary.DictionaryRequest;
import com.algolia.search.models.dictionary.DictionaryResponse;
import com.algolia.search.models.dictionary.DictionarySettings;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientDictionary extends SearchClientBase {

  /**
   * Save dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   */
  default DictionaryResponse saveDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return saveDictionaryEntries(dictionary, dictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be saved. entries from the dictionary.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse saveDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        saveDictionaryEntriesAsync(dictionary, dictionaryEntries, requestOptions));
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   */
  default CompletableFuture<DictionaryResponse> saveDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return saveDictionaryEntriesAsync(dictionary, dictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> saveDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {

    Objects.requireNonNull(dictionary, "A dictionary is required.");
    Objects.requireNonNull(dictionaryEntries, "Dictionary entries is required.");

    DictionaryRequest request = DictionaryRequest.add(false, dictionaryEntries);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/dictionaries/" + dictionary + "/batch",
            CallType.WRITE,
            request,
            DictionaryResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitAppTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   */
  default DictionaryResponse replaceDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return replaceDictionaryEntries(dictionary, dictionaryEntries, null);
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse replaceDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        replaceDictionaryEntriesAsync(dictionary, dictionaryEntries, requestOptions));
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   */
  default CompletableFuture<DictionaryResponse> replaceDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return replaceDictionaryEntriesAsync(dictionary, dictionaryEntries, null);
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> replaceDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {

    Objects.requireNonNull(dictionary, "A dictionary is required.");
    Objects.requireNonNull(dictionaryEntries, "Dictionary entries is required.");

    DictionaryRequest request = DictionaryRequest.add(true, dictionaryEntries);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/dictionaries/" + dictionary + "/batch",
            CallType.WRITE,
            request,
            DictionaryResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitAppTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   */
  default DictionaryResponse deleteDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<String> objectIDs) {
    return deleteDictionaryEntries(dictionary, objectIDs, null);
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse deleteDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<String> objectIDs,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        deleteDictionaryEntriesAsync(dictionary, objectIDs, requestOptions));
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   */
  default CompletableFuture<DictionaryResponse> deleteDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<String> objectIDs) {
    return deleteDictionaryEntriesAsync(dictionary, objectIDs, null);
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> deleteDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<String> objectIDs,
      RequestOptions requestOptions) {

    Objects.requireNonNull(dictionary, "A dictionary is required.");
    Objects.requireNonNull(objectIDs, "Dictionary entries is required.");

    if (objectIDs.isEmpty()) {
      throw new IllegalArgumentException("objectIDs can't be empty.");
    }

    DictionaryRequest request = DictionaryRequest.delete(objectIDs);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/dictionaries/" + dictionary + "/batch",
            CallType.WRITE,
            request,
            DictionaryResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitAppTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Clear all dictionary entries.
   *
   * @param dictionary Target dictionary.
   */
  default DictionaryResponse clearDictionaryEntries(@Nonnull Dictionary dictionary) {
    return clearDictionaryEntries(dictionary, null);
  }

  /**
   * Clear all dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse clearDictionaryEntries(
      @Nonnull Dictionary dictionary, RequestOptions requestOptions) {
    return LaunderThrowable.await(clearDictionaryEntriesAsync(dictionary, requestOptions));
  }

  /**
   * Clear all dictionary entries.
   *
   * @param dictionary Target dictionary.
   */
  default CompletableFuture<DictionaryResponse> clearDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary) {
    return clearDictionaryEntriesAsync(dictionary, null);
  }

  /**
   * Clear all dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> clearDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, RequestOptions requestOptions) {
    return replaceDictionaryEntriesAsync(dictionary, Collections.emptyList(), requestOptions);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param query the Query used to search.
   */
  default <T> SearchResult<T> searchDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull Query query) {
    return searchDictionaryEntries(dictionary, query, null);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param query the Query used to search.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default <T> SearchResult<T> searchDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.await(searchDictionaryEntriesAsync(dictionary, query, requestOptions));
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param query the Query used to search.
   */
  default <T> CompletableFuture<SearchResult<T>> searchDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull Query query) {
    return searchDictionaryEntriesAsync(dictionary, query, null);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary Target dictionary.
   * @param query the Query used to search.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default <T> CompletableFuture<SearchResult<T>> searchDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull Query query, RequestOptions requestOptions) {

    Objects.requireNonNull(dictionary, "A dictionary is required.");
    Objects.requireNonNull(query, "A query key is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/dictionaries/" + dictionary + "/search",
            CallType.READ,
            query,
            SearchResult.class,
            dictionary.getEntry(),
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * Update dictionary settings. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying `null` for a setting resets it to its default value.
   *
   * @param dictionarySettings settings to be applied.
   */
  default DictionaryResponse setDictionarySettings(@Nonnull DictionarySettings dictionarySettings) {
    return setDictionarySettings(dictionarySettings, null);
  }

  /**
   * Update dictionary settings. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying `null` for a setting resets it to its default value.
   *
   * @param dictionarySettings settings to be applied.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse setDictionarySettings(
      @Nonnull DictionarySettings dictionarySettings, RequestOptions requestOptions) {
    return LaunderThrowable.await(setDictionarySettingsAsync(dictionarySettings, requestOptions));
  }

  /**
   * Update dictionary settings. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying `null` for a setting resets it to its default value.
   *
   * @param dictionarySettings settings to be applied.
   */
  default CompletableFuture<DictionaryResponse> setDictionarySettingsAsync(
      @Nonnull DictionarySettings dictionarySettings) {
    return setDictionarySettingsAsync(dictionarySettings, null);
  }

  /**
   * Update dictionary settings. Only specified settings are overridden; unspecified settings are
   * left unchanged. Specifying `null` for a setting resets it to its default value.
   *
   * @param dictionarySettings settings to be applied.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> setDictionarySettingsAsync(
      @Nonnull DictionarySettings dictionarySettings, RequestOptions requestOptions) {
    Objects.requireNonNull(dictionarySettings, "Dictionary settings is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/dictionaries/*/settings",
            CallType.WRITE,
            dictionarySettings,
            DictionaryResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitAppTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /** Retrieve dictionaries settings. */
  default DictionarySettings getDictionarySettings() {
    return getDictionarySettings(null);
  }

  /**
   * Retrieve dictionaries settings.
   *
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionarySettings getDictionarySettings(RequestOptions requestOptions) {
    return LaunderThrowable.await(getDictionarySettingsAsync(requestOptions));
  }

  /** Retrieve dictionaries settings. */
  default CompletableFuture<DictionarySettings> getDictionarySettingsAsync() {
    return getDictionarySettingsAsync(null);
  }

  /**
   * Retrieve dictionaries settings.
   *
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionarySettings> getDictionarySettingsAsync(
      RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/dictionaries/*/settings",
            CallType.READ,
            DictionarySettings.class,
            requestOptions);
  }
}

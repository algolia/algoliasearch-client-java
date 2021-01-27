package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.TaskStatusResponse;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.DictionaryRequest;
import com.algolia.search.models.dictionary.DictionaryResponse;
import com.algolia.search.models.dictionary.DictionarySettings;
import com.algolia.search.models.dictionary.DictionaryEntry;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientDictionary extends SearchClientBase {

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   */
  default DictionaryResponse saveDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return saveDictionaryEntries(dictionary, dictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   * @param clearExistingDictionaryEntries when `true`, start the batch by removing all the custom
   *     entries from the dictionary.
   */
  default DictionaryResponse saveDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      Boolean clearExistingDictionaryEntries) {
    return saveDictionaryEntries(
        dictionary, dictionaryEntries, clearExistingDictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   * @param clearExistingDictionaryEntries when `true`, start the batch by removing all the custom
   *     entries from the dictionary.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default DictionaryResponse saveDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      Boolean clearExistingDictionaryEntries,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        saveDictionaryEntriesAsync(
            dictionary, dictionaryEntries, clearExistingDictionaryEntries, requestOptions));
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   */
  default CompletableFuture<DictionaryResponse> saveDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return saveDictionaryEntriesAsync(dictionary, dictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   * @param clearExistingDictionaryEntries when `true`, start the batch by removing all the custom
   *     entries from the dictionary.
   */
  default CompletableFuture<DictionaryResponse> saveDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      Boolean clearExistingDictionaryEntries) {
    return saveDictionaryEntriesAsync(
        dictionary, dictionaryEntries, clearExistingDictionaryEntries, null);
  }

  /**
   * Save dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be saved.
   * @param clearExistingDictionaryEntries when `true`, start the batch by removing all the custom
   *     entries from the dictionary.
   * @param requestOptions Configure request locally with RequestOptions.
   */
  default CompletableFuture<DictionaryResponse> saveDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      Boolean clearExistingDictionaryEntries,
      RequestOptions requestOptions) {

    Objects.requireNonNull(dictionary, "A dictionary is required.");
    Objects.requireNonNull(dictionaryEntries, "Dictionary entries is required.");

    DictionaryRequest request =
        DictionaryRequest.add(clearExistingDictionaryEntries, dictionaryEntries);

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
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   */
  default DictionaryResponse replaceDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return replaceDictionaryEntries(dictionary, dictionaryEntries, null);
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   * @param requestOptions Configure request locally with [RequestOptions].
   */
  default DictionaryResponse replaceDictionaryEntries(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {
    return saveDictionaryEntries(dictionary, dictionaryEntries, true, requestOptions);
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   */
  default CompletableFuture<DictionaryResponse> replaceDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<DictionaryEntry> dictionaryEntries) {
    return replaceDictionaryEntriesAsync(dictionary, dictionaryEntries, null);
  }

  /**
   * Replace dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param dictionaryEntries dictionary entries to be replaced.
   * @param requestOptions Configure request locally with [RequestOptions].
   */
  default CompletableFuture<DictionaryResponse> replaceDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary,
      @Nonnull List<DictionaryEntry> dictionaryEntries,
      RequestOptions requestOptions) {
    return saveDictionaryEntriesAsync(dictionary, dictionaryEntries, true, requestOptions);
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   */
  default DictionaryResponse deleteDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull List<String> objectIDs) {
    return deleteDictionaryEntries(dictionary, objectIDs, null);
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   * @param requestOptions Configure request locally with [RequestOptions].
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
   * @param dictionary target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   */
  default CompletableFuture<DictionaryResponse> deleteDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull List<String> objectIDs) {
    return deleteDictionaryEntriesAsync(dictionary, objectIDs, null);
  }

  /**
   * Delete dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param objectIDs list of entries' IDs to delete.
   * @param requestOptions Configure request locally with [RequestOptions].
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
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param query the [Query] used to search.
   */
  default <T> SearchResult<T> searchDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull Query query) {
    return searchDictionaryEntries(dictionary, query, null);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param query the [Query] used to search.
   * @param requestOptions Configure request locally with [RequestOptions].
   */
  default <T> SearchResult<T> searchDictionaryEntries(
      @Nonnull Dictionary dictionary, @Nonnull Query query, RequestOptions requestOptions) {
    return LaunderThrowable.await(searchDictionaryEntriesAsync(dictionary, query, requestOptions));
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param query the [Query] used to search.
   */
  default <T> CompletableFuture<SearchResult<T>> searchDictionaryEntriesAsync(
      @Nonnull Dictionary dictionary, @Nonnull Query query) {
    return searchDictionaryEntriesAsync(dictionary, query, null);
  }

  /**
   * Search the dictionary entries.
   *
   * @param dictionary target dictionary.
   * @param query the [Query] used to search.
   * @param requestOptions Configure request locally with [RequestOptions].
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
   * @param requestOptions Configure request locally with [RequestOptions].
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
   * @param requestOptions Configure request locally with [RequestOptions].
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
              resp.setWaitConsumer(this::waitTask);
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
   * @param requestOptions Configure request locally with [RequestOptions].
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
   * @param requestOptions Configure request locally with [RequestOptions].
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

  /**
   * Wait for a dictionary task to complete before executing the next line of code. All write
   * operations in Algolia are asynchronous by design.
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default void waitTask(long taskID) {
    waitTask(taskID, 100, null);
  }

  /**
   * Wait for a dictionary task to complete before executing the next line of code. All write
   * operations in Algolia are asynchronous by design.
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default void waitTask(long taskID, RequestOptions requestOptions) {
    waitTask(taskID, 100, requestOptions);
  }

  /**
   * Wait for a dictionary task to complete before executing the next line of code. All write
   * operations in Algolia are asynchronous by design.
   *
   * @param taskID The Algolia taskID
   * @param timeToWait The time to wait between each call
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default void waitTask(long taskID, long timeToWait, RequestOptions requestOptions) {
    TaskUtils.waitTask(taskID, timeToWait, requestOptions, this::getTaskAsync);
  }

  /**
   * Get the status of the given dictionary task.
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default TaskStatusResponse getTask(long taskID, RequestOptions requestOptions) {
    return LaunderThrowable.await(getTaskAsync(taskID, requestOptions));
  }

  /**
   * Get the status of the given dictionary task.
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default TaskStatusResponse getTask(long taskID) {
    return LaunderThrowable.await(getTaskAsync(taskID));
  }

  /**
   * Get the status of the given dictionary task.
   *
   * @param taskID The Algolia taskID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<TaskStatusResponse> getTaskAsync(long taskID) {
    return getTaskAsync(taskID, null);
  }

  /**
   * Get the status of the given dictionary task.
   *
   * @param taskID The Algolia taskID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<TaskStatusResponse> getTaskAsync(
      long taskID, RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/task/" + taskID,
            CallType.READ,
            TaskStatusResponse.class,
            requestOptions);
  }
}

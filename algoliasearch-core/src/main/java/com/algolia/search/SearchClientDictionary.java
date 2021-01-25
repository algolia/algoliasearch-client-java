package com.algolia.search;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.algolia.search.models.dictionary.DictionaryRequest;
import com.algolia.search.models.dictionary.DictionaryResponse;
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
            requestOptions);
  }
}

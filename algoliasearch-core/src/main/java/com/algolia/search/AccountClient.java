package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.iterators.SynonymsIterable;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.WaitableResponse;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.MultiResponse;
import com.algolia.search.models.rules.SaveRuleResponse;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * The account client performs cross indices operations This class is an helper and it doesn't hold
 * resources.
 */
@SuppressWarnings("WeakerAccess")
public final class AccountClient {

  /**
   * The method copy settings, synonyms, rules and objects from the source index to the destination
   * index
   *
   * @param sourceIndex The source index to copy
   * @param destinationIndex The destination index
   * @throws AlgoliaRuntimeException If destination index already exist or source and destination
   *     are on the same application
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public static <T> MultiResponse copyIndex(
      @Nonnull SearchIndex<T> sourceIndex, @Nonnull SearchIndex<T> destinationIndex) {
    return LaunderThrowable.await(copyIndexAsync(sourceIndex, destinationIndex));
  }

  /**
   * The method copy settings, synonyms, rules and objects from the source index to the destination
   * index
   *
   * @param sourceIndex The source index to copy
   * @param destinationIndex The destination index
   * @param requestOptions Request options to pass to the request
   * @throws AlgoliaRuntimeException If destination index already exist or source and destination
   *     are on the same application
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public static <T> MultiResponse copyIndex(
      @Nonnull SearchIndex<T> sourceIndex,
      @Nonnull SearchIndex<T> destinationIndex,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(copyIndexAsync(sourceIndex, destinationIndex, requestOptions));
  }

  /**
   * The method copy settings, synonyms, rules and objects from the source index to the destination
   * index
   *
   * @param sourceIndex The source index to copy
   * @param destinationIndex The destination index
   * @throws AlgoliaRuntimeException If destination index already exist or source and destination
   *     are on the same application
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public static <T> CompletableFuture<MultiResponse> copyIndexAsync(
      @Nonnull SearchIndex<T> sourceIndex, @Nonnull SearchIndex<T> destinationIndex) {
    return copyIndexAsync(sourceIndex, destinationIndex, null);
  }

  /**
   * The method copy settings, synonyms, rules and objects from the source index to the destination
   * index
   *
   * @param sourceIndex The source index to copy
   * @param destinationIndex The destination index
   * @param requestOptions Request options to pass to the request
   * @throws AlgoliaRuntimeException If destination index already exist or source and destination
   *     are on the same application
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   */
  public static <T> CompletableFuture<MultiResponse> copyIndexAsync(
      @Nonnull SearchIndex<T> sourceIndex,
      @Nonnull SearchIndex<T> destinationIndex,
      RequestOptions requestOptions) {

    Objects.requireNonNull(sourceIndex, "A source index is required");
    Objects.requireNonNull(destinationIndex, "A destination index is required");

    if (sourceIndex
        .getConfig()
        .getApplicationID()
        .equals(destinationIndex.getConfig().getApplicationID())) {

      throw new AlgoliaRuntimeException(
          "Source and Destination indices should not be on the same application.");
    }

    try {
      IndexSettings destinationSettings = destinationIndex.getSettings(requestOptions);

      if (destinationSettings != null) {
        throw new AlgoliaRuntimeException(
            "Destination index should not exist. Make sure the destination index does not exist or copy to another index.");
      }
    } catch (AlgoliaApiException ex) {
      // We want to catch an non existing index exception (404) and continue
      // Otherwise, we want to throw if it's another Http exception
      if (ex.getHttpErrorCode() != 404) {
        throw ex;
      }
    }

    List<CompletableFuture<? extends WaitableResponse>> futures = new ArrayList<>();

    // Save settings
    CompletableFuture<SetSettingsResponse> destinationSettingsFuture =
        sourceIndex
            .getSettingsAsync(requestOptions)
            .thenComposeAsync(r -> destinationIndex.setSettingsAsync(r, requestOptions));
    futures.add(destinationSettingsFuture);

    // Save synonyms
    SynonymsIterable sourceSynonyms = new SynonymsIterable(sourceIndex);
    CompletableFuture<SaveSynonymResponse> destinationSynonymsFuture =
        destinationIndex.saveSynonymsAsync(sourceSynonyms, requestOptions);
    futures.add(destinationSynonymsFuture);

    // Save rules
    RulesIterable sourceRules = new RulesIterable(sourceIndex);
    CompletableFuture<SaveRuleResponse> destinationRulesFuture =
        destinationIndex.saveRulesAsync(sourceRules, requestOptions);
    futures.add(destinationRulesFuture);

    // Save objects
    IndexIterable<T> sourceObjects = new IndexIterable<>(sourceIndex);
    CompletableFuture<BatchIndexingResponse> destinationSaveObjects =
        destinationIndex.saveObjectsAsync(sourceObjects, requestOptions);

    futures.add(destinationSaveObjects);

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenComposeAsync(
            v -> {
              List<WaitableResponse> resp =
                  futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
              return CompletableFuture.completedFuture(new MultiResponse().setResponses(resp));
            });
  }
}

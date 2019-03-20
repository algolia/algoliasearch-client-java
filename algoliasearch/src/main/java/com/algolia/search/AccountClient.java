package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.iterators.SynonymsIterable;
import com.algolia.search.models.IAlgoliaWaitableResponse;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.MultiResponse;
import com.algolia.search.models.rules.Rule;
import com.algolia.search.models.rules.SaveRuleResponse;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import com.algolia.search.models.synonyms.Synonym;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/** Client to perform cross indices operations */
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
   */
  public <T> CompletableFuture<MultiResponse> copyIndexAsync(
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
   */
  public <T> CompletableFuture<MultiResponse> copyIndexAsync(
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
            "Source and Destination indices should not be on the same application.");
      }
    } catch (AlgoliaApiException ex) {
      // We want to catch an non existing index exception (404) and continue
      // Otherwise, we want to throw if it's another Http exception
      if (ex.getHttpErrorCode() != 404) {
        throw ex;
      }
    }

    List<CompletableFuture<? extends IAlgoliaWaitableResponse>> futures = new ArrayList<>();

    // Save settings
    CompletableFuture<SetSettingsResponse> destinationSettingsFuture =
        sourceIndex
            .getSettingsAsync(requestOptions)
            .thenComposeAsync(r -> destinationIndex.setSettingsAsync(r, requestOptions));
    futures.add(destinationSettingsFuture);

    // Save synonyms
    CompletableFuture<SaveSynonymResponse> destinationSynonymsFuture =
        CompletableFuture.supplyAsync(
                () -> {
                  List<Synonym> synonyms = new ArrayList<>();
                  SynonymsIterable sourceSynonyms = new SynonymsIterable(sourceIndex);
                  sourceSynonyms.forEach(synonyms::add);
                  return synonyms;
                })
            .thenCompose(s -> destinationIndex.saveSynonymsAsync(s, requestOptions));

    futures.add(destinationSynonymsFuture);

    // Save rules
    CompletableFuture<SaveRuleResponse> destinationRulesFuture =
        CompletableFuture.supplyAsync(
                () -> {
                  List<Rule> rules = new ArrayList<>();
                  RulesIterable sourceRules = new RulesIterable(sourceIndex);
                  sourceRules.forEach(rules::add);
                  return rules;
                })
            .thenCompose(r -> destinationIndex.saveRulesAsync(r, requestOptions));

    futures.add(destinationRulesFuture);

    // Save objects
    IndexIterable<T> sourceObjects = new IndexIterable<>(sourceIndex);
    CompletableFuture<BatchIndexingResponse> destinationSaveObjects =
        destinationIndex.saveObjectsAsync(sourceObjects, requestOptions);

    futures.add(destinationSaveObjects);

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenComposeAsync(
            v -> {
              List<IAlgoliaWaitableResponse> resp =
                  futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

              return CompletableFuture.completedFuture(new MultiResponse().setResponses(resp));
            });
  }
}

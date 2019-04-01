package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.*;
import com.algolia.search.utils.QueryStringUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientCopyOperations extends SearchClientBase {

  /**
   * Make a copy of the settings of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copySettings(@Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return LaunderThrowable.unwrap(copyIndexAsync(sourceIndex, destinationIndex));
  }

  /**
   * Make a copy of the settings of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copySettings(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(
        copySettingsAsync(sourceIndex, destinationIndex, requestOptions));
  }

  /**
   * Make a copy of the settings of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copySettingsAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return copyIndexAsync(
        sourceIndex, destinationIndex, Collections.singletonList(CopyScope.SETTINGS), null);
  }

  /**
   * Make a copy of the settings of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copySettingsAsync(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return copyIndexAsync(
        sourceIndex, destinationIndex, Collections.singletonList(CopyScope.SETTINGS), null);
  }

  /**
   * Make a copy of the rules of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copyRules(@Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return LaunderThrowable.unwrap(copyRulesAsync(sourceIndex, destinationIndex));
  }

  /**
   * Make a copy of the rules of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copyRules(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(copyRulesAsync(sourceIndex, destinationIndex, requestOptions));
  }

  /**
   * Make a copy of the rules of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copyRulesAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return copyIndexAsync(
        sourceIndex, destinationIndex, Collections.singletonList(CopyScope.RULES), null);
  }

  /**
   * Make a copy of the rules of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copyRulesAsync(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return copyIndexAsync(
        sourceIndex, destinationIndex, Collections.singletonList(CopyScope.RULES), null);
  }

  /**
   * Make a copy of the synonyms of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copySynonyms(@Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return LaunderThrowable.unwrap(copySynonymsAsync(sourceIndex, destinationIndex));
  }

  /**
   * Make a copy of the synonyms of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copySynonyms(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(
        copySynonymsAsync(sourceIndex, destinationIndex, requestOptions));
  }

  /**
   * Make a copy of the synonyms of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copySynonymsAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return copyIndexAsync(
        sourceIndex, destinationIndex, Collections.singletonList(CopyScope.SYNONYMS), null);
  }

  /**
   * Make a copy of the synonyms of an index
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copySynonymsAsync(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return copyIndexAsync(
        sourceIndex,
        destinationIndex,
        Collections.singletonList(CopyScope.SYNONYMS),
        requestOptions);
  }

  /**
   * Make a copy of an index, in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copyIndex(@Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return LaunderThrowable.unwrap(copyIndexAsync(sourceIndex, destinationIndex));
  }

  /**
   * Make a copy of an index, in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param scopes Scope of the copy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copyIndex(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex, List<String> scopes) {
    return LaunderThrowable.unwrap(copyIndexAsync(sourceIndex, destinationIndex, scopes));
  }

  /**
   * Make a copy of an index in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param scopes Scope of the copy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CopyResponse copyIndex(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      List<String> scopes,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(
        copyIndexAsync(sourceIndex, destinationIndex, scopes, requestOptions));
  }

  /**
   * Make a copy of an index, in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copyIndexAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return copyIndexAsync(sourceIndex, destinationIndex, null, null);
  }

  /**
   * Make a copy of an index, in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param scopes Scope of the copy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copyIndexAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex, List<String> scopes) {
    return copyIndexAsync(sourceIndex, destinationIndex, scopes, null);
  }

  /**
   * Make a copy of an index in the given scope.
   *
   * @param sourceIndex The source index to copy from
   * @param destinationIndex the destination index
   * @param scopes Scope of the copy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<CopyResponse> copyIndexAsync(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      List<String> scopes,
      RequestOptions requestOptions) {

    checkIndicesBeforeMoving(sourceIndex, destinationIndex);

    CopyToRequest request =
        new CopyToRequest()
            .setDestination(destinationIndex)
            .setScope(scopes)
            .setOperation(MoveType.COPY);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + QueryStringUtils.urlEncodeUTF8(sourceIndex) + "/operation",
            CallType.WRITE,
            request,
            CopyResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setIndexName(sourceIndex);
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source index. Should not be null or empty.
   * @param destinationIndex The destination destination. Should not be null or empty.
   */
  default MoveIndexResponse moveIndex(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return LaunderThrowable.unwrap(moveIndexAsync(sourceIndex, destinationIndex));
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source index. Should not be null or empty.
   * @param destinationIndex The destination destination. Should not be null or empty.
   * @param requestOptions Options to pass to this request
   */
  default MoveIndexResponse moveIndex(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(moveIndexAsync(sourceIndex, destinationIndex, requestOptions));
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source index. Should not be null or empty.
   * @param destinationIndex The destination destination. Should not be null or empty.
   */
  default CompletableFuture<MoveIndexResponse> moveIndexAsync(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    return moveIndexAsync(sourceIndex, destinationIndex, null);
  }

  /**
   * Rename an index. Normally used to reindex your data atomically, without any down time.
   *
   * @param sourceIndex The source index. Should not be null or empty.
   * @param destinationIndex The destination destination. Should not be null or empty.
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<MoveIndexResponse> moveIndexAsync(
      @Nonnull String sourceIndex,
      @Nonnull String destinationIndex,
      RequestOptions requestOptions) {
    checkIndicesBeforeMoving(sourceIndex, destinationIndex);

    MoveIndexRequest request =
        new MoveIndexRequest().setDestination(destinationIndex).setOperation(MoveType.MOVE);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + QueryStringUtils.urlEncodeUTF8(sourceIndex) + "/operation",
            CallType.WRITE,
            request,
            MoveIndexResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setIndexName(destinationIndex);
              resp.setWaitBiConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  default void checkIndicesBeforeMoving(
      @Nonnull String sourceIndex, @Nonnull String destinationIndex) {
    Objects.requireNonNull(sourceIndex, "The source index is required.");
    Objects.requireNonNull(destinationIndex, "The destination index is required.");

    if (sourceIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("destination index must not be empty.");
    }

    if (destinationIndex.trim().length() == 0) {
      throw new AlgoliaRuntimeException("destination index must not be empty.");
    }
  }
}

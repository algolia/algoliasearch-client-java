package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientMultipleOperations extends SearchClientBase {

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param queries The query object
   * @param clazz Class of the data to retrieve
   * @param <T> Type of the data to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleGetObjectsResponse<T> multipleGetObjects(
      List<MultipleGetObject> queries, Class<T> clazz) throws AlgoliaRuntimeException {
    return multipleGetObjects(queries, clazz, null);
  }

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param queries The query object
   * @param clazz Class of the data to retrieve
   * @param requestOptions Options to pass to this request
   * @param <T> Type of the data to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleGetObjectsResponse<T> multipleGetObjects(
      List<MultipleGetObject> queries, Class<T> clazz, RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.unwrap(multipleGetObjectsAsync(queries, clazz, requestOptions));
  }

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param queries The query object
   * @param clazz Class of the data to retrieve
   * @param <T> Type of the data to retrieve
   */
  default <T> CompletableFuture<MultipleGetObjectsResponse<T>> multipleGetObjectsAsync(
      List<MultipleGetObject> queries, Class<T> clazz) {
    return multipleGetObjectsAsync(queries, clazz, null);
  }

  /**
   * Retrieve one or more objects, potentially from different indices, in a single API call.
   *
   * @param queries The query object
   * @param clazz Class of the data to retrieve
   * @param requestOptions Options to pass to this request
   * @param <T> Type of the data to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  @SuppressWarnings("unchecked")
  default <T> CompletableFuture<MultipleGetObjectsResponse<T>> multipleGetObjectsAsync(
      List<MultipleGetObject> queries, Class<T> clazz, RequestOptions requestOptions) {

    Objects.requireNonNull(queries, "Queries is required");
    Objects.requireNonNull(clazz, "Class is required");

    MultipleGetObjectsRequest request = new MultipleGetObjectsRequest(queries);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/objects",
            CallType.READ,
            request,
            MultipleGetObjectsResponse.class,
            clazz,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<MultipleGetObjectsResponse<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param operations The batch operations to process. It could be on multiple indices with
   *     multiple actions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleIndexBatchIndexingResponse multipleBatch(
      @Nonnull List<BatchOperation<T>> operations) {
    return LaunderThrowable.unwrap(multipleBatchAsync(operations, null));
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param operations The batch operations to process. It could be on multiple indices with
   *     multiple action
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleIndexBatchIndexingResponse multipleBatch(
      @Nonnull List<BatchOperation<T>> operations, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(multipleBatchAsync(operations, requestOptions));
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param operations The batch operations to process. It could be on multiple indices with
   *     multiple action
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> CompletableFuture<MultipleIndexBatchIndexingResponse> multipleBatchAsync(
      @Nonnull List<BatchOperation<T>> operations) {
    return multipleBatchAsync(operations, null);
  }

  /**
   * Perform multiple write operations, potentially targeting multiple indices, in a single API
   * call.
   *
   * @param operations The batch operations to process. It could be on multiple indices with
   *     multiple action
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> CompletableFuture<MultipleIndexBatchIndexingResponse> multipleBatchAsync(
      @Nonnull List<BatchOperation<T>> operations, RequestOptions requestOptions) {

    Objects.requireNonNull(operations, "Operations are required");

    BatchRequest<T> request = new BatchRequest<>(operations);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/batch",
            CallType.WRITE,
            request,
            MultipleIndexBatchIndexingResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * This method allows to send multiple search queries, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param request The request
   * @param clazz The class of the expected results
   * @param <T> Type of the expected results
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleQueriesResponse<T> multipleQueries(
      @Nonnull MultipleQueriesRequest request, @Nonnull Class<T> clazz) {
    return LaunderThrowable.unwrap(multipleQueriesAsync(request, clazz, null));
  }

  /**
   * This method allows to send multiple search queries, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param request The request
   * @param clazz The class of the expected results
   * @param requestOptions Options to pass to this request
   * @param <T> Type of the expected results
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> MultipleQueriesResponse<T> multipleQueries(
      @Nonnull MultipleQueriesRequest request,
      @Nonnull Class<T> clazz,
      RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(multipleQueriesAsync(request, clazz, requestOptions));
  }

  /**
   * This method allows to send multiple search queries, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param request The request
   * @param clazz The class of the expected results
   * @param <T> Type of the expected results
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T> CompletableFuture<MultipleQueriesResponse<T>> multipleQueriesAsync(
      @Nonnull MultipleQueriesRequest request, @Nonnull Class<T> clazz) {
    return multipleQueriesAsync(request, clazz, null);
  }

  /**
   * This method allows to send multiple search queries, potentially targeting multiple indices, in
   * a single API call.
   *
   * @param request The request
   * @param clazz The class of the expected results
   * @param requestOptions Options to pass to this request
   * @param <T> Type of the expected results
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  @SuppressWarnings("unchecked")
  default <T> CompletableFuture<MultipleQueriesResponse<T>> multipleQueriesAsync(
      @Nonnull MultipleQueriesRequest request,
      @Nonnull Class<T> clazz,
      RequestOptions requestOptions) {

    Objects.requireNonNull(request, "Request is required");
    Objects.requireNonNull(clazz, "A Class is required");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/queries",
            CallType.READ,
            request,
            MultipleQueriesResponse.class,
            clazz,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<MultipleQueriesResponse<T>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }
}

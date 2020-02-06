package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.Log;
import com.algolia.search.models.common.LogType;
import com.algolia.search.models.common.Logs;
import com.algolia.search.models.indexing.IndicesResponse;
import com.algolia.search.models.indexing.ListIndicesResponse;
import com.algolia.search.util.AlgoliaUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientAdvanced extends SearchClientBase {

  /**
   * List all existing indexes
   *
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<IndicesResponse> listIndices() {
    return listIndices(null);
  }

  /**
   * List all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<IndicesResponse> listIndices(RequestOptions requestOptions) {
    return LaunderThrowable.await(listIndicesAsync(requestOptions));
  }

  /**
   * List asynchronously all existing indexes
   *
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<IndicesResponse>> listIndicesAsync() {
    return listIndicesAsync(null);
  }

  /**
   * List asynchronously all existing indexes
   *
   * @param requestOptions Options to pass to this request
   * @return A List of the indices and their metadata
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<IndicesResponse>> listIndicesAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET, "/1/indexes", CallType.READ, ListIndicesResponse.class, requestOptions)
        .thenApplyAsync(ListIndicesResponse::getIndices, getConfig().getExecutor());
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<Log> getLogs() {
    return LaunderThrowable.await(getLogsAsync(0, 10));
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<Log> getLogs(RequestOptions requestOptions) {
    return LaunderThrowable.await(getLogsAsync(requestOptions));
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<Log> getLogs(int offset, int length) {
    return LaunderThrowable.await(getLogsAsync(offset, length));
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @param logType Log type values can be found in {@link LogType}
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<Log> getLogs(int offset, int length, @Nonnull String logType) {
    return LaunderThrowable.await(getLogsAsync(offset, length, logType));
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<Log> getLogs(int offset, int length, RequestOptions requestOptions) {
    return LaunderThrowable.await(
        getLogsAsync(offset, length, LogType.LOG_ALL.getName(), requestOptions));
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<Log>> getLogsAsync() {
    return getLogsAsync(0, 10);
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<Log>> getLogsAsync(int offset, int length) {
    return getLogsAsync(offset, length, LogType.LOG_ALL.getName());
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @param logType Log type values can be found in {@link LogType}
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<Log>> getLogsAsync(
      int offset, int length, @Nonnull String logType) {
    return getLogsAsync(offset, length, logType, null);
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<Log>> getLogsAsync(RequestOptions requestOptions) {
    return getLogsAsync(0, 10, LogType.LOG_ALL.getName(), requestOptions);
  }

  /**
   * Get the logs of the latest search and indexing operations You can retrieve the logs of your
   * last 1,000 API calls. It is designed for immediate, real-time debugging.
   *
   * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
   * @param length Specify the maximum number of entries to retrieve starting at the offset. Maximum
   *     allowed value: 1,000.
   * @param logType Log type values can be found in {@link LogType}
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<Log>> getLogsAsync(
      int offset, int length, @Nonnull String logType, RequestOptions requestOptions) {

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(logType)) {
      throw new AlgoliaRuntimeException("logType can't be null, empty or whitespaces");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters("type", logType);
    requestOptions.addExtraQueryParameters("offset", Integer.toString(offset));
    requestOptions.addExtraQueryParameters("length", Integer.toString(length));

    return getTransport()
        .executeRequestAsync(HttpMethod.GET, "/1/logs", CallType.READ, Logs.class, requestOptions)
        .thenApplyAsync(Logs::getLogs, getConfig().getExecutor());
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param data The data to send to the API
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param <U> The return class
   * @param <T> The data class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T, U> U customRequest(
      T data,
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType) {
    return LaunderThrowable.await(
        customRequestAsync(data, returnClazz, httpMethod, path, callType));
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param data The data to send to the API
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param requestOptions Options to pass to this request
   * @param <U> The return class
   * @param <T> The data class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T, U> U customRequest(
      T data,
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        customRequestAsync(data, returnClazz, httpMethod, path, callType, requestOptions));
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param <U> The return class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <U> U customRequest(
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType) {
    return LaunderThrowable.await(customRequestAsync(returnClazz, httpMethod, path, callType));
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param requestOptions Options to pass to this request
   * @param <U> The return class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <U> U customRequest(
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        customRequestAsync(returnClazz, httpMethod, path, callType, requestOptions));
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param data The data to send to the API
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param <U> The return class
   * @param <T> The data class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T, U> CompletableFuture<U> customRequestAsync(
      T data,
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType) {
    return customRequestAsync(data, returnClazz, httpMethod, path, callType, null);
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param data The data to send to the API
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param requestOptions Options to pass to this request
   * @param <U> The return class
   * @param <T> The data class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <T, U> CompletableFuture<U> customRequestAsync(
      T data,
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType,
      RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(httpMethod, path, callType, data, returnClazz, requestOptions);
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param <U> The return class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <U> CompletableFuture<U> customRequestAsync(
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType) {
    return customRequestAsync(returnClazz, httpMethod, path, callType, null);
  }

  /**
   * Executes a custom to the Algolia API under the retry strategy.
   *
   * @param returnClazz The return class
   * @param httpMethod The http method. {@link HttpMethod}
   * @param path The path to the Algolia REST API method
   * @param callType The CallType. {@link CallType}
   * @param requestOptions Options to pass to this request
   * @param <U> The return class
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default <U> CompletableFuture<U> customRequestAsync(
      Class<U> returnClazz,
      @Nonnull HttpMethod httpMethod,
      @Nonnull String path,
      @Nonnull CallType callType,
      RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(httpMethod, path, callType, returnClazz, requestOptions);
  }
}

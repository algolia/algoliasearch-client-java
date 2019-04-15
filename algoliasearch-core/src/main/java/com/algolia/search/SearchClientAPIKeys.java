package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.apikeys.*;
import com.algolia.search.models.common.CallType;
import com.algolia.search.util.AlgoliaUtils;
import com.algolia.search.util.HmacShaUtils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientAPIKeys extends SearchClientBase {

  /**
   * List all existing user keys with their associated ACLs
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<ApiKey> listApiKeys() throws AlgoliaRuntimeException {
    return listApiKeys(null);
  }

  /**
   * List all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default List<ApiKey> listApiKeys(RequestOptions requestOptions) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(listApiKeysAsync(requestOptions));
  }

  /**
   * List asynchronously all existing user keys with their associated ACLs
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<ApiKey>> listApiKeysAsync() {
    return listApiKeysAsync(null);
  }

  /**
   * List asynchronously all existing user keys with their associated ACLs
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<List<ApiKey>> listApiKeysAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET, "/1/keys", CallType.READ, null, ApiKeys.class, requestOptions)
        .thenApplyAsync(ApiKeys::getKeys, getConfig().getExecutor());
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ApiKey getApiKey(@Nonnull String apiKey) throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getApiKeyAsync(apiKey, null));
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ApiKey getApiKey(@Nonnull String apiKey, RequestOptions requestOptions)
      throws AlgoliaRuntimeException {
    return LaunderThrowable.await(getApiKeyAsync(apiKey, requestOptions));
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<ApiKey> getApiKeyAsync(@Nonnull String apiKey) {
    return getApiKeyAsync(apiKey, null);
  }

  /**
   * Get the permissions of an API Key.
   *
   * @param apiKey The API key to retrieve
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<ApiKey> getApiKeyAsync(
      @Nonnull String apiKey, RequestOptions requestOptions) {

    Objects.requireNonNull(apiKey, "An API key is required.");

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(apiKey)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET, "/1/keys/" + apiKey, CallType.READ, null, ApiKey.class, requestOptions);
  }

  /**
   * Add a new API Key with specific permissions/restrictions
   *
   * @param acl The api with the restrictions/permissions to add
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AddApiKeyResponse addApiKey(@Nonnull ApiKey acl) {
    return LaunderThrowable.await(addApiKeyAsync(acl));
  }

  /**
   * Add a new API Key with specific permissions/restrictions
   *
   * @param acl The api with the restrictions/permissions to add
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AddApiKeyResponse addApiKey(@Nonnull ApiKey acl, RequestOptions requestOptions) {
    return LaunderThrowable.await(addApiKeyAsync(acl, requestOptions));
  }

  /**
   * Add a new API Key with specific permissions/restrictions
   *
   * @param acl The api with the restrictions/permissions to add
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AddApiKeyResponse> addApiKeyAsync(@Nonnull ApiKey acl) {
    return addApiKeyAsync(acl, null);
  }

  /**
   * Add a new API Key with specific permissions/restrictions
   *
   * @param acl The api with the restrictions/permissions to add
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AddApiKeyResponse> addApiKeyAsync(
      @Nonnull ApiKey acl, RequestOptions requestOptions) {
    Objects.requireNonNull(acl, "An API key is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/keys",
            CallType.WRITE,
            acl,
            AddApiKeyResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setGetApiKeyFunction(this::getApiKey);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Delete an existing API Key
   *
   * @param apiKey The API Key to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteApiKeyResponse deleteApiKey(@Nonnull String apiKey) {
    return LaunderThrowable.await(deleteApiKeyAsync(apiKey));
  }

  /**
   * Delete an existing API Key
   *
   * @param apiKey The API Key to delete
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteApiKeyResponse deleteApiKey(@Nonnull String apiKey, RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteApiKeyAsync(apiKey, requestOptions));
  }

  /**
   * Delete an existing API Key
   *
   * @param apiKey The API Key to delete
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteApiKeyResponse> deleteApiKeyAsync(@Nonnull String apiKey) {
    return deleteApiKeyAsync(apiKey, null);
  }

  /**
   * Delete an existing API Key
   *
   * @param apiKey The API Key to delete
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteApiKeyResponse> deleteApiKeyAsync(
      @Nonnull String apiKey, RequestOptions requestOptions) {
    Objects.requireNonNull(apiKey, "An API key is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(apiKey)) {
      throw new AlgoliaRuntimeException("API key must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/keys/" + apiKey,
            CallType.WRITE,
            DeleteApiKeyResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setKey(apiKey);
              resp.setGetApiKeyFunction(this::getApiKey);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Update the permissions of an existing API Key.
   *
   * @param request The API key to update
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default UpdateApiKeyResponse updateApiKey(@Nonnull ApiKey request) {
    return LaunderThrowable.await(updateApiKeyAsync(request, null));
  }

  /**
   * Update the permissions of an existing API Key.
   *
   * @param request The API key to update
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default UpdateApiKeyResponse updateApiKey(
      @Nonnull ApiKey request, RequestOptions requestOptions) {
    return LaunderThrowable.await(updateApiKeyAsync(request, requestOptions));
  }

  /**
   * Update the permissions of an existing API Key.
   *
   * @param request The API key to update
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<UpdateApiKeyResponse> updateApiKeyAsync(@Nonnull ApiKey request) {
    return updateApiKeyAsync(request, null);
  }

  /**
   * Update the permissions of an existing API Key.
   *
   * @param request The API key to update
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<UpdateApiKeyResponse> updateApiKeyAsync(
      @Nonnull ApiKey request, RequestOptions requestOptions) {
    Objects.requireNonNull(request, "An API key is required.");

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(request.getValue())) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/keys/" + request.getValue(),
            CallType.WRITE,
            request,
            UpdateApiKeyResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setPendingKey(request);
              resp.setGetApiKeyFunction(this::getApiKey);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Restore the given API Key
   *
   * @param apiKey The given API Key
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization or
   *     deserialization
   */
  default RestoreApiKeyResponse restoreApiKey(@Nonnull String apiKey) {
    return LaunderThrowable.await(restoreApiKeyAsync(apiKey));
  }

  /**
   * Restore the given API Key
   *
   * @param apiKey The given API Key
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default RestoreApiKeyResponse restoreApiKey(
      @Nonnull String apiKey, RequestOptions requestOptions) {
    return LaunderThrowable.await(restoreApiKeyAsync(apiKey, requestOptions));
  }

  /**
   * Restore the given API Key
   *
   * @param apiKey The given API Key
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization or
   *     deserialization
   */
  default CompletableFuture<RestoreApiKeyResponse> restoreApiKeyAsync(@Nonnull String apiKey) {
    return restoreApiKeyAsync(apiKey, null);
  }

  /**
   * Restore the given API Key
   *
   * @param apiKey The given API Key
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<RestoreApiKeyResponse> restoreApiKeyAsync(
      @Nonnull String apiKey, RequestOptions requestOptions) {

    Objects.requireNonNull(apiKey, "An API Key is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(apiKey)) {
      throw new AlgoliaRuntimeException("API Key must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/keys/" + apiKey + "/restore",
            CallType.WRITE,
            RestoreApiKeyResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setKey(apiKey);
              resp.setGetApiKeyFunction(this::getApiKey);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Generate a virtual API Key without any call to the server.
   *
   * @param parentAPIKey API key to generate from.
   * @param restriction Restriction to add the key
   * @throws Exception if an error occurs during the encoding
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default String generateSecuredAPIKey(
      @Nonnull String parentAPIKey, SecuredApiKeyRestriction restriction) throws Exception {
    return HmacShaUtils.generateSecuredApiKey(parentAPIKey, restriction);
  }
}

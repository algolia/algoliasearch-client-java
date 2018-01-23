package com.algolia.search;

import com.algolia.search.objects.ApiKey;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.CreateUpdateKey;
import com.algolia.search.responses.DeleteKey;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncKey<T> extends AsyncBaseIndex<T> {

  /** Deprecated: use {@link #listApiKeys()} */
  @Deprecated
  default CompletableFuture<List<ApiKey>> listKeys() {
    return listApiKeys();
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   */
  default CompletableFuture<List<ApiKey>> listApiKeys() {
    return listApiKeys(RequestOptions.empty);
  }

  /**
   * List keys of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the list of keys
   */
  default CompletableFuture<List<ApiKey>> listApiKeys(@Nonnull RequestOptions requestOptions) {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /** Deprecated: use {@link #getApiKey(String)} */
  @Deprecated
  default CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) {
    return getApiKey(key);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   */
  default CompletableFuture<Optional<ApiKey>> getApiKey(@Nonnull String key) {
    return getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the key
   */
  default CompletableFuture<Optional<ApiKey>> getApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #deleteApiKey(String)} */
  @Deprecated
  default CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) {
    return deleteApiKey(key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   */
  default CompletableFuture<DeleteKey> deleteApiKey(@Nonnull String key) {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the deleted key
   */
  default CompletableFuture<DeleteKey> deleteApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #addApiKey(ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) {
    return addApiKey(key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   */
  default CompletableFuture<CreateUpdateKey> addApiKey(@Nonnull ApiKey key) {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @param requestOptions Options to pass to this request
   * @return the created key
   */
  default CompletableFuture<CreateUpdateKey> addApiKey(
      @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #updateApiKey(String, ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @return the updated key
   */
  default CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @param requestOptions Options to pass to this request
   * @return the updated key
   */
  default CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }
}

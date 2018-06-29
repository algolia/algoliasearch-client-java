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

  /** Deprecated: use {@link AsyncAPIClient#listApiKeys()} */
  @Deprecated
  default CompletableFuture<List<ApiKey>> listKeys() {
    return listApiKeys();
  }

  /** Deprecated: use {@link AsyncAPIClient#listApiKeys()} */
  @Deprecated
  default CompletableFuture<List<ApiKey>> listApiKeys() {
    return listApiKeys(RequestOptions.empty);
  }

  /** Deprecated: use {@link AsyncAPIClient#listApiKeys(RequestOptions)} */
  @Deprecated
  default CompletableFuture<List<ApiKey>> listApiKeys(@Nonnull RequestOptions requestOptions) {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /** Deprecated: use {@link AsyncAPIClient#getApiKey(String)} */
  @Deprecated
  default CompletableFuture<Optional<ApiKey>> getKey(@Nonnull String key) {
    return getApiKey(key);
  }

  /** Deprecated: use {@link AsyncAPIClient#getApiKey(String)} */
  @Deprecated
  default CompletableFuture<Optional<ApiKey>> getApiKey(@Nonnull String key) {
    return getApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link AsyncAPIClient#getApiKey(String, RequestOptions)} */
  @Deprecated
  default CompletableFuture<Optional<ApiKey>> getApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link AsyncAPIClient#deleteApiKey(String)} */
  @Deprecated
  default CompletableFuture<DeleteKey> deleteKey(@Nonnull String key) {
    return deleteApiKey(key);
  }

  /** Deprecated: use {@link AsyncAPIClient#deleteApiKey(String)} */
  @Deprecated
  default CompletableFuture<DeleteKey> deleteApiKey(@Nonnull String key) {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link AsyncAPIClient#deleteApiKey(String, RequestOptions)} */
  @Deprecated
  default CompletableFuture<DeleteKey> deleteApiKey(
      @Nonnull String key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link AsyncAPIClient#addApiKey(ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> addKey(@Nonnull ApiKey key) {
    return addApiKey(key);
  }

  /** Deprecated: use {@link AsyncAPIClient#addApiKey(ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> addApiKey(@Nonnull ApiKey key) {
    return addApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link AsyncAPIClient#addApiKey(ApiKey, RequestOptions)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> addApiKey(
      @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link AsyncAPIClient#updateApiKey(String, ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key);
  }

  /** Deprecated: use {@link AsyncAPIClient#updateApiKey(String, ApiKey)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key) {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /** Deprecated: use {@link AsyncAPIClient#updateApiKey(String, ApiKey, RequestOptions)} */
  @Deprecated
  default CompletableFuture<CreateUpdateKey> updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions) {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }
}

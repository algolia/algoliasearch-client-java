package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.ApiKey;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.CreateUpdateKey;
import com.algolia.search.responses.DeleteKey;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface SyncKey<T> extends SyncBaseIndex<T> {

  /** Deprecated: use {@link APIClient#listApiKeys()} */
  @Deprecated
  default List<ApiKey> listKeys() throws AlgoliaException {
    return listApiKeys();
  }

  /** Deprecated: use {@link APIClient#listApiKeys()} */
  @Deprecated
  default List<ApiKey> listApiKeys() throws AlgoliaException {
    return listApiKeys(RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#listApiKeys(RequestOptions)} */
  @Deprecated
  default List<ApiKey> listApiKeys(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /** Deprecated: use {@link APIClient#getApiKey(String)} */
  @Deprecated
  default Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key);
  }

  /** Deprecated: use {@link APIClient#getApiKey(String)} */
  @Deprecated
  default Optional<ApiKey> getApiKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#getApiKey(String, RequestOptions)} */
  @Deprecated
  default Optional<ApiKey> getApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link APIClient#deleteApiKey(String)} */
  @Deprecated
  default DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key);
  }

  /** Deprecated: use {@link APIClient#deleteApiKey(String)} */
  @Deprecated
  default DeleteKey deleteApiKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#deleteApiKey(String, RequestOptions)} */
  @Deprecated
  default DeleteKey deleteApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link APIClient#addApiKey(ApiKey)} */
  @Deprecated
  default CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key);
  }

  /** Deprecated: use {@link APIClient#addApiKey(ApiKey)} */
  @Deprecated
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key, RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#addApiKey(ApiKey, RequestOptions)} */
  @Deprecated
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link APIClient#updateApiKey(String, ApiKey)} */
  @Deprecated
  default CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#updateApiKey(String, ApiKey)} */
  @Deprecated
  default CreateUpdateKey updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /** Deprecated: use {@link APIClient#updateApiKey(String, ApiKey, RequestOptions)} */
  @Deprecated
  default CreateUpdateKey updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }

  /** Deprecated: use {@link APIClient#updateApiKey(String, ApiKey, RequestOptions)} */
  @Deprecated
  default CreateUpdateKey updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }
}

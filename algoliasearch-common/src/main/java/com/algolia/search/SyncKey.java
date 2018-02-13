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

  /** Deprecated: use {@link #listApiKeys()} */
  @Deprecated
  default List<ApiKey> listKeys() throws AlgoliaException {
    return listApiKeys();
  }

  /**
   * List keys of this index
   *
   * @return the list of keys
   */
  default List<ApiKey> listApiKeys() throws AlgoliaException {
    return listApiKeys(RequestOptions.empty);
  }

  /**
   * List keys of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the list of keys
   */
  default List<ApiKey> listApiKeys(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().listKeys(getName(), requestOptions);
  }

  /** Deprecated: use {@link #getApiKey(String)} */
  @Deprecated
  default Optional<ApiKey> getKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @return the key
   */
  default Optional<ApiKey> getApiKey(@Nonnull String key) throws AlgoliaException {
    return getApiKey(key, RequestOptions.empty);
  }

  /**
   * Get a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the key
   */
  default Optional<ApiKey> getApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #deleteApiKey(String)} */
  @Deprecated
  default DeleteKey deleteKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @return the deleted key
   */
  default DeleteKey deleteApiKey(@Nonnull String key) throws AlgoliaException {
    return deleteApiKey(key, RequestOptions.empty);
  }

  /**
   * Delete a key by name from this index
   *
   * @param key the key name
   * @param requestOptions Options to pass to this request
   * @return the deleted key
   */
  default DeleteKey deleteApiKey(@Nonnull String key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #addApiKey(ApiKey)} */
  @Deprecated
  default CreateUpdateKey addKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @return the created key
   */
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key) throws AlgoliaException {
    return addApiKey(key, RequestOptions.empty);
  }

  /**
   * Add a key to this index
   *
   * @param key the key
   * @param requestOptions Options to pass to this request
   * @return the created key
   */
  default CreateUpdateKey addApiKey(@Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().addKey(getName(), key, requestOptions);
  }

  /** Deprecated: use {@link #updateApiKey(String, ApiKey)} */
  @Deprecated
  default CreateUpdateKey updateKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @return the updated key
   */
  default CreateUpdateKey updateApiKey(@Nonnull String keyName, @Nonnull ApiKey key)
      throws AlgoliaException {
    return updateApiKey(keyName, key, RequestOptions.empty);
  }

  /** Deprecated: use {@link #updateApiKey(String, ApiKey, RequestOptions)} */
  @Deprecated
  default CreateUpdateKey updateKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }

  /**
   * Update a key by name from this index
   *
   * @param keyName the key name
   * @param key the key to update
   * @param requestOptions Options to pass to this request
   * @return the updated key
   */
  default CreateUpdateKey updateApiKey(
      @Nonnull String keyName, @Nonnull ApiKey key, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().updateApiKey(getName(), keyName, key, requestOptions);
  }
}

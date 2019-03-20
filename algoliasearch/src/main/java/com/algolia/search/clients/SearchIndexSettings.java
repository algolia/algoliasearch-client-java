package com.algolia.search.clients;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchIndexSettings<T> extends SearchIndexBase<T> {

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   */
  default SetSettingsResponse setSettings(@Nonnull IndexSettings settings) {
    return LaunderThrowable.unwrap(setSettingsAsync(settings));
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   */
  default CompletableFuture<SetSettingsResponse> setSettingsAsync(@Nonnull IndexSettings settings) {
    return setSettingsAsync(settings, new RequestOptions());
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @param forwardToReplicas if true forward the settings to the replicas
   */
  default CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {

    return setSettingsAsync(settings, forwardToReplicas, new RequestOptions());
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @param forwardToReplicas if true forward the settings to the replicas
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return setSettingsAsync(settings, requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<SetSettingsResponse> setSettingsAsync(
      @Nonnull IndexSettings settings, RequestOptions requestOptions) {

    Objects.requireNonNull(settings, "Index settings are required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + getUrlEncodedIndexName() + "/settings",
            CallType.WRITE,
            settings,
            SetSettingsResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /** Get the settings of an index. */
  default IndexSettings getSettings() {
    return LaunderThrowable.unwrap(getSettingsAsync(null));
  }

  /**
   * Get the settings of an index.
   *
   * @param requestOptions Options to pass to this request
   */
  default IndexSettings getSettings(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getSettingsAsync(requestOptions));
  }

  /** Get the settings of an index. */
  default CompletableFuture<IndexSettings> getSettingsAsync() {
    return getSettingsAsync(null);
  }

  /**
   * Get the settings of an index.
   *
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<IndexSettings> getSettingsAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/indexes/" + getUrlEncodedIndexName() + "/settings",
            CallType.READ,
            IndexSettings.class,
            requestOptions);
  }
}

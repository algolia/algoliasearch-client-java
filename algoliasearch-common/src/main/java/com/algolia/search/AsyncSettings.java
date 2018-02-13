package com.algolia.search;

import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncSettings<T> extends AsyncBaseIndex<T> {

  /**
   * Get settings of this index
   *
   * @return the settings
   */
  default CompletableFuture<IndexSettings> getSettings() {
    return getSettings(RequestOptions.empty);
  }

  /**
   * Get settings of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the settings
   */
  default CompletableFuture<IndexSettings> getSettings(@Nonnull RequestOptions requestOptions) {
    return getApiClient().getSettings(getName(), requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(@Nonnull IndexSettings settings) {
    return setSettings(settings, false);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(
      @Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions) {
    return setSettings(settings, false, requestOptions);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the replicas
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(
      @Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas) {
    return setSettings(settings, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @param requestOptions Options to pass to this request
   * @return the associated AsyncTask
   */
  default CompletableFuture<AsyncTask> setSettings(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient().setSettings(getName(), settings, forwardToReplicas, requestOptions);
  }
}

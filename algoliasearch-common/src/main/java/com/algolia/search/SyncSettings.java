package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import javax.annotation.Nonnull;

public interface SyncSettings<T> extends SyncBaseIndex<T> {

  /**
   * Get settings of this index
   *
   * @return the settings
   */
  default IndexSettings getSettings() throws AlgoliaException {
    return getSettings(RequestOptions.empty);
  }

  /**
   * Get settings of this index
   *
   * @param requestOptions Options to pass to this request
   * @return the settings
   */
  default IndexSettings getSettings(@Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getSettings(getName(), requestOptions);
  }

  /**
   * Set settings of this index, and do not forward to replicas
   *
   * @param settings the settings to set
   * @return the associated Task
   */
  default Task setSettings(@Nonnull IndexSettings settings) throws AlgoliaException {
    return setSettings(settings, RequestOptions.empty);
  }

  /**
   * Set settings of this index, and do not forward to slaves
   *
   * @param settings the settings to set
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task setSettings(@Nonnull IndexSettings settings, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return setSettings(settings, false, requestOptions);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the replicas
   * @return the associated Task
   */
  default Task setSettings(@Nonnull IndexSettings settings, @Nonnull Boolean forwardToReplicas)
      throws AlgoliaException {
    return setSettings(settings, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Set settings of this index
   *
   * @param settings the settings to set
   * @param forwardToReplicas should these updates be forwarded to the slaves
   * @param requestOptions Options to pass to this request
   * @return the associated Task
   */
  default Task setSettings(
      @Nonnull IndexSettings settings,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().setSettings(getName(), settings, forwardToReplicas, requestOptions);
  }
}

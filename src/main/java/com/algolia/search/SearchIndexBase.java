package com.algolia.search;

import com.algolia.search.models.RequestOptions;

/**
 * This interface holds all index common methods.
 *
 * @param <T>
 */
public interface SearchIndexBase<T> {
  HttpTransport getTransport();

  String getUrlEncodedIndexName();

  SearchConfig getConfig();

  Class<T> getKlass();

  void waitTask(long taskId, long timeToWait, RequestOptions requestOptions);

  void waitTask(long taskId);
}

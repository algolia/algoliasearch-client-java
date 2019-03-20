package com.algolia.search;

import com.algolia.search.models.RequestOptions;

public interface SearchIndexBase<T> {
  HttpTransport getTransport();

  String getUrlEncodedIndexName();

  SearchConfig getConfig();

  Class<T> getKlass();

  void waitTask(long taskId, long timeToWait, RequestOptions requestOptions);

  void waitTask(long taskId);
}

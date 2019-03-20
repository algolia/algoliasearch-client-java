package com.algolia.search.clients;

import com.algolia.search.models.RequestOptions;
import com.algolia.search.transport.HttpTransport;

public interface SearchIndexBase<T> {
  HttpTransport getTransport();

  String getUrlEncodedIndexName();

  SearchConfig getConfig();

  Class<T> getKlass();

  void waitTask(long taskId, long timeToWait, RequestOptions requestOptions);

  void waitTask(long taskId);
}

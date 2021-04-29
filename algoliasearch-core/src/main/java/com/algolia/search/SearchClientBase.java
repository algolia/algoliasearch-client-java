package com.algolia.search;

import com.algolia.search.models.RequestOptions;
import javax.annotation.Nonnull;

public interface SearchClientBase {
  HttpTransport getTransport();

  ConfigBase getConfig();

  void waitTask(
      @Nonnull String indexName, long taskId, long timeToWait, RequestOptions requestOptions);

  void waitTask(@Nonnull String indexName, long taskId);

  void waitAppTask(long taskId, long timeToWait, RequestOptions requestOptions);

  void waitAppTask(long taskId);
}

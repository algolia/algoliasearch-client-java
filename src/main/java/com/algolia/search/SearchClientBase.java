package com.algolia.search;

import com.algolia.search.models.RequestOptions;
import javax.annotation.Nonnull;

public interface SearchClientBase {
  HttpTransport getTransport();

  AlgoliaConfigBase getConfig();

  void waitTask(
      @Nonnull String indexName, long taskId, long timeToWait, RequestOptions requestOptions);

  void waitTask(@Nonnull String indexName, long taskId);
}

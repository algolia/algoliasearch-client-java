package com.algolia.search.integration.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.models.common.LogType;
import com.algolia.search.models.indexing.IndicesResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public abstract class LogsTest {
  protected final SearchClient searchClient;

  protected LogsTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testLog() throws ExecutionException, InterruptedException {
    CompletableFuture<List<IndicesResponse>> listIndices1 = searchClient.listIndicesAsync();
    CompletableFuture<List<IndicesResponse>> listIndices2 = searchClient.listIndicesAsync();

    CompletableFuture.allOf(listIndices1, listIndices2).get();
    searchClient
        .getLogsAsync(0, 2, LogType.LOG_ALL.getName())
        .thenApply(
            r -> {
              assertThat(r).hasSize(2);
              return r;
            })
        .get();
  }
}

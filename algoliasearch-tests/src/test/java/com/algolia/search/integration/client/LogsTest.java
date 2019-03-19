package com.algolia.search.integration.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.models.common.IndicesResponse;
import com.algolia.search.models.common.Log;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class LogsTest extends AlgoliaBaseIntegrationTest {

  @Test
  void testLog() throws ExecutionException, InterruptedException {
    CompletableFuture<List<IndicesResponse>> listIndices1 = searchClient.listIndicesAsync();
    CompletableFuture<List<IndicesResponse>> listIndices2 = searchClient.listIndicesAsync();

    CompletableFuture.allOf(listIndices1, listIndices2).get();
    CompletableFuture<List<Log>> logs =
        searchClient
            .getLogsAsync(0, 2)
            .thenApply(
                r -> {
                  assertEquals(2, r.size());
                  return r;
                });
  }
}

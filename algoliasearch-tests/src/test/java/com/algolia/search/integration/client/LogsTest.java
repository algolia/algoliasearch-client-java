package com.algolia.search.integration.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.models.IndicesResponse;
import com.algolia.search.objects.Log;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class LogsTest extends AlgoliaBaseIntegrationTest {

  @Test
  void testLog() {
    CompletableFuture<List<IndicesResponse>> listIndices1 = searchClient.listIndicesAsync();
    CompletableFuture<List<IndicesResponse>> listIndices2 = searchClient.listIndicesAsync();

    CompletableFuture.allOf(listIndices1, listIndices2).join();
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

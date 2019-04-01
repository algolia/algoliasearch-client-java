package com.algolia.search.integration.client;

import static com.algolia.search.integration.AlgoliaIntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.integration.AlgoliaIntegrationTestExtension;
import com.algolia.search.models.indexing.IndicesResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({AlgoliaIntegrationTestExtension.class})
class LogsTest {

  @Test
  void testLog() throws ExecutionException, InterruptedException {
    CompletableFuture<List<IndicesResponse>> listIndices1 = searchClient.listIndicesAsync();
    CompletableFuture<List<IndicesResponse>> listIndices2 = searchClient.listIndicesAsync();

    CompletableFuture.allOf(listIndices1, listIndices2).get();
    searchClient
        .getLogsAsync(0, 2)
        .thenApply(
            r -> {
              assertThat(r).hasSize(2);
              return r;
            });
  }
}

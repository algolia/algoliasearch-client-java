package com.algolia.search.integration.index;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.ObjectToBatch;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.BatchOperation;
import com.algolia.search.models.indexing.BatchRequest;
import com.algolia.search.models.indexing.BatchResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public abstract class BatchingTest {

  protected final SearchClient searchClient;

  protected BatchingTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testBatching() throws ExecutionException, InterruptedException {
    SearchIndex<ObjectToBatch> index =
        searchClient.initIndex(getTestIndexName("index_batching"), ObjectToBatch.class);

    List<ObjectToBatch> batchOne =
        Arrays.asList(
            new ObjectToBatch("one", "value"),
            new ObjectToBatch("two", "value"),
            new ObjectToBatch("three", "value"),
            new ObjectToBatch("four", "value"),
            new ObjectToBatch("five", "value"));

    CompletableFuture<BatchIndexingResponse> batchOneFuture = index.saveObjectsAsync(batchOne);

    List<BatchOperation<ObjectToBatch>> operations =
        Arrays.asList(
            BatchOperation.createAddObject(new ObjectToBatch("zero", "value")),
            BatchOperation.createUpdateObject(new ObjectToBatch("one", "v")),
            BatchOperation.createPartialUpdateObject(new ObjectToBatch("two", "v")),
            BatchOperation.createPartialUpdateObject(new ObjectToBatch("two_bis", "value")),
            BatchOperation.createPartialUpdateObjectNoCreate(new ObjectToBatch("three", "v")),
            BatchOperation.createDeleteObject(new ObjectToBatch("four")));

    batchOneFuture.get().waitTask();

    CompletableFuture<BatchResponse> batchTwoFuture =
        index.batchAsync(new BatchRequest<>(operations));

    batchTwoFuture.get().waitTask();

    List<ObjectToBatch> objectsFromIterator = new ArrayList<>(Collections.emptyList());
    IndexIterable<ObjectToBatch> indexIterable = new IndexIterable<>(index);

    for (ObjectToBatch item : indexIterable) {
      objectsFromIterator.add(item);
    }

    assertThat(objectsFromIterator).hasSize(6);

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("zero"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(
            operations.stream()
                .map(BatchOperation::getBody)
                .filter(r -> r.getObjectID().equals("zero"))
                .findFirst()
                .get());

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("one"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(
            operations.stream()
                .map(BatchOperation::getBody)
                .filter(r -> r.getObjectID().equals("one"))
                .findFirst()
                .get());

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("two"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(
            operations.stream()
                .map(BatchOperation::getBody)
                .filter(r -> r.getObjectID().equals("two"))
                .findFirst()
                .get());

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("two_bis"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(
            operations.stream()
                .map(BatchOperation::getBody)
                .filter(r -> r.getObjectID().equals("two_bis"))
                .findFirst()
                .get());

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("three"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(
            operations.stream()
                .map(BatchOperation::getBody)
                .filter(r -> r.getObjectID().equals("three"))
                .findFirst()
                .get());

    assertThat(
            objectsFromIterator.stream()
                .filter(r -> r.getObjectID().equals("five"))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(batchOne.stream().filter(r -> r.getObjectID().equals("five")).findFirst().get());

    assertThat(objectsFromIterator).extracting(ObjectToBatch::getObjectID).doesNotContain("four");
  }
}

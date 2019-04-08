package com.algolia.search.integration.client;

import static com.algolia.search.integration.IntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.integration.IntegrationTestExtension;
import com.algolia.search.integration.models.AlgoliaMultipleOpObject;
import com.algolia.search.models.indexing.*;
import com.algolia.search.models.indexing.BatchOperation;
import com.algolia.search.models.indexing.Query;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class MultipleOperationsTest {

  private String index1Name;
  private String index2Name;

  MultipleOperationsTest() {
    index1Name = getTestIndexName("multiple_operations");
    index2Name = getTestIndexName("multiple_operations_dev");
  }

  @Test
  void testMultipleOperation() {

    List<BatchOperation<AlgoliaMultipleOpObject>> objectsToSave =
        Arrays.asList(
            new BatchOperation<>(
                index1Name, ActionEnum.ADD_OBJECT, new AlgoliaMultipleOpObject("Jimmie")),
            new BatchOperation<>(
                index1Name, ActionEnum.ADD_OBJECT, new AlgoliaMultipleOpObject("Jimmie")),
            new BatchOperation<>(
                index2Name, ActionEnum.ADD_OBJECT, new AlgoliaMultipleOpObject("Jimmie")),
            new BatchOperation<>(
                index2Name, ActionEnum.ADD_OBJECT, new AlgoliaMultipleOpObject("Jimmie")));

    MultipleIndexBatchIndexingResponse saveMultiple =
        searchClient.multipleBatchAsync(objectsToSave).join();

    saveMultiple.waitTask();

    List<MultipleGetObject> objectsToRetrieve =
        Arrays.asList(
            new MultipleGetObject(index1Name, saveMultiple.getObjectIDs().get(0)),
            new MultipleGetObject(index1Name, saveMultiple.getObjectIDs().get(1)),
            new MultipleGetObject(index2Name, saveMultiple.getObjectIDs().get(2)),
            new MultipleGetObject(index2Name, saveMultiple.getObjectIDs().get(3)));

    MultipleGetObjectsResponse<AlgoliaMultipleOpObject> multipleGet =
        searchClient
            .multipleGetObjectsAsync(objectsToRetrieve, AlgoliaMultipleOpObject.class)
            .join();

    // Asserting that objects are the same

    assertThat(multipleGet.getResults()).hasSize(4);

    assertThat(multipleGet.getResults())
        .extracting(AlgoliaMultipleOpObject::getFirstName)
        .containsExactly("Jimmie", "Jimmie", "Jimmie", "Jimmie");

    assertThat(multipleGet.getResults())
        .extracting(AlgoliaMultipleOpObject::getObjectID)
        .contains(
            saveMultiple.getObjectIDs().get(0),
            saveMultiple.getObjectIDs().get(1),
            saveMultiple.getObjectIDs().get(2),
            saveMultiple.getObjectIDs().get(3));

    // Performing multiple queries
    List<MultipleQueries> multipleSearch =
        Arrays.asList(
            new MultipleQueries(index1Name, new Query().setHitsPerPage(2)),
            new MultipleQueries(index2Name, new Query().setHitsPerPage(2)));

    MultipleQueriesRequest request = new MultipleQueriesRequest(StrategyType.NONE, multipleSearch);
    MultipleQueriesRequest request2 =
        new MultipleQueriesRequest(StrategyType.STOP_IF_ENOUGH_MATCHES, multipleSearch);

    CompletableFuture<MultipleQueriesResponse<AlgoliaMultipleOpObject>> multiSearchFuture =
        searchClient.multipleQueriesAsync(request, AlgoliaMultipleOpObject.class);
    CompletableFuture<MultipleQueriesResponse<AlgoliaMultipleOpObject>> multiSearchFuture2 =
        searchClient.multipleQueriesAsync(request2, AlgoliaMultipleOpObject.class);

    CompletableFuture.allOf(multiSearchFuture, multiSearchFuture2);

    MultipleQueriesResponse<AlgoliaMultipleOpObject> multiSearch = multiSearchFuture.join();
    MultipleQueriesResponse<AlgoliaMultipleOpObject> multiSearch2 = multiSearchFuture2.join();

    // Asserting expected results

    assertThat(multiSearch.getResults()).hasSize(2);
    assertThat(multiSearch.getResults().get(0).getHits()).hasSize(2);
    assertThat(multiSearch.getResults().get(1).getHits()).hasSize(2);

    assertThat(multiSearch2.getResults()).hasSize(2);
    assertThat(multiSearch2.getResults().get(0).getHits()).hasSize(2);
    assertThat(multiSearch2.getResults().get(1).getHits()).isEmpty();
  }
}

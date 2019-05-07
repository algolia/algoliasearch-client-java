package com.algolia.search.integration.index;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.AlgoliaIndexingObject;
import com.algolia.search.integration.models.DeleteByObject;
import com.algolia.search.models.indexing.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public abstract class IndexingTest {

  private final SearchClient searchClient;

  protected IndexingTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testIndexingOperations() throws ExecutionException, InterruptedException {
    String indexName = getTestIndexName("indexing");
    SearchIndex<AlgoliaIndexingObject> index =
        searchClient.initIndex(indexName, AlgoliaIndexingObject.class);

    // AddObject with ID
    AlgoliaIndexingObject objectOne = new AlgoliaIndexingObject("one");
    CompletableFuture<BatchIndexingResponse> addObjectOneFuture = index.saveObjectAsync(objectOne);

    // AddObject without ID
    AlgoliaIndexingObject objectWoId = new AlgoliaIndexingObject();
    CompletableFuture<BatchIndexingResponse> addObjectWoIdFuture =
        index.saveObjectAsync(objectWoId, true);

    // Save two objects with objectID
    List<AlgoliaIndexingObject> objectsWithIds =
        Arrays.asList(new AlgoliaIndexingObject("two"), new AlgoliaIndexingObject("three"));
    CompletableFuture<BatchIndexingResponse> objectsWithIdsFuture = index.saveObjectsAsync(objectsWithIds, true);

    // Save two objects w/o objectIDs
    List<AlgoliaIndexingObject> objectsWoIds =
        Arrays.asList(
            new AlgoliaIndexingObject().setProperty("addObjectsWoId"),
            new AlgoliaIndexingObject().setProperty("addObjectsWoId"));
    CompletableFuture<BatchIndexingResponse> addObjectsWoIdsFuture =
        index.saveObjectsAsync(objectsWoIds, true);

    // Batch 1000 objects
    List<AlgoliaIndexingObject> objectsToBatch = new ArrayList<>(1010);
    List<String> ids = new ArrayList<>(1010);

    for (int i = 0; i < 1000; i++) {
      String id = String.valueOf(i + 1);
      objectsToBatch.add(new AlgoliaIndexingObject(id, "Property" + id));
      ids.add(id);
    }

    CompletableFuture<BatchIndexingResponse> batchFuture = index.saveObjectsAsync(objectsToBatch);

    List<CompletableFuture<BatchIndexingResponse>> futures =
        Arrays.asList(addObjectOneFuture, addObjectWoIdFuture, objectsWithIdsFuture, addObjectsWoIdsFuture, batchFuture);

    // Wait for futures to finish
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenRun(
            () ->
                futures.forEach(
                    r -> r.join().waitTask())) // Wait for Algolia's task to finish (indexing)
        .get();

    List<String> generatedId = addObjectWoIdFuture.get().getResponses().get(0).getObjectIDs();
    objectWoId.setObjectID(generatedId.get(0));

    List<String> generatedIds = addObjectsWoIdsFuture.get().getResponses().get(0).getObjectIDs();
    objectsWoIds.get(0).setObjectID(generatedIds.get(0));
    objectsWoIds.get(1).setObjectID(generatedIds.get(1));

    List<String> sixFirstRecordsIds =
        Stream.of(Arrays.asList("one", "two", "three"), generatedId, generatedIds)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    List<AlgoliaIndexingObject> sixFirstRecord = index.getObjectsAsync(sixFirstRecordsIds).get();
    assertThat(sixFirstRecord).hasSize(6);

    List<AlgoliaIndexingObject> objectsToCompare =
        Stream.of(
                Collections.singletonList(objectOne),
                objectsWithIds,
                Collections.singletonList(objectWoId),
                objectsWoIds)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    for (int i = 0; i < objectsToCompare.size(); i++) {
      assertThat(objectsToCompare.get(i))
          .usingRecursiveComparison()
          .isEqualTo(sixFirstRecord.get(i));
    }

    // 1000 records
    List<AlgoliaIndexingObject> batchResponse = index.getObjectsAsync(ids).get();
    assertThat(batchResponse).hasSize(1000);

    for (int i = 0; i < batchResponse.size(); i++) {
      assertThat(batchResponse.get(i)).usingRecursiveComparison().isEqualTo(objectsToBatch.get(i));
    }

    // Browse all index to assert that we have 1006 objects
    List<AlgoliaIndexingObject> objectsBrowsed = new ArrayList<>();

    for (AlgoliaIndexingObject object : index.browseObjects(new BrowseIndexQuery())) {
      objectsBrowsed.add(object);
    }

    assertThat(objectsBrowsed).hasSize(1006);

    // Update one object
    AlgoliaIndexingObject objectToPartialUpdate = objectsToBatch.get(0);
    objectToPartialUpdate.setProperty("PartialUpdated");

    index.partialUpdateObjectAsync(objectToPartialUpdate).get().waitTask();

    AlgoliaIndexingObject updatedObject =
        index.getObjectAsync(objectToPartialUpdate.getObjectID()).get();
    assertThat(updatedObject).usingRecursiveComparison().isEqualTo(objectToPartialUpdate);

    // Update two objects
    AlgoliaIndexingObject objectToPartialUpdate1 =
        objectsToBatch.get(1).setProperty("PartialUpdated1");
    AlgoliaIndexingObject objectToPartialUpdate2 =
        objectsToBatch.get(2).setProperty("PartialUpdated2");

    BatchIndexingResponse partialUpdateObjects =
        index
            .partialUpdateObjectsAsync(
                Arrays.asList(objectToPartialUpdate1, objectToPartialUpdate2))
            .get();
    partialUpdateObjects.waitTask();

    List<AlgoliaIndexingObject> updatedObjects =
        index
            .getObjectsAsync(
                Arrays.asList(
                    objectToPartialUpdate1.getObjectID(), objectToPartialUpdate2.getObjectID()))
            .get();

    assertThat(objectToPartialUpdate1).usingRecursiveComparison().isEqualTo(updatedObjects.get(0));
    assertThat(objectToPartialUpdate2).usingRecursiveComparison().isEqualTo(updatedObjects.get(1));

    // Delete six first objects
    index.deleteObjectsAsync(sixFirstRecordsIds).get().waitTask();

    // Browse all index to assert that we have 1006 objects
    List<AlgoliaIndexingObject> objectsBrowsedAfterDelete = new ArrayList<>();

    for (AlgoliaIndexingObject object : index.browseObjects(new BrowseIndexQuery())) {
      objectsBrowsedAfterDelete.add(object);
    }

    assertThat(objectsBrowsedAfterDelete).hasSize(1000);

    // Delete remaining objects
    index.deleteObjectsAsync(ids).get().waitTask();

    // Assert that all objects were deleted
    SearchResult<AlgoliaIndexingObject> search = index.searchAsync(new Query("")).get();
    assertThat(search.getHits()).hasSize(0);
  }

  @Test
  void deleteByTest() throws ExecutionException, InterruptedException {
    String indexDeleteByName = getTestIndexName("delete_by");
    SearchIndex<DeleteByObject> indexDeleteBy =
        searchClient.initIndex(indexDeleteByName, DeleteByObject.class);

    List<DeleteByObject> objectsToBatch = new ArrayList<>(10);

    for (int i = 0; i < 10; i++) {
      String id = String.valueOf(i + 1);
      objectsToBatch.add(new DeleteByObject(id, Collections.singletonList("car")));
    }

    indexDeleteBy.saveObjectsAsync(objectsToBatch).get().waitTask();

    indexDeleteBy.deleteObjectAsync("1").get().waitTask();

    SearchResult<DeleteByObject> searchAfterDelete = indexDeleteBy.searchAsync(new Query()).get();
    assertThat(searchAfterDelete.getHits()).hasSize(9);

    indexDeleteBy
        .deleteByAsync(
            new Query().setTagFilters(Collections.singletonList(Collections.singletonList("car"))))
        .get()
        .waitTask();

    SearchResult<DeleteByObject> searchAfterDeleteBy = indexDeleteBy.searchAsync(new Query()).get();
    assertThat(searchAfterDeleteBy.getHits()).hasSize(0);
  }

  @Test
  void testClearObjects() throws ExecutionException, InterruptedException {
    String indexClearName = getTestIndexName("clear_objects");
    SearchIndex<AlgoliaIndexingObject> indexClear =
        searchClient.initIndex(indexClearName, AlgoliaIndexingObject.class);

    List<AlgoliaIndexingObject> objectsToBatch = new ArrayList<>(10);

    for (int i = 0; i < 10; i++) {
      String id = String.valueOf(i + 1);
      objectsToBatch.add(new AlgoliaIndexingObject(id));
    }

    indexClear.saveObjectsAsync(objectsToBatch).get().waitTask();

    indexClear.clearObjectsAsync().get().waitTask();

    SearchResult<AlgoliaIndexingObject> searchAfterDeleteBy =
        indexClear.searchAsync(new Query()).get();
    assertThat(searchAfterDeleteBy.getHits()).hasSize(0);
  }

  @Test
  void MoveIndexTest() throws ExecutionException, InterruptedException {
    String indexMoveName = getTestIndexName("move_test_source");
    SearchIndex<AlgoliaIndexingObject> indexMove =
        searchClient.initIndex(indexMoveName, AlgoliaIndexingObject.class);

    indexMove.saveObjectAsync(new AlgoliaIndexingObject("one")).get().waitTask();

    String indexDestName = getTestIndexName("move_test_dest");

    searchClient.moveIndexAsync(indexMoveName, indexDestName).get().waitTask();

    List<IndicesResponse> indices = searchClient.listIndicesAsync().get();
    assertThat(indices).extracting(IndicesResponse::getName).contains(indexDestName);
    assertThat(indices).extracting(IndicesResponse::getName).doesNotContain(indexMoveName);
  }
}

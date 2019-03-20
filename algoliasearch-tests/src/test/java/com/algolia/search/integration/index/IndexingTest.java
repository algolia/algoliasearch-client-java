package com.algolia.search.integration.index;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchIndex;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.BrowseIndexQuery;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class IndexingTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex<AlgoliaMock> index;
  private SearchIndex<AlgoliaMock> indexDeleteBy;
  private SearchIndex<AlgoliaMock> indexMove;
  private SearchIndex<AlgoliaMock> indexClear;

  private String indexName;
  private String indexDeleteByName;
  private String indexMoveName;
  private String indexClearName;

  void init() {
    indexName = getTestIndexName("indexing");
    index = searchClient.initIndex(indexName, AlgoliaMock.class);

    indexDeleteByName = getTestIndexName("delete_by");
    indexDeleteBy = searchClient.initIndex(indexDeleteByName, AlgoliaMock.class);

    indexMoveName = getTestIndexName("move_test_source");
    indexMove = searchClient.initIndex(indexMoveName, AlgoliaMock.class);

    indexClearName = getTestIndexName("clear_objects");
    indexClear = searchClient.initIndex(indexClearName, AlgoliaMock.class);
  }

  @Test
  void testIndexingOperations() throws ExecutionException, InterruptedException {
    init();

    // AddObject with ID
    AlgoliaMock objectOne = new AlgoliaMock("one");
    CompletableFuture<BatchIndexingResponse> addObjectOneFuture = index.saveObjectAsync(objectOne);

    // AddObject without ID
    AlgoliaMock objectWoId = new AlgoliaMock();
    CompletableFuture<BatchIndexingResponse> addObjectWoIdFuture =
        index.saveObjectAsync(objectWoId, true);

    // Save two objects with objectID
    List<AlgoliaMock> objectsWithIds =
        Arrays.asList(new AlgoliaMock("two"), new AlgoliaMock("three"));
    CompletableFuture<BatchIndexingResponse> addObjectsFuture =
        index.saveObjectsAsync(objectsWithIds, true);

    // Save two objects w/o objectIDs
    List<AlgoliaMock> objectsWoIds =
        Arrays.asList(
            new AlgoliaMock().setProperty("addObjectsWoId"),
            new AlgoliaMock().setProperty("addObjectsWoId"));
    CompletableFuture<BatchIndexingResponse> addObjectsWoIdsFuture =
        index.saveObjectsAsync(objectsWoIds, true);

    // Batch 1000 objects
    List<AlgoliaMock> objectsToBatch = new ArrayList<>(1000);
    List<String> ids = new ArrayList<>(1000);

    for (int i = 0; i < 1000; i++) {
      String id = String.valueOf(i + 1);
      objectsToBatch.add(new AlgoliaMock(id, "Property" + id));
      ids.add(id);
    }

    CompletableFuture<BatchIndexingResponse> batchFuture = index.saveObjectsAsync(objectsToBatch);

    List<CompletableFuture<BatchIndexingResponse>> futures =
        Arrays.asList(addObjectOneFuture, addObjectWoIdFuture, addObjectsWoIdsFuture, batchFuture);

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

    List<AlgoliaMock> sixFirstRecord = index.getObjectsAsync(sixFirstRecordsIds).get();
    assertThat(sixFirstRecord).hasSize(6);

    List<AlgoliaMock> objectsToCompare =
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
    List<AlgoliaMock> batchResponse = index.getObjectsAsync(ids).get();
    assertThat(batchResponse).hasSize(1000);

    for (int i = 0; i < batchResponse.size(); i++) {
      assertThat(batchResponse.get(i)).usingRecursiveComparison().isEqualTo(objectsToBatch.get(i));
    }

    // Browse all index to assert that we have 1006 objects
    List<AlgoliaMock> objectsBrowsed = new ArrayList<>();

    for (AlgoliaMock object : index.browse(new BrowseIndexQuery())) {
      objectsBrowsed.add(object);
    }

    assertThat(objectsBrowsed).hasSize(1006);

    // Update one object
    AlgoliaMock objectToPartialUpdate = objectsToBatch.get(0);
    objectToPartialUpdate.setProperty("PartialUpdated");

    index.partialUpdateObjectAsync(objectToPartialUpdate).get().waitTask();

    AlgoliaMock updatedObject = index.getObjectAsync(objectToPartialUpdate.getObjectID()).get();
    assertThat(updatedObject).usingRecursiveComparison().isEqualTo(objectToPartialUpdate);

    // Update two objects
    AlgoliaMock objectToPartialUpdate1 = objectsToBatch.get(1).setProperty("PartialUpdated1");
    AlgoliaMock objectToPartialUpdate2 = objectsToBatch.get(2).setProperty("PartialUpdated2");

    BatchIndexingResponse partialUpdateObjects =
        index
            .partialUpdateObjectsAsync(
                Arrays.asList(objectToPartialUpdate1, objectToPartialUpdate2))
            .get();
    partialUpdateObjects.waitTask();

    List<AlgoliaMock> updatedObjects =
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
    List<AlgoliaMock> objectsBrowsedAfterDelete = new ArrayList<>();

    for (AlgoliaMock object : index.browse(new BrowseIndexQuery())) {
      objectsBrowsedAfterDelete.add(object);
    }

    assertThat(objectsBrowsedAfterDelete).hasSize(1000);

    // Delete remaining objects
    index.deleteObjectsAsync(ids).get().waitTask();

    // Assert that all objects were deleted
    SearchResult<AlgoliaMock> search = index.searchAsync(new Query("")).get();
    assertThat(search.getHits()).hasSize(0);
  }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("WeakerAccess")
class AlgoliaMock extends AlgoliaWithId {

  public String getProperty() {
    return property;
  }

  AlgoliaMock setProperty(String property) {
    this.property = property;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public AlgoliaMock setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  private String property = "default";

  @JsonProperty("_tags")
  private List<String> tags;

  public AlgoliaMock() {}

  public AlgoliaMock(String objectID) {
    setObjectID(objectID);
  }

  public AlgoliaMock(String objectID, String property) {
    setObjectID(objectID);
    this.property = property;
  }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("WeakerAccess")
class AlgoliaWithId {

  public AlgoliaWithId() {}

  public String getObjectID() {
    return id;
  }

  public void setObjectID(String objectID) {
    this.id = objectID;
  }

  @JsonProperty("objectID")
  private String id;
}

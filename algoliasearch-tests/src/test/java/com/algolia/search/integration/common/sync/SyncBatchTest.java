package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchClearIndexOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.objects.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

@SuppressWarnings("ConstantConditions")
public abstract class SyncBatchTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void batchOnOneIndex() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    index.addObject(new AlgoliaObjectWithID("1", "name", 10)).waitForCompletion();

    List<BatchOperation> operations =
        Arrays.asList(
            new BatchAddObjectOperation<>(new AlgoliaObjectWithID("2", "name2", 11)),
            new BatchUpdateObjectOperation<>(new AlgoliaObjectWithID("1", "name1", 10)));

    waitForCompletion(index.batch(operations));
    assertThat(index.search(new Query("")).getNbHits()).isEqualTo(2);
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void batchOnMultipleIndices() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index2 = createIndex(AlgoliaObjectWithID.class);
    Index<AlgoliaObjectWithID> index3 = createIndex(AlgoliaObjectWithID.class);
    Index<AlgoliaObjectWithID> index4 = createIndex(AlgoliaObjectWithID.class);

    waitForCompletion(
        client.batch(
            Arrays.asList(
                new BatchAddObjectOperation<>(index2, new AlgoliaObjectWithID("1", "name", 2)),
                new BatchAddObjectOperation<>(index3, new AlgoliaObjectWithID("1", "name", 2)),
                new BatchAddObjectOperation<>(index4, new AlgoliaObjectWithID("1", "name", 2)))));

    waitForCompletion(
        client.batch(
            Arrays.asList(
                new BatchClearIndexOperation(index2),
                new BatchDeleteIndexOperation(index3),
                new BatchUpdateObjectOperation<>(
                    index4, new AlgoliaObjectWithID("1", "name2", 2)))));

    assertThat(index2.search(new Query("")).getNbHits()).isEqualTo(0);
    assertThat(client.listIndexes()).extracting("name").doesNotContain(index3.getName());
    assertThat(index4.getObject("1").get())
        .isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", "name2", 2));
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void batchPartialUpdateObjects() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index5 = createIndex(AlgoliaObjectWithID.class);

    waitForCompletion(
        index5.addObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "name", 1), new AlgoliaObjectWithID("2", "name", 2))));

    waitForCompletion(
        index5.partialUpdateObjects(
            Arrays.asList(
                new AlgoliaObjectOnlyAgeAndId().setAge(10).setObjectID("1"),
                new AlgoliaObjectOnlyAgeAndId().setAge(20).setObjectID("2"))));

    Optional<AlgoliaObjectWithID> obj1 = index5.getObject("1");
    Optional<AlgoliaObjectWithID> obj2 = index5.getObject("2");

    assertThat(obj1.get()).isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", "name", 10));
    assertThat(obj2.get()).isEqualToComparingFieldByField(new AlgoliaObjectWithID("2", "name", 20));
  }

  public static class AlgoliaObjectOnlyAgeAndId {

    private int age;
    private String objectID;

    @SuppressWarnings("unused")
    public int getAge() {
      return age;
    }

    public AlgoliaObjectOnlyAgeAndId setAge(int age) {
      this.age = age;
      return this;
    }

    @SuppressWarnings("unused")
    public String getObjectID() {
      return objectID;
    }

    public AlgoliaObjectOnlyAgeAndId setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }

    @Override
    public String toString() {
      return "AlgoliaObjectOnlyAgeAndId{" + "age=" + age + ", objectID='" + objectID + '\'' + '}';
    }
  }
}

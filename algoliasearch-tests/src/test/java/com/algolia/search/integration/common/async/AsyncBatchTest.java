package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchClearIndexOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.objects.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AsyncBatchTest extends AsyncAlgoliaIntegrationTest {

  private static List<String> indicesNames =
      Arrays.asList("index1", "index2", "index3", "index4", "index5");

  @Before
  @After
  public void cleanUp() throws Exception {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).get();
  }

  @Test
  public void batchOnOneIndex() throws Exception {
    AsyncIndex<AlgoliaObjectWithID> index = client.initIndex("index1", AlgoliaObjectWithID.class);
    waitForCompletion(index.addObject(new AlgoliaObjectWithID("1", "name", 10)));

    List<BatchOperation> operations =
        Arrays.asList(
            new BatchAddObjectOperation<>(new AlgoliaObjectWithID("2", "name2", 11)),
            new BatchUpdateObjectOperation<>(new AlgoliaObjectWithID("1", "name1", 10)));

    waitForCompletion(index.batch(operations));

    assertThat(index.search(new Query("")).get().getNbHits()).isEqualTo(2);
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"})
  @Test
  public void batchOnMultipleIndices() throws Exception {
    AsyncIndex<AlgoliaObjectWithID> index2 = client.initIndex("index2", AlgoliaObjectWithID.class);
    AsyncIndex<AlgoliaObjectWithID> index3 = client.initIndex("index3", AlgoliaObjectWithID.class);
    AsyncIndex<AlgoliaObjectWithID> index4 = client.initIndex("index4", AlgoliaObjectWithID.class);

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

    assertThat(index2.search(new Query("")).get().getNbHits()).isEqualTo(0);
    futureAssertThat(client.listIndices()).extracting("name").doesNotContain("index3");
    assertThat(index4.getObject("1").get().get())
        .isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", "name2", 2));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void batchPartialUpdateObjects() throws Exception {
    AsyncIndex<AlgoliaObjectWithID> index5 = client.initIndex("index5", AlgoliaObjectWithID.class);

    waitForCompletion(
        index5.addObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "name", 1), new AlgoliaObjectWithID("2", "name", 2))));

    waitForCompletion(
        index5.partialUpdateObjects(
            Arrays.asList(
                new AlgoliaObjectOnlyAgeAndId().setAge(10).setObjectID("1"),
                new AlgoliaObjectOnlyAgeAndId().setAge(20).setObjectID("2"))));

    Optional<AlgoliaObjectWithID> obj1 = index5.getObject("1").get();
    Optional<AlgoliaObjectWithID> obj2 = index5.getObject("2").get();

    assertThat(obj1.get()).isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", "name", 10));
    assertThat(obj2.get()).isEqualToComparingFieldByField(new AlgoliaObjectWithID("2", "name", 20));
  }

  @SuppressWarnings("WeakerAccess")
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
  }
}

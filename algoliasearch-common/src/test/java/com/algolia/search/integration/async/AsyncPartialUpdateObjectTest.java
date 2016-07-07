package com.algolia.search.integration.async;

import com.algolia.search.AlgoliaObjectWithArray;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.partial_update.AddValueOperation;
import com.algolia.search.inputs.partial_update.IncrementValueOperation;
import com.algolia.search.inputs.partial_update.RemoveValueOperation;
import com.algolia.search.objects.tasks.async.AsyncTaskIndexing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class AsyncPartialUpdateObjectTest extends AsyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @Before
  @After
  public void cleanUp() throws Exception {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    waitForCompletion(client.batch(clean));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void partialUpdates() throws Exception {
    AsyncIndex<AlgoliaObjectWithArray> index = client.initIndex("index1", AlgoliaObjectWithArray.class);
    AsyncTaskIndexing task = index.addObject(new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1)).get();
    client.waitTask(task, WAIT_TIME_IN_SECONDS);

    waitForCompletion(index.partialUpdateObject(new AddValueOperation(task.getObjectID(), "tags", "tag3")));
    waitForCompletion(index.partialUpdateObject(new RemoveValueOperation(task.getObjectID(), "tags", "tag1")));
    waitForCompletion(index.partialUpdateObject(new IncrementValueOperation(task.getObjectID(), "age", 1)));

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get().get();
    assertThat(obj.getAge()).isEqualTo(2);
    assertThat(obj.getTags()).containsOnly("tag3", "tag2");
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void partialUpdateAttributes() throws Exception {
    AsyncIndex<AlgoliaObjectWithArray> index = client.initIndex("index2", AlgoliaObjectWithArray.class);
    AsyncTaskIndexing task = index.addObject(new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1)).get();
    client.waitTask(task, WAIT_TIME_IN_SECONDS);

    waitForCompletion(index.partialUpdateObject(task.getObjectID(), new AlgoliaObjectOnlyAge().setAge(10)));

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get().get();
    assertThat(obj.getTags()).containsOnly("tag1", "tag2");
    assertThat(obj.getAge()).isEqualTo(10);
  }

  @SuppressWarnings("WeakerAccess")
  public static class AlgoliaObjectOnlyAge {

    private int age;

    @SuppressWarnings("unused")
    public int getAge() {
      return age;
    }

    public AlgoliaObjectOnlyAge setAge(int age) {
      this.age = age;
      return this;
    }
  }

}

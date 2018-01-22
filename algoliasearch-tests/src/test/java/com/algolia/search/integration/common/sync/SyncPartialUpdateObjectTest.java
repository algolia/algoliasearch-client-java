package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObjectWithArray;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.partial_update.AddValueOperation;
import com.algolia.search.inputs.partial_update.IncrementValueOperation;
import com.algolia.search.inputs.partial_update.RemoveValueOperation;
import com.algolia.search.objects.tasks.sync.TaskIndexing;
import java.util.Arrays;
import org.junit.Test;

public abstract class SyncPartialUpdateObjectTest extends SyncAlgoliaIntegrationTest {

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void partialUpdates() throws AlgoliaException {
    Index<AlgoliaObjectWithArray> index = createIndex(AlgoliaObjectWithArray.class);
    TaskIndexing task =
        index.addObject(
            new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1));
    waitForCompletion(task);

    waitForCompletion(
        index.partialUpdateObject(new AddValueOperation(task.getObjectID(), "tags", "tag3")));
    waitForCompletion(
        index.partialUpdateObject(new RemoveValueOperation(task.getObjectID(), "tags", "tag1")));
    waitForCompletion(
        index.partialUpdateObject(new IncrementValueOperation(task.getObjectID(), "age", 1)));

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get();
    assertThat(obj.getAge()).isEqualTo(2);
    assertThat(obj.getTags()).containsOnly("tag3", "tag2");
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void partialUpdateAttributes() throws AlgoliaException {
    Index<AlgoliaObjectWithArray> index = createIndex(AlgoliaObjectWithArray.class);
    TaskIndexing task =
        index.addObject(
            new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1));
    waitForCompletion(task);

    waitForCompletion(
        index.partialUpdateObject(task.getObjectID(), new AlgoliaObjectOnlyAge().setAge(10)));

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get();
    assertThat(obj.getTags()).containsOnly("tag1", "tag2");
    assertThat(obj.getAge()).isEqualTo(10);
  }

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

    @Override
    public String toString() {
      return "AlgoliaObjectOnlyAge{" + "age=" + age + '}';
    }
  }
}

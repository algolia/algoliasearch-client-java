package com.algolia.search.integration;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.AlgoliaObjectWithArray;
import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.partial_update.AddValueOperation;
import com.algolia.search.inputs.partial_update.IncrementValueOperation;
import com.algolia.search.inputs.partial_update.RemoveValueOperation;
import com.algolia.search.objects.TaskIndexing;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PartialUpdateObjectTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @BeforeClass
  @AfterClass
  public static void after() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void partialUpdates() throws AlgoliaException {
    Index<AlgoliaObjectWithArray> index = client.initIndex("index1", AlgoliaObjectWithArray.class);
    TaskIndexing task = index.addObject(new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1));
    task.waitForCompletion();

    index.partialUpdateObject(new AddValueOperation(task.getObjectID(), "tags", "tag3")).waitForCompletion();
    index.partialUpdateObject(new RemoveValueOperation(task.getObjectID(), "tags", "tag1")).waitForCompletion();
    index.partialUpdateObject(new IncrementValueOperation(task.getObjectID(), "age", 1)).waitForCompletion();

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get();
    assertThat(obj.getAge()).isEqualTo(2);
    assertThat(obj.getTags()).containsOnly("tag3", "tag2");
  }

  @Test
  public void partialUpdateAttributes() throws AlgoliaException {
    Index<AlgoliaObjectWithArray> index = client.initIndex("index2", AlgoliaObjectWithArray.class);
    TaskIndexing task = index.addObject(new AlgoliaObjectWithArray().setTags(Arrays.asList("tag1", "tag2")).setAge(1));
    task.waitForCompletion();

    index.partialUpdateObject(task.getObjectID(), new AlgoliaObjectOnlyAge().setAge(10)).waitForCompletion();

    AlgoliaObjectWithArray obj = index.getObject(task.getObjectID()).get();
    assertThat(obj.getTags()).containsOnly("tag1", "tag2");
    assertThat(obj.getAge()).isEqualTo(10);
  }

  private static class AlgoliaObjectOnlyAge {

    private int age;

    @SuppressWarnings("unused")
    public int getAge() {
      return age;
    }

    AlgoliaObjectOnlyAge setAge(int age) {
      this.age = age;
      return this;
    }
  }

  @Test
  public void partialUpdateObjects() throws AlgoliaException {
    assertThat(true).isFalse();
  }

}

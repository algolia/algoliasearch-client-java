package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncObjectsTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames =
      Arrays.asList("index1", "index2", "index3", "index4", "index5", "index6", "index7", "index8");

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void getAnObject() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = client.initIndex("index1", AlgoliaObjectWithID.class);
    AlgoliaObjectWithID objectWithID = new AlgoliaObjectWithID("1", "algolia", 4);
    index.addObject(objectWithID).waitForCompletion();

    Optional<AlgoliaObjectWithID> result = index.getObject("1");

    assertThat(objectWithID).isEqualToComparingFieldByField(result.get());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void getAnObjectWithId() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    index.addObject("2", object).waitForCompletion();

    Optional<AlgoliaObject> result = index.getObject("2");

    assertThat(object).isEqualToComparingFieldByField(result.get());
  }

  @Test
  public void addObjects() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = client.initIndex("index3", AlgoliaObjectWithID.class);
    List<AlgoliaObjectWithID> objectsWithID =
        Arrays.asList(
            new AlgoliaObjectWithID("1", "algolia", 4), new AlgoliaObjectWithID("2", "algolia", 4));
    index.addObjects(objectsWithID).waitForCompletion();

    List<AlgoliaObjectWithID> objects = index.getObjects(Arrays.asList("1", "2"));

    assertThat(objects).extracting("objectID").containsOnly("1", "2");
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveObject() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index4", AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);

    index.addObject("1", object);

    index.saveObject("1", new AlgoliaObject("algolia", 5)).waitForCompletion();
    Optional<AlgoliaObject> result = index.getObject("1");

    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia", 5));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveObjects() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index5", AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    index.addObject("1", obj1);
    index.addObject("2", obj2);

    index
        .saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5)))
        .waitForCompletion();

    Optional<AlgoliaObject> result = index.getObject("1");
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));

    result = index.getObject("2");
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));
  }

  @Test
  public void deleteObject() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index6", AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    index.addObject("1", object);

    index.deleteObject("1").waitForCompletion();

    assertThat(index.getObject("1")).isEmpty();
  }

  @Test
  public void deleteObjects() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index7", AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    index.addObject("1", obj1);
    index.addObject("2", obj2);

    index.deleteObjects(Arrays.asList("1", "2")).waitForCompletion();

    assertThat(index.getObject("1")).isEmpty();
    assertThat(index.getObject("2")).isEmpty();
  }

  @Test
  public void getObjectsWithAttributesToRetrieve() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index8", AlgoliaObject.class);
    index
        .saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5)))
        .waitForCompletion();

    List<AlgoliaObject> objects =
        index.getObjects(Collections.singletonList("1"), Collections.singletonList("age"));
    assertThat(objects).hasSize(1);
    assertThat(objects.get(0))
        .isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", null, 5));
  }
}

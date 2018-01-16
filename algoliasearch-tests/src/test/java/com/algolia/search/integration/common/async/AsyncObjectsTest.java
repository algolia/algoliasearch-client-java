package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;

@SuppressWarnings("ConstantConditions")
public abstract class AsyncObjectsTest extends AsyncAlgoliaIntegrationTest {

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void getAnObject() throws Exception {
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    AlgoliaObjectWithID objectWithID = new AlgoliaObjectWithID("1", "algolia", 4);
    waitForCompletion(index.addObject(objectWithID));

    Optional<AlgoliaObjectWithID> result = index.getObject("1").get();

    assertThat(objectWithID).isEqualToComparingFieldByField(result.get());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void getAnObjectWithId() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    waitForCompletion(index.addObject("2", object));

    Optional<AlgoliaObject> result = index.getObject("2").get();

    assertThat(object).isEqualToComparingFieldByField(result.get());
  }

  @Test
  public void addObjects() throws Exception {
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    List<AlgoliaObjectWithID> objectsWithID =
        Arrays.asList(
            new AlgoliaObjectWithID("1", "algolia", 4), new AlgoliaObjectWithID("2", "algolia", 4));
    waitForCompletion(index.addObjects(objectsWithID));

    futureAssertThat(index.getObjects(Arrays.asList("1", "2")))
        .extracting("objectID")
        .containsOnly("1", "2");
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveObject() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);

    waitForCompletion(index.addObject("1", object));

    waitForCompletion(index.saveObject("1", new AlgoliaObject("algolia", 5)));
    Optional<AlgoliaObject> result = index.getObject("1").get();

    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia", 5));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveObjects() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    waitForCompletion(index.addObject("1", obj1));
    waitForCompletion(index.addObject("2", obj2));

    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5))));

    Optional<AlgoliaObject> result = index.getObject("1").get();
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));

    result = index.getObject("2").get();
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));
  }

  @Test
  public void deleteObject() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    waitForCompletion(index.addObject("1", object));

    waitForCompletion(index.deleteObject("1"));

    assertThat(index.getObject("1").get()).isEmpty();
  }

  @Test
  public void deleteObjects() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    waitForCompletion(index.addObject("1", obj1));
    waitForCompletion(index.addObject("2", obj2));

    waitForCompletion(index.deleteObjects(Arrays.asList("1", "2")));

    assertThat(index.getObject("1").get()).isEmpty();
    assertThat(index.getObject("2").get()).isEmpty();
  }

  @Test
  public void getObjectsWithAttributesToRetrieve() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5))));

    CompletableFuture<List<AlgoliaObject>> result =
        index.getObjects(Collections.singletonList("1"), Collections.singletonList("age"));

    futureAssertThat(result).hasSize(1);
    futureAssertThat(result).extracting("name").containsNull();
  }
}

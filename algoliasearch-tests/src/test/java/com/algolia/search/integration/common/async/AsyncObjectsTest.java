package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.*;
import com.algolia.search.inputs.MultipleGetObjectsRequests;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.*;
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

  @Test(expected = IllegalArgumentException.class)
  public void deleteObjectEmptyObjectIdShouldFail() throws IllegalArgumentException {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteObjectWhiteSpaceObjectIdShouldFail() throws IllegalArgumentException {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject("  ");
  }

  @Test(expected = NullPointerException.class)
  public void deleteObjectNullObjectIdShouldFail() throws NullPointerException {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject(null);
  }

  @Test
  public void testMultipleGetObjects() throws Exception {

    // Save object in Index1
    AsyncIndex<AlgoliaObject> index1 = createIndex(AlgoliaObject.class);
    waitForCompletion(
        index1.saveObject("objectID1", new AlgoliaObjectWithID("objectID1", "algolia1", 5)));

    // Save object in Index2
    AsyncIndex<AlgoliaObject> index2 = createIndex(AlgoliaObject.class);
    waitForCompletion(
        index2.saveObject("objectID2", new AlgoliaObjectWithID("objectID2", "algolia2", 6)));

    // Perform the multiple index queries
    List<MultipleGetObjectsRequests> requests =
        Arrays.asList(
            new MultipleGetObjectsRequests(index1.getName(), "objectID1"),
            new MultipleGetObjectsRequests(index2.getName(), "objectID2"));

    List<Map<String, String>> result = client.multipleGetObjects(requests).get();

    // Verify that objects are present in the results
    assertThat(result).isNotNull();
    assertThat(result.get(0).get("objectID")).isEqualTo("objectID1");
    assertThat(result.get(1).get("objectID")).isEqualTo("objectID2");
  }

  @Test
  public void testReplaceAllObjects() throws Exception {
    // Create index with initials data
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Replace old objects with new objects
    List<AlgoliaObjectWithID> newObjects =
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5));

    // Save Rule
    waitForCompletion(index.saveRule("id", AsyncRulesTest.generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // Perform the replacement
    index.replaceAllObjects(newObjects, true);

    // Test if objects well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    Optional<Rule> queryRule1 = index.getRule("id").get();
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(AsyncRulesTest.generateRule("id"));

    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1").get();
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(
            new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    IndexSettings settingsRes = index.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }
}

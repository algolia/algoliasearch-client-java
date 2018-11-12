package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.MultipleGetObjectsRequests;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.*;
import org.junit.Test;

public abstract class SyncObjectsTest extends SyncAlgoliaIntegrationTest {

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void getAnObject() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    AlgoliaObjectWithID objectWithID = new AlgoliaObjectWithID("1", "algolia", 4);
    waitForCompletion(index.addObject(objectWithID));

    Optional<AlgoliaObjectWithID> result = index.getObject("1");

    assertThat(objectWithID).isEqualToComparingFieldByField(result.get());
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void getAnObjectWithId() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    waitForCompletion(index.addObject("2", object));

    Optional<AlgoliaObject> result = index.getObject("2");

    assertThat(object).isEqualToComparingFieldByField(result.get());
  }

  @Test
  public void addObjects() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    List<AlgoliaObjectWithID> objectsWithID =
        Arrays.asList(
            new AlgoliaObjectWithID("1", "algolia", 4), new AlgoliaObjectWithID("2", "algolia", 4));
    waitForCompletion(index.addObjects(objectsWithID));

    List<AlgoliaObjectWithID> objects = index.getObjects(Arrays.asList("1", "2"));

    assertThat(objects).extracting("objectID").containsOnly("1", "2");
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void saveObject() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);

    waitForCompletion(index.addObject("1", object));

    waitForCompletion(index.saveObject("1", new AlgoliaObject("algolia", 5)));
    Optional<AlgoliaObject> result = index.getObject("1");

    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia", 5));
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void saveObjects() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    waitForCompletion(index.addObject("1", obj1));
    waitForCompletion(index.addObject("2", obj2));

    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5))));

    Optional<AlgoliaObject> result = index.getObject("1");
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));

    result = index.getObject("2");
    assertThat(result.get()).isEqualToComparingFieldByField(new AlgoliaObject("algolia1", 5));
  }

  @Test
  public void deleteObject() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject object = new AlgoliaObject("algolia", 4);
    waitForCompletion(index.addObject("1", object));

    waitForCompletion(index.deleteObject("1"));

    assertThat(index.getObject("1")).isEmpty();
  }

  @Test
  public void deleteObjects() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    AlgoliaObject obj1 = new AlgoliaObject("algolia1", 4);
    AlgoliaObject obj2 = new AlgoliaObject("algolia2", 4);

    waitForCompletion(index.addObject("1", obj1));
    waitForCompletion(index.addObject("2", obj2));

    waitForCompletion(index.deleteObjects(Arrays.asList("1", "2")));

    assertThat(index.getObject("1")).isEmpty();
    assertThat(index.getObject("2")).isEmpty();
  }

  @Test
  public void getObjectsWithAttributesToRetrieve() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia1", 5))));

    List<AlgoliaObject> objects =
        index.getObjects(Collections.singletonList("1"), Collections.singletonList("age"));
    assertThat(objects).hasSize(1);
    assertThat(objects.get(0))
        .isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", null, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteObjectEmptyObjectIdShouldFail()
      throws AlgoliaException, IllegalArgumentException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteObjectWhiteSpaceObjectIdShouldFail()
      throws AlgoliaException, IllegalArgumentException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject("  ");
  }

  @Test(expected = NullPointerException.class)
  public void deleteObjectNullObjectIdShouldFail() throws AlgoliaException, NullPointerException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.deleteObject(null);
  }

  @Test
  public void testMultipleGetObjects() throws AlgoliaException {

    // Save object in Index1
    Index<AlgoliaObject> index1 = createIndex(AlgoliaObject.class);
    waitForCompletion(
        index1.saveObject("objectID1", new AlgoliaObjectWithID("objectID1", "algolia1", 5)));

    // Save object in Index2
    Index<AlgoliaObject> index2 = createIndex(AlgoliaObject.class);
    waitForCompletion(
        index2.saveObject("objectID2", new AlgoliaObjectWithID("objectID2", "algolia2", 6)));

    // Perform the multiple index queries
    List<MultipleGetObjectsRequests> requests =
        Arrays.asList(
            new MultipleGetObjectsRequests(index1.getName(), "objectID1"),
            new MultipleGetObjectsRequests(index2.getName(), "objectID2"));

    List<Map<String, String>> result = client.multipleGetObjects(requests);

    // Verify that objects are present in the results
    assertThat(result).isNotNull();
    assertThat(result.get(0).get("objectID")).isEqualTo("objectID1");
    assertThat(result.get(1).get("objectID")).isEqualTo("objectID2");
  }

  @Test
  public void testReplaceAllObjects() throws AlgoliaException {
    // Create index with initials data
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
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
    waitForCompletion(index.saveRule("id", generateRule("id")));

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
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    Optional<Rule> queryRule1 = index.getRule("id");
    assertThat(queryRule1.get())
            .isInstanceOf(Rule.class)
            .isEqualToComparingFieldByFieldRecursively(generateRule("id"));

    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1");
    assertThat(synonym1.get())
            .isInstanceOf(Synonym.class)
            .isEqualToComparingFieldByField(
                    new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }
}

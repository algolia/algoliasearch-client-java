package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.objects.*;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchRuleResult;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public abstract class SyncIndicesTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void getAllIndices() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    List<Index.Attributes> listIndices = client.listIndexes();
    assertThat(listIndices).extracting("name").contains(index.getName());
    assertThat(listIndices).extracting("numberOfPendingTasks").isNotNull();
  }

  @Test
  public void deleteIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    waitForCompletion(index.delete());
    assertThat(client.listIndexes()).extracting("name").doesNotContain(index.getName());
  }

  @Test
  public void moveIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexMoveTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.moveTo(indexMoveTo.getName()));
    assertThat(client.listIndexes())
        .extracting("name")
        .doesNotContain(index.getName())
        .contains(indexMoveTo.getName());
  }

  @Test
  public void copyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexCopyTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.copyTo(indexCopyTo.getName()));
    assertThat(client.listIndexes())
        .extracting("name")
        .contains(index.getName(), indexCopyTo.getName());
  }

  @Test
  public void clearIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    waitForCompletion(index.clear());

    SearchResult<AlgoliaObject> results = index.search(new Query(""));

    assertThat(results.getHits()).isEmpty();
  }

  @Test
  public void testFullReindex() throws AlgoliaException {
    // *********  Create index with initials data *********
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(index.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // ********* ReIndex with new data *********
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>(AlgoliaObjectWithID.class);

    // Object
    newIndexContent.setObjects(
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5)));

    // Rule
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Synonym
    Synonym newSynonym = new Synonym(Arrays.asList("Paris", "IDF")).setObjectID("synonym2");
    newIndexContent.setSynonyms(Collections.singletonList(newSynonym));

    // Settings
    IndexSettings newSettings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("age"));

    // Perform the reindex
    newIndexContent.setSettings(newSettings);

    List<Long> taskIds = index.reIndex(newIndexContent);

    for (Long taskId : taskIds) {
      index.waitTask(taskId);
    }

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1");
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are well replaced
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym2");
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(newSynonym);

    // Assert that the settings are well replaced
    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("age");
  }

  @Test
  public void testFullReindexWithIterator() throws AlgoliaException {
    // *********  Create index with initials data *********
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(index.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // ********* ReIndex with new data *********
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>(AlgoliaObjectWithID.class);

    // Set the Objects iterator
    IndexIterable<AlgoliaObjectWithID> iterator = index.browse(new Query(""));
    newIndexContent.setObjects(iterator);

    // Set new Rules
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Set new Synonyms
    Synonym newSynonym = new Synonym(Arrays.asList("Paris", "IDF")).setObjectID("synonym2");
    newIndexContent.setSynonyms(Collections.singletonList(newSynonym));

    // Set new Settings
    IndexSettings newSettings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("age"));
    newIndexContent.setSettings(newSettings);

    // Perform the reindex
    newIndexContent.setSettings(newSettings);

    List<Long> taskIds = index.reIndex(newIndexContent);

    for (Long taskId : taskIds) {
      index.waitTask(taskId);
    }

    // Assert that objects are the same
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("1");
    assertThat(result.getHits()).extracting("objectID").contains("2");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1");
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are well replaced
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym2");
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(newSynonym);

    // Assert that the settings are well replaced
    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("age");
  }

  @Test
  public void testPartialReindex() throws AlgoliaException {
    // *********  Create index with initials data *********
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(index.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // ********* ReIndex with new data *********
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>(AlgoliaObjectWithID.class);

    // Set new Objects
    newIndexContent.setObjects(
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5)));

    // Set new Rules
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Perform the reindex
    index.reIndex(newIndexContent ,true);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1");
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are still the same
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1");
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(
            new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    // Assert that the settings are the same
    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }

  @Test
  public void testPartialReindexEmptyRulesAndSynonyms() throws AlgoliaException {
    // *********  Create index with initials data *********
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(index.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // ********* ReIndex with new data *********
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>(AlgoliaObjectWithID.class);

    // Set new Objects
    newIndexContent.setObjects(
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5)));

    // Empty Rules
    newIndexContent.setRules(Collections.emptyList());

    // Empty Synonyms
    newIndexContent.setSynonyms(Collections.emptyList());

    // Perform the reindex
    index.reIndex(newIndexContent, true);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are empty
    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);

    // Assert that the synonyms are empty
    SearchSynonymResult searchSynonymResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchSynonymResult.getHits()).hasSize(0);

    // Assert that the settings are still the same
    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }

  @Test
  public void testPartialReindexNoObjects() throws AlgoliaException {
    // *********  Create index with initials data *********
    Index<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
        index.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(index.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(index.setSettings(settings));

    // ********* ReIndex with new data *********
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>(AlgoliaObjectWithID.class);

    // Rule
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Synonym
    Synonym newSynonym = new Synonym(Arrays.asList("Paris", "IDF")).setObjectID("synonym2");
    newIndexContent.setSynonyms(Collections.singletonList(newSynonym));

    // Settings
    IndexSettings newSettings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("age"));
    newIndexContent.setSettings(newSettings);

    index.reIndex(newIndexContent, true);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(0);

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1");
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are well replaced
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym2");
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(newSynonym);

    // Assert that the settings are well replaced
    IndexSettings settingsRes = index.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("age");
  }
}

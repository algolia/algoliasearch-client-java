package com.algolia.search.integration.common.async;

import static com.algolia.search.integration.common.async.AsyncRulesTest.generateRule;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.*;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchRuleResult;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public abstract class AsyncIndicesTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void getAllIndices() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());
  }

  @Test
  public void deleteIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    waitForCompletion(index.delete());
    futureAssertThat(client.listIndices()).extracting("name").doesNotContain(index.getName());
  }

  @Test
  public void moveIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    AsyncIndex<AlgoliaObject> indexMoveTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.moveTo(indexMoveTo.getName()));
    futureAssertThat(client.listIndices())
        .extracting("name")
        .doesNotContain(index.getName())
        .contains(indexMoveTo.getName());
  }

  @Test
  public void copyIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    AsyncIndex<AlgoliaObject> indexCopyTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.copyTo(indexCopyTo.getName()));
    futureAssertThat(client.listIndices())
        .extracting("name")
        .contains(index.getName(), indexCopyTo.getName());
  }

  @Test
  public void clearIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    waitForCompletion(index.clear());

    SearchResult<AlgoliaObject> results = index.search(new Query("")).get();

    assertThat(results.getHits()).isEmpty();
  }

  @Test
  public void testFullReindex() throws Exception {
    // *********  Create index with initials data *********
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
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
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>();

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

    newIndexContent.setSettings(newSettings);

    index.reIndex(newIndexContent);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1").get();
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are well replaced
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym2").get();
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(newSynonym);

    // Assert that the settings are well replaced
    IndexSettings settingsRes = index.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("age");
  }

  @Test
  public void testPartialReindex() throws Exception {
    // *********  Create index with initials data *********
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
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
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>();

    // Set new Objects
    newIndexContent.setObjects(
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5)));

    // Set new Rules
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Perform the reindex
    index.reIndex(newIndexContent);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1").get();
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are still the same
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1").get();
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(
            new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    // Assert that the settings are the same
    IndexSettings settingsRes = index.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }

  @Test
  public void testPartialReindexEmptyRulesAndSynonyms() throws Exception {
    // *********  Create index with initials data *********
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
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
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>();

    // Set new Objects
    newIndexContent.setObjects(
        Arrays.asList(
            new AlgoliaObjectWithID("3", "algolia3", 5),
            new AlgoliaObjectWithID("4", "algolia4", 5)));

    // Empty Rules
    newIndexContent.setRules(Collections.emptyList());

    // Empty Synonyms
    newIndexContent.setSynonyms(Collections.emptyList());

    // Perfom the reindex
    index.reIndex(newIndexContent);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("3");
    assertThat(result.getHits()).extracting("objectID").contains("4");

    // Assert that the rules are empty
    SearchRuleResult searchResult = index.searchRules(new RuleQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(0);

    // Assert that the synonyms are empty
    SearchSynonymResult searchSynonymResult = index.searchSynonyms(new SynonymQuery("")).get();
    assertThat(searchSynonymResult.getHits()).hasSize(0);

    // Assert that the settings are still the same
    IndexSettings settingsRes = index.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }

  @Test
  public void testPartialReindexNoObjects() throws Exception {
    // *********  Create index with initials data *********
    AsyncIndex<AlgoliaObjectWithID> index = createIndex(AlgoliaObjectWithID.class);
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
    IndexContent<AlgoliaObjectWithID> newIndexContent = new IndexContent<>();

    // Rule
    newIndexContent.setRules(Collections.singletonList(generateRule("id1")));

    // Synonym
    Synonym newSynonym = new Synonym(Arrays.asList("Paris", "IDF")).setObjectID("synonym2");
    newIndexContent.setSynonyms(Collections.singletonList(newSynonym));

    // Settings
    IndexSettings newSettings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("age"));
    newIndexContent.setSettings(newSettings);

    index.reIndex(newIndexContent);

    // Assert that objects are well replaced
    SearchResult<AlgoliaObjectWithID> result = index.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(0);

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = index.getRule("id1").get();
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id1"));

    // Assert that the synonyms are well replaced
    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym2").get();
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(newSynonym);

    // Assert that the settings are well replaced
    IndexSettings settingsRes = index.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("age");
  }
}

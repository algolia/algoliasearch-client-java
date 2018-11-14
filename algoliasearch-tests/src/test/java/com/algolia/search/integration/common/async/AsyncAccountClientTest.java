package com.algolia.search.integration.common.async;

import com.algolia.search.*;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.algolia.search.integration.common.async.AsyncRulesTest.generateRule;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AsyncAccountClientTest extends AsyncAlgoliaIntegrationTest {

  @Test(expected = AlgoliaException.class)
  public void copyIndexWithExistingIndex() throws Exception {
    // Save object in Index1
    AsyncIndex<AlgoliaObject> srcIndex = createIndex(AlgoliaObject.class);
    waitForCompletion(
            srcIndex.saveObject("objectID1", new AlgoliaObjectWithID("objectID1", "algolia1", 5)));

    // Save object in Index2
    AsyncIndex<AlgoliaObject> dstIndex = createIndex(AlgoliaObject.class);
    waitForCompletion(
            dstIndex.saveObject("objectID2", new AlgoliaObjectWithID("objectID2", "algolia2", 6)));

    AsyncAccountClient.copyIndex(srcIndex, dstIndex);
  }

  @Test
  public void copyFullIndex() throws Exception {

    // Create source index
    AsyncIndex<AlgoliaObjectWithID> srcIndex = createIndex(AlgoliaObjectWithID.class);
    waitForCompletion(
            srcIndex.saveObjects(
                    Arrays.asList(
                            new AlgoliaObjectWithID("1", "algolia1", 5),
                            new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    waitForCompletion(srcIndex.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    waitForCompletion(srcIndex.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
            new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    waitForCompletion(srcIndex.setSettings(settings));

    // Create destination index
    AsyncIndex<AlgoliaObjectWithID> dstIndex = createIndex(AlgoliaObjectWithID.class);

    List<Long> taskIds = AsyncAccountClient.copyIndex(srcIndex, dstIndex);

    for (Long taskId : taskIds) {
      dstIndex.waitTask(taskId);
    }

    // Assert that objects are the same
    SearchResult<AlgoliaObjectWithID> result = dstIndex.search(new Query("")).get();
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("1");
    assertThat(result.getHits()).extracting("objectID").contains("2");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = dstIndex.getRule("id").get();
    assertThat(queryRule1.orElse(null))
            .isInstanceOf(Rule.class)
            .isEqualToComparingFieldByFieldRecursively(generateRule("id"));

    // Assert that the synonyms are still the same
    Optional<AbstractSynonym> synonym1 = dstIndex.getSynonym("synonym1").get();
    assertThat(synonym1.orElse(null))
            .isInstanceOf(Synonym.class)
            .isEqualToComparingFieldByField(
                    new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    // Assert that the settings are still the same
    IndexSettings settingsRes = dstIndex.getSettings().get();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");
  }
}

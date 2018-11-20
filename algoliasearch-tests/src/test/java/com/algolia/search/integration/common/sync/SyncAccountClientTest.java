package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.*;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.time.Instant;
import java.util.*;
import org.junit.Test;

public abstract class SyncAccountClientTest extends SyncAlgoliaIntegrationTest {

  private APIClient client2 = createInstance(ALGOLIA_APPLICATION_ID_2, ALGOLIA_API_KEY_2);

  @Test(expected = AlgoliaException.class)
  public void copyIndexOnSameApp() throws AlgoliaException {
    Index<AlgoliaObject> srcIndex = createIndex(AlgoliaObject.class);
    Index<AlgoliaObject> dstIndex = createIndex(AlgoliaObject.class);

    SyncAccountClient.copyIndex(srcIndex, dstIndex);
  }

  @Test(expected = AlgoliaException.class)
  public void copyOnExistingIndex() throws AlgoliaException {

    String sourceIndexName =
        "java_" + System.getProperty("user.name") + Instant.now().toString() + "_copyIndex";
    Index<AlgoliaObject> srcIndex = client2.initIndex(sourceIndexName, AlgoliaObject.class);

    String destinationIndexName =
        "java_" + System.getProperty("user.name") + Instant.now().toString() + "_copyIndex";
    Index<AlgoliaObject> dstIndex = createIndex(destinationIndexName, AlgoliaObject.class);
    dstIndex.waitTask(
        dstIndex.saveObject("objectID2", new AlgoliaObjectWithID("objectID2", "algolia2", 6)));

    SyncAccountClient.copyIndex(srcIndex, dstIndex);
  }

  @Test
  public void copyFullIndex() throws AlgoliaException {

    // Create source index
    String sourceIndexName =
        "java_" + System.getProperty("user.name") + Instant.now().toString() + "_copyFullIndex";

    Index<AlgoliaObjectWithID> srcIndex = createIndex(sourceIndexName, AlgoliaObjectWithID.class);

    srcIndex.waitTask(
        srcIndex.saveObjects(
            Arrays.asList(
                new AlgoliaObjectWithID("1", "algolia1", 5),
                new AlgoliaObjectWithID("2", "algolia2", 5))));

    // Save Rule
    srcIndex.waitTask(srcIndex.saveRule("id", generateRule("id")));

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    srcIndex.waitTask(srcIndex.saveSynonym("synonym1", new Synonym(synonymList)));

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    srcIndex.waitTask(srcIndex.setSettings(settings));

    // Create destination index
    String destinationIndexName =
        "java_"
            + System.getProperty("user.name")
            + "_"
            + Instant.now().toString()
            + "_copyFullIndex";
    Index<AlgoliaObjectWithID> dstIndex =
        client2.initIndex(destinationIndexName, AlgoliaObjectWithID.class);

    List<Long> taskIds = SyncAccountClient.copyIndex(srcIndex, dstIndex);

    for (Long taskId : taskIds) {
      dstIndex.waitTask(taskId);
    }

    // Assert that objects are the same
    SearchResult<AlgoliaObjectWithID> result = dstIndex.search(new Query(""));
    assertThat(result).isNotNull();
    assertThat(result.getHits()).isNotNull();
    assertThat(result.getNbHits()).isEqualTo(2);
    assertThat(result.getHits()).extracting("objectID").contains("1");
    assertThat(result.getHits()).extracting("objectID").contains("2");

    // Assert that the rules are well replaced
    Optional<Rule> queryRule1 = dstIndex.getRule("id");
    assertThat(queryRule1.orElse(null))
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule("id"));

    // Assert that the synonyms are still the same
    Optional<AbstractSynonym> synonym1 = dstIndex.getSynonym("synonym1");
    assertThat(synonym1.orElse(null))
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(
            new Synonym().setObjectID("synonym1").setSynonyms(synonymList));

    // Assert that the settings are still the same
    IndexSettings settingsRes = dstIndex.getSettings();
    assertThat(settingsRes.getAttributesForFaceting()).containsOnly("name");

    client2.deleteIndex(destinationIndexName);
  }
}

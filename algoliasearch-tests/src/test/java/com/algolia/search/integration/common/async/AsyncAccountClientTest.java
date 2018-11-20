package com.algolia.search.integration.common.async;

import static com.algolia.search.integration.common.async.AsyncRulesTest.generateRule;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public abstract class AsyncAccountClientTest extends AsyncAlgoliaIntegrationTest {

  private AsyncAPIClient client2 = createInstance(ALGOLIA_APPLICATION_ID_2, ALGOLIA_API_KEY_2);

  @Test(expected = AlgoliaException.class)
  public void copyIndexOnSameApp()
      throws AlgoliaException, ExecutionException, InterruptedException {
    AsyncIndex<AlgoliaObject> srcIndex = createIndex(AlgoliaObject.class);
    AsyncIndex<AlgoliaObject> dstIndex = createIndex(AlgoliaObject.class);

    AsyncAccountClient.copyIndex(srcIndex, dstIndex);
  }

  @Test(expected = AlgoliaException.class)
  public void copyOnExistingIndex()
      throws AlgoliaException, ExecutionException, InterruptedException {

    String sourceIndexName =
        "java_"
            + System.getProperty("user.name")
            + "_"
            + Instant.now().toString()
            + "_copyIndexAsync";
    AsyncIndex<AlgoliaObject> srcIndex = client2.initIndex(sourceIndexName, AlgoliaObject.class);

    String destinationIndexName =
        "java_"
            + System.getProperty("user.name")
            + "_"
            + Instant.now().toString()
            + "_copyIndexAsync";
    AsyncIndex<AlgoliaObject> dstIndex = createIndex(destinationIndexName, AlgoliaObject.class);
    dstIndex.waitTask(
        dstIndex
            .saveObject("objectID2", new AlgoliaObjectWithID("objectID2", "algolia2", 6))
            .get());

    AsyncAccountClient.copyIndex(srcIndex, dstIndex);
  }

  @Test
  public void copyFullIndex() throws AlgoliaException, ExecutionException, InterruptedException {

    // Create source index
    String sourceIndexName =
        "java_"
            + System.getProperty("user.name")
            + "_"
            + Instant.now().toString()
            + "_copyFullIndexAsync";

    AsyncIndex<AlgoliaObjectWithID> srcIndex =
        createIndex(sourceIndexName, AlgoliaObjectWithID.class);

    srcIndex.waitTask(
        srcIndex
            .saveObjects(
                Arrays.asList(
                    new AlgoliaObjectWithID("1", "algolia1", 5),
                    new AlgoliaObjectWithID("2", "algolia2", 5)))
            .get());

    // Save Rule
    srcIndex.waitTask(srcIndex.saveRule("id", generateRule("id")).get());

    // Save synonym
    List<String> synonymList = Arrays.asList("San Francisco", "SF");
    srcIndex.waitTask(srcIndex.saveSynonym("synonym1", new Synonym(synonymList)).get());

    // Save settings
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("name"));
    srcIndex.waitTask(srcIndex.setSettings(settings).get());

    // Create destination index
    String destinationIndexName =
        "java_"
            + System.getProperty("user.name")
            + "_"
            + Instant.now().toString()
            + "_copyFullIndexAsync";
    AsyncIndex<AlgoliaObjectWithID> dstIndex =
        client2.initIndex(destinationIndexName, AlgoliaObjectWithID.class);

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

    client2.deleteIndex(destinationIndexName);
  }
}

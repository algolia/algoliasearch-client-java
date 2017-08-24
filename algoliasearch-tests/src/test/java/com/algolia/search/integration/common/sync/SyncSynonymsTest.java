package com.algolia.search.integration.common.sync;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.responses.SearchSynonymResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class SyncSynonymsTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2",
    "index3",
    "index4"
  );

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveAndGetSynonym() throws AlgoliaException {
    Index<?> index = client.initIndex("index1");

    List<String> synonymList = Arrays.asList("San Francisco", "SF");

    index.saveSynonym("synonym1", new Synonym(synonymList)).waitForCompletion();

    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1");
    assertThat(synonym1.get())
      .isInstanceOf(Synonym.class)
      .isEqualToComparingFieldByField(new Synonym().setObjectID("synonym1").setSynonyms(synonymList));
  }

  @Test
  public void deleteSynonym() throws AlgoliaException {
    Index<?> index = client.initIndex("index2");

    index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))).waitForCompletion();
    index.deleteSynonym("synonym1").waitForCompletion();

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearSynonym() throws AlgoliaException {
    Index<?> index = client.initIndex("index3");

    index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))).waitForCompletion();
    index.clearSynonyms().waitForCompletion();

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchSaveSynonyms() throws AlgoliaException {
    Index<?> index = client.initIndex("index4");

    List<String> a = Arrays.asList("San Francisco", "SF");
    List<String> b = Arrays.asList("Paris", "pas la province");

    Synonym syn1 = new Synonym().setObjectID("syn1").setSynonyms(a);
    Synonym syn2 = new Synonym().setObjectID("syn2").setSynonyms(b);

    index.batchSynonyms(Arrays.asList(syn1, syn2)).waitForCompletion();

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(2);
  }

}

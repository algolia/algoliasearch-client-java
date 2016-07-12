package com.algolia.search.integration.async;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
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

abstract public class AsyncSynonymsTest extends AsyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2",
    "index3",
    "index4"
  );

  @Before
  @After
  public void cleanUp() throws Exception {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    waitForCompletion(client.batch(clean));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveAndGetSynonym() throws Exception {
    AsyncIndex<?> index = client.initIndex("index1");

    List<String> synonymList = Arrays.asList("San Francisco", "SF");

    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1").get();
    assertThat(synonym1.get())
      .isInstanceOf(Synonym.class)
      .isEqualToComparingFieldByField(new Synonym().setObjectID("synonym1").setSynonyms(synonymList));
  }

  @Test
  public void deleteSynonym() throws Exception {
    AsyncIndex<?> index = client.initIndex("index2");

    waitForCompletion(index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))));
    waitForCompletion(index.deleteSynonym("synonym1"));

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearSynonym() throws Exception {
    AsyncIndex<?> index = client.initIndex("index3");

    waitForCompletion(index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))));
    waitForCompletion(index.clearSynonyms());

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchSaveSynonyms() throws Exception {
    AsyncIndex<?> index = client.initIndex("index4");

    List<String> a = Arrays.asList("San Francisco", "SF");
    List<String> b = Arrays.asList("Paris", "pas la province");

    Synonym syn1 = new Synonym().setObjectID("syn1").setSynonyms(a);
    Synonym syn2 = new Synonym().setObjectID("syn2").setSynonyms(b);

    waitForCompletion(index.batchSynonyms(Arrays.asList(syn1, syn2)));

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(2);
  }

}

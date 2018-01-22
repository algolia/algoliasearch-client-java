package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public abstract class SyncSynonymsTest extends SyncAlgoliaIntegrationTest {

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
  @Test
  public void saveAndGetSynonym() throws AlgoliaException {
    Index<?> index = createIndex();

    List<String> synonymList = Arrays.asList("San Francisco", "SF");

    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    Optional<AbstractSynonym> synonym1 = index.getSynonym("synonym1");
    assertThat(synonym1.get())
        .isInstanceOf(Synonym.class)
        .isEqualToComparingFieldByField(
            new Synonym().setObjectID("synonym1").setSynonyms(synonymList));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void searchWithType() throws AlgoliaException {
    Index<?> index = createIndex();

    List<String> synonymList = Arrays.asList("San Francisco", "SF");

    waitForCompletion(index.saveSynonym("synonym1", new Synonym(synonymList)));

    SearchSynonymResult searchResult =
        index.searchSynonyms(new SynonymQuery("").setType("synonym"));
    assertThat(searchResult.getHits()).hasSize(1);
  }

  @Test
  public void deleteSynonym() throws AlgoliaException {
    Index<?> index = createIndex();

    waitForCompletion(
        index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))));
    waitForCompletion(index.deleteSynonym("synonym1"));

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearSynonym() throws AlgoliaException {
    Index<?> index = createIndex();

    waitForCompletion(
        index.saveSynonym("synonym1", new Synonym(Arrays.asList("San Francisco", "SF"))));
    waitForCompletion(index.clearSynonyms());

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchSaveSynonyms() throws AlgoliaException {
    Index<?> index = createIndex();

    List<String> a = Arrays.asList("San Francisco", "SF");
    List<String> b = Arrays.asList("Paris", "pas la province");

    Synonym syn1 = new Synonym().setObjectID("syn1").setSynonyms(a);
    Synonym syn2 = new Synonym().setObjectID("syn2").setSynonyms(b);

    waitForCompletion(index.batchSynonyms(Arrays.asList(syn1, syn2)));

    SearchSynonymResult searchResult = index.searchSynonyms(new SynonymQuery(""));
    assertThat(searchResult.getHits()).hasSize(2);
  }
}

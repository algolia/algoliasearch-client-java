package com.algolia.search.integration.index;

import static com.algolia.search.integration.IntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.SearchIndex;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.integration.IntegrationTestExtension;
import com.algolia.search.iterators.SynonymsIterable;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import com.algolia.search.models.synonyms.Synonym;
import com.algolia.search.models.synonyms.SynonymQuery;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith({IntegrationTestExtension.class})
class SynonymsTest {

  private SearchIndex<SynonymTestObject> index;

  SynonymsTest() {
    String indexName = getTestIndexName("synonyms");
    index = searchClient.initIndex(indexName, SynonymTestObject.class);
  }

  @Test
  void testSynonyms() {

    List<SynonymTestObject> objectsToSave =
        Arrays.asList(
            new SynonymTestObject("Sony PlayStation <PLAYSTATIONVERSION>"),
            new SynonymTestObject("Nintendo Switch"),
            new SynonymTestObject("Nintendo Wii U"),
            new SynonymTestObject("Nintendo Game Boy Advance"),
            new SynonymTestObject("Microsoft Xbox"),
            new SynonymTestObject("Microsoft Xbox 360"),
            new SynonymTestObject("Microsoft Xbox One"));

    CompletableFuture<BatchIndexingResponse> saveObjectsFuture =
        index.saveObjectsAsync(objectsToSave, true);

    Synonym gba =
        Synonym.createSynonym("gba", Arrays.asList("gba", "gameboy advance", "game boy advance"));

    saveObjectsFuture.join().waitTask();
    CompletableFuture<SaveSynonymResponse> saveSynonymFuture = index.saveSynonymAsync(gba);

    Synonym wiiToWiiu =
        Synonym.createOneWaySynonym("wii_to_wii_u", "wii", Collections.singletonList("wii u"));

    Synonym playstationPlaceholder =
        Synonym.createPlaceHolder(
            "playstation_version_placeholder",
            "<PLAYSTATIONVERSION>",
            Arrays.asList("1", "one", "2", "3", "4", "4 pro"));

    Synonym ps4 =
        Synonym.createAltCorrection1("ps4", "ps4", Collections.singletonList("playstation4"));

    Synonym psone =
        Synonym.createAltCorrection2("psone", "psone", Collections.singletonList("playstationone"));

    CompletableFuture<SaveSynonymResponse> saveSynonymsFuture =
        index.saveSynonymsAsync(Arrays.asList(wiiToWiiu, playstationPlaceholder, ps4, psone));

    saveSynonymFuture.join().waitTask();
    saveSynonymsFuture.join().waitTask();

    // Retrieve the 5 added synonyms with getSynonym and check they are correctly retrieved
    CompletableFuture<Synonym> gbaFuture = index.getSynonymAsync(gba.getObjectID());
    CompletableFuture<Synonym> wiiFuture = index.getSynonymAsync(wiiToWiiu.getObjectID());
    CompletableFuture<Synonym> playstationtionPlaceHolderFuture =
        index.getSynonymAsync(playstationPlaceholder.getObjectID());
    CompletableFuture<Synonym> ps4Future = index.getSynonymAsync(ps4.getObjectID());
    CompletableFuture<Synonym> psoneFuture = index.getSynonymAsync(psone.getObjectID());

    CompletableFuture.allOf(
            gbaFuture, wiiFuture, playstationtionPlaceHolderFuture, ps4Future, psoneFuture)
        .join();

    assertThat(gba).usingRecursiveComparison().isEqualTo(gbaFuture.join());
    assertThat(wiiToWiiu).usingRecursiveComparison().isEqualTo(wiiFuture.join());
    assertThat(playstationPlaceholder)
        .usingRecursiveComparison()
        .isEqualTo(playstationtionPlaceHolderFuture.join());
    assertThat(ps4).usingRecursiveComparison().isEqualTo(ps4Future.join());
    assertThat(psone).usingRecursiveComparison().isEqualTo(psoneFuture.join());

    // Perform a synonym search using searchSynonyms with an empty query, page 0 and hitsPerPage set
    // to 10 and check that the returned synonyms are the same as the 5 originally saved
    SearchResult<Synonym> searchResult =
        index.searchSynonymsAsync(new SynonymQuery("").setHitsPerPage(10).setPage(0)).join();
    assertThat(searchResult.getHits()).hasSize(5);

    // Instantiate a new SynonymIterator using newSynonymIterator and iterate over all the synonyms
    // and check that those collected synonyms are the same as the 5 originally saved
    List<Synonym> synonymsFromIterator = new ArrayList<>();

    for (Synonym synonym : new SynonymsIterable(index)) {
      synonymsFromIterator.add(synonym);
    }

    for (Synonym synonym : Arrays.asList(gba, wiiToWiiu, playstationPlaceholder, ps4, psone)) {
      assertThat(
              synonymsFromIterator.stream()
                  .filter(r -> r.getObjectID().equals(synonym.getObjectID()))
                  .findFirst()
                  .get())
          .usingRecursiveComparison()
          .isEqualTo(synonym);
    }

    // Delete the synonym with objectID=”gba” using deleteSynonym and wait for the task to
    // terminate using waitTask with the returned taskID
    index.deleteSynonymAsync(gba.getObjectID()).join().waitTask();

    // Try to get the synonym with getSynonym with objectID “gba” and check that the synonym does
    // not exist anymore (404)
    assertThatThrownBy(() -> index.getSynonymAsync(gba.getObjectID()).join())
        .hasCauseInstanceOf(AlgoliaApiException.class)
        .hasMessageContaining("Synonym set does not exist");

    // Clear all the synonyms using clearSynonyms and wait for the task to terminate using
    // waitTask with the returned taskID
    index.clearSynonymsAsync().join().waitTask();

    // Perform a synonym search using searchSynonyms with an empty query, page 0 and hitsPerPage
    // set to 10 and check that the number of returned synonyms is equal to 0
    SearchResult<Synonym> searchResultAfterClear =
        index.searchSynonymsAsync(new SynonymQuery("").setHitsPerPage(10).setPage(0)).join();
    assertThat(searchResultAfterClear.getHits()).hasSize(0);
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  class SynonymTestObject {

    SynonymTestObject(String console) {
      this.console = console;
    }

    public String getObjectID() {
      return objectID;
    }

    public SynonymTestObject setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }

    public String getConsole() {
      return console;
    }

    public SynonymTestObject setConsole(String console) {
      this.console = console;
      return this;
    }

    private String objectID;
    private String console;
  }
}

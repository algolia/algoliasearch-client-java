package com.algolia.search.integration.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClientDictionary;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.DictionarySettings;
import com.algolia.search.models.dictionary.DisableStandardEntries;
import com.algolia.search.models.dictionary.Compound;
import com.algolia.search.models.dictionary.DictionaryEntry;
import com.algolia.search.models.dictionary.Plural;
import com.algolia.search.models.dictionary.Stopword;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public abstract class DictionaryTest {

  private final SearchClientDictionary searchClient;

  protected DictionaryTest(SearchClientDictionary searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testStopwordsDictionaries() {
    String objectID = UUID.randomUUID().toString();
    Query query = new Query(objectID);
    Dictionary dictionary = Dictionary.STOPWORDS;

    // Search non-existent.
    SearchResult<Stopword> search = searchClient.searchDictionaryEntries(dictionary, query, null);
    assertThat(search.getNbHits()).isZero();

    Stopword stopword = DictionaryEntry.stopword(objectID, "en", "upper", "enabled");

    // Save entry
    searchClient.saveDictionaryEntries(dictionary, Collections.singletonList(stopword)).waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(stopword);

    // Replace entry
    stopword.setWord("uppercase");
    searchClient
        .replaceDictionaryEntries(dictionary, Collections.singletonList(stopword))
        .waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(stopword);
    assertThat(search.getHits().get(0).getWord()).isEqualTo(stopword.getWord());

    // Delete entry
    searchClient
        .deleteDictionaryEntries(dictionary, Collections.singletonList(objectID))
        .waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isZero();
  }

  @Test
  void testPluralsDictionaries() {
    String objectID = UUID.randomUUID().toString();
    Query query = new Query(objectID);
    Dictionary dictionary = Dictionary.PLURALS;

    // Search non-existent.
    SearchResult<Plural> search = searchClient.searchDictionaryEntries(dictionary, query, null);
    assertThat(search.getNbHits()).isZero();

    Plural plural = DictionaryEntry.plural(objectID, "en", Arrays.asList("cheval", "chevaux"));

    // Save
    searchClient.saveDictionaryEntries(dictionary, Collections.singletonList(plural)).waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(plural);

    // Delete
    searchClient
        .deleteDictionaryEntries(dictionary, Collections.singletonList(objectID))
        .waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isZero();
  }

  @Test
  void testCompoundsDictionary() {
    String objectID = UUID.randomUUID().toString();
    Query query = new Query(objectID);
    Dictionary dictionary = Dictionary.COMPOUNDS;

    SearchResult<Compound> search = searchClient.searchDictionaryEntries(dictionary, query, null);
    assertThat(search.getNbHits()).isZero();

    Compound compound =
        DictionaryEntry.compound(
            objectID, "nl", "kopfschmerztablette", Arrays.asList("kopf", "schmerz", "tablette"));

    // Save
    searchClient.saveDictionaryEntries(dictionary, Collections.singletonList(compound)).waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(compound);

    // Delete
    searchClient
        .deleteDictionaryEntries(dictionary, Collections.singletonList(objectID))
        .waitTask();
    search = searchClient.searchDictionaryEntries(dictionary, query);
    assertThat(search.getNbHits()).isZero();
  }

  @Test
  void testSettings() {
    DictionarySettings settings =
        new DictionarySettings()
            .setDisableStandardEntries(
                new DisableStandardEntries().setStopwords(Collections.singletonMap("en", true)));

    searchClient.setDictionarySettings(settings).waitTask();
    assertThat(searchClient.getDictionarySettings()).isEqualTo(settings);
  }
}

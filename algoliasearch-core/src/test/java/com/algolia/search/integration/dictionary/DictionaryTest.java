package com.algolia.search.integration.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClientDictionary;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.DictionarySettings;
import com.algolia.search.models.dictionary.DisableStandardEntries;
import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.algolia.search.models.dictionary.entry.Stopword;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
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

    SearchResult<Stopword> search =
        searchClient.searchDictionaryEntries(Dictionary.STOPWORDS, query, null);
    assertThat(search.getNbHits()).isZero();

    Stopword stopword = DictionaryEntry.stopword(objectID, "en", "upper", "enabled");

    // Save entry
    searchClient
        .saveDictionaryEntries(Dictionary.STOPWORDS, Collections.singletonList(stopword))
        .waitTask();
    search = searchClient.searchDictionaryEntries(Dictionary.STOPWORDS, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(stopword);

    // Replace entry
    stopword.setWord("uppercase");
    searchClient
        .replaceDictionaryEntries(Dictionary.STOPWORDS, Collections.singletonList(stopword))
        .waitTask();
    search = searchClient.searchDictionaryEntries(Dictionary.STOPWORDS, query);
    assertThat(search.getNbHits()).isEqualTo(1);
    assertThat(search.getHits().get(0)).isEqualTo(stopword);
    assertThat(search.getHits().get(0).getWord()).isEqualTo(stopword.getWord());

    // Delete entry
    searchClient
        .deleteDictionaryEntries(Dictionary.STOPWORDS, Collections.singletonList(objectID))
        .waitTask();
    search = searchClient.searchDictionaryEntries(Dictionary.STOPWORDS, query);
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

package com.algolia.search.integration.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClientDictionary;
import com.algolia.search.models.dictionary.Dictionary;
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

    SearchResult<Stopword> search =
        searchClient.search(Dictionary.STOPWORDS, new Query().setQuery(objectID), null);
    assertThat(search.getNbHits()).isZero();

    Stopword stopword = DictionaryEntry.stopword(objectID, "en", "upper", "enabled");

    // Save entry
    searchClient
        .saveDictionaryEntries(
            Dictionary.STOPWORDS, Collections.singletonList(stopword), null, null)
        .waitTask();
    SearchResult<Stopword> searchAfterSave =
        searchClient.search(Dictionary.STOPWORDS, new Query().setQuery(objectID), null);
    assertThat(searchAfterSave.getNbHits()).isEqualTo(1);
    assertThat(searchAfterSave.getHits().get(0)).isEqualTo(stopword);

    // Delete entry
    searchClient
        .deleteDictionaryEntries(Dictionary.STOPWORDS, Collections.singletonList(objectID), null)
        .waitTask();
    SearchResult<Stopword> searchAfterDelete =
        searchClient.search(Dictionary.STOPWORDS, new Query().setQuery(objectID), null);
    assertThat(searchAfterDelete.getNbHits()).isZero();
  }
}

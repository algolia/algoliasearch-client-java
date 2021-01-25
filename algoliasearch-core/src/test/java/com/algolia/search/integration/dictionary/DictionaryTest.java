package com.algolia.search.integration.dictionary;

import com.algolia.search.SearchClient;
import com.algolia.search.models.dictionary.Dictionary;
import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.algolia.search.models.dictionary.entry.Stopword;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public abstract class DictionaryTest {

  private final SearchClient searchClient;

  protected DictionaryTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testStopwordsDictionaries() {
    String objectID = UUID.randomUUID().toString();
    Stopword stopword = DictionaryEntry.stopword(objectID, "en", "upper", "enabled");
    searchClient.saveDictionaryEntries(
        Dictionary.STOPWORDS, Collections.singletonList(stopword), null, null);
  }
}

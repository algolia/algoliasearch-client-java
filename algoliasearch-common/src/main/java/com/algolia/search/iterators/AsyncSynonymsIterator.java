package com.algolia.search.iterators;

import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class AsyncSynonymsIterator extends AsyncAlgoliaIterator<AbstractSynonym> {
  private static final SynonymQuery EMPTY_QUERY = new SynonymQuery("");

  AsyncSynonymsIterator(@Nonnull AsyncIndex<?> index) {
    super(index);
  }

  AsyncSynonymsIterator(@Nonnull AsyncIndex<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  @Override
  List<AbstractSynonym> doQueryToGetHits(Integer page) {
    try {
      SearchSynonymResult result =
          index.searchSynonyms(EMPTY_QUERY.setPage(page).setHitsPerPage(hitsPerPage)).get();
      if (result == null) { // Non existing index
        return SearchSynonymResult.empty().getHits();
      }
      return result.getHits();
    } catch (InterruptedException | ExecutionException e) {
      // If there is a jackson exception we have to throw a runtime because Iterator doesn't have
      // exceptions
      throw new RuntimeException(e);
    }
  }
}

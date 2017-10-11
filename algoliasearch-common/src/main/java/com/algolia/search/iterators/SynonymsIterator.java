package com.algolia.search.iterators;

import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.objects.SynonymQuery;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.List;
import javax.annotation.Nonnull;

public class SynonymsIterator extends AlgoliaIterator<AbstractSynonym> {

  private static final SynonymQuery EMPTY_QUERY = new SynonymQuery("");

  SynonymsIterator(@Nonnull Index<?> index) {
    super(index);
  }

  SynonymsIterator(@Nonnull Index<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  @Override
  List<AbstractSynonym> doQueryToGetHits(Integer page) {
    try {
      SearchSynonymResult result =
          index.searchSynonyms(EMPTY_QUERY.setPage(page).setHitsPerPage(hitsPerPage));
      if (result == null) { // Non existing index
        return SearchSynonymResult.empty().getHits();
      }
      return result.getHits();
    } catch (AlgoliaException e) {
      // If there is a jackson exception we have to throw a runtime because Iterator doesn't have
      // exceptions
      throw new RuntimeException(e);
    }
  }
}

package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.SearchResult;
import com.algolia.search.models.Synonym;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.SynonymQuery;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class SynonymsIterator extends AlgoliaIterator<Synonym> {

  public SynonymsIterator(@Nonnull SearchIndex<?> index) {
    super(index);
  }

  public SynonymsIterator(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  public SynonymsIterator(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {
    super(index, hitsPerPage, requestOptions);
  }

  @Override
  SearchResult<Synonym> doQuery(Integer page, RequestOptions requestOptions) {
    return index.searchSynonyms(
        new SynonymQuery("").setPage(page).setHitsPerPage(hitsPerPage), requestOptions);
  }
}

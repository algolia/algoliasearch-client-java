package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.SearchResult;
import com.algolia.search.models.Synonym;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.SynonymQuery;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class SynonymsIterator extends AlgoliaIterator<Synonym> {

  private SynonymQuery query;

  public SynonymsIterator(@Nonnull SearchIndex<?> index) {
    super(index);
  }

  public SynonymsIterator(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    this(index, hitsPerPage, null);
  }

  public SynonymsIterator(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {
    super(index, hitsPerPage, requestOptions);
    query = new SynonymQuery("").setHitsPerPage(hitsPerPage);
  }

  @Override
  SearchResult<Synonym> doQuery(Integer page, RequestOptions requestOptions) {
    return index.searchSynonyms(query.setPage(page), requestOptions);
  }
}

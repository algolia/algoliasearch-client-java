package com.algolia.search.iterators;

import com.algolia.search.SearchIndex;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.synonyms.Synonym;
import com.algolia.search.models.synonyms.SynonymQuery;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class SynonymsIterator extends AlgoliaBaseIterator<Synonym> {

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

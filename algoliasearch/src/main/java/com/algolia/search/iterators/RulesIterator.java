package com.algolia.search.iterators;

import com.algolia.search.SearchIndex;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.rules.Rule;
import com.algolia.search.models.rules.RuleQuery;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class RulesIterator extends AlgoliaIterator<Rule> {

  private RuleQuery query;

  public RulesIterator(@Nonnull SearchIndex<?> index) {
    super(index);
  }

  public RulesIterator(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    this(index, hitsPerPage, null);
  }

  public RulesIterator(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {
    super(index, hitsPerPage, requestOptions);
    query = new RuleQuery("").setHitsPerPage(hitsPerPage);
  }

  @Override
  SearchResult<Rule> doQuery(Integer page, RequestOptions requestOptions) {
    return index.searchRules(query.setPage(page), requestOptions);
  }
}

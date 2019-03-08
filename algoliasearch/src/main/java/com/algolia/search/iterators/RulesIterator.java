package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.Rule;
import com.algolia.search.models.SearchResult;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.RuleQuery;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class RulesIterator extends AlgoliaIterator<Rule> {

  public RulesIterator(@Nonnull SearchIndex<?> index) {
    super(index);
  }

  public RulesIterator(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  public RulesIterator(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {
    super(index, hitsPerPage, requestOptions);
  }

  @Override
  SearchResult<Rule> doQuery(Integer page, RequestOptions requestOptions) {
    return index.searchRules(
        new RuleQuery("").setPage(page).setHitsPerPage(hitsPerPage), requestOptions);
  }
}

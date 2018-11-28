package com.algolia.search.iterators;

import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchRuleResult;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class AsyncRulesIterator extends AsyncAlgoliaIterator<Rule> {

  private static final RuleQuery EMPTY_QUERY = new RuleQuery("");

  public AsyncRulesIterator(@Nonnull AsyncIndex<?> index) {
    super(index);
  }

  public AsyncRulesIterator(@Nonnull AsyncIndex<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  @Override
  List<Rule> doQueryToGetHits(Integer page) {
    try {
      SearchRuleResult result =
          index.searchRules(EMPTY_QUERY.setPage(page).setHitsPerPage(hitsPerPage)).get();
      if (result == null) { // Non existing index
        return SearchRuleResult.empty().getHits();
      }
      return result.getHits();
    } catch (InterruptedException | ExecutionException e) {
      // If there is a jackson exception we have to throw a runtime because Iterator doesn't have
      // exceptions
      throw new RuntimeException(e);
    }
  }
}

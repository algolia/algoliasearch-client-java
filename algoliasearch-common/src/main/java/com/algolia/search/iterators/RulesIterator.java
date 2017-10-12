package com.algolia.search.iterators;

import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchRuleResult;
import java.util.List;
import javax.annotation.Nonnull;

public class RulesIterator extends AlgoliaIterator<Rule> {

  private static final RuleQuery EMPTY_QUERY = new RuleQuery("");

  public RulesIterator(@Nonnull Index<?> index) {
    super(index);
  }

  public RulesIterator(@Nonnull Index<?> index, @Nonnull Integer hitsPerPage) {
    super(index, hitsPerPage);
  }

  @Override
  List<Rule> doQueryToGetHits(Integer page) {
    try {
      SearchRuleResult result =
          index.searchRules(EMPTY_QUERY.setPage(page).setHitsPerPage(hitsPerPage));
      if (result == null) { // Non existing index
        return SearchRuleResult.empty().getHits();
      }
      return result.getHits();
    } catch (AlgoliaException e) {
      // If there is a jackson exception we have to throw a runtime because Iterator doesn't have
      // exceptions
      throw new RuntimeException(e);
    }
  }
}

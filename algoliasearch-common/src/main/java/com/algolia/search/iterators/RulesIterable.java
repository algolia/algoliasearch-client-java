package com.algolia.search.iterators;

import com.algolia.search.Index;
import com.algolia.search.inputs.query_rules.Rule;
import java.util.Iterator;
import javax.annotation.Nonnull;

public class RulesIterable implements Iterable<Rule> {

  private final Index<?> index;
  private final Integer hitsPerPage;

  public RulesIterable(@Nonnull Index<?> index) {
    this(index, 10000);
  }

  public RulesIterable(@Nonnull Index<?> index, @Nonnull Integer hitsPerPage) {
    this.index = index;
    this.hitsPerPage = hitsPerPage;
  }

  @Override
  public Iterator<Rule> iterator() {
    return new RulesIterator(index, hitsPerPage);
  }
}

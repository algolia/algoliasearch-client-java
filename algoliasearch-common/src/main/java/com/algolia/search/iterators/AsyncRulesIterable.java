package com.algolia.search.iterators;

import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.Rule;
import java.util.Iterator;
import javax.annotation.Nonnull;

public class AsyncRulesIterable implements Iterable<Rule> {

  private final AsyncIndex<?> index;
  private final Integer hitsPerPage;

  public AsyncRulesIterable(@Nonnull AsyncIndex<?> index) {
    this(index, 1000);
  }

  public AsyncRulesIterable(@Nonnull AsyncIndex<?> index, @Nonnull Integer hitsPerPage) {
    this.index = index;
    this.hitsPerPage = hitsPerPage;
  }

  @Override
  public Iterator<Rule> iterator() {
    return new AsyncRulesIterator(index, hitsPerPage);
  }
}

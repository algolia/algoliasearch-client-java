package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.Rule;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class RulesIterable implements Iterable<Rule> {

  private final SearchIndex<?> index;
  private final Integer hitsPerPage;
  private final RequestOptions requestOptions;

  public RulesIterable(@Nonnull SearchIndex<?> index) {
    this(index, 1000, null);
  }

  public RulesIterable(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    this(index, hitsPerPage, null);
  }

  public RulesIterable(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {

    Objects.requireNonNull(index, "Index is required");
    Objects.requireNonNull(hitsPerPage, "hitsPerPage is required");

    this.index = index;
    this.hitsPerPage = hitsPerPage;
    this.requestOptions = requestOptions;
  }

  @Override
  @Nonnull
  public Iterator<Rule> iterator() {
    return new RulesIterator(index, hitsPerPage, requestOptions);
  }
}

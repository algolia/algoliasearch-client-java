package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.BrowseIndexQuery;
import com.algolia.search.models.RequestOptions;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class IndexIterable<E> implements Iterable<E> {

  private final SearchIndex<E> index;
  private BrowseIndexQuery query;
  private final RequestOptions requestOptions;

  public IndexIterable(@Nonnull SearchIndex<E> index) {
    this(index, new BrowseIndexQuery());
  }

  public IndexIterable(@Nonnull SearchIndex<E> index, @Nonnull BrowseIndexQuery query) {
    this(index, query, null);
  }

  public IndexIterable(
      @Nonnull SearchIndex<E> index,
      @Nonnull BrowseIndexQuery query,
      RequestOptions requestOptions) {

    Objects.requireNonNull(index, "Index is required");
    Objects.requireNonNull(query, "Query is required");

    this.index = index;
    this.query = query;
    this.requestOptions = requestOptions;
  }

  @Override
  @Nonnull
  public Iterator<E> iterator() {
    return new IndexIterator<>(index, query, requestOptions);
  }
}

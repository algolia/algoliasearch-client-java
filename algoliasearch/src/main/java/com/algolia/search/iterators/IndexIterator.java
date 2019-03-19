package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.BrowseIndexQuery;
import com.algolia.search.models.common.BrowseIndexResponse;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class IndexIterator<E> implements Iterator<E> {

  private final SearchIndex<E> index;
  private final RequestOptions requestOptions;
  private final BrowseIndexQuery query;
  private Integer currentPage = 0;
  private Iterator<E> currentIterator = null;
  private boolean isFirstRequest = true;

  public IndexIterator(@Nonnull SearchIndex<E> index) {
    this(index, (BrowseIndexQuery) new BrowseIndexQuery().setHitsPerPage(1000));
  }

  public IndexIterator(@Nonnull SearchIndex<E> index, @Nonnull BrowseIndexQuery query) {
    this(index, query, null);
  }

  public IndexIterator(
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
  public boolean hasNext() {

    if (isFirstRequest) {
      browseAndSetInnerState();
      isFirstRequest = false;
    }

    if (currentPage != null && !currentIterator.hasNext()) {
      browseAndSetInnerState();
    }

    return currentIterator != null && currentIterator.hasNext();
  }

  @Override
  public E next() {
    if (currentIterator == null || !currentIterator.hasNext()) {
      browseAndSetInnerState();
      isFirstRequest = false;
    }
    return currentIterator.next();
  }

  private void browseAndSetInnerState() {
    BrowseIndexResponse<E> result =
        doQuery((BrowseIndexQuery) query.setPage(currentPage), requestOptions);
    currentIterator = result.getHits().iterator();

    if (result.getCursor() != null) {
      currentPage++;
    } else {
      currentPage = null;
    }
  }

  BrowseIndexResponse<E> doQuery(BrowseIndexQuery query, RequestOptions requestOptions) {
    return index.browseFrom(query, requestOptions);
  }
}

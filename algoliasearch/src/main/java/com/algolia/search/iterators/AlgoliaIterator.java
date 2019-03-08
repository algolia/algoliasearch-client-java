package com.algolia.search.iterators;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.models.SearchResult;
import com.algolia.search.objects.RequestOptions;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

abstract class AlgoliaIterator<E> implements Iterator<E> {

  protected final SearchIndex<?> index;
  protected final Integer hitsPerPage;
  private final RequestOptions requestOptions;

  // Internal state
  private boolean isFirstRequest = true;
  private Integer currentPage = 0;
  private Iterator<E> currentIterator = null;

  abstract SearchResult<E> doQuery(Integer page, RequestOptions requestOptions);

  AlgoliaIterator(@Nonnull SearchIndex<?> index) {
    this(index, 1000, null);
  }

  AlgoliaIterator(@Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage) {
    this(index, hitsPerPage, null);
  }

  AlgoliaIterator(
      @Nonnull SearchIndex<?> index, @Nonnull Integer hitsPerPage, RequestOptions requestOptions) {

    Objects.requireNonNull(index, "Index is required");
    Objects.requireNonNull(hitsPerPage, "hitsPerPage is required");

    this.index = index;
    this.hitsPerPage = hitsPerPage;
    this.requestOptions = requestOptions;
  }

  @Override
  public boolean hasNext() {

    if (isFirstRequest) {
      executeQueryAndSetInnerState();
      isFirstRequest = false;
    }

    if (currentPage != null && !currentIterator.hasNext()) {
      executeQueryAndSetInnerState();
    }

    return currentIterator != null && currentIterator.hasNext();
  }

  @Override
  public E next() {
    if (currentIterator == null || !currentIterator.hasNext()) {
      executeQueryAndSetInnerState();
      isFirstRequest = false;
    }
    return currentIterator.next();
  }

  private void executeQueryAndSetInnerState() {
    SearchResult<E> result = doQuery(currentPage, requestOptions);
    currentIterator = result.getHits().iterator();

    if (result.getNbHits() > 0) {
      currentPage++;
    } else {
      currentPage = null;
    }
  }
}

package com.algolia.search.iterators;

import com.algolia.search.Index;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

abstract class AlgoliaIterator<E> implements Iterator<E> {

  protected final Index<?> index;
  protected final Integer hitsPerPage;

  // Internal state
  private Integer currentPage = 0;
  private Iterator<E> currentIterator = null;
  private boolean isFirstRequest = true;

  AlgoliaIterator(@Nonnull Index<?> index) {
    this(index, 1000);
  }

  AlgoliaIterator(@Nonnull Index<?> index, @Nonnull Integer hitsPerPage) {
    this.index = index;
    this.hitsPerPage = hitsPerPage;
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
    List<E> result = doQueryToGetHits(currentPage);
    currentIterator = result.iterator();

    if (result.size() <= hitsPerPage) { // end of rules
      currentPage += 1;
    } else {
      currentPage = null;
    }
  }

  abstract List<E> doQueryToGetHits(Integer page);
}

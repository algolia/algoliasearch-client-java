package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.BrowseResult;

import java.util.Iterator;

public class IndexIterator<T> implements Iterator<T> {

  private final APIClient apiClient;
  private final String indexName;
  private final Query query;
  private final Class<T> klass;

  private String currentCursor = null;
  private boolean isFirstRequest = true;
  private Iterator<T> currentIterator = null;

  IndexIterator(APIClient apiClient, String indexName, Query query, String cursor, Class<T> klass) {
    this.apiClient = apiClient;
    this.indexName = indexName;
    this.query = query;
    this.currentCursor = cursor;
    this.klass = klass;
  }

  @Override
  public boolean hasNext() {
    if (isFirstRequest) {
      executeQueryAndSetInnerState();
      isFirstRequest = false;
    }
    if(currentCursor != null && !currentIterator.hasNext()) {
      executeQueryAndSetInnerState();
    }
    return currentIterator != null && currentIterator.hasNext();
  }

  @Override
  public T next() {
    if (currentIterator == null || !currentIterator.hasNext()) {
      executeQueryAndSetInnerState();
      isFirstRequest = false;
    }
    return currentIterator.next();
  }

  private void executeQueryAndSetInnerState() {
    BrowseResult<T> browseResult = doQuery(currentCursor);
    currentCursor = browseResult.getCursor();
    currentIterator = browseResult.getHits().iterator();
  }

  private BrowseResult<T> doQuery(String cursor) {
    try {
      BrowseResult<T> browseResult = apiClient.browse(indexName, query, cursor, klass);
      if (browseResult == null) { //Non existing index
        return BrowseResult.empty();
      }
      return browseResult;
    } catch (AlgoliaException e) {
      return BrowseResult.empty();
    }
  }

  public String getCursor() {
    return currentCursor;
  }
}

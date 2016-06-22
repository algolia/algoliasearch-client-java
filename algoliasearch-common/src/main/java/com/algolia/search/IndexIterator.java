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
    return isFirstRequest || currentCursor != null;
  }

  @Override
  public T next() {
    if (currentIterator == null || !currentIterator.hasNext()) {
      BrowseResult<T> browseResult = doQuery(currentCursor);
      currentCursor = browseResult.getCursor();
      currentIterator = browseResult.getHits().iterator();
      isFirstRequest = false;
    }
    return currentIterator.next();
  }

  private BrowseResult<T> doQuery(String cursor) {
    try {
      return apiClient.browse(indexName, query, cursor, klass);
    } catch (AlgoliaException e) {
      return BrowseResult.empty();
    }
  }
}

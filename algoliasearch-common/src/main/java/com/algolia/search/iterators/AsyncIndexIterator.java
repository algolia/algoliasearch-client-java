package com.algolia.search.iterators;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.responses.BrowseResult;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class AsyncIndexIterator<T> implements Iterator<T> {

  private final AsyncAPIClient apiClient;
  private final String indexName;
  private final Query query;
  private final RequestOptions options;
  private final Class<T> klass;

  private String currentCursor;
  private boolean isFirstRequest = true;
  private Iterator<T> currentIterator = null;

  AsyncIndexIterator(
      AsyncAPIClient apiClient,
      String indexName,
      Query query,
      String cursor,
      RequestOptions options,
      Class<T> klass) {
    this.apiClient = apiClient;
    this.indexName = indexName;
    this.query = query;
    this.currentCursor = cursor;
    this.options = options;
    this.klass = klass;
  }

  @Override
  public boolean hasNext() {
    if (isFirstRequest) {
      executeQueryAndSetInnerState();
      isFirstRequest = false;
    }
    if (currentCursor != null && !currentIterator.hasNext()) {
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
      BrowseResult<T> browseResult =
          apiClient.browse(indexName, query, cursor, klass, options).get();
      if (browseResult == null) { // Non existing index
        return BrowseResult.empty();
      }
      return browseResult;
    } catch (InterruptedException | ExecutionException e) {
      // If there is a jackson exception we have to throw a runtime because Iterator doesn't have
      // exceptions
      throw new RuntimeException(e);
    }
  }

  public String getCursor() {
    return currentCursor;
  }
}

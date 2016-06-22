package com.algolia.search;

import com.algolia.search.objects.Query;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IndexIterable<T> implements Iterable<T> {

  private final APIClient apiClient;
  private final String indexName;
  private final Query query;
  private final Class<T> klass;
  private final String cursor;

  IndexIterable(APIClient apiClient, String indexName, Query query, Class<T> klass) {
    this(apiClient, indexName, query, null, klass);
  }

  IndexIterable(APIClient apiClient, String indexName, Query query, String cursor, Class<T> klass) {
    this.apiClient = apiClient;
    this.indexName = indexName;
    this.query = query;
    this.cursor = cursor;
    this.klass = klass;
  }

  @Override
  public Iterator<T> iterator() {
    return new IndexIterator<>(apiClient, indexName, query, cursor, klass);
  }

  /**
   * Get a stream from this iterable
   *
   * @return a Stream
   */
  public Stream<T> stream() {
    return StreamSupport.stream(spliterator(), false);
  }
}

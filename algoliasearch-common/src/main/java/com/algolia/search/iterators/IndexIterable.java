package com.algolia.search.iterators;

import com.algolia.search.APIClient;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;

public class IndexIterable<T> implements Iterable<T> {

  private final IndexIterator<T> iterator;

  public IndexIterable(
      @Nonnull APIClient apiClient,
      @Nonnull String indexName,
      @Nonnull Query query,
      @Nonnull RequestOptions requestOptions,
      @Nonnull Class<T> klass) {
    this(apiClient, indexName, query, requestOptions, null, klass);
  }

  public IndexIterable(
      @Nonnull APIClient apiClient,
      @Nonnull String indexName,
      @Nonnull Query query,
      @Nonnull RequestOptions requestOptions,
      String cursor,
      @Nonnull Class<T> klass) {
    this.iterator = new IndexIterator<>(apiClient, indexName, query, cursor, requestOptions, klass);
  }

  @Override
  public Iterator<T> iterator() {
    return iterator;
  }

  /**
   * Get the cursor
   *
   * @return the cursor (can be null)
   */
  public String getCursor() {
    return iterator.getCursor();
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

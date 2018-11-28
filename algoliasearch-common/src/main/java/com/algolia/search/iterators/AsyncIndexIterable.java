package com.algolia.search.iterators;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;

public class AsyncIndexIterable<T> implements Iterable<T> {

  private final AsyncIndexIterator<T> iterator;

  public AsyncIndexIterable(
      @Nonnull AsyncAPIClient apiClient,
      @Nonnull String indexName,
      @Nonnull Query query,
      @Nonnull RequestOptions requestOptions,
      @Nonnull Class<T> klass) {
    this(apiClient, indexName, query, requestOptions, null, klass);
  }

  public AsyncIndexIterable(
      @Nonnull AsyncAPIClient apiClient,
      @Nonnull String indexName,
      @Nonnull Query query,
      @Nonnull RequestOptions requestOptions,
      String cursor,
      @Nonnull Class<T> klass) {
    this.iterator =
        new AsyncIndexIterator<>(apiClient, indexName, query, cursor, requestOptions, klass);
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

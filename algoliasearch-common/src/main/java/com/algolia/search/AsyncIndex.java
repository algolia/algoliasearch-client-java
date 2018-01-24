package com.algolia.search;

import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTaskSingleIndex;
import com.algolia.search.responses.SearchResult;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class AsyncIndex<T>
    implements AsyncIndexCRUD<T>,
        AsyncTasks<T>,
        AsyncObjects<T>,
        AsyncSettings<T>,
        AsyncKey<T>,
        AsyncSearchForFacet<T>,
        AsyncPartialUpdate<T>,
        AsyncSynonyms<T>,
        AsyncRules<T>,
        AsyncDeleteByQuery<T> {

  /** Index name */
  private final String name;

  /** The type of the objects in this Index */
  private final Class<T> klass;

  private final AsyncAPIClient client;

  AsyncIndex(String name, Class<T> klass, AsyncAPIClient client) {
    this.name = name;
    this.klass = klass;
    this.client = client;
  }

  public String getName() {
    return name;
  }

  public Class<T> getKlass() {
    return klass;
  }

  public AsyncAPIClient getApiClient() {
    return client;
  }

  /**
   * Search in the index
   *
   * @param query the query
   * @return the result of the search, or a failed Future if the index does not exists
   */
  public CompletableFuture<SearchResult<T>> search(@Nonnull Query query) {
    return search(query, RequestOptions.empty);
  }

  /**
   * Search in the index
   *
   * @param query the query
   * @param requestOptions Options to pass to this request
   * @return the result of the search, or a failed Future if the index does not exists
   */
  public CompletableFuture<SearchResult<T>> search(
      @Nonnull Query query, @Nonnull RequestOptions requestOptions) {
    return client.search(name, query, klass, requestOptions);
  }

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> batch(@Nonnull List<BatchOperation> operations) {
    return batch(operations, RequestOptions.empty);
  }

  /**
   * Custom batch
   *
   * <p>
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public CompletableFuture<AsyncTaskSingleIndex> batch(
      @Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions) {
    return client.batch(name, operations, requestOptions);
  }

  @Override
  public String toString() {
    return "AsyncIndex{" + "name='" + name + '\'' + ", klass=" + klass + '}';
  }
}

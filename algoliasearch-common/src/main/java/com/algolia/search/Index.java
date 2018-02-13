package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.TaskSingleIndex;
import com.algolia.search.responses.SearchResult;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public class Index<T>
    implements SyncDeleteByQuery<T>,
        SyncBrowse<T>,
        SyncSynonyms<T>,
        SyncPartialUpdate<T>,
        SyncSearchForFacet<T>,
        SyncKey<T>,
        SyncSettings<T>,
        SyncObjects<T>,
        SyncTasks<T>,
        SyncIndexCRUD<T>,
        SyncRules<T> {

  /** Index name */
  private final String name;

  /** The type of the objects in this Index */
  private final Class<T> klass;

  private final APIClient client;

  Index(String name, Class<T> klass, APIClient client) {
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

  @Override
  public APIClient getApiClient() {
    return client;
  }

  /**
   * Search in the index throws a {@link
   * com.algolia.search.exceptions.AlgoliaIndexNotFoundException} if the index does not exists
   *
   * @param query the query
   * @return the result of the search
   */
  public SearchResult<T> search(@Nonnull Query query) throws AlgoliaException {
    return search(query, RequestOptions.empty);
  }

  /**
   * Search in the index throws a {@link
   * com.algolia.search.exceptions.AlgoliaIndexNotFoundException} if the index does not exists
   *
   * @param query the query
   * @return the result of the search
   */
  public SearchResult<T> search(@Nonnull Query query, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return client.search(name, query, klass, requestOptions);
  }

  /**
   * Custom batch
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public TaskSingleIndex batch(@Nonnull List<BatchOperation> operations) throws AlgoliaException {
    return batch(operations, RequestOptions.empty);
  }

  /**
   * Custom batch
   *
   * <p>All operations must have index name set to <code>null</code>
   *
   * @param operations the list of operations to perform on this index
   * @param requestOptions Options to pass to this request
   * @return the associated task
   * @see BatchOperation & subclasses
   */
  public TaskSingleIndex batch(
      @Nonnull List<BatchOperation> operations, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return client.batch(name, operations, requestOptions);
  }

  @Override
  public String toString() {
    return "Index{" + "name='" + name + '\'' + ", klass=" + klass + '}';
  }

  @SuppressWarnings("unused")
  public static class Attributes {
    private String name;
    private String createdAt;
    private String updatedAt;
    private Long entries;
    private Long dataSize;
    private Long fileSize;
    private Long lastBuildTimeS;
    private Long numberOfPendingTasks;
    private Boolean pendingTask;

    public String getName() {
      return name;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public String getUpdatedAt() {
      return updatedAt;
    }

    public Long getEntries() {
      return entries;
    }

    public Long getDataSize() {
      return dataSize;
    }

    public Long getFileSize() {
      return fileSize;
    }

    public Long getLastBuildTimeS() {
      return lastBuildTimeS;
    }

    public Long getNumberOfPendingTasks() {
      return numberOfPendingTasks;
    }

    public Boolean getPendingTask() {
      return pendingTask;
    }
  }
}

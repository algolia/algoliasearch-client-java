package com.algolia.search;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.RecommendationsResult;
import com.algolia.search.models.recommend.GetRecommendationsResponse;
import com.algolia.search.models.recommend.RecommendationsQuery;
import com.algolia.search.models.recommend.RecommendationsRequests;
import com.algolia.search.models.recommend.RelatedProductsQuery;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Algolia's REST recommend client that wraps an instance of the transporter {@link HttpTransport}
 * which wraps the HTTP Client This client allows to build typed requests and read typed responses.
 * Requests are made under the Algolia's retry-strategy. This client is intended to be reused, and
 * it's thread-safe.
 *
 * @see <a href="https://www.algolia.com/doc/rest-api/recommend">Algolia.com</a>
 */
public final class RecommendClient implements Closeable {

  /** The transport layer. Should be reused. */
  private final HttpTransport transport;

  /** Client's configuration. Must be reused. */
  private final SearchConfig config;

  public RecommendClient(@Nonnull SearchConfig config, @Nonnull HttpRequester httpRequester) {
    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");
    this.config = config;
    this.transport = new HttpTransport(config, httpRequester);
  }

  /**
   * Close the underlying Http Client
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    transport.close();
  }

  public HttpTransport getTransport() {
    return transport;
  }

  public SearchConfig getConfig() {
    return config;
  }

  // region get_recommendations
  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public List<RecommendationsResult<Object>> getRecommendations(
      @Nonnull List<RecommendationsQuery> requests) {
    return LaunderThrowable.await(getRecommendationsAsync(requests));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T> List<RecommendationsResult<T>> getRecommendations(
      @Nonnull List<RecommendationsQuery> requests, Class<T> clazz) {
    return LaunderThrowable.await(getRecommendationsAsync(requests, clazz));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T> List<RecommendationsResult<T>> getRecommendations(
      @Nonnull List<RecommendationsQuery> requests, Class<T> clazz, RequestOptions requestOptions) {
    return LaunderThrowable.await(getRecommendationsAsync(requests, clazz, requestOptions));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public CompletableFuture<List<RecommendationsResult<Object>>> getRecommendationsAsync(
      @Nonnull List<RecommendationsQuery> requests) {
    return getRecommendationsAsync(requests, Object.class, null);
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T> CompletableFuture<List<RecommendationsResult<T>>> getRecommendationsAsync(
      @Nonnull List<RecommendationsQuery> requests, Class<T> clazz) {
    return getRecommendationsAsync(requests, clazz, null);
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  @SuppressWarnings("unchecked")
  public <T> CompletableFuture<List<RecommendationsResult<T>>> getRecommendationsAsync(
      @Nonnull List<RecommendationsQuery> requests, Class<T> clazz, RequestOptions requestOptions) {
    Objects.requireNonNull(requests);

    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/recommendations",
            CallType.READ,
            new RecommendationsRequests<>(requests),
            GetRecommendationsResponse.class,
            clazz,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<List<RecommendationsResult<T>>> r = new CompletableFuture<>();
              r.complete(resp.getResults());
              return r;
            },
            config.getExecutor());
  }
  // endregion

  // region get_related_products
  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public <T> GetRecommendationsResponse<T> getRelatedProducts(
      @Nonnull List<RelatedProductsQuery> requests) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public <T> GetRecommendationsResponse<T> getRelatedProducts(
      @Nonnull List<RelatedProductsQuery> requests, RequestOptions requestOptions) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests, requestOptions));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public <T> CompletableFuture<GetRecommendationsResponse<T>> getRelatedProductsAsync(
      @Nonnull List<RelatedProductsQuery> requests) {
    return getRelatedProductsAsync(requests, null);
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public <T> CompletableFuture<GetRecommendationsResponse<T>> getRelatedProductsAsync(
      @Nonnull List<RelatedProductsQuery> requests, RequestOptions requestOptions) {
    // TODO
    return null;
  }
  // endregion

  // region get_frequently_bought_together
  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public <T> GetRecommendationsResponse<T> getFrequentlyBoughtTogether(
      @Nonnull List<RecommendationsQuery> requests) {
    return LaunderThrowable.await(getFrequentlyBoughtTogetherAsync(requests, null));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public <T> GetRecommendationsResponse<T> getFrequentlyBoughtTogether(
      @Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    return LaunderThrowable.await(getFrequentlyBoughtTogetherAsync(requests, requestOptions));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public <T> CompletableFuture<GetRecommendationsResponse<T>> getFrequentlyBoughtTogetherAsync(
      @Nonnull List<RecommendationsQuery> requests) {
    return getFrequentlyBoughtTogetherAsync(requests, null);
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public <T> CompletableFuture<GetRecommendationsResponse<T>> getFrequentlyBoughtTogetherAsync(
      @Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    // TODO
    return null;
  }
  // endregion

  // private <T> CompletableFuture<GetRecommendationsResponse<T>> requestRecommendations(
  //    T requests, RequestOptions requestOptions) {
  //  return transport.executeRequestAsync(
  //      HttpMethod.POST,
  //      "/1/indexes/*/recommendations",
  //      CallType.READ,
  //      requests,
  //      GetRecommendationsResponse<T>.class,
  //      requestOptions);
  // }
}

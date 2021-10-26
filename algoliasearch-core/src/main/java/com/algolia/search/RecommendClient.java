package com.algolia.search;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.RecommendHit;
import com.algolia.search.models.indexing.RecommendationsResult;
import com.algolia.search.models.recommend.*;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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

  /** Transport object responsible for the serialization/deserialization and the retry strategy. */
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
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getRecommendations(
      @Nonnull List<RecommendationsQuery> requests, @Nonnull Class<T> clazz) {
    return LaunderThrowable.await(getRecommendationsAsync(requests, clazz));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getRecommendations(
      @Nonnull List<RecommendationsQuery> requests,
      @Nonnull Class<T> clazz,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(getRecommendationsAsync(requests, clazz, requestOptions));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getRecommendationsAsync(
          @Nonnull List<RecommendationsQuery> requests, @Nonnull Class<T> clazz) {
    return getRecommendationsAsync(requests, clazz, null);
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getRecommendationsAsync(
          @Nonnull List<RecommendationsQuery> requests,
          @Nonnull Class<T> clazz,
          RequestOptions requestOptions) {
    Objects.requireNonNull(requests);
    Objects.requireNonNull(clazz);
    RecommendationsRequests<RecommendationsQuery> data = new RecommendationsRequests<>(requests);
    return performGetRecommends(clazz, requestOptions, data);
  }
  // endregion

  // region get_related_products
  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getRelatedProducts(
      @Nonnull List<RelatedProductsQuery> requests, @Nonnull Class<T> clazz) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests, clazz));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getRelatedProducts(
      @Nonnull List<RelatedProductsQuery> requests,
      @Nonnull Class<T> clazz,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests, clazz, requestOptions));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getRelatedProductsAsync(
          @Nonnull List<RelatedProductsQuery> requests, @Nonnull Class<T> clazz) {
    return getRelatedProductsAsync(requests, clazz, null);
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getRelatedProductsAsync(
          @Nonnull List<RelatedProductsQuery> requests,
          @Nonnull Class<T> clazz,
          RequestOptions requestOptions) {
    Objects.requireNonNull(requests);
    Objects.requireNonNull(clazz);
    RecommendationsRequests<RelatedProductsQuery> data = new RecommendationsRequests<>(requests);
    return performGetRecommends(clazz, requestOptions, data);
  }
  // endregion

  // region get_frequently_bought_together
  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getFrequentlyBoughtTogether(
      @Nonnull List<FrequentlyBoughtTogetherQuery> requests, @Nonnull Class<T> clazz) {
    return LaunderThrowable.await(getFrequentlyBoughtTogetherAsync(requests, clazz));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit> List<RecommendationsResult<T>> getFrequentlyBoughtTogether(
      @Nonnull List<FrequentlyBoughtTogetherQuery> requests,
      @Nonnull Class<T> clazz,
      RequestOptions requestOptions) {
    return LaunderThrowable.await(
        getFrequentlyBoughtTogetherAsync(requests, clazz, requestOptions));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getFrequentlyBoughtTogetherAsync(
          @Nonnull List<FrequentlyBoughtTogetherQuery> requests, @Nonnull Class<T> clazz) {
    return getFrequentlyBoughtTogetherAsync(requests, clazz, null);
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param clazz The class held by the index. Could be your business object or {@link Object}
   * @param requestOptions options to pass to this request
   */
  public <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> getFrequentlyBoughtTogetherAsync(
          @Nonnull List<FrequentlyBoughtTogetherQuery> requests,
          @Nonnull Class<T> clazz,
          RequestOptions requestOptions) {
    Objects.requireNonNull(requests);
    Objects.requireNonNull(clazz);
    RecommendationsRequests<FrequentlyBoughtTogetherQuery> data =
        new RecommendationsRequests<>(requests);
    return performGetRecommends(clazz, requestOptions, data);
  }
  // endregion

  @SuppressWarnings("unchecked")
  private <T extends RecommendHit>
      CompletableFuture<List<RecommendationsResult<T>>> performGetRecommends(
          Class<T> clazz, RequestOptions requestOptions, RecommendationsRequests<?> data) {
    return transport
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/*/recommendations",
            CallType.READ,
            data,
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
}

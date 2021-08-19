package com.algolia.search;

import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.recommend.GetRecommendationsResponse;
import com.algolia.search.models.recommend.RecommendationsQuery;
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
  private final ConfigBase config;

  public RecommendClient(@Nonnull ConfigBase config, @Nonnull HttpTransport httpRequester) {
    Objects.requireNonNull(httpRequester, "An httpRequester is required.");
    Objects.requireNonNull(config, "A configuration is required.");
    this.transport = httpRequester;
    this.config = config;
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

  //region get_recommendations
  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public GetRecommendationsResponse getRecommendations(@Nonnull List<RecommendationsQuery> requests) {
    return LaunderThrowable.await(getRecommendationsAsync(requests));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public GetRecommendationsResponse getRecommendations(@Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    return LaunderThrowable.await(getRecommendationsAsync(requests, requestOptions));
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public CompletableFuture<GetRecommendationsResponse> getRecommendationsAsync(@Nonnull List<RecommendationsQuery> requests) {
    return getRecommendationsAsync(requests, null);
  }

  /**
   * Returns recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public CompletableFuture<GetRecommendationsResponse> getRecommendationsAsync(@Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    // TODO
  }
  //endregion

  //region get_related_products
  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public GetRecommendationsResponse getRelatedProducts(@Nonnull List<RelatedProductsQuery> requests) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public GetRecommendationsResponse getRelatedProducts(@Nonnull List<RelatedProductsQuery> requests, RequestOptions requestOptions) {
    return LaunderThrowable.await(getRelatedProductsAsync(requests, requestOptions));
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public CompletableFuture<GetRecommendationsResponse> getRelatedProductsAsync(@Nonnull List<RelatedProductsQuery> requests) {
    return getRelatedProductsAsync(requests, null);
  }

  /**
   * Returns related products recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public CompletableFuture<GetRecommendationsResponse> getRelatedProductsAsync(@Nonnull List<RelatedProductsQuery> requests, RequestOptions requestOptions) {
    // TODO
  }
  //endregion

  //region get_frequently_bought_together
  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public GetRecommendationsResponse getFrequentlyBoughtTogether(@Nonnull List<RecommendationsQuery> requests) {
    return LaunderThrowable.await(getFrequentlyBoughtTogetherAsync(requests, null));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public GetRecommendationsResponse getFrequentlyBoughtTogether(@Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    return LaunderThrowable.await(getFrequentlyBoughtTogetherAsync(requests, requestOptions));
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   */
  public CompletableFuture<GetRecommendationsResponse> getFrequentlyBoughtTogetherAsync(@Nonnull List<RecommendationsQuery> requests) {
    return getFrequentlyBoughtTogetherAsync(requests, null);
  }

  /**
   * Returns frequently bought together recommendations for a specific model and objectID.
   *
   * @param requests a list of recommendation requests to execute
   * @param requestOptions options to pass to this request
   */
  public CompletableFuture<GetRecommendationsResponse> getFrequentlyBoughtTogetherAsync(@Nonnull List<RecommendationsQuery> requests, RequestOptions requestOptions) {
    // TODO
  }
  //endregion
}

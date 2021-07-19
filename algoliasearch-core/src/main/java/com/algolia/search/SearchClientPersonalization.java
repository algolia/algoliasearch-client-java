package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.personalization.GetStrategyResponse;
import com.algolia.search.models.personalization.SetStrategyRequest;
import com.algolia.search.models.personalization.SetStrategyResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientPersonalization extends SearchClientBase {

  /**
   * Returns the personalization strategy of the application
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default GetStrategyResponse getPersonalizationStrategy() {
    return LaunderThrowable.await(getPersonalizationStrategyAsync());
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default GetStrategyResponse getPersonalizationStrategy(RequestOptions requestOptions) {
    return LaunderThrowable.await(getPersonalizationStrategyAsync(requestOptions));
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default CompletableFuture<GetStrategyResponse> getPersonalizationStrategyAsync() {
    return getPersonalizationStrategyAsync(null);
  }

  /**
   * Returns the personalization strategy of the application
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default CompletableFuture<GetStrategyResponse> getPersonalizationStrategyAsync(
      RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/recommendation/personalization/strategy",
            CallType.READ,
            GetStrategyResponse.class,
            requestOptions);
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default SetStrategyResponse setPersonalizationStrategy(@Nonnull SetStrategyRequest request) {
    return LaunderThrowable.await(setPersonalizationStrategyAsync(request));
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default SetStrategyResponse setPersonalizationStrategy(
      @Nonnull SetStrategyRequest request, RequestOptions requestOptions) {
    return LaunderThrowable.await(setPersonalizationStrategyAsync(request, requestOptions));
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default CompletableFuture<SetStrategyResponse> setPersonalizationStrategyAsync(
      @Nonnull SetStrategyRequest request) {
    return setPersonalizationStrategyAsync(request, null);
  }

  /**
   * This command configures the personalization strategy
   *
   * @param request The personalization strategy
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   * @deprecated Endpoint will be deprecated. Please use {@link PersonalizationConfig} instead.
   */
  @Deprecated
  default CompletableFuture<SetStrategyResponse> setPersonalizationStrategyAsync(
      @Nonnull SetStrategyRequest request, RequestOptions requestOptions) {
    Objects.requireNonNull(request, "request key is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/recommendation/personalization/strategy",
            CallType.WRITE,
            request,
            SetStrategyResponse.class,
            requestOptions);
  }
}

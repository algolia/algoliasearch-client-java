package com.algolia.search;

import javax.annotation.Nonnull;

public final class ApacheAPIClientBuilder extends APIClientBuilder {

  public ApacheAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(@Nonnull APIClientConfiguration configuration) {
    return new APIClient(new ApacheHttpClient(configuration), configuration);
  }

}

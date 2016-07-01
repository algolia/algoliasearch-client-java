package com.algolia.search;

import javax.annotation.Nonnull;

public final class AppEngineAPIClientBuilder extends APIClientBuilder {

  public AppEngineAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(@Nonnull APIClientConfiguration configuration) {
    throw new UnsupportedOperationException();
  }

}

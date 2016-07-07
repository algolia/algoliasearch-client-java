package com.algolia.search;

import javax.annotation.Nonnull;

public final class AsyncHttpAPIClientBuilder extends AsyncAPIClientBuilder {

  public AsyncHttpAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected AsyncAPIClient build(@Nonnull AsyncAPIClientConfiguration configuration) {
    return new AsyncAPIClient(new AsyncHttpClient(configuration), configuration);
  }

}

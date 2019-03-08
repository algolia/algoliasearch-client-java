package com.algolia.search.models;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.objects.ApiKey;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.function.Function;

public class AddApiKeyResponse implements IAlgoliaWaitableResponse, Serializable {

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setGetApiKeyFunction(Function<String, ApiKey> getApiKeyConsumer) {
    this.getApiKeyFunction = getApiKeyConsumer;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  private Function<String, ApiKey> getApiKeyFunction;
  private String key;
  private OffsetDateTime createdAt;

  @Override
  public void waitTask() {
    // Loop until the key is created on the server => error code != 404
    while (true) {
      try {
        getApiKeyFunction.apply(getKey());
      } catch (AlgoliaApiException ex) {
        if (ex.getHttpErrorCode() == 404) {
          try {
            Thread.sleep(1001);
          } catch (InterruptedException ignored) {
          }
          continue;
        }
      }
      break;
    }
  }
}

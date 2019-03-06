package com.algolia.search.models;

import com.algolia.search.objects.ApiKey;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.function.Function;

public class UpdateApiKeyResponse implements IAlgoliaWaitableResponse, Serializable {

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setGetApiKeyFunction(Function<String, ApiKey> getApiKeyConsumer) {
    this.getApiKeyFunction = getApiKeyConsumer;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  private Function<String, ApiKey> getApiKeyFunction;
  private String key;
  private OffsetDateTime updatedAt;
  private ApiKey pendingKey;

  @Override
  public void waitTask() {
    while (true) {
      String actualKey = getApiKeyFunction.apply(getKey()).toString();

      // When the key on the server equals the key we sent we break the loop
      if (Objects.equals(actualKey, pendingKey.toString())) {
        break;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }
  }
}

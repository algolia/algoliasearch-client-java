package com.algolia.search.models.apikeys;

import com.algolia.search.models.WaitableResponse;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
public class UpdateApiKeyResponse implements WaitableResponse, Serializable {

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

  public Function<String, ApiKey> getGetApiKeyFunction() {
    return getApiKeyFunction;
  }

  public ApiKey getPendingKey() {
    return pendingKey;
  }

  public void setPendingKey(ApiKey pendingKey) {
    this.pendingKey = pendingKey;
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

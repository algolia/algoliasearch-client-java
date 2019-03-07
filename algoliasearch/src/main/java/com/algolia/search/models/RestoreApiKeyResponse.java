package com.algolia.search.models;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.objects.ApiKey;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.function.Function;

public class RestoreApiKeyResponse implements IAlgoliaWaitableResponse, Serializable {

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setGetApiKeyFunction(Function<String, ApiKey> getApiKeyConsumer) {
    this.getApiKeyFunction = getApiKeyConsumer;
  }

  private Function<String, ApiKey> getApiKeyFunction;
  private String key;
  private OffsetDateTime createdAt;

  @Override
  public void waitTask() {
    while (true) {
      try {
        getApiKeyFunction.apply(getKey());
      } catch (AlgoliaApiException ex) {
        if (ex.getHttpErrorCode() == 404) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignored) {
          }
          continue;
        }
      }
      break;
    }
  }
}

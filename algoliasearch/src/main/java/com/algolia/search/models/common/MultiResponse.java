package com.algolia.search.models.common;

import com.algolia.search.models.IAlgoliaWaitableResponse;
import java.io.Serializable;
import java.util.List;

public class MultiResponse implements Serializable, IAlgoliaWaitableResponse {

  public List<IAlgoliaWaitableResponse> getResponses() {
    return responses;
  }

  public MultiResponse setResponses(List<IAlgoliaWaitableResponse> responses) {
    this.responses = responses;
    return this;
  }

  private List<IAlgoliaWaitableResponse> responses;

  @Override
  public void waitTask() {
    for (IAlgoliaWaitableResponse resp : responses) {
      resp.waitTask();
    }
  }
}

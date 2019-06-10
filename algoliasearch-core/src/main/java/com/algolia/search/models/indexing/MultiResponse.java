package com.algolia.search.models.indexing;

import com.algolia.search.models.WaitableResponse;
import java.io.Serializable;
import java.util.List;

public class MultiResponse implements Serializable, WaitableResponse {

  public List<WaitableResponse> getResponses() {
    return responses;
  }

  public MultiResponse setResponses(List<WaitableResponse> responses) {
    this.responses = responses;
    return this;
  }

  private List<WaitableResponse> responses;

  @Override
  public void waitTask() {
    for (WaitableResponse resp : responses) {
      resp.waitTask();
    }
  }
}

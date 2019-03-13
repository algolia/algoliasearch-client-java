package com.algolia.search.models;

import java.util.List;

public class MultiResponse implements IAlgoliaWaitableResponse {

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

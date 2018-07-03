package com.algolia.search.objects.tasks.async;

public class AsyncTaskABTest extends AsyncTask {

  public int abTestID;
  public String index;

  @Override
  public AsyncTaskABTest setIndex(String index) {
    this.index = index;
    super.setIndex(index);
    return this;
  }
}

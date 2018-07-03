package com.algolia.search.responses;

import com.algolia.search.inputs.analytics.ABTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ABTests implements Serializable {
  private ArrayList<ABTest> abtests;
  private int count;
  private int total;

  public ArrayList<ABTest> getAbtests() {
    return abtests;
  }

  public int getCount() {
    return count;
  }

  public int getTotal() {
    return total;
  }
}

package com.algolia.search.models.analytics;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ABTests implements Serializable {
  private ArrayList<ABTestResponse> abtests;
  private int count;
  private int total;

  public ArrayList<ABTestResponse> getAbtests() {
    return abtests;
  }

  public int getCount() {
    return count;
  }

  public int getTotal() {
    return total;
  }
}

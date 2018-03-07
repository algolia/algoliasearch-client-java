package com.algolia.search.responses;

import java.io.Serializable;

public class FacetStats implements Serializable {

  private Float min;
  private Float max;
  private Float avg;
  private Float sum;

  public Float getMin() {
    return min;
  }

  public FacetStats setMin(Float min) {
    this.min = min;
    return this;
  }

  public Float getMax() {
    return max;
  }

  public FacetStats setMax(Float max) {
    this.max = max;
    return this;
  }

  public Float getAvg() {
    return avg;
  }

  public FacetStats setAvg(Float avg) {
    this.avg = avg;
    return this;
  }

  public Float getSum() {
    return sum;
  }

  public FacetStats setSum(Float sum) {
    this.sum = sum;
    return this;
  }

  @Override
  public String toString() {
    return "FacetStats{" + "min=" + min + ", max=" + max + ", avg=" + avg + ", sum=" + sum + '}';
  }
}

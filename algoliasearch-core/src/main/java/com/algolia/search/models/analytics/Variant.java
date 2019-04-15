package com.algolia.search.models.analytics;

import com.algolia.search.models.indexing.Query;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.annotation.Nonnull;

/**
 * Variant
 *
 * <p>* @see <a
 * href="https://www.algolia.com/doc/api-reference/api-methods/add-ab-test">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Serializable {

  // Properties available at construction time
  private String index;
  private int trafficPercentage;
  private String description;

  // Properties set by the AB Testing API
  private Query customSearchParameters;

  public Variant() {}

  public Variant(@Nonnull String index, int trafficPercentage, String description) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = (description != null) ? description : "";
  }

  public Variant(
      String index, int trafficPercentage, String description, Query customSearchParameters) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = description;
    this.customSearchParameters = customSearchParameters;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public int getTrafficPercentage() {
    return trafficPercentage;
  }

  public void setTrafficPercentage(int trafficPercentage) {
    this.trafficPercentage = trafficPercentage;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty
  public Query getCustomSearchParameters() {
    return customSearchParameters;
  }

  @JsonProperty
  public void setCustomSearchParameters(Query customSearchParameters) {
    this.customSearchParameters = customSearchParameters;
  }
}

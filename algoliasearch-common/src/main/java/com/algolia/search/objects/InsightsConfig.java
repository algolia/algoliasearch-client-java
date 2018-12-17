package com.algolia.search.objects;

public class InsightsConfig {

  private String applicationId;
  private String apiKey;
  private String region;

  public String getApplicationId() {
    return applicationId;
  }

  public InsightsConfig setApplicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public InsightsConfig setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public String getRegion() {
    return region;
  }

  public InsightsConfig setRegion(String region) {
    this.region = region;
    return this;
  }
}

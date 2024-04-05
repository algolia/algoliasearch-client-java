// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Credentials for authenticating with the Algolia Insights API. */
@JsonDeserialize(as = AuthAlgoliaInsights.class)
public class AuthAlgoliaInsights implements AuthInput {

  @JsonProperty("appID")
  private String appID;

  @JsonProperty("apiKey")
  private String apiKey;

  public AuthAlgoliaInsights setAppID(String appID) {
    this.appID = appID;
    return this;
  }

  /** Algolia application ID. */
  @javax.annotation.Nonnull
  public String getAppID() {
    return appID;
  }

  public AuthAlgoliaInsights setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /** Algolia API key with the ACL: `search`. This field is `null` in the API response. */
  @javax.annotation.Nonnull
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthAlgoliaInsights authAlgoliaInsights = (AuthAlgoliaInsights) o;
    return Objects.equals(this.appID, authAlgoliaInsights.appID) && Objects.equals(this.apiKey, authAlgoliaInsights.apiKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appID, apiKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthAlgoliaInsights {\n");
    sb.append("    appID: ").append(toIndentedString(appID)).append("\n");
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

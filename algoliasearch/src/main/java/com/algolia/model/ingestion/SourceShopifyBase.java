// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** SourceShopifyBase */
public class SourceShopifyBase {

  @JsonProperty("shopURL")
  private String shopURL;

  public SourceShopifyBase setShopURL(String shopURL) {
    this.shopURL = shopURL;
    return this;
  }

  /** URL of the Shopify store. */
  @javax.annotation.Nonnull
  public String getShopURL() {
    return shopURL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SourceShopifyBase sourceShopifyBase = (SourceShopifyBase) o;
    return Objects.equals(this.shopURL, sourceShopifyBase.shopURL);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shopURL);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SourceShopifyBase {\n");
    sb.append("    shopURL: ").append(toIndentedString(shopURL)).append("\n");
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
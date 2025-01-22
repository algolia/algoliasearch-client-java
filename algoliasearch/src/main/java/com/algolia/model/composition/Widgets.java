// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.composition;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Widgets returned from any rules that are applied to the current search. */
public class Widgets {

  @JsonProperty("banners")
  private List<Banner> banners;

  public Widgets setBanners(List<Banner> banners) {
    this.banners = banners;
    return this;
  }

  public Widgets addBanners(Banner bannersItem) {
    if (this.banners == null) {
      this.banners = new ArrayList<>();
    }
    this.banners.add(bannersItem);
    return this;
  }

  /** Banners defined in the Merchandising Studio for a given search. */
  @javax.annotation.Nullable
  public List<Banner> getBanners() {
    return banners;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Widgets widgets = (Widgets) o;
    return Objects.equals(this.banners, widgets.banners);
  }

  @Override
  public int hashCode() {
    return Objects.hash(banners);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Widgets {\n");
    sb.append("    banners: ").append(toIndentedString(banners)).append("\n");
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

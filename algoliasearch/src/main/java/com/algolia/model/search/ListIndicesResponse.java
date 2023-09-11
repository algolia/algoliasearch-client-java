// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** ListIndicesResponse */
public class ListIndicesResponse {

  @JsonProperty("items")
  private List<FetchedIndex> items = new ArrayList<>();

  @JsonProperty("nbPages")
  private Integer nbPages;

  public ListIndicesResponse setItems(List<FetchedIndex> items) {
    this.items = items;
    return this;
  }

  public ListIndicesResponse addItems(FetchedIndex itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /** All indices in your Algolia application. */
  @javax.annotation.Nonnull
  public List<FetchedIndex> getItems() {
    return items;
  }

  public ListIndicesResponse setNbPages(Integer nbPages) {
    this.nbPages = nbPages;
    return this;
  }

  /** Number of pages. */
  @javax.annotation.Nullable
  public Integer getNbPages() {
    return nbPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListIndicesResponse listIndicesResponse = (ListIndicesResponse) o;
    return Objects.equals(this.items, listIndicesResponse.items) && Objects.equals(this.nbPages, listIndicesResponse.nbPages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, nbPages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListIndicesResponse {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    nbPages: ").append(toIndentedString(nbPages)).append("\n");
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

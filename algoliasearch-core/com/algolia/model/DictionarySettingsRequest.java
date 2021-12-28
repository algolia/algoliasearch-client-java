package com.algolia.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/** Disable the builtin Algolia entries for a type of dictionary per language. */
@ApiModel(
  description = "Disable the builtin Algolia entries for a type of dictionary per language."
)
public class DictionarySettingsRequest {

  public static final String SERIALIZED_NAME_DISABLE_STANDARD_ENTRIES =
    "disableStandardEntries";

  @SerializedName(SERIALIZED_NAME_DISABLE_STANDARD_ENTRIES)
  private StandardEntries disableStandardEntries;

  public DictionarySettingsRequest disableStandardEntries(
    StandardEntries disableStandardEntries
  ) {
    this.disableStandardEntries = disableStandardEntries;
    return this;
  }

  /**
   * Get disableStandardEntries
   *
   * @return disableStandardEntries
   */
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  public StandardEntries getDisableStandardEntries() {
    return disableStandardEntries;
  }

  public void setDisableStandardEntries(
    StandardEntries disableStandardEntries
  ) {
    this.disableStandardEntries = disableStandardEntries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DictionarySettingsRequest dictionarySettingsRequest = (DictionarySettingsRequest) o;
    return Objects.equals(
      this.disableStandardEntries,
      dictionarySettingsRequest.disableStandardEntries
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(disableStandardEntries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DictionarySettingsRequest {\n");
    sb
      .append("    disableStandardEntries: ")
      .append(toIndentedString(disableStandardEntries))
      .append("\n");
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

package com.algolia.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** The `batchDictionaryEntries` requests. */
@ApiModel(description = "The `batchDictionaryEntries` requests.")
public class BatchDictionaryEntries {

  public static final String SERIALIZED_NAME_CLEAR_EXISTING_DICTIONARY_ENTRIES =
    "clearExistingDictionaryEntries";

  @SerializedName(SERIALIZED_NAME_CLEAR_EXISTING_DICTIONARY_ENTRIES)
  private Boolean clearExistingDictionaryEntries = false;

  public static final String SERIALIZED_NAME_REQUESTS = "requests";

  @SerializedName(SERIALIZED_NAME_REQUESTS)
  private List<BatchDictionaryEntriesRequest> requests = new ArrayList<>();

  public BatchDictionaryEntries clearExistingDictionaryEntries(
    Boolean clearExistingDictionaryEntries
  ) {
    this.clearExistingDictionaryEntries = clearExistingDictionaryEntries;
    return this;
  }

  /**
   * When `true`, start the batch by removing all the custom entries from the dictionary.
   *
   * @return clearExistingDictionaryEntries
   */
  @javax.annotation.Nullable
  @ApiModelProperty(
    value = "When `true`, start the batch by removing all the custom entries from the dictionary."
  )
  public Boolean getClearExistingDictionaryEntries() {
    return clearExistingDictionaryEntries;
  }

  public void setClearExistingDictionaryEntries(
    Boolean clearExistingDictionaryEntries
  ) {
    this.clearExistingDictionaryEntries = clearExistingDictionaryEntries;
  }

  public BatchDictionaryEntries requests(
    List<BatchDictionaryEntriesRequest> requests
  ) {
    this.requests = requests;
    return this;
  }

  public BatchDictionaryEntries addRequestsItem(
    BatchDictionaryEntriesRequest requestsItem
  ) {
    this.requests.add(requestsItem);
    return this;
  }

  /**
   * List of operations to batch. Each operation is described by an `action` and a `body`.
   *
   * @return requests
   */
  @javax.annotation.Nonnull
  @ApiModelProperty(
    required = true,
    value = "List of operations to batch. Each operation is described by an `action` and a `body`."
  )
  public List<BatchDictionaryEntriesRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<BatchDictionaryEntriesRequest> requests) {
    this.requests = requests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchDictionaryEntries batchDictionaryEntries = (BatchDictionaryEntries) o;
    return (
      Objects.equals(
        this.clearExistingDictionaryEntries,
        batchDictionaryEntries.clearExistingDictionaryEntries
      ) &&
      Objects.equals(this.requests, batchDictionaryEntries.requests)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(clearExistingDictionaryEntries, requests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchDictionaryEntries {\n");
    sb
      .append("    clearExistingDictionaryEntries: ")
      .append(toIndentedString(clearExistingDictionaryEntries))
      .append("\n");
    sb.append("    requests: ").append(toIndentedString(requests)).append("\n");
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

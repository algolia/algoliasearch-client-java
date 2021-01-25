package com.algolia.search.models.dictionary;

import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryRequest {

  public static DictionaryRequest add(
      Boolean clearExistingDictionaryEntries, List<DictionaryEntry> requests) {
    return new DictionaryRequest(
        clearExistingDictionaryEntries,
        requests.stream()
            .map(entry -> new RequestBody(entry, "addEntry"))
            .collect(Collectors.toList()));
  }

  public static DictionaryRequest delete(
      Boolean clearExistingDictionaryEntries, List<DictionaryEntry> requests) {
    return new DictionaryRequest(
        clearExistingDictionaryEntries,
        requests.stream()
            .map(entry -> new RequestBody(entry, "deleteEntry"))
            .collect(Collectors.toList()));
  }

  private final Boolean clearExistingDictionaryEntries;

  private final List<RequestBody> requests;

  public DictionaryRequest(Boolean clearExistingDictionaryEntries, List<RequestBody> requests) {
    this.clearExistingDictionaryEntries = clearExistingDictionaryEntries;
    this.requests = requests;
  }

  public Boolean getClearExistingDictionaryEntries() {
    return clearExistingDictionaryEntries;
  }

  public List<RequestBody> getRequests() {
    return requests;
  }
}

class RequestBody implements Serializable {

  private final DictionaryEntry body;
  private final String action;

  public RequestBody(DictionaryEntry body, String action) {
    this.body = body;
    this.action = action;
  }

  public DictionaryEntry getBody() {
    return body;
  }

  public String getAction() {
    return action;
  }
}

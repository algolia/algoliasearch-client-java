package com.algolia.search.models.dictionary;

import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class DictionaryRequest {

  public static DictionaryRequest add(
      Boolean clearExistingDictionaryEntries, List<DictionaryEntry> requests) {
    return new DictionaryRequestAdd(clearExistingDictionaryEntries, requests);
  }

  public static DictionaryRequest delete(List<String> objectIDs) {
    return new DictionaryRequestDelete(false, objectIDs);
  }

  private final Boolean clearExistingDictionaryEntries;

  protected DictionaryRequest(Boolean clearExistingDictionaryEntries) {
    this.clearExistingDictionaryEntries = clearExistingDictionaryEntries;
  }

  public Boolean getClearExistingDictionaryEntries() {
    return clearExistingDictionaryEntries;
  }
}

class DictionaryRequestAdd extends DictionaryRequest {

  private final List<RequestBody<DictionaryEntry>> requests;

  DictionaryRequestAdd(Boolean clearExistingDictionaryEntries, List<DictionaryEntry> requests) {
    super(clearExistingDictionaryEntries);
    this.requests =
        requests.stream()
            .map(entry -> new RequestBody<>(entry, "addEntry"))
            .collect(Collectors.toList());
  }

  public List<RequestBody<DictionaryEntry>> getRequests() {
    return requests;
  }
}

class DictionaryRequestDelete extends DictionaryRequest {

  private final List<RequestBody<RequestBodyDelete>> requests;

  DictionaryRequestDelete(Boolean clearExistingDictionaryEntries, List<String> objectIDs) {
    super(clearExistingDictionaryEntries);
    this.requests =
        objectIDs.stream()
            .map(RequestBodyDelete::new)
            .map(entry -> new RequestBody<>(entry, "deleteEntry"))
            .collect(Collectors.toList());
  }

  public List<RequestBody<RequestBodyDelete>> getRequests() {
    return requests;
  }
}

class RequestBody<T> implements Serializable {

  private final T body;
  private final String action;

  RequestBody(T body, String action) {
    this.body = body;
    this.action = action;
  }

  public T getBody() {
    return body;
  }

  public String getAction() {
    return action;
  }
}

class RequestBodyDelete {

  private final String objectID;

  RequestBodyDelete(String objectID) {
    this.objectID = objectID;
  }

  public String getObjectID() {
    return objectID;
  }
}

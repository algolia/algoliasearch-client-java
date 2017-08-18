package com.algolia.search.http;

import com.algolia.search.Utils;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaHttpException;
import com.algolia.search.exceptions.AlgoliaHttpRetriesException;
import com.algolia.search.exceptions.AlgoliaIOException;
import com.algolia.search.responses.AlgoliaError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AlgoliaHttpClient {

  @VisibleForTesting
  Map<String, HostStatus> hostStatuses = new ConcurrentHashMap<>();

  protected Instant now() {
    return Instant.now();
  }

  protected abstract AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException;

  protected abstract ObjectMapper getObjectMapper();

  public abstract List<String> getQueryHosts();

  public abstract List<String> getBuildHosts();

  public abstract int getHostDownTimeout();

  private HostStatus emptyHostStatus() {
    return new HostStatus(getHostDownTimeout(), true, now());
  }

  private HostStatus downHostStatus() {
    return new HostStatus(getHostDownTimeout(), false, now());
  }

  private HostStatus upHostStatus() {
    return new HostStatus(getHostDownTimeout(), true, now());
  }

  private HostStatus getStatus(String host) {
    return hostStatuses.getOrDefault(host, emptyHostStatus());
  }

  private List<String> queryHostsThatAreUp() {
    return hostsThatAreUp(getQueryHosts());
  }

  private List<String> buildHostsThatAreUp() {
    return hostsThatAreUp(getBuildHosts());
  }

  private List<String> hostsThatAreUp(List<String> hosts) {
    return hosts
      .stream()
      .filter(s -> getStatus(s).isUpOrCouldBeRetried(now()))
      .collect(Collectors.toList());
  }

  private void markHostAsDown(String host) {
    hostStatuses.put(host, downHostStatus());
  }

  private void markHostAsUpdatedAndUp(String host) {
    hostStatuses.put(host, upHostStatus());
  }

  public <T> T requestWithRetry(@Nonnull AlgoliaRequest<T> request) throws AlgoliaException {
    List<String> hosts = request.isSearch() ? queryHostsThatAreUp() : buildHostsThatAreUp();

    String content = null;
    if (request.hasData()) {
      try {
        content = getObjectMapper().writeValueAsString(request.getData());
      } catch (IOException e) {
        throw new AlgoliaException("can not serialize request body", e);
      }
    }

    AlgoliaHttpResponse response = null;

    List<AlgoliaIOException> ioExceptionList = new ArrayList<>(4);
    for (String host : hosts) {
      try {
        response = request(new AlgoliaHttpRequest(host, content, request));
      } catch (IOException e) {
        markHostAsDown(host);
        ioExceptionList.add(new AlgoliaIOException(host, e));
        continue;
      }

      //No exception, mark the host as up & update time
      markHostAsUpdatedAndUp(host);
      int code = response.getStatusCode() / 100;
      if (code == 2 || code == 4) { //not a server error, so no retry
        break;
      }
    }

    if (response == null) { //if every retry failed
      throw new AlgoliaHttpRetriesException("All retries failed", ioExceptionList);
    }

    try {
      int code = response.getStatusCode();
      if (code / 100 == 4) {
        String message = Utils.parseAs(getObjectMapper(), response.getBody(), AlgoliaError.class).getMessage();

        switch (code) {
          case 400:
             throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Bad build request");
          case 403:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Invalid Application-ID or API-Key");
          case 404:
            return null; //Special case, means we didn't find the object or the index is not existing
          default:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Error");
        }
      }

      return Utils.parseAs(getObjectMapper(), response.getBody(), request.getJavaType(getObjectMapper().getTypeFactory()));
    } catch (IOException e) {
      throw new AlgoliaException("Error while deserialization the response", e);
    }
  }

}

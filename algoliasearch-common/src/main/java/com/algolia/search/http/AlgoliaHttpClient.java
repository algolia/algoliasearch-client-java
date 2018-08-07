package com.algolia.search.http;

import com.algolia.search.Utils;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaHttpException;
import com.algolia.search.exceptions.AlgoliaHttpRetriesException;
import com.algolia.search.exceptions.AlgoliaIOException;
import com.algolia.search.responses.AlgoliaError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AlgoliaHttpClient {

  protected final Logger logger = LoggerFactory.getLogger("algoliasearch");

  @VisibleForTesting Map<String, HostStatus> hostStatuses = new ConcurrentHashMap<>();

  protected Instant now() {
    return Instant.now();
  }

  protected abstract AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request)
      throws IOException;

  protected abstract ObjectMapper getObjectMapper();

  public abstract String getAnalyticsHost();

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

  protected List<String> queryHostsThatAreUp() {
    return hostsThatAreUp(getQueryHosts());
  }

  protected List<String> buildHostsThatAreUp() {
    return hostsThatAreUp(getBuildHosts());
  }

  private List<String> hostsThatAreUp(List<String> hosts) {
    List<String> list =
        hosts
            .stream()
            .filter(s -> getStatus(s).isUpOrCouldBeRetried(now()))
            .collect(Collectors.toList());

    if (list.isEmpty()) {
      return hosts;
    } else {
      return list;
    }
  }

  private void markHostAsDown(String host) {
    logger.debug("Marking {} as `down`", host);
    hostStatuses.put(host, downHostStatus());
  }

  private void markHostAsUpdatedAndUp(String host) {
    logger.debug("Marking {} as `up`", host);
    hostStatuses.put(host, upHostStatus());
  }

  public <T> T requestWithRetry(@Nonnull AlgoliaRequest<T> request) throws AlgoliaException {
    List<String> hosts = request.isSearch() ? queryHostsThatAreUp() : buildHostsThatAreUp();
    String content = serializeRequest(request);
    AlgoliaHttpResponse response = null;

    List<AlgoliaIOException> ioExceptionList = new ArrayList<>(4);
    for (String host : hosts) {
      logRequest(host, request, content);

      try {
        response = request(new AlgoliaHttpRequest(host, content, request));
      } catch (IOException e) {
        logger.debug("Failing to query {}", host, e);
        markHostAsDown(host);
        ioExceptionList.add(new AlgoliaIOException(host, e));
        continue;
      }

      // No exception, mark the host as up & update time
      markHostAsUpdatedAndUp(host);
      int code = response.getStatusCode() / 100;
      if (code == 2 || code == 4) { // not a server error, so no retry
        logger.debug("Got HTTP code {}, no retry", response.getStatusCode());
        break;
      }
    }

    if (response == null) { // if every retry failed
      logger.debug("All retries failed");
      throw new AlgoliaHttpRetriesException("All retries failed", ioExceptionList);
    }

    return buildResponse(request, response);
  }

  public <T> T requestAnalytics(@Nonnull AlgoliaRequest<T> request) throws AlgoliaException {
    String host = getAnalyticsHost();
    String content = serializeRequest(request);
    logRequest(host, request, content);
    AlgoliaHttpResponse response = null;
    AlgoliaIOException requestException = null;

    try {
      response = request(new AlgoliaHttpRequest(host, content, request));
    } catch (IOException e) {
      logger.debug("Failing to query {}", host, e);
      requestException = new AlgoliaIOException(host, e);
    }

    if (response == null) {
      throw requestException;
    }

    return buildResponse(request, response);
  }

  private <T> String serializeRequest(@Nonnull AlgoliaRequest<T> request) throws AlgoliaException {
    String content = null;
    if (request.hasData()) {
      try {
        content = getObjectMapper().writeValueAsString(request.getData());
      } catch (IOException e) {
        throw new AlgoliaException("can not serialize request body", e);
      }
    }
    return content;
  }

  private <T> T buildResponse(
      @Nonnull AlgoliaRequest<T> request, @Nonnull AlgoliaHttpResponse response)
      throws AlgoliaException {

    try {
      Reader body = response.getBody();
      int code = response.getStatusCode();

      body = logResponse(code, body);

      if (code / 100 == 4) {
        String message = Utils.parseAs(getObjectMapper(), body, AlgoliaError.class).getMessage();

        logger.debug("Got HTTP code {}", code);

        switch (code) {
          case 400:
            throw new AlgoliaHttpException(
                code, message.length() > 0 ? message : "Bad build request");
          case 403:
            throw new AlgoliaHttpException(
                code, message.length() > 0 ? message : "Invalid Application-ID or API-Key");
          case 404:
            return null; // Special case, means we didn't find the object or the index is not
            // existing
          default:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Error");
        }
      }

      return Utils.parseAs(
          getObjectMapper(), body, request.getJavaType(getObjectMapper().getTypeFactory()));
    } catch (IOException e) {
      logger.debug("Error while deserialization", e);
      throw new AlgoliaException("Error while deserialization the response", e);
    } finally {
      try {
        response.close();
      } catch (IOException e) {
        logger.debug("Can not close underlying response", e);
        throw new AlgoliaException("Can not close underlying response", e);
      }
    }
  }

  private <T> void logRequest(
      @Nonnull String host, @Nonnull AlgoliaRequest<T> request, String content) {
    if (logger.isDebugEnabled()) {
      if (content == null) {
        content = "";
      }
      logger.debug("HTTP request {} with {}", request.toString(host), content);
    }
  }

  private Reader logResponse(int code, @Nonnull Reader body) throws IOException {
    if (!logger.isDebugEnabled()) {
      return body;
    }

    String bodyAsString = CharStreams.toString(body);
    logger.debug("HTTP response {}", bodyAsString);
    return new StringReader(bodyAsString);
  }

  public abstract void close() throws AlgoliaException;
}

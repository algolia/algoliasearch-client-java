package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaHttpException;
import com.algolia.search.exceptions.AlgoliaHttpRetriesException;
import com.algolia.search.responses.AlgoliaError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Internal HttpClient
 */
class AlgoliaHttpClient {

  final HttpRequestFactory requestFactory;
  final ObjectMapper objectMapper;
  final List<String> queryHosts;
  final List<String> buildHosts;
  final JacksonParser jacksonParser;

  AlgoliaHttpClient(HttpRequestFactory requestFactory, ObjectMapper objectMapper, List<String> queryHosts, List<String> buildHosts) {
    this.requestFactory = requestFactory;
    this.objectMapper = objectMapper;
    this.queryHosts = queryHosts;
    this.buildHosts = buildHosts;
    this.jacksonParser = new JacksonParser(objectMapper);
  }

  HttpRequest buildRequest(HttpMethod method, String host, List<String> path, Map<String, String> parameters, HttpContent content) throws IOException {
    GenericUrl url = new GenericUrl();
    url.setScheme("https");
    url.setHost(host);

    List<String> computedPath = new ArrayList<>();
    computedPath.add(""); //Add the first `/` see java doc of setPathParts
    computedPath.addAll(path);
    url.setPathParts(computedPath);

    if (parameters != null && !parameters.isEmpty()) {
      for (Map.Entry<String, String> entry : parameters.entrySet()) {
        url.set(entry.getKey(), entry.getValue());
      }
    }

    return requestFactory
      .buildRequest(method.name, url, content)
      .setParser(jacksonParser)
      .setLoggingEnabled(true)
      .setCurlLoggingEnabled(true) //nice logging it necessary
      .setThrowExceptionOnExecuteError(false) //4XX won't throw exceptions
      .setNumberOfRetries(0); //let us handle retries
  }

  <T> T requestWithRetry(HttpMethod method, boolean isSearch, List<String> path, Type type) throws AlgoliaException {
    return requestWithRetry(method, isSearch, path, null, null, type);
  }

  <T> T requestWithRetry(HttpMethod method, boolean isSearch, List<String> path, Map<String, String> parameters, Type type) throws AlgoliaException {
    return requestWithRetry(method, isSearch, path, parameters, null, type);
  }

  <T> T requestWithRetry(HttpMethod method, boolean isSearch, List<String> path, Object data, Type type) throws AlgoliaException {
    return requestWithRetry(method, isSearch, path, null, data, type);
  }

  @SuppressWarnings("unchecked")
  <T> T requestWithRetry(HttpMethod method, boolean isSearch, List<String> path, Map<String, String> parameters, Object data, Type type) throws AlgoliaException {
    List<String> hosts = isSearch ? queryHosts : buildHosts;

    HttpContent content = null;
    if (data != null) {
      content = new JacksonHttpContent(objectMapper, data);
    }

    HttpResponse httpResponse = null;
    HttpRequest request;

    List<IOException> ioExceptionList = new ArrayList<>(4);
    for (String host : hosts) {
      try {
        request = buildRequest(method, host, path, parameters, content);
        httpResponse = request.execute();
      } catch (IOException e) {
        ioExceptionList.add(e);
        continue;
      }

      int code = httpResponse.getStatusCode() / 100;
      if (code == 2 || code == 4) { //not a server error, so no retry
        break;
      }
    }

    if (httpResponse == null) { //if every retry failed
      throw new AlgoliaHttpRetriesException("All retries failed", ioExceptionList);
    }

    try {
      int code = httpResponse.getStatusCode();
      if (code / 100 == 4) {
        String message = httpResponse.parseAs(AlgoliaError.class).getMessage();

        switch (code) {
          case 400:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Bad buildRequest");
          case 403:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Invalid Application-ID or API-Key");
          case 404:
            return null; //Special case, means we didn't find the object
          default:
            throw new AlgoliaHttpException(code, message.length() > 0 ? message : "Error");
        }
      }

      return (T) httpResponse.parseAs(type);
    } catch (IOException e) {
      throw new AlgoliaException("Error while deserialization the response", e);
    }
  }

}

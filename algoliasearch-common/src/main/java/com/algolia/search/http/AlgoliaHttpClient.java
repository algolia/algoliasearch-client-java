package com.algolia.search.http;

import com.algolia.search.Utils;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.exceptions.AlgoliaHttpException;
import com.algolia.search.exceptions.AlgoliaHttpRetriesException;
import com.algolia.search.exceptions.AlgoliaIOException;
import com.algolia.search.responses.AlgoliaError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class AlgoliaHttpClient {

  protected abstract AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException;

  protected abstract ObjectMapper getObjectMapper();

  public abstract List<String> getQueryHosts();

  public abstract List<String> getBuildHosts();

  public <T> T requestWithRetry(@Nonnull AlgoliaRequest<T> request) throws AlgoliaException {
    List<String> hosts = request.isSearch() ? getQueryHosts() : getBuildHosts();

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
        ioExceptionList.add(new AlgoliaIOException(host, e));
        continue;
      }

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

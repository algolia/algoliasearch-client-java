package com.algolia.search.models;

import com.algolia.search.models.common.CompressionType;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HttpRequest {

  public HttpRequest(
      HttpMethod method,
      String methodPath,
      Map<String, String> headers,
      int timeout,
      CompressionType compressionType) {
    this(method, methodPath, headers, timeout);
    this.compressionType = compressionType;
  }

  public HttpRequest(
      HttpMethod method, String methodPath, Map<String, String> headers, int timeout) {
    this.method = method;
    this.methodPath = methodPath;
    this.headers = headers;
    this.timeout = timeout;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public HttpRequest setMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  public URL getUri() {
    return uri;
  }

  public HttpRequest setUri(URL uri) {
    this.uri = uri;
    return this;
  }

  public String getMethodPath() {
    return methodPath;
  }

  public HttpRequest setMethodPath(String methodPath) {
    this.methodPath = methodPath;
    return this;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public HttpRequest setHeaders(HashMap<String, String> headers) {
    this.headers = headers;
    return this;
  }

  /** @deprecated produced input stream is not reusable, use {@link #getBodySupplier()} instead. */
  @Deprecated
  public InputStream getBody() {
    return body;
  }

  /** @deprecated use {@link #setBodySupplier(Supplier)} instead. */
  @Deprecated
  public HttpRequest setBody(InputStream body) {
    this.body = body;
    return this;
  }

  public int getTimeout() {
    return timeout;
  }

  public HttpRequest setTimeout(int timeout) {
    this.timeout = timeout;
    return this;
  }

  public CompressionType getCompressionType() {
    return compressionType;
  }

  public HttpRequest setCompressionType(CompressionType compressionType) {
    this.compressionType = compressionType;
    return this;
  }

  /**
   * Tells if any compression can be enabled for a request or not. Compression is enabled only for
   * POST/PUT methods on the Search API (not on Analytics and Insights).
   */
  public boolean canCompress() {
    if (this.compressionType == null || this.method == null) {
      return false;
    }

    boolean isMethodValid =
        this.method.equals(HttpMethod.POST) || this.method.equals(HttpMethod.PUT);
    boolean isCompressionEnabled = this.compressionType.equals(CompressionType.GZIP);

    return isMethodValid && isCompressionEnabled;
  }

  public void incrementTimeout(int retryCount) {
    this.timeout *= (retryCount + 1);
  }

  public Supplier<InputStream> getBodySupplier() {
    return bodySupplier;
  }

  public HttpRequest setBodySupplier(Supplier<InputStream> bodySupplier) {
    this.bodySupplier = bodySupplier;
    return this;
  }

  private HttpMethod method;
  private URL uri;
  private String methodPath;
  private Map<String, String> headers;
  private InputStream body; // deprecated since, not reusable
  private int timeout;
  private CompressionType compressionType;
  private Supplier<InputStream> bodySupplier;
}

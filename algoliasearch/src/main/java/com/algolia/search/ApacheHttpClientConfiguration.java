package com.algolia.search;

import java.util.Collection;
import org.apache.http.HttpHost;

public class ApacheHttpClientConfiguration {

  private HttpHost proxy;
  private Collection<String> proxyPreferredAuthSchemes;

  public ApacheHttpClientConfiguration setProxy(HttpHost proxy) {
    this.proxy = proxy;
    return this;
  }

  public ApacheHttpClientConfiguration setProxyPreferredAuthSchemes(
      Collection<String> proxyPreferredAuthSchemes) {
    this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
    return this;
  }

  public HttpHost getProxy() {
    return proxy;
  }

  public Collection<String> getProxyPreferredAuthSchemes() {
    return proxyPreferredAuthSchemes;
  }
}

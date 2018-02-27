package com.algolia.search;

import java.util.Collection;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;

public class ApacheHttpClientConfiguration {

  private HttpHost proxy;
  private Collection<String> proxyPreferredAuthSchemes;
  private CredentialsProvider defaultCredentialsProvider;

  public ApacheHttpClientConfiguration setProxy(HttpHost proxy) {
    this.proxy = proxy;
    return this;
  }

  public ApacheHttpClientConfiguration setProxyPreferredAuthSchemes(
      Collection<String> proxyPreferredAuthSchemes) {
    this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
    return this;
  }

  public ApacheHttpClientConfiguration setDefaultCredentialsProvider(
      CredentialsProvider defaultCredentialsProvider) {
    this.defaultCredentialsProvider = defaultCredentialsProvider;
    return this;
  }

  public HttpHost getProxy() {
    return proxy;
  }

  public Collection<String> getProxyPreferredAuthSchemes() {
    return proxyPreferredAuthSchemes;
  }

  public CredentialsProvider getDefaultCredentialsProvider() {
    return defaultCredentialsProvider;
  }
}

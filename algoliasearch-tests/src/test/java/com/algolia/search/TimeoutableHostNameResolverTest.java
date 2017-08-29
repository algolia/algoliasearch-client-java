package com.algolia.search;

import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeoutableHostNameResolverTest {

  private TimeoutableHostNameResolver resolver = new TimeoutableHostNameResolver(2000);

  @Test
  public void resolveNoTimeout() throws Exception {
    long start = System.currentTimeMillis();
    resolver.resolve("www.algolia.com");
    long end = System.currentTimeMillis();

    assertThat(end - start).isLessThanOrEqualTo(2000);
  }

  @Test(expected = UnknownHostException.class)
  public void resolveTimeout() throws IOException {
    resolver.resolve("java-8-dsn.algolia.biz");
  }

}
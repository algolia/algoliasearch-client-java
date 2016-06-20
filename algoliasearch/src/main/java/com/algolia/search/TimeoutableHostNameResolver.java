package com.algolia.search;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import org.apache.http.conn.scheme.HostNameResolver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

class TimeoutableHostNameResolver implements HostNameResolver {

  /**
   * Timeout in ms
   */
  private final long timeout;

  private final TimeLimiter timeLimiter;

  TimeoutableHostNameResolver(long timeout) {
    this.timeout = timeout;
    this.timeLimiter = new SimpleTimeLimiter();
  }

  @Override
  public InetAddress resolve(String hostname) throws IOException {
    try {
      return timeLimiter.callWithTimeout(
        () -> InetAddress.getByName(hostname),
        timeout,
        TimeUnit.MILLISECONDS,
        true
      );
    } catch (Exception e) {
      throw new UnknownHostException(hostname);
    }
  }
}

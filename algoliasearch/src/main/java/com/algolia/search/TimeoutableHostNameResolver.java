package com.algolia.search;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.DnsResolver;

class TimeoutableHostNameResolver implements DnsResolver {

  /** Timeout in ms */
  private final long timeout;

  private final TimeLimiter timeLimiter;

  TimeoutableHostNameResolver(long timeout) {
    this.timeout = timeout;
    this.timeLimiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());
  }

  @Override
  public InetAddress[] resolve(String hostname) throws UnknownHostException {
    try {
      return timeLimiter.callUninterruptiblyWithTimeout(
          () -> new InetAddress[] {InetAddress.getByName(hostname)},
          timeout,
          TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      throw new UnknownHostException(hostname);
    }
  }
}

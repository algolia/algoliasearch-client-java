package com.algolia.search.saas;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class NetworkTest {

  private APIClient client;

  @Before
  public void init() {
    String applicationID = System.getenv("ALGOLIA_APPLICATION_ID");
    String apiKey = System.getenv("ALGOLIA_API_KEY");
    Assume.assumeFalse("You must set environement variables ALGOLIA_APPLICATION_ID and ALGOLIA_API_KEY to run the tests.", applicationID == null || apiKey == null);

    List<String> hosts = new ArrayList<String>();
    hosts.add("java-dsn.algolia.biz");
    hosts.add(applicationID + "-1.algolianet.com");

    client = new APIClient(applicationID, apiKey, hosts, hosts);
  }

  @Test
  public void shouldHandleTimeoutsInDns() throws AlgoliaException {
    Long start = System.currentTimeMillis();
    client.listIndexes();
    assertTrue((System.currentTimeMillis() - start) < 3 * 1000);
  }


}

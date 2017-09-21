package com.algolia.search.integration.common.async;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.objects.LogType;
import org.junit.Test;

public abstract class AsyncLogsTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void getLogs() throws Exception {
    futureAssertThat(client.getLogs()).isNotEmpty();
  }

  @Test
  public void getLogsWithOffset() throws Exception {
    futureAssertThat(client.getLogs(0, 1, LogType.LOG_ALL)).isNotEmpty();
  }
}

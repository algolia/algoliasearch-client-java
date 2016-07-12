package com.algolia.search.integration.async;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.LogType;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class AsyncLogsTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void getLogs() throws Exception {
    futureAssertThat(client.getLogs()).isNotEmpty();
  }

  @Test
  public void getLogsWithOffset() throws Exception {
    futureAssertThat(client.getLogs(0, 1, LogType.LOG_ALL)).isNotEmpty();
  }

}

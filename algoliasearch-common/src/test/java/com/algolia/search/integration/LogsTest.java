package com.algolia.search.integration;

import com.algolia.search.AlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.LogType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class LogsTest extends AlgoliaIntegrationTest {

  @Test
  public void getLogs() throws AlgoliaException {
    assertThat(client.getLogs()).isNotEmpty();
  }

  @Test
  public void getLogsWithOffset() throws AlgoliaException {
    assertThat(client.getLogs(0, 1, LogType.LOG_ALL)).isNotEmpty();
  }

}

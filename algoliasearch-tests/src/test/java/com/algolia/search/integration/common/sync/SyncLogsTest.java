package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.LogType;
import org.junit.Test;

public abstract class SyncLogsTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void getLogs() throws AlgoliaException {
    assertThat(client.getLogs()).isNotEmpty();
  }

  @Test
  public void getLogsWithOffset() throws AlgoliaException {
    assertThat(client.getLogs(0, 1, LogType.LOG_ALL)).isNotEmpty();
  }
}

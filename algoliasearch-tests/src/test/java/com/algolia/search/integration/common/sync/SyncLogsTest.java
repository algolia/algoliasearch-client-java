package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.LogType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public abstract class SyncLogsTest extends SyncAlgoliaIntegrationTest {

  @Before
  public void resetDebugLevel() {
    ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("algoliasearch")).setLevel(Level.INFO);
  }

  @Test
  public void getLogs() throws AlgoliaException {
    assertThat(client.getLogs()).isNotEmpty();
  }

  @Test
  public void getLogsWithOffset() throws AlgoliaException {
    assertThat(client.getLogs(0, 1, LogType.LOG_ALL)).isNotEmpty();
  }

  @Test
  public void getLogsWithDebugShouldNotFail() throws AlgoliaException {
    ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("algoliasearch"))
        .setLevel(Level.DEBUG);
    assertThat(client.getLogs()).isNotEmpty();
  }
}

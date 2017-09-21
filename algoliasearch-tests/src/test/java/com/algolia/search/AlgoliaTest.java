package com.algolia.search;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaRequest;
import com.algolia.search.inputs.batch.BatchClearIndexOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.tasks.sync.TaskSingleIndex;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class AlgoliaTest {

  protected APIClient client;
  protected AlgoliaHttpClient httpClient;

  @Before
  public void before() {
    httpClient = mock(AlgoliaHttpClient.class);
    client = new APIClient(httpClient, null);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void batchesMustBeOnTheSameIndex() throws AlgoliaException {
    when(httpClient.requestWithRetry(any(AlgoliaRequest.class))).thenReturn(new TaskSingleIndex());

    Index index = client.initIndex("index");
    index.batch(Arrays.asList(new BatchClearIndexOperation(), new BatchDeleteIndexOperation()));
  }

  @Test(expected = AlgoliaException.class)
  public void batchesShouldBeOnTheSameIndex() throws AlgoliaException {
    Index index = client.initIndex("index");
    index.batch(
        Arrays.asList(new BatchClearIndexOperation("index1"), new BatchDeleteIndexOperation()));
  }
}

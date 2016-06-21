package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperations;
import com.algolia.search.inputs.batch.BatchClearIndexOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.TaskSingleIndex;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlgoliaTest {

  protected APIClient client;
  protected AlgoliaHttpClient httpClient;

  @Before
  public void before() {
    httpClient = mock(AlgoliaHttpClient.class);
    client = new APIClient(httpClient);
  }

  @Test
  public void batchesMustBeOnTheSameIndex() throws AlgoliaException {
    when(httpClient.requestWithRetry(
      eq(HttpMethod.POST),
      eq(false),
      anyListOf(String.class),
      any(BatchOperations.class),
      any(Class.class)
    )).thenReturn(new TaskSingleIndex());

    Index index = client.initIndex("index");
    index.batch(Arrays.asList(
      new BatchClearIndexOperation(),
      new BatchDeleteIndexOperation()
    ));
  }

  @Test(expected = AlgoliaException.class)
  public void batchesShouldBeOnTheSameIndex() throws AlgoliaException {
    Index index = client.initIndex("index");
    index.batch(Arrays.asList(
      new BatchClearIndexOperation("index1"),
      new BatchDeleteIndexOperation()
    ));
  }

}

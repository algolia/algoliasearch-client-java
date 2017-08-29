package com.algolia.search.integration.common.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class AsyncDeleteByTest extends AsyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1"
  );

  @Before
  @After
  public void cleanUp() throws Exception {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    waitForCompletion(client.batch(clean));
  }

  @Test
  public void deleteBy() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.rangeClosed(1, 10).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    waitForCompletion(index.deleteBy(new Query().setTagFilters(Collections.singletonList("a"))));

    SearchResult<AlgoliaObject> search = index.search(new Query("")).get();

    assertThat(search.getHits()).hasSize(10);
  }

}

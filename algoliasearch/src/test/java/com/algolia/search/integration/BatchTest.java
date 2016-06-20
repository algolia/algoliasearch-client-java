package com.algolia.search.integration;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchAddObjectOperation;
import com.algolia.search.inputs.batch.BatchClearIndexOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.batch.BatchUpdateObjectOperation;
import com.algolia.search.objects.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2",
    "index3",
    "index4"
  );

  @BeforeClass
  @AfterClass
  public static void after() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void batchOnOneIndex() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index = client.initIndex("index1", AlgoliaObjectWithID.class);
    index.addObject(new AlgoliaObjectWithID("1", "name", 10)).waitForCompletion();

    List<BatchOperation> operations = Arrays.asList(
      new BatchAddObjectOperation<>(new AlgoliaObjectWithID("2", "name2", 11)),
      new BatchUpdateObjectOperation<>(new AlgoliaObjectWithID("1", "name1", 10))
    );

    index.batch(operations).waitForCompletion();
  }

  @Test
  public void batchOnMultipleIndices() throws AlgoliaException {
    Index<AlgoliaObjectWithID> index2 = client.initIndex("index2", AlgoliaObjectWithID.class);
    Index<AlgoliaObjectWithID> index3 = client.initIndex("index3", AlgoliaObjectWithID.class);
    Index<AlgoliaObjectWithID> index4 = client.initIndex("index4", AlgoliaObjectWithID.class);

    client.batch(Arrays.asList(
      new BatchAddObjectOperation<>(index2, new AlgoliaObjectWithID("1", "name", 2)),
      new BatchAddObjectOperation<>(index3, new AlgoliaObjectWithID("1", "name", 2)),
      new BatchAddObjectOperation<>(index4, new AlgoliaObjectWithID("1", "name", 2))
    )).waitForCompletion();

    client.batch(Arrays.asList(
      new BatchClearIndexOperation(index2),
      new BatchDeleteIndexOperation(index3),
      new BatchUpdateObjectOperation<>(index4, new AlgoliaObjectWithID("1", "name2", 2))
    )).waitForCompletion();

    assertThat(index2.search(new Query("")).getNbHits()).isEqualTo(0);
    assertThat(client.listIndices()).extracting("name").doesNotContain("index3");
    assertThat(index4.getObject("1").get()).isEqualToComparingFieldByField(new AlgoliaObjectWithID("1", "name2", 2));
  }

}

package com.algolia.search.integration;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.IndexIterable;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BrowseTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2"
  );

  @BeforeClass
  @AfterClass
  public static void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void browse() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.range(1, 10).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));

    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }
}

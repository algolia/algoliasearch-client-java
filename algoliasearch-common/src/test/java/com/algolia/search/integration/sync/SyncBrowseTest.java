package com.algolia.search.integration.sync;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.IndexIterable;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class SyncBrowseTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1",
    "index2",
    "index3",
    "index4",
    "index5",
    "index6"
  );

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void browse() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.rangeClosed(1, 10).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));

    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseWithQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index4", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.rangeClosed(1, 5).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    objects.addAll(IntStream.rangeClosed(1, 5).mapToObj(i -> new AlgoliaObject("other" + i, i)).collect(Collectors.toList()));
    index.addObjects(objects).waitForCompletion();

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("name").setHitsPerPage(5));

    assertThat(iterator).hasSize(5);
    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseNonExistingIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index5", AlgoliaObject.class);

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));

    assertThat(iterator).isEmpty();
  }

  @Test
  public void browseEmptyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index6", AlgoliaObject.class);

    //Add object then clear => index is empty
    index.addObject(new AlgoliaObject("name", 1)).waitForCompletion();
    index.clear().waitForCompletion();

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));
    assertThat(iterator).isEmpty();
  }

  @Test
  public void deleteByQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.rangeClosed(1, 10).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    index.deleteByQuery(new Query(""));

    assertThat(index.search(new Query("")).getHits()).isEmpty();
  }

  @Test
  public void deleteByQueryBatchSize2() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index3", AlgoliaObject.class);

    List<AlgoliaObject> objects = IntStream.rangeClosed(1, 10).mapToObj(i -> new AlgoliaObject("name" + i, i)).collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    index.deleteByQuery(new Query(""), 2);

    assertThat(index.search(new Query("")).getHits()).isEmpty();
  }
}

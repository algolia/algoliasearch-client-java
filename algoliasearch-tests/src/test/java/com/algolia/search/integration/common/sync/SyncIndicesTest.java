package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.List;
import org.junit.Test;

public abstract class SyncIndicesTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void getAllIndices() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    List<Index.Attributes> listIndices = client.listIndexes();
    assertThat(listIndices).extracting("name").contains(index.getName());
    assertThat(listIndices).extracting("numberOfPendingTasks").isNotNull();
  }

  @Test
  public void deleteIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    waitForCompletion(index.delete());
    assertThat(client.listIndexes()).extracting("name").doesNotContain(index.getName());
  }

  @Test
  public void moveIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexMoveTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.moveTo(indexMoveTo.getName()));
    assertThat(client.listIndexes())
        .extracting("name")
        .doesNotContain(index.getName())
        .contains(indexMoveTo.getName());
  }

  @Test
  public void copyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexCopyTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.copyTo(indexCopyTo.getName()));
    assertThat(client.listIndexes())
        .extracting("name")
        .contains(index.getName(), indexCopyTo.getName());
  }

  @Test
  public void clearIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    waitForCompletion(index.clear());

    SearchResult<AlgoliaObject> results = index.search(new Query(""));

    assertThat(results.getHits()).isEmpty();
  }
}

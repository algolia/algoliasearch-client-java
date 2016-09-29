package com.algolia.search.integration.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.IndexSettings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract public class AsyncSettingsTest extends AsyncAlgoliaIntegrationTest {

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
  public void manageSettings() throws Exception {
    AsyncIndex<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));

    IndexSettings settings = index.getSettings().get();
    assertThat(settings.getSearchableAttributes()).isNull();

    settings.setAttributesForFaceting(Collections.singletonList("name"));

    waitForCompletion(index.setSettings(settings));

    settings = index.getSettings().get();
    assertThat(settings.getAttributesForFaceting()).containsOnly("name");
  }

}

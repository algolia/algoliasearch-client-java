package com.algolia.search.integration;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.IndexSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingsTest extends AlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList(
    "index1"
  );

  @BeforeClass
  @AfterClass
  public static void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean = indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void manageSettings() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    index.addObject(new AlgoliaObject("name", 1)).waitForCompletion();

    IndexSettings settings = index.getSettings();
    assertThat(settings.getAttributesToIndex()).isNull();

    settings.setAttributesForFaceting(Collections.singletonList("name"));

    index.setSettings(settings).waitForCompletion();

    settings = index.getSettings();
    assertThat(settings.getAttributesForFaceting()).containsOnly("name");
  }

}

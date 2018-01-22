package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.IndexSettings;
import java.util.Collections;
import org.junit.Test;

public abstract class SyncSettingsTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void manageSettings() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));

    IndexSettings settings = index.getSettings();
    assertThat(settings.getSearchableAttributes()).isNull();

    settings = settings.setAttributesForFaceting(Collections.singletonList("name"));

    waitForCompletion(index.setSettings(settings));

    settings = index.getSettings();
    assertThat(settings.getAttributesForFaceting()).containsOnly("name");
  }
}

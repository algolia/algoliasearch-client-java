package com.algolia.search.integration.common.async;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.objects.IndexSettings;
import java.util.Collections;
import org.junit.Test;

public abstract class AsyncSettingsTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void manageSettings() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));

    IndexSettings settings = index.getSettings().get();
    futureObjectAssertThat(index.getSettings()).extracting("searchableAttributes").isNotNull();

    settings.setAttributesForFaceting(Collections.singletonList("name"));

    waitForCompletion(index.setSettings(settings));

    futureObjectAssertThat(index.getSettings())
        .extracting("attributesForFaceting")
        .containsOnly(Collections.singletonList("name"));
  }
}

package com.algolia.example.osgi;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.IndicesResponse;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

public class DemoBundle implements BundleActivator {

    public static String userName = System.getProperty("user.name");
    private static String osName = System.getProperty("os.name").trim();
    private static String javaVersion = System.getProperty("java.version");

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        String appID = System.getenv("ALGOLIA_APPLICATION_ID_1");
        String apiKey = System.getenv("ALGOLIA_ADMIN_KEY_1");
        SearchClient client = DefaultSearchClient.create(appID, apiKey);
        List<IndicesResponse> indices = client.listIndices();
        System.out.printf("Algolia Demo Bundle is starting (listIndices() listed %d indices)%n", indices.size());

        IndicesResponse firstIndex = indices.get(0);
        SearchIndex<AlgoliaIndexingObject> index = client.initIndex(firstIndex.getName(), AlgoliaIndexingObject.class);
        SearchResult<AlgoliaIndexingObject> search = index.search(new Query());
        System.out.printf("[%s] Hit founds: %s%n", firstIndex.getName(), search.getNbHits());
    }

    public static String getTestIndexName(String indexName) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return String.format("java_jvm_%s_%s_%s_%s_%s", javaVersion, utc, osName, userName, indexName);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Algolia Demo Bundle is stopping.");
    }
}

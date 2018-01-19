package other.not.algolia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import com.algolia.search.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.Test;

/**
 * This test is outside the com.algolia package because that's how we can reproduce it See
 * https://github.com/algolia/algoliasearch-client-java-2/issues/451
 */
public class ClojureCompatibilityTest {

  @Test
  public void reflectionVisibilityForIndex() {
    APIClient apiClient = new ApacheAPIClientBuilder("appID", "apiKey").build();
    Index<String> index = apiClient.initIndex("indexName", String.class);

    assertThat(Index.class.getMethods())
        .filteredOn(m -> Modifier.isPublic(m.getModifiers()))
        .allMatch(
            m -> {
              try {
                Object[] parameters = new Object[m.getParameterCount()];
                m.invoke(index, parameters);
              } catch (InvocationTargetException | IllegalArgumentException ignored) {
              } catch (IllegalAccessException e) {
                fail("IllegalAccessException, can not call method from reflexion `%s`, %s", m, e);
              }
              return true;
            });
  }

  @Test
  public void reflectionVisibilityForAsyncIndex() {
    AsyncAPIClient apiClient = new AsyncHttpAPIClientBuilder("appID", "apiKey").build();
    AsyncIndex<?> index = apiClient.initIndex("indexName", String.class);

    assertThat(AsyncIndex.class.getMethods())
        .filteredOn(m -> Modifier.isPublic(m.getModifiers()))
        .allMatch(
            m -> {
              try {
                Object[] parameters = new Object[m.getParameterCount()];
                m.invoke(index, parameters);
              } catch (InvocationTargetException | IllegalArgumentException ignored) {
              } catch (IllegalAccessException e) {
                fail("IllegalAccessException, can not call method from reflexion `%s`, %s", m, e);
              }
              return true;
            });
  }
}

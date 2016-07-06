package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.objects.Query;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class APIClientTest {

  private APIClient client;

  @Before
  public void before() {
    client = new APIClient(mock(AlgoliaHttpClient.class), mock(APIClientConfiguration.class));
  }

  @Test
  public void secureKeys() throws AlgoliaException {
    assertThat(
      client.generateSecuredApiKey("182634d8894831d5dbce3b3185c50881", new Query().setTagFilters("(public,user1)"))
    ).isEqualTo("MDZkNWNjNDY4M2MzMDA0NmUyNmNkZjY5OTMzYjVlNmVlMTk1NTEwMGNmNTVjZmJhMmIwOTIzYjdjMTk2NTFiMnRhZ0ZpbHRlcnM9JTI4cHVibGljJTJDdXNlcjElMjk=");

    assertThat(
      client.generateSecuredApiKey("182634d8894831d5dbce3b3185c50881", new Query().setTagFilters("(public,user1)").setUserToken("42"))
    ).isEqualTo("OGYwN2NlNTdlOGM2ZmM4MjA5NGM0ZmYwNTk3MDBkNzMzZjQ0MDI3MWZjNTNjM2Y3YTAzMWM4NTBkMzRiNTM5YnRhZ0ZpbHRlcnM9JTI4cHVibGljJTJDdXNlcjElMjkmdXNlclRva2VuPTQy");
  }

}
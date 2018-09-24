package com.algolia.search.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

public class FacetFiltersTest {

  private ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

  private FacetFilters serializeDeserialize(FacetFilters facetFilters) throws IOException {
    String serialized = objectMapper.writeValueAsString(facetFilters);
    return objectMapper.readValue(
        serialized, objectMapper.getTypeFactory().constructType(FacetFilters.class));
  }

  @Test
  public void facetFiltersAsListOfString() throws IOException {
    FacetFilters facetFilters = new FacetFiltersAsListOfString(Arrays.asList("filter1", "filter2"));
    FacetFilters result = serializeDeserialize(facetFilters);
    assertThat(result).isEqualToComparingFieldByField(facetFilters);
  }

  @Test
  public void facetFiltersAsListOfList() throws IOException {
    FacetFilters facetFilters =
        new FacetFiltersAsListOfList(
            Arrays.asList(Arrays.asList("filter1", "filter2"), Arrays.asList("filter3")));
    FacetFilters result = serializeDeserialize(facetFilters);
    assertThat(result).isEqualToComparingFieldByField(facetFilters);
  }
}

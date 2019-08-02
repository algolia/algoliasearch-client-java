package com.algolia.search.models.indexing;

import com.algolia.search.util.QueryStringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/** Customer serializer to serialize an Algolia search query as URL parameters */
public class QuerySerializer extends StdSerializer<Query> {

  public QuerySerializer() {
    this(null);
  }

  private QuerySerializer(Class<Query> t) {
    super(t);
  }

  @Override
  public void serialize(Query value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(QueryStringUtils.buildQueryAsQueryParams(value));
  }
}

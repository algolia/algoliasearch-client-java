package com.algolia.search.serializer;

import com.algolia.search.Defaults;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.models.search.Query;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class QuerySerializer extends StdSerializer<Query> {

  public QuerySerializer() {
    this(null);
  }

  public QuerySerializer(Class<Query> t) {
    super(t);
  }

  @Override
  public void serialize(Query value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    Map<String, String> map =
        Defaults.getObjectMapper().convertValue(value, new TypeReference<Map<String, String>>() {});
    gen.writeString(QueryStringHelper.buildQueryString(map, true));
  }
}

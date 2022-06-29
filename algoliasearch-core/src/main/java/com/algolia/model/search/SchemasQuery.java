package com.algolia.model.search;

import com.algolia.utils.CompoundType;
import com.algolia.utils.JSON;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

@JsonAdapter(SchemasQuery.Adapter.class)
/**
 * When providing a string, it replaces the entire query string. When providing an object, it
 * describes incremental edits to be made to the query string (but you can't do both).
 */
public abstract class SchemasQuery implements CompoundType {

  public static SchemasQuery of(ConsequenceQuery inside) {
    return new SchemasQueryConsequenceQuery(inside);
  }

  public static SchemasQuery of(String inside) {
    return new SchemasQueryString(inside);
  }

  public static class Adapter extends TypeAdapter<SchemasQuery> {

    @Override
    public void write(final JsonWriter out, final SchemasQuery oneOf) throws IOException {
      TypeAdapter runtimeTypeAdapter = (TypeAdapter) JSON.getGson().getAdapter(TypeToken.get(oneOf.getInsideValue().getClass()));
      runtimeTypeAdapter.write(out, oneOf.getInsideValue());
    }

    @Override
    public SchemasQuery read(final JsonReader jsonReader) throws IOException {
      ConsequenceQuery consequencequery = JSON.tryDeserialize(jsonReader, new TypeToken<ConsequenceQuery>() {}.getType());
      if (consequencequery != null) {
        return SchemasQuery.of(consequencequery);
      }
      String string = JSON.tryDeserialize(jsonReader, new TypeToken<String>() {}.getType());
      if (string != null) {
        return SchemasQuery.of(string);
      }
      return null;
    }
  }
}

@JsonAdapter(SchemasQuery.Adapter.class)
class SchemasQueryConsequenceQuery extends SchemasQuery {

  private final ConsequenceQuery insideValue;

  SchemasQueryConsequenceQuery(ConsequenceQuery insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public ConsequenceQuery getInsideValue() {
    return insideValue;
  }
}

@JsonAdapter(SchemasQuery.Adapter.class)
class SchemasQueryString extends SchemasQuery {

  private final String insideValue;

  SchemasQueryString(String insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public String getInsideValue() {
    return insideValue;
  }
}

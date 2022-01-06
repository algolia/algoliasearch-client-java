package com.algolia.search.models.rules;

import com.algolia.search.Defaults;
import com.algolia.search.util.AlgoliaUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ConsequenceQueryDeserializer.class)
@JsonSerialize(using = ConsequenceQuerySerializer.class)
public class ConsequenceQuery implements Serializable {

  public ConsequenceQuery() {}

  public List<Edit> getEdits() {
    return edits;
  }

  public ConsequenceQuery setEdits(List<Edit> edits) {
    this.edits = edits;
    return this;
  }

  String getQueryString() {
    return queryString;
  }

  ConsequenceQuery setQueryString(String queryString) {
    this.queryString = queryString;
    return this;
  }

  private List<Edit> edits;
  private String queryString;
}

/**
 * Custom serializer to handle polymorphism/legacy of "query" attributes in ConsequenceQuery
 *
 * <p>Example:
 *
 * <pre>{@code
 *  // query string
 *  "query": "some query string"
 *
 *  // remove attribute (deprecated)
 *  "query": {"remove": ["query1", "query2"]}
 *
 *  // edits attribute
 *  "query": {
 *     "edits": [
 *        { "type": "remove", "delete": "old" },
 *        { "type": "replace", "delete": "new", "insert": "newer" }
 *     ]
 * }
 * }</pre>
 */
class ConsequenceQuerySerializer extends JsonSerializer<ConsequenceQuery> {
  @Override
  public void serialize(ConsequenceQuery value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    /*
     * Consequence query edits will override regular "query" - both can't be set at the same time
     * https://www.algolia.com/doc/api-reference/api-methods/save-rule/#method-param-query
     * */
    if (value.getQueryString() != null) {
      gen.writeString(value.getQueryString());
    } else {
      gen.writeStartObject();
      gen.writeObjectField("edits", value.getEdits());
      gen.writeEndObject();
    }
  }
}

/**
 * Custom deserializer to handle polymorphism/legacy of "query" attributes in ConsequenceQuery
 *
 * <p>Example:
 *
 * <pre>{@code
 *  // query string
 *  "query": "some query string"
 *
 *  // remove attribute (deprecated)
 *  "query": {"remove": ["query1", "query2"]}
 *
 *  // edits attribute
 *  "query": {
 *     "edits": [
 *        { "type": "remove", "delete":s "old" },
 *        { "type": "replace", "delete": "new", "insert": "newer" }
 *     ]
 * }
 * }</pre>
 *
 * <b>NOTE</b>: this deserializer is "silently" converting deprecated "remove" to "edits"
 * consequence query
 */
class ConsequenceQueryDeserializer extends JsonDeserializer<ConsequenceQuery> {
  @Override
  public ConsequenceQuery deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.getObjectMapper();

    // edits and remove attribute
    if (node.isObject()) {

      ConsequenceQuery ret = new ConsequenceQuery();
      List<Edit> edits = new ArrayList<>(Collections.emptyList());

      if (node.findValue("remove") != null) {
        // remove attributes deprecated
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
        List<String> list = reader.readValue(node.get("remove"));
        List<Edit> remove =
            list.stream().map(r -> new Edit(EditType.REMOVE, r, null)).collect(Collectors.toList());
        edits.addAll(remove);
      }

      if (node.findValue("edits") != null) {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Edit>>() {});
        List<Edit> list = reader.readValue(node.get("edits"));
        edits.addAll(list);
      }

      ret.setEdits(edits);
      return ret;
    } else {
      // query string
      return new ConsequenceQuery().setQueryString(node.asText());
    }
  }
}

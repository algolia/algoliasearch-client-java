package com.algolia.search.inputs.synonym;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = AltCorrection1.class, name = SynonymType.ALT_CORRECTION_1),
  @JsonSubTypes.Type(value = AltCorrection2.class, name = SynonymType.ALT_CORRECTION_2),
  @JsonSubTypes.Type(value = OneWaySynonym.class, name = SynonymType.ONE_WAY_SYNONYM),
  @JsonSubTypes.Type(value = Placeholder.class, name = SynonymType.PLACEHOLDER),
  @JsonSubTypes.Type(value = Synonym.class, name = SynonymType.SYNONYM),
})
public interface AbstractSynonym extends Serializable {

  String getObjectID();

  String getType();
}

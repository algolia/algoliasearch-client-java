package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.util.AlgoliaUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnsureObjectIDTest {

  @Test
  @DisplayName("Test AlgoliaUtils.ensureObjectID without ObjectID")
  void testClassWithoutObjectID() {
    assertThatThrownBy(() -> AlgoliaUtils.ensureObjectID(DummyObjectWithoutObjectId.class))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");

    assertThatThrownBy(() -> AlgoliaUtils.ensureObjectID(DummyChildWithoutObjectID.class))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
  }

  @Test
  @DisplayName("Test AlgoliaUtils.ensureObjectID without Jackson annotation")
  void testClassWithWrongAnnotation() {
    assertThatThrownBy(() -> AlgoliaUtils.ensureObjectID(DummyObjectWithWrongAnnotation.class))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");

    assertThatThrownBy(() -> AlgoliaUtils.ensureObjectID(DummyChildWithWrongAnnotation.class))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
  }

  @Test
  @DisplayName("Test AlgoliaUtils.ensureObjectID with ObjectID")
  void testClassWithObjectID() {
    assertThatCode(() -> AlgoliaUtils.ensureObjectID(DummyObjectWithObjectID.class))
        .doesNotThrowAnyException();

    assertThatCode(() -> AlgoliaUtils.ensureObjectID(DummyChildWithObjectID.class))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Test AlgoliaUtils.ensureObjectID with Jackson Annotation")
  void testClassWithAnnotation() {
    assertThatCode(() -> AlgoliaUtils.ensureObjectID(DummyObjectWithAnnotation.class))
        .doesNotThrowAnyException();

    assertThatCode(() -> AlgoliaUtils.ensureObjectID(DummyChildWithAnnotation.class))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Test AlgoliaUtils.getObjectID without ObjectID")
  void testGetObjectIDWithoutObjectID() {
    assertThatThrownBy(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyObjectWithoutObjectId()))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");

    assertThatThrownBy(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyChildWithoutObjectID()))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
  }

  @Test
  @DisplayName("Test AlgoliaUtils.getObjectID with wrong jackson annotation")
  void testGetObjectIDWithWrongAnnotation() {
    assertThatThrownBy(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyObjectWithWrongAnnotation()))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");

    assertThatThrownBy(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyChildWithWrongAnnotation()))
        .isInstanceOf(AlgoliaRuntimeException.class)
        .hasMessageContaining(
            "must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
  }

  @Test
  @DisplayName("Test AlgoliaUtils.getObjectID with ObjectID")
  void testGetObjectIDWithObjectID() {
    assertThatCode(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyObjectWithObjectID().setObjectID("foo")
                ))
        .doesNotThrowAnyException();

    assertThatCode(
            () ->
                AlgoliaUtils.getObjectID(
                    (DummyChildWithObjectID) new DummyChildWithObjectID().setObjectID("foo")
                ))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Test AlgoliaUtils.getObjectID with Jackson annotation")
  void testGetObjectIDWithAnnotation() {
    assertThatCode(
            () ->
                AlgoliaUtils.getObjectID(
                    new DummyObjectWithAnnotation().setId("foo")))
        .doesNotThrowAnyException();

    assertThatCode(
            () ->
                AlgoliaUtils.getObjectID(
                    (DummyChildWithAnnotation) new DummyChildWithAnnotation().setId("foo")
                ))
        .doesNotThrowAnyException();
  }
}

class DummyObjectWithoutObjectId {
  public int getId() {
    return id;
  }

  public DummyObjectWithoutObjectId setId(int id) {
    this.id = id;
    return this;
  }

  private int id;
}

class DummyObjectWithWrongAnnotation {
  public String getId() {
    return id;
  }

  public DummyObjectWithWrongAnnotation setId(String id) {
    this.id = id;
    return this;
  }

  @JsonProperty("ObjectId")
  private String id;
}

class DummyObjectWithObjectID {
  public String getObjectID() {
    return objectID;
  }

  public DummyObjectWithObjectID setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  private String objectID;
}

class DummyObjectWithAnnotation {
  public String getId() {
    return id;
  }

  public DummyObjectWithAnnotation setId(String id) {
    this.id = id;
    return this;
  }

  @JsonProperty("objectID")
  private String id;
}

class DummyChildWithAnnotation extends DummyObjectWithAnnotation {
  public String getBar() {
    return bar;
  }

  public DummyChildWithAnnotation setBar(String bar) {
    this.bar = bar;
    return this;
  }

  private String bar;
}

class DummyChildWithObjectID extends DummyObjectWithObjectID {
  public String getBar() {
    return bar;
  }

  public DummyChildWithObjectID setBar(String bar) {
    this.bar = bar;
    return this;
  }

  private String bar;
}

class DummyChildWithoutObjectID extends DummyObjectWithoutObjectId {
  public String getBar() {
    return bar;
  }

  public DummyChildWithoutObjectID setBar(String bar) {
    this.bar = bar;
    return this;
  }

  private String bar;
}

class DummyChildWithWrongAnnotation extends DummyObjectWithWrongAnnotation {
  public String getBar() {
    return bar;
  }

  public DummyChildWithWrongAnnotation setBar(String bar) {
    this.bar = bar;
    return this;
  }

  private String bar;
}

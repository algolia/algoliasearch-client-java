package com.algolia.search.util;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

public class AlgoliaUtils {

  /** Checks if the given string is empty or white spaces */
  public static Boolean isEmptyWhiteSpace(final String stringToCheck) {
    return stringToCheck.trim().length() == 0;
  }

  /** Checks if the given string is null, empty or white spaces */
  public static Boolean isNullOrEmptyWhiteSpace(final String stringToCheck) {
    return stringToCheck == null || stringToCheck.trim().length() == 0;
  }

  /**
   * Ensure that the objectID field or the @JsonProperty(\"objectID\")" is present in the given
   * class
   *
   * @param clazz The class to scan
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   */
  public static <T> void ensureObjectID(@Nonnull Class<T> clazz) {
    // Try to find the objectID field
    Field objectIDField = getField(clazz, "objectID");

    // If objectID field doesn't exist, let's check for Jackson annotations in all the fields
    Optional<Field> optObjectIDField = findObjectIDInAnnotation(clazz);

    if (objectIDField == null && !optObjectIDField.isPresent()) {
      throw new AlgoliaRuntimeException(
          "The "
              + clazz
              + " must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
    }
  }

  /**
   * Get the objectID of the given class at runtime
   *
   * @param clazz The class to scan
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
   *     annotation @JsonProperty(\"objectID\"")
   */
  public static <T> String getObjectID(@Nonnull T data, @Nonnull Class<T> clazz) {

    String objectID = null;

    // Try to find the objectID field
    try {
      Field objectIDField = getField(clazz, "objectID");
      if (objectIDField != null) {
        objectID = (String) objectIDField.get(data);
      }
    } catch (
        IllegalAccessException
            ignored) { // Ignored because if it fails we want to move forward on annotations
    }

    if (objectID != null) {
      return objectID;
    }

    // If objectID field doesn't exist, let's check for Jackson annotations in all the fields
    Optional<Field> optObjectIDField = findObjectIDInAnnotation(clazz);

    if (optObjectIDField.isPresent()) {
      Field objectIDField = optObjectIDField.get();
      try {
        objectIDField.setAccessible(true);

        objectID = (String) objectIDField.get(data);

        if (objectID != null) {
          return objectID;
        }

      } catch (IllegalAccessException ignored) {
        throw new AlgoliaRuntimeException("Can't access the ObjectID field.");
      }
    }

    // If non of the both above the method fails
    throw new AlgoliaRuntimeException(
        "The "
            + clazz
            + " must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
  }

  private static Optional<Field> findObjectIDInAnnotation(@Nonnull Class<?> clazz) {
    List<Field> fields = getFields(clazz);
    return fields.stream()
        .filter(
            f ->
                f.getAnnotation(JsonProperty.class) != null
                    && f.getAnnotation(JsonProperty.class).value().equals("objectID"))
        .findFirst();
  }

  /**
   * Recursively search for the given field in the given class
   *
   * @param clazz The class to reflect on
   * @param fieldName The field to reach
   */
  private static Field getField(@Nonnull Class<?> clazz, @Nonnull String fieldName) {
    Class<?> tmpClass = clazz;
    do {
      try {
        Field f = tmpClass.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f;
      } catch (NoSuchFieldException e) {
        tmpClass = tmpClass.getSuperclass();
      }
    } while (tmpClass != null);

    return null;
  }

  /**
   * Recursively search for all fields in the given class
   *
   * @param clazz The class to reflect on
   */
  private static List<Field> getFields(@Nonnull Class<?> clazz) {
    List<Field> result = new ArrayList<>();
    Class<?> i = clazz;

    while (i != null && i != Object.class) {
      Collections.addAll(result, i.getDeclaredFields());
      i = i.getSuperclass();
    }

    return result;
  }
}

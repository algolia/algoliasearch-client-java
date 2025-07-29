package com.algolia.search.util;

import com.algolia.search.Defaults;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.*;
import javax.annotation.Nonnull;

public class AlgoliaUtils {

    public static final String PROPERTY_OBJECT_ID = "objectID";

    /**
     * Checks if the given string is empty or white spaces
     */
    public static Boolean isEmptyWhiteSpace(final String stringToCheck) {
        return stringToCheck.trim().isEmpty();
    }

    /**
     * Checks if the given string is null, empty or white spaces
     */
    public static Boolean isNullOrEmptyWhiteSpace(final String stringToCheck) {
        return stringToCheck == null || stringToCheck.trim().isEmpty();
    }

    private static final ZoneRules ZONE_RULES_UTC = ZoneOffset.UTC.getRules();

    /**
     * Memory optimization for getZoneRules with the same ZoneOffset (UTC). ZoneRules is immutable and
     * thread-safe, but getRules method consumes a lot of memory during load testing.
     */
    public static OffsetDateTime nowUTC() {
        final Instant now = Clock.system(ZoneOffset.UTC).instant();
        return OffsetDateTime.ofInstant(now, ZONE_RULES_UTC.getOffset(now));
    }

    /**
     * Ensure that the objectID field or the @JsonProperty(\"objectID\")" is present in the given
     * class
     *
     * @param clazz The class to scan
     * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
     *                                 annotation @JsonProperty(\"objectID\"")
     */
    public static <T> void ensureObjectID(@Nonnull Class<T> clazz) {
        BeanDescription introspection = introspectClass(clazz);
        if (!containsObjectID(introspection)) throw objectIDNotFoundException(clazz);
    }

    private static <T> AlgoliaRuntimeException objectIDNotFoundException(Class<T> clazz) {
        return new AlgoliaRuntimeException(
                "The " + clazz + " must have an objectID property or a Jackson annotation @JsonProperty(\"objectID\")");
    }

    /**
     * Checks if the {@value PROPERTY_OBJECT_ID} is present in the classes public fields, getter methods or
     * annotations using Jackson's {@link BeanDescription}
     */
    protected static boolean containsObjectID(BeanDescription introspection) {
        return introspection.findProperties().stream()
                .filter(d -> d.getPrimaryType().isTypeOrSubTypeOf(String.class))
                .anyMatch(d -> PROPERTY_OBJECT_ID.equals(d.getName()));
    }

    /**
     * Introspection of the class using Jackson
     */
    protected static <T> BeanDescription introspectClass(Class<T> clazz) {
        ObjectMapper mapper = getMapper();
        JavaType type = mapper.getTypeFactory().constructType(clazz);
        return mapper.getSerializationConfig().introspect(type);
    }

    private static ObjectMapper getMapper() {
        return Defaults.getObjectMapper();
    }

    /**
     * Get the objectID of the given class at runtime
     *
     * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a Jackson
     *                                 annotation @JsonProperty(\"objectID\"")
     */
    public static <T> String getObjectID(@Nonnull T data) {
        return Optional.ofNullable(getMapper().valueToTree(data)
                        .get(PROPERTY_OBJECT_ID))
                .filter(JsonNode::isTextual)
                .map(JsonNode::asText)
                .orElseThrow(() -> objectIDNotFoundException(data.getClass()));
    }

}

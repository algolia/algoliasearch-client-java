package com.algolia.search.util;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class AlgoliaUtilsTest {

    protected interface SetObjectId {
        void set(String objectId);
    }

    /**
     * Additional fields to ensure Jackson calls won't fail because of missing type converters
     */
    protected abstract static class BaseClass implements SetObjectId {

        public LocalDate localDate = LocalDate.now();
        public Calendar calendar = Calendar.getInstance();
        public BigDecimal bigDecimal = new BigDecimal(10);
        public LocalDateTime localDateTime = LocalDateTime.now();
    }

    protected static class SomeClassWithPublicField extends BaseClass {

        public String objectID;

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }
    }

    protected static class SomeClassWithGetter extends BaseClass {

        private String objectID;

        public String getObjectID() {
            return objectID;
        }

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }
    }


    protected static class SomeClassWithAnnotation extends BaseClass {

        @JsonProperty(AlgoliaUtils.PROPERTY_OBJECT_ID)
        private String objectID;

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }

    }

    protected static class SomeClassWithGetterAndAnnotation extends BaseClass {

        private String objectID;

        @JsonProperty(AlgoliaUtils.PROPERTY_OBJECT_ID)
        public String getWithCustomName() {
            return objectID;
        }

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }
    }

    /**
     * To test if {@link ObjectMapper} fails because of missing type converters
     */
    protected static class SomeClassWithInvalidObjectIDType extends BaseClass {
        public SomeNonTextualObject someNonTextualObject = new SomeNonTextualObject();

        protected static class SomeNonTextualObject {
        }

        @Override
        public void set(String objectId) {
        }
    }

    @ParameterizedTest
    @ValueSource(classes = {
            SomeClassWithPublicField.class,
            SomeClassWithGetter.class,
            SomeClassWithAnnotation.class,
            SomeClassWithGetterAndAnnotation.class})
    void containsObjectID(Class<?> clazz) {
        BeanDescription introspection = AlgoliaUtils.introspectClass(clazz);
        assertTrue(AlgoliaUtils.containsObjectID(introspection));
    }

    @ParameterizedTest
    @ValueSource(classes = {
            SomeClassWithPublicField.class,
            SomeClassWithGetter.class,
            SomeClassWithAnnotation.class,
            SomeClassWithGetterAndAnnotation.class})
    void getObjectId(Class<? extends SetObjectId> clazz) throws Exception {
        SetObjectId instance = clazz.getDeclaredConstructor().newInstance();
        instance.set("12345");
        assertEquals("12345", AlgoliaUtils.getObjectID(instance));
    }

    @Test
    void containsObjectID_WithInvalidType_ThrowsError() {
        BeanDescription introspection = AlgoliaUtils.introspectClass(SomeClassWithInvalidObjectIDType.class);
        assertFalse(AlgoliaUtils.containsObjectID(introspection));
    }

    @Test
    void getObjectID_WithInvalidType_ThrowsError() {
        assertThrows(AlgoliaRuntimeException.class, () -> AlgoliaUtils.getObjectID(new SomeClassWithInvalidObjectIDType()));
    }
}

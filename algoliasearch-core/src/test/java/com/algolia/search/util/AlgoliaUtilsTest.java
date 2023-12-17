package com.algolia.search.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BeanDescription;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Marian at 17.12.2023
 */
class AlgoliaUtilsTest {

    protected interface SetObjectId {
        void set(String objectId);
    }

    protected static class SomeClassWithPublicField implements SetObjectId {

        public String objectID;

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }
    }

    protected static class SomeClassWithGetter implements SetObjectId {

        private String objectID;

        public String getObjectID() {
            return objectID;
        }

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }
    }


    protected static class SomeClassWithAnnotation implements SetObjectId {

        @JsonProperty(AlgoliaUtils.PROPERTY_OBJECT_ID)
        private String objectID;

        @Override
        public void set(String objectId) {
            this.objectID = objectId;
        }

    }

    protected static class SomeClassWithGetterAndAnnotation implements SetObjectId {

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
    void extractObjectID(Class<? extends SetObjectId> clazz) throws Exception {
        SetObjectId instance = clazz.getDeclaredConstructor().newInstance();
        instance.set("12345");
        assertEquals("12345", AlgoliaUtils.getObjectID(instance));
    }
}

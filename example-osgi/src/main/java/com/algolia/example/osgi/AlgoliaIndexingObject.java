package com.algolia.example.osgi;


import com.algolia.search.com.fasterxml.jackson.annotation.JsonInclude;
import com.algolia.search.com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlgoliaIndexingObject extends AlgoliaWithId {

    public String getProperty() {
        return property;
    }

    public AlgoliaIndexingObject setProperty(String property) {
        this.property = property;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public AlgoliaIndexingObject setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    private String property = "default";

    @JsonProperty("_tags")
    private List<String> tags;

    public AlgoliaIndexingObject() {
    }

    public AlgoliaIndexingObject(String objectID) {
        setObjectID(objectID);
    }

    public AlgoliaIndexingObject(String objectID, String property) {
        setObjectID(objectID);
        this.property = property;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("WeakerAccess")
class AlgoliaWithId implements Serializable {

    public AlgoliaWithId() {
    }

    public String getObjectID() {
        return id;
    }

    public void setObjectID(String objectID) {
        this.id = objectID;
    }

    @JsonProperty("objectID")
    private String id;
}

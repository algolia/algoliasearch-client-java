package com.algolia.search.saas.synonyms;

import org.json.JSONObject;

public abstract class AbstractSynonym {
    public enum Type {
        SYNONYM("synonym"),
        PLACEHOLDER("placeholder"),
        ONEWAY("onewaysynonym"),
        ALTCORRECTION_1("altCorrection1"),
        ALTCORRECTION_2("altCorrection2");

        String name;

        Type(String typeName) {
            name = typeName;
        }

        public String getName() {
            return name;
        }
    }

    protected String objectID;
    protected Type type;

    public AbstractSynonym(String objectID, Type type) {
        this.objectID = objectID;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract JSONObject toJsonObject();
}

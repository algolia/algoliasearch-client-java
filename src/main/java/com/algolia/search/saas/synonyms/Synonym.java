package com.algolia.search.saas.synonyms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Synonym extends AbstractSynonym {
    List<String> synonyms;

    public Synonym(String objectID, List<String> synonyms) {
        super(objectID, Type.SYNONYM);
        this.synonyms = synonyms;
    }

    public Synonym(String objectID, String... synonyms) {
        this(objectID, Arrays.asList(synonyms));
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("objectID", objectID)
                    .put("type", type.getName());
            for (String s : synonyms) {
                jsonObject.append("synonyms", s);
            }
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
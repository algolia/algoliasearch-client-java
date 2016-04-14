package com.algolia.search.saas.synonyms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class OneWaySynonym extends AbstractSynonym {
    String input;
    List<String> synonyms;

    public OneWaySynonym(String objectID, String input, List<String> synonyms) {
        super(objectID, Type.ONEWAY);
        this.input = input;
        this.synonyms = synonyms;
    }

    public OneWaySynonym(String objectID, String input, String... synonyms) {
        this(objectID, input, Arrays.asList(synonyms));
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("objectID", objectID)
                    .put("type", type.getName())
                    .put("input", input);
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

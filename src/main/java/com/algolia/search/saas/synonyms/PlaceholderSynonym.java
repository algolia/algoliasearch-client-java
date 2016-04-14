package com.algolia.search.saas.synonyms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class PlaceholderSynonym extends AbstractSynonym {
    String placeholder;
    List<String> replacements;

    public PlaceholderSynonym(String objectID, String placeholder, List<String> replacements) {
        super(objectID, Type.PLACEHOLDER);
        this.placeholder = placeholder;
        this.replacements = replacements;
    }

    public PlaceholderSynonym(String objectID, String placeholder, String... replacements) {
        this(objectID, placeholder, Arrays.asList(replacements));
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("objectID", objectID)
                    .put("type", type.getName())
                    .put("placeholder", placeholder);
            for (String s : replacements) {
                jsonObject.append("replacements", s);
            }
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

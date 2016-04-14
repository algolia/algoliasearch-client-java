package com.algolia.search.saas.synonyms;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractAltCorrectionSynonym extends AbstractSynonym {
    private String word;
    private List<String> corrections;

    public AbstractAltCorrectionSynonym(Type type, String objectID, String word, List<String> corrections) {
        super(objectID, type);
        this.word = word;
        this.corrections = corrections;
    }

    public AbstractAltCorrectionSynonym(Type type, String objectID, String word, String... corrections) {
        this(type, objectID, word, Arrays.asList(corrections));
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("objectID", objectID)
                    .put("type", type.getName())
                    .put("word", word);
            for (String s : corrections) {
                jsonObject.append("corrections", s);
            }
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

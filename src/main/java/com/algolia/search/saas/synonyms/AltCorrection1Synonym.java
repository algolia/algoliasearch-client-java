package com.algolia.search.saas.synonyms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class AltCorrection1Synonym extends AbstractAltCorrectionSynonym {
    String word;
    List<String> corrections;

    public AltCorrection1Synonym(String objectID, String word, List<String> corrections) {
        super(Type.ALTCORRECTION_1, objectID, word, corrections);
    }

    public AltCorrection1Synonym(String objectID, String word, String... corrections) {
        this(objectID, word, Arrays.asList(corrections));
    }
}

package com.algolia.search.saas;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SynonymTest extends AlgoliaTest {

	@Test
	public void saveAndGetSynonym() throws JSONException, AlgoliaException {
    List<String> a = new ArrayList<String>();
    a.add("San Francisco");
    a.add("SF");

		JSONObject res = index.saveSynonym("synonym1", new JSONObject().put("type", "synonym").put("synonyms", a));
		index.waitTask(res.getString("taskID"));

		res = index.getSynonym("synonym1");
		assertEquals("synonym", res.getString("type"));
	}

	@Test
	public void deleteSynonym() throws JSONException, AlgoliaException {
    List<String> a = new ArrayList<String>();
    a.add("San Francisco");
    a.add("SF");

		JSONObject res = index.saveSynonym("synonym1", new JSONObject().put("type", "synonym").put("synonyms", a));
		index.waitTask(res.getString("taskID"));

		res = index.deleteSynonym("synonym1");
		index.waitTask(res.getString("taskID"));

		res = index.searchSynonyms(new SynonymQuery(""));
		assertEquals(0, res.getInt("nbHits"));
	}

	@Test
	public void clearSynonym() throws JSONException, AlgoliaException {
    List<String> a = new ArrayList<String>();
    a.add("San Francisco");
    a.add("SF");

		JSONObject res = index.saveSynonym("synonym1", new JSONObject().put("type", "synonym").put("synonyms", a));
		index.waitTask(res.getString("taskID"));

		res = index.clearSynonyms();
		index.waitTask(res.getString("taskID"));

		res = index.searchSynonyms(new SynonymQuery(""));
		assertEquals(0, res.getInt("nbHits"));
	}

	@Test
	public void batchSaveSynonyms() throws JSONException, AlgoliaException {
    List<String> a = new ArrayList<String>();
    a.add("San Francisco");
    a.add("SF");

    List<String> b = new ArrayList<String>();
    b.add("Paris");
    b.add("pas la province");

		JSONObject syn1 = new JSONObject().put("objectID", "syn1").put("type", "synonym").put("synonyms", a);
		JSONObject syn2 = new JSONObject().put("objectID", "syn2").put("type", "synonym").put("synonyms", b);

    List<JSONObject> c = new ArrayList<JSONObject>();
    c.add(syn1);
    c.add(syn2);
		JSONObject res = index.batchSynonyms(c);
		index.waitTask(res.getString("taskID"));

		res = index.searchSynonyms(new SynonymQuery(""));
		assertEquals(2, res.getInt("nbHits"));
	}
}

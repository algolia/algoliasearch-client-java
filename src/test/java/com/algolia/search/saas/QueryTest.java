package com.algolia.search.saas;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryTest {

    @Test
    public void hitsPerPageEquals20() {
        Query q0 = new Query();
        q0.setHitsPerPage(20);
        String qs = q0.getQueryString();
        assertTrue(qs.contains("hitsPerPage=20"));
    }
}

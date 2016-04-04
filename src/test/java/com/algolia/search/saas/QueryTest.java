package com.algolia.search.saas;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryTest {
    private Query q;

    @Before
    public void setUp() {
        q = new Query();
    }

    @Test
    public void hitsPerPageEquals20() {
        q.setHitsPerPage(20);
        String qs = q.getQueryString();
        assertTrue(qs.contains("hitsPerPage=20"));
    }

    @Test
    public void hasAroundRadius() {
        q.setAroundRadius(42);
        String qs = q.getQueryString();
        assertTrue(qs.contains("aroundRadius=42"));
    }

    @Test
    public void hasAroundPrecision() {
        q.setAroundPrecision(42);
        String qs = q.getQueryString();
        assertTrue(qs.contains("aroundPrecision=42"));
    }

    @Test
    public void hasMinimumAroundRadius() {
        q.setMinimumAroundRadius(42);
        String qs = q.getQueryString();
        assertTrue(qs.contains("minimumAroundRadius=42"));
    }
}

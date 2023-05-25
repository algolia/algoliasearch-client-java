package com.algolia.search;

import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.HttpRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {

    @Test
    void testIncrementTimeout() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "/", Collections.emptyMap(), 1);

        // Test normal case
        httpRequest.incrementTimeout(1);
        assertEquals(2, httpRequest.getTimeout()); // 1*(1+1) = 2

        // Test large value
        httpRequest.incrementTimeout(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, httpRequest.getTimeout()); // Should have caused overflow and been set to MAX_VALUE

        // Test timeout already at max value
        httpRequest.incrementTimeout(1);
        assertEquals(Integer.MAX_VALUE, httpRequest.getTimeout()); // Should not change timeout
    }
}

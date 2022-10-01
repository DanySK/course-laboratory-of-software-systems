package it.unibo.sampleapp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test public void testAppHasAGreeting() {
        assertNotNull(System.getenv("OMDB_API_KEY"));
        assertFalse(System.getenv("OMDB_API_KEY").isBlank());
        RateAMovie.main(new String[] { "Breaking Bad" });
    }
}

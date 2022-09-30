/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.unibo.sampleapp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AppTest {
    @Test public void testAppHasAGreeting() {
        assertNotNull(System.getenv("OMDB_API_KEY"));
        assertFalse(System.getenv("OMDB_API_KEY").isBlank());
        RateAMovie.main(new String[] { "Breaking Bad" });
    }
}

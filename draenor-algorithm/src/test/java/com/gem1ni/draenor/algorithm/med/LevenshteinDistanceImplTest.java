package com.gem1ni.draenor.algorithm.med;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LevenshteinDistanceImplTest {

    private final String from = "kitten";

    private final String to = "sitting";

    @Test
    void distanceBetween() {
        MinimumEditDistance med = new LevenshteinDistanceImpl();
        int distance = med.distanceBetween(this.from, this.to);
        System.err.println("distance: " + distance);
        float similarity = 1.0f - distance / (float) Math.max(this.from.length(), this.to.length());
        System.err.println(String.format(Locale.getDefault(), "similarity: %#.2f%%", similarity * 100));
        assertTrue(0 < distance);
    }
}
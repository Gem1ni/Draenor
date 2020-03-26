package com.gem1ni.draenor.algorithm.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LengthOfLongestSubstringTest {

    private final String str = "ffjdiaiobsc";

    @Test
    void getLength() {
        int length = LengthOfLongestSubstring.getLength(this.str);
        System.err.println("max length: " + length);
        assertTrue(length > 0);
    }
}
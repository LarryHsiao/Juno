package com.larryhsiao.juno.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Learning test for string split.
 */
public class StringSplitTest {
    /**
     * Splits with two parameter
     */
    @Test
    void twoParameters() {
        Assertions.assertEquals(2, "a b".split(" ").length);
    }

    /**
     * Tailing separator which have one parameter.
     */
    @Test
    void tailingSeparator() {
        Assertions.assertEquals(1, "a ".split(" ").length);
    }

    /**
     * Heading separator
     */
    @Test
    void headSeparator() {
        Assertions.assertEquals(2, " a".split(" ").length);
        Assertions.assertEquals("", " a".split(" ")[0]);
        Assertions.assertEquals("a", " a".split(" ")[1]);
    }

    /**
     * Double separator
     */
    @Test
    void doubleSeparator() {
        Assertions.assertEquals(3, "b  a".split(" ").length);
    }
}

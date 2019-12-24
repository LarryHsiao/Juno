package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link TagByName}.
 */
class TagByNameTest {
    /**
     * Check the built Tag have same name as input.
     */
    @Test
    void existResult() {
        assertEquals(
            "tag",
            new TagByName(
                new FakeDataConn(
                    new TagDbConn(
                        new MemoryH2Conn()
                    )
                ),
                "tag"
            ).value().name()
        );
    }

    /**
     * Should create an entry for non-exist tag.
     */
    @Test
    void nonExistResult() {
        assertEquals(
            "tag_non_exist",
            new TagByName(
                new FakeDataConn(
                    new TagDbConn(
                        new MemoryH2Conn()
                    )
                ),
                "tag_non_exist"
            ).value().name()
        );
    }
}
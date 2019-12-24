package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link FilesByKeyword}.
 */
class FilesByKeywordTest {
    /**
     * Check result count.
     */
    @Test
    void simple() {
        assertEquals(
            2,
            new QueriedAFiles(
                new FilesByKeyword(
                    new FakeDataConn(
                        new TagDbConn(new MemoryH2Conn())
                    ),
                    "tag"
                )
            ).value().size()
        );
    }
}
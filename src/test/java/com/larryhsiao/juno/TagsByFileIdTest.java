package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link TagsByFileId}.
 */
class TagsByFileIdTest {

    /**
     * Check the queried result count.
     */
    @Test
    void checkSizeWithFakeData() {
        assertEquals(
            2,
            new QueriedTags(
                new TagsByFileId(
                    new FakeDataConn(
                        new TagDbConn(new MemoryH2Conn())
                    ),
                    1
                )
            ).value().size()
        );
    }
}
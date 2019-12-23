package com.larryhsiao.juno;

import com.silverhetch.clotho.database.sqlite.InMemoryConn;
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
                        new TagDbConn(new InMemoryConn())
                    ),
                    "tag"
                )
            ).value().size()
        );
    }
}
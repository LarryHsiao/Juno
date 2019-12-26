package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit-test for the class {@link FilesByTagId}
 */
class FilesByTagIdTest {
    /**
     * Check output
     */
    @Test
    void simple() {
        Source<Connection> conn = new SingleConn(new FakeDataConn(new TagDbConn(new MemoryH2Conn())));
        assertEquals(
            2,
            new QueriedAFiles(
                new FilesByTagId(conn, 2)
            ).value().size()
        );
    }
}
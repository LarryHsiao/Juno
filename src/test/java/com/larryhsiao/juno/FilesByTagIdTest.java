package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit-test for the class {@link FilesByTagId}
 */
class FilesByTagIdTest {
    /**
     * Check single tag output
     */
    @Test
    void single() {
        Source<Connection> conn =
            new SingleConn(new FakeDataConn(new TagDbConn(new MemoryH2Conn())));
        assertEquals(
            2,
            new QueriedAFiles(
                new FilesByTagId(conn, 2)
            ).value().size()
        );
    }

    /**
     * Check multiple tag output
     */
    @Test
    void multiple() {
        Source<Connection> conn = new SingleConn(
            new FakeDataConn(new TagDbConn(new MemoryH2Conn()))
        );
        new CreatedAFile(conn, "NewFileName").value();
        assertEquals(
            2,
            new QueriedAFiles(
                new FilesByTagId(conn, 1, 2)
            ).value().size()
        );
    }
}
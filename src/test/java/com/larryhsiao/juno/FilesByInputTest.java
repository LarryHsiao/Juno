package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Unit-test for the class {@link FilesByInput}
 */
class FilesByInputTest {

    /**
     * Get files by keyword.
     */
    @Test
    void keyword() {
        final Source<Connection> conn =
            new SingleConn(new FakeDataConn(new TagDbConn(new MemoryH2Conn())));

        Assertions.assertEquals(
            0,
            new QueriedAFiles(
                new FilesByInput(conn, "fileName")
            ).value().size());
    }
}
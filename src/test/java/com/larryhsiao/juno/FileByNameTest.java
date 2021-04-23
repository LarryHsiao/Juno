package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Unit-test for the class {@link FileByName}
 */
class FileByNameTest {

    /**
     * Check the output
     */
    @Test
    void simple() {
        Source<Connection> conn =
            new SingleConn(new FakeDataConn(new TagDbConn(new MemoryH2Conn())));
        Assertions.assertEquals(
            "filename",
            new FileByName(
                conn, "filename"
            ).value().name()
        );
    }

    @Test
    void nonExist() {
        try {
            Source<Connection> conn =
                new SingleConn(
                    new FakeDataConn(
                        new TagDbConn(
                            new MemoryH2Conn()
                        )
                    )
                );
            final AFile file = new FileByName(conn, "NON_EXIST").value();
            Assertions.assertEquals("NON_EXIST", file.name());
            Assertions.assertEquals(3, file.id());
        } catch (Exception e) {
            Assertions.fail("Should create new file if not exist");
        }
    }
}
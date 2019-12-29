package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Test for {@link TagDeletionById}
 */
class TagDeletionByIdTest {

    /**
     * Check delete tag is not exist.
     */
    @Test
    void simple() {
        Source<Connection> conn = new SingleConn(
            new FakeDataConn(new TagDbConn(new MemoryH2Conn()))
        );
        new TagDeletionById(conn, 2).fire();
        Assertions.assertEquals(1,
            new QueriedTags(new AllTags(conn)).value().size()
        );
        Assertions.assertEquals(1,
            new QueriedTags(new TagsByFileId(conn, 1)).value().size()
        );
    }

    /**
     * Check the multi-deletion output
     */
    @Test
    void multipleDeletion() {
        final Source<Connection> conn = new SingleConn(new FakeDataConn(
            new TagDbConn(
                new MemoryH2Conn()
            )
        ));

        new TagDeletionById(conn, 1, 2).fire();

        Assertions.assertEquals(
            0,
            new QueriedTags(new AllTags(conn)).value().size()
        );

        Assertions.assertEquals(
            0,
            new QueriedTags(new FilesByTagId(
                conn, 2
            )).value().size()
        );
    }
}
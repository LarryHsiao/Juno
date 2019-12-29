package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Unit-test for the class {@link TagDeletionByIds}
 */
class TagDeletionByIdsTest {

    /**
     * Check the output
     */
    @Test
    void simple() {
        final Source<Connection> conn = new SingleConn(new FakeDataConn(
            new TagDbConn(
                new MemoryH2Conn()
            )
        ));

        new TagDeletionByIds(conn, 1, 2).fire();

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
package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Unit-test for the class {@link TagMerging}
 */
class TagMergingTest {

    /**
     * Check the output
     */
    @Test
    void simple() {
        final Source<Connection> db = new SingleConn(
            new FakeDataConn(new TagDbConn(new MemoryH2Conn()))
        );
        new TagMerging(db,
            new TagById(db, 1).value(),
            new TagById(db, 2).value()
        ).fire();

        Assertions.assertEquals(1, new QueriedTags(
            new AllTags(db)).value().size()
        );
        Assertions.assertEquals(
            1,
            new QueriedTags(
                new TagsByFileId(db, 1)
            ).value().size()
        );
    }
}
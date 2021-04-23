package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-test for the class {@link DetachAction}
 */
class DetachActionTest {

    /**
     * Check the output
     */
    @Test
    void simple() {
        Source<Connection> db = new SingleConn(
            new FakeDataConn(
                new TagDbConn(new MemoryH2Conn())
            )
        );
        new DetachAction(
            db,
            1,
            new TagByName(db, "tag2").value().id()
        ).fire();

        Assertions.assertEquals(
            1,
            new QueriedTags(
                new TagsByFileId(db, 1)
            ).value().size()
        );
    }
}
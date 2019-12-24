package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import com.silverhetch.clotho.source.ConstSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Test for {@link AttachAction}.
 */
class AttachActionTest {

    /**
     * Check the attach process.
     */
    @Test
    void checkNewTagIsAttached() {
        Source<Connection> db = new SingleConn(
            new FakeDataConn(
                new TagDbConn(new MemoryH2Conn())
            )
        );
        new AttachAction(
            db,
            2,
            new CreatedTag(db, "Random").value().id()
        ).fire();

        Assertions.assertEquals(
            2,
            new QueriedTags(
                new TagsByFileId(db, 2)
            ).value().size()
        );
    }
}
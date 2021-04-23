package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import com.larryhsiao.clotho.source.ConstSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit-test for the class {@link CleanUpFiles}
 */
class CleanUpFilesTest {

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
        // Serve empty file list indicates no file exist in file system.
        new CleanUpFiles(db, new ConstSource<>(new ArrayList<>())).fire();
        assertEquals(0, new QueriedAFiles(new AllFiles(db)).value().size());
        assertEquals(0,
            new QueriedTags(
                new TagsByFileId(db, 1)).value().size()
        );
        assertEquals(0,
            new QueriedTags(
                new TagsByFileId(db, 2)).value().size()
        );
    }
}
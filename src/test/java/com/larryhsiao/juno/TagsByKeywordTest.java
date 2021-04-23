package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link TagsByKeyword}
 */
public class TagsByKeywordTest {
    /**
     * Simple check output wiht fake data
     */
    @Test
    void simple() {
        Source<Connection> conn = new SingleConn(new FakeDataConn(new TagDbConn(new MemoryH2Conn())));
        assertEquals(
            2,
            new QueriedTags(new TagsByKeyword(conn, "tag")).value().size()
        );
    }
}
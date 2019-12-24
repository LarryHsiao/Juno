package com.larryhsiao.juno.h2;

import com.larryhsiao.juno.AllFiles;
import com.larryhsiao.juno.FakeDataConn;
import com.larryhsiao.juno.QueriedAFiles;
import com.larryhsiao.juno.TagDbConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link EmbedH2Conn}
 */
public class EmbedH2ConnTest {
    /**
     * Check if we can connect to the embed database.
     */
    @Test
    public void simple() throws Exception {
        Assertions.assertEquals(
            2,
            new QueriedAFiles(
                new AllFiles(
                    new FakeDataConn(
                        new TagDbConn(
                            new MemoryH2Conn()
                        )
                    )
                )
            ).value().size()
        );
    }
}
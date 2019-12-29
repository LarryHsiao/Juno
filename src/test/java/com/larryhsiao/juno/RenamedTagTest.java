package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Unit-test for the class {@link RenamedTag}
 */
public class RenamedTagTest {

    /**
     * Check the output
     */
    @Test
    public void simple() {
        final String newTagName = "NewTagName";
        final Source<Connection> conn = new SingleConn(new FakeDataConn(
            new TagDbConn(
                new MemoryH2Conn()
            )
        ));
        new RenamedTag(
            conn,
            new TagByName(conn, "tag").value(),
            newTagName
        ).value();

        Assertions.assertEquals(
            newTagName,
            new TagByName(conn, newTagName).value().name()
        );
    }
}
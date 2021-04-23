package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link FilesNotTagged}
 */
class FilesNotTaggedTest {
    /**
     * Check the size.
     */
    @Test
    void entryCount() throws SQLException {
        final Source<Connection> db = new SingleConn(new FakeDataConn(
            new TagDbConn(new MemoryH2Conn())
        ));
        db.value().prepareStatement(
            //language=H2
            "INSERT INTO files(name) VALUES ('temp_file_name');"
        ).execute();
        assertEquals(
            1,
            new QueriedAFiles(
                new FilesNotTagged(
                    db
                )
            ).value().size()
        );
    }
}
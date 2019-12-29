package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit-test for the class {@link FavoriteFiles}
 */
class FavoriteFilesTest {

    /**
     * Check the output
     */
    @Test
    void simple() {
        var db = new SingleConn(
            new FakeDataConn(new TagDbConn(new MemoryH2Conn())));
        new MarkFavorite(db, 1).fire();
        Assertions.assertEquals(
            1,
            new QueriedAFiles(new FavoriteFiles(db)).value().size()
        );
    }
}
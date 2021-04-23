package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.larryhsiao.clotho.database.SingleConn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit-test for the class {@link UnMarkFavorite}
 */
class UnMarkFavoriteTest {

    /**
     * Check the un marked
     */
    @Test
    void simple() {
        final var db = new SingleConn(
            new FakeDataConn(new TagDbConn(new MemoryH2Conn()))
        );
        new MarkFavorite(db, 1).fire();
        new UnMarkFavorite(db, 1).fire();
        Assertions.assertEquals(
            0,
            new QueriedAFiles(new FavoriteFiles(db)).value().size()
        );
    }
}
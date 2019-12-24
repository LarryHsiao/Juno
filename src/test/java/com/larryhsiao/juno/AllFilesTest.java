package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.MemoryH2Conn;
import com.silverhetch.clotho.source.ConstSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link AllFiles}.
 */
public class AllFilesTest {
    /**
     * Check the field exists.
     */
    @Test
    public void fields() throws Exception {
        ResultSet result = new AllFiles(new FakeDataConn(new TagDbConn(new MemoryH2Conn()))).value();
        result.next();
        assertNotEquals(
            "",
            result.getString("name")
        );
    }


    /**
     * Check that it should have a exception if table not constructed.
     */
    @Test
    public void exceptionNoTable() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            new AllFiles(new ConstSource<>(conn)).value();
            fail("Should throw a exception.");
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
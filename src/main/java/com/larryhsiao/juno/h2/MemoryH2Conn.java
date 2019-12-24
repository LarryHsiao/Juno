package com.larryhsiao.juno.h2;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Source to build In-Memory h2 connection.
 */
public class MemoryH2Conn implements Source<Connection> {
    @Override
    public Connection value() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:test-" + UUID.randomUUID().toString().substring(0, 7));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

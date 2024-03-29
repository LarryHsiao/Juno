package com.larryhsiao.juno;

import com.larryhsiao.juno.h2.EmbedH2Conn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.source.ConstSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Db connection for tagging.
 */
public class TagDbConn implements Source<Connection> {
    private final Source<Connection> connSource;

    public TagDbConn(Source<Connection> connSource) {
        this.connSource = connSource;
    }

    public TagDbConn(File root) {
        this.connSource = new EmbedH2Conn(
            new ConstSource<>(
                new File(root, ".auxo.h2")
            )
        );
    }

    @Override
    public Connection value() {
        Connection conn = this.connSource.value();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                // language=H2
                "create table IF NOT EXISTS tags (" +
                    "id integer primary key AUTO_INCREMENT, " +
                    "name varchar not null," +
                    "unique (name)" +
                    ");"
            );
            stmt.execute(
                // language=H2
                "create table IF NOT EXISTS files(" +
                    "id integer primary key AUTO_INCREMENT, " +
                    "name varchar not null," +
                    "unique (name)" +
                    ");"
            );
            stmt.execute(
                // language=H2
                "create table IF NOT EXISTS file_tag(" +
                    "id integer primary key AUTO_INCREMENT," +
                    "file_id integer not null ," +
                    "tag_id integer not null ," +
                    "unique (file_id, tag_id)" +
                    ");"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}

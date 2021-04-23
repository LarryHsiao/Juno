package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to build a {@link Tag} that just create to db by name.
 */
public class CreatedTag implements Source<Tag> {
    private final Source<Connection> conn;
    private final String tagName;

    public CreatedTag(Source<Connection> conn, String tagName) {
        this.conn = conn;
        this.tagName = tagName;
    }

    @Override
    public Tag value() {
        try (PreparedStatement stmt = conn.value().prepareStatement(
            // language=H2
            "INSERT INTO tags(name) VALUES (?);", new String[]{"id"}
        )) {
            stmt.setString(1, tagName);
            stmt.execute();
            final ResultSet idRes = stmt.getGeneratedKeys();
            if (idRes.next()) {
                return new ConstTag(idRes.getLong(1), tagName);
            }
            throw new SQLException("Insert failed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

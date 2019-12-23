package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to build a {@link Tag} by name.
 * Create an entry if not exist.
 */
public class TagByName implements Source<Tag> {
    private final Source<Connection> connSource;
    private final String tagName;

    public TagByName(Source<Connection> connSource, String tagName) {
        this.connSource = connSource;
        this.tagName = tagName;
    }

    @Override
    public Tag value() {
        final Connection conn = connSource.value();
        try (PreparedStatement stmt = conn.prepareStatement(
            // language=SQLite
            "SELECT  * FROM tags WHERE name=?"
        )) {
            stmt.setString(1, tagName);
            final ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return new ConstTag(res.getLong("id"), res.getString("name"));
            } else {
                return new CreatedTag(
                    connSource,
                    tagName
                ).value();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
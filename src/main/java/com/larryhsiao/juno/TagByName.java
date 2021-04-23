package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

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
    private final boolean autoCreate;

    public TagByName(Source<Connection> connSource, String tagName) {
        this(connSource, tagName, true);
    }

    public TagByName(
        Source<Connection> connSource,
        String tagName,
        boolean autoCreate) {
        this.connSource = connSource;
        this.tagName = tagName;
        this.autoCreate = autoCreate;
    }

    @Override
    public Tag value() {
        final Connection conn = connSource.value();
        try (PreparedStatement stmt = conn.prepareStatement(
            // language=H2
            "SELECT  * FROM tags WHERE name=?"
        )) {
            stmt.setString(1, tagName);
            final ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return new ConstTag(res.getLong("id"), res.getString("name"));
            } else {
                if (autoCreate) {
                    return new CreatedTag(
                        connSource,
                        tagName
                    ).value();
                }else {
                    throw new SQLException("Tag not found: "+tagName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

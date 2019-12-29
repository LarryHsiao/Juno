package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to build a {@link Tag} by id.
 * Create an entry if not exist.
 */
public class TagById implements Source<Tag> {
    private final Source<Connection> connSource;
    private final long id;

    public TagById(Source<Connection> connSource, long id) {
        this.connSource = connSource;
        this.id = id;
    }

    @Override
    public Tag value() {
        final Connection conn = connSource.value();
        try (PreparedStatement stmt = conn.prepareStatement(
            // language=H2
            "SELECT  * FROM tags WHERE id=?"
        )) {
            stmt.setLong(1, id);
            final ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return new ConstTag(res.getLong("id"), res.getString("name"));
            } else {
                throw new SQLException("Tag not found, id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
